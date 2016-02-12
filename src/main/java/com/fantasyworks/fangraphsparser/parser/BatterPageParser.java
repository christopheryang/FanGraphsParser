package com.fantasyworks.fangraphsparser.parser;

import static com.google.common.base.Preconditions.checkState;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fantasyworks.fangraphsparser.entity.BatterMinorsSeasonStats;
import com.fantasyworks.fangraphsparser.entity.BatterPostSeasonStats;
import com.fantasyworks.fangraphsparser.entity.BatterRegularSeasonPartialStats;
import com.fantasyworks.fangraphsparser.entity.BatterRegularSeasonProjectedStats;
import com.fantasyworks.fangraphsparser.entity.BatterRegularSeasonStats;
import com.fantasyworks.fangraphsparser.entity.BatterStats;
import com.fantasyworks.fangraphsparser.entity.Player;
import com.fantasyworks.util.ConversionUtil;
import com.fantasyworks.util.FilesUtil;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;

@Service
public class BatterPageParser extends PlayerProfileParser {

	private static final Logger logger = LoggerFactory.getLogger(BatterPageParser.class);
	
	// e.g. 45.9% (91.5)
	private static final Pattern BIS_PITCH_TYPE_STATS_STRING_PATTERN = Pattern.compile("([\\d.%]+) \\(([\\d.]+)\\)");
	
	/**
	 * 
	 * @param fileName
	 * @param player
	 * @return
	 */
	public Collection<BatterStats> parseBatterSeasonStats(String fileName, Player player){
		logger.debug("Parsing batter season stats: "+fileName);
		
		List<BatterStats> statsList = Lists.newArrayList();
		Document doc = Jsoup.parse(FilesUtil.readFileToString(fileName));
		
		// Parse the stats one section at a time. Store the result in class variables
		parseDashboardStats(doc, player, statsList);
		parseStandardStats(doc, player, statsList);
		parseAdvancedStats(doc, player, statsList);
		
		Map<String, BatterStats> filteredSeasonStatsMap = statsList.stream()
				.filter(s->(s instanceof BatterRegularSeasonStats || s instanceof BatterRegularSeasonPartialStats || s instanceof BatterPostSeasonStats))
				.collect(Collectors.toMap(BatterStats::getUid, Function.identity()));
		
		parseBattedBallStats(doc, player, filteredSeasonStatsMap);
		parseMoreBattedBallStats(doc, player, filteredSeasonStatsMap);
		parseWinProbabilityStats(doc, player, filteredSeasonStatsMap);
		parsePitchTypeStats(doc, player, filteredSeasonStatsMap);
		parsePitchfxPitchTypeStats(doc, player, filteredSeasonStatsMap);
		parsePitchfxPitchVelocityStats(doc, player, filteredSeasonStatsMap);
		parsePitchValues(doc, player, filteredSeasonStatsMap);
		parsePitchfxPitchValues(doc, player, filteredSeasonStatsMap);
		parsePitchfxPitchValuesPer100(doc, player, filteredSeasonStatsMap);
		parsePlateDiscipline(doc, player, filteredSeasonStatsMap);
		parsePitchfxPlateDiscipline(doc, player, filteredSeasonStatsMap);
		
		return statsList;
	}
	
	
	/**
	 * Parse the stats in the dashboard section.
	 * @param doc
	 * @param player
	 * @param statsList
	 */
	protected void parseDashboardStats(Document doc, Player player, List<BatterStats> statsList){
		Element dashboardTable = doc.select("table#SeasonStats1_dgSeason11_ctl00").first();
		Elements rows = dashboardTable.select("tr");
		
		// First row is always the header row.
		for(int i=1; i<rows.size(); i++){
			Element row = rows.get(i);
			Elements cols = row.select("td");
			int currCol=0;
			
			// Break out of the loop when we've reached the "Total" row or "Postseason" row
			// Partial season starts with 4 &nbsp; before the actual year
			String seasonStr = cols.get(currCol++).text().replace("\u00a0", "");
			if(Ints.tryParse(seasonStr)==null){
				break;
			}
			
			// Initialize stats
			String rowCssClass = row.attr("class");
			BatterStats stats = createBatterStatsBaseOnRowCssClass(rowCssClass, seasonStr);
			stats.setPlayer(player);
			
			// Season, Team, G, PA, HR, R, RBI, SB, BB%, K%, ISO, BABIP, AVG, OBP, SLG, wOBA, wRC+, BsR, Off, Def, WAR
			stats.setSeason(Integer.parseInt(seasonStr));
			stats.setTeam(cols.get(currCol++).text());
			stats.setGames(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setPa(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setHr(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setRuns(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setRbi(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setSb(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setBbPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setKPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setIso(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setBabip(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setAvg(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setObp(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setSlg(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setWOBA(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setWRCPlus(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setBsr(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 1));
			stats.setOff(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 1));
			stats.setDef(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 1));
			stats.setWar(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 1));
			
			statsList.add(stats);
		}
	}

	/**
	 * Parse the stats in the standard section.
	 * @param doc
	 * @param player
	 * @param statsList
	 */
	protected void parseStandardStats(Document doc, Player player, List<BatterStats> statsList){
		Element standardStatsTable = doc.select("table#SeasonStats1_dgSeason1_ctl00").first();
		Elements rows = standardStatsTable.select("tr");
		
		if(rows.size()-1<statsList.size()){
			throw new RuntimeException("Rows in Standard section: "+(rows.size()-1)+" is fewer than that in Dashboard section: "+statsList.size()+" for player: "+player);
		}
		
		// Always start from the second row. First row is the header row.
		for(int i=1; i<=statsList.size(); i++){
			Element row = rows.get(i);
			Elements cols = row.select("td");
			
			BatterStats stats = statsList.get(i-1);
			int currCol=0;
			
			// Season, Team, G, AB, PA, H, 1B, 2B, 3B, HR, R, RBI, BB, IBB, SO, HBP, SF, SH, GDP, SB, CS, AVG
			currCol++;
			currCol++;
			currCol++;
			stats.setAb(ConversionUtil.toInteger(cols.get(currCol++).text()));
			currCol++; // PA
			stats.setHits(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setSingles(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setDoubles(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setTriples(ConversionUtil.toInteger(cols.get(currCol++).text()));
			currCol++; // HR
			currCol++; // R
			currCol++; // RBI
			stats.setBb(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setIbb(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setSo(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setHbp(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setSf(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setSh(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setGdp(ConversionUtil.toInteger(cols.get(currCol++).text()));
			currCol++; // SB
			stats.setCs(ConversionUtil.toInteger(cols.get(currCol++).text()));
			currCol++; // AVG
		}
	}
	
	
	/**
	 * 
	 * @param doc
	 * @param player
	 * @param statsList
	 */
	protected void parseAdvancedStats(Document doc, Player player, List<BatterStats> statsList){
		Element standardStatsTable = doc.select("table#SeasonStats1_dgSeason2_ctl00").first();
		Elements rows = standardStatsTable.select("tr");
		
		if(rows.size()-1<statsList.size()){
			throw new RuntimeException("Rows in Advanced section: "+(rows.size()-1)+" is fewer than that in Dashboard section: "+statsList.size()+" for player: "+player);
		}
		
		// Always start from the second row. First row is the header row.
		int skipCount=0;
		for(int i=1; i-skipCount<=statsList.size(); i++){
			Element row = rows.get(i);
			Elements cols = row.select("td");
			
			BatterStats stats = statsList.get(i-skipCount-1);
			int currCol=0;
			
			// In the Advanced section, there are the "average" stats rows
			if("Average".equals(cols.get(1).text())){
				skipCount++;
				continue;
			}
			
			// Season, Team, BB%, K%, BB/K, AVG, OBP, SLG, OPS, ISO, Spd, BABIP, UBR, wGDP, wSB, wRC, wRAA, wOBA, wRC+
			currCol = validateStatsRow(cols, currCol, stats, "Advanced stats"); // Season and Team must match
			currCol++; // BB%
			currCol++; // K%
			stats.setBbPerK(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 2));
			currCol++; // AVG
			currCol++; // OBP
			currCol++; // SLG
			stats.setOps(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			currCol++; // ISO
			stats.setSpd(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 1));
			currCol++; // BABIP
			stats.setUbr(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 1));
			stats.setWGdp(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 1));
			stats.setWSb(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 1));
			stats.setWRc(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setWRaa(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 1));
			currCol++; // wOBA
			currCol++; // wRC+
		}
	}
	
	
	/**
	 * 
	 * @param doc
	 * @param player
	 * @param statsList
	 */
	protected void parseBattedBallStats(Document doc, Player player, Map<String, BatterStats> statsMap){
		Element battedBallTable = doc.select("table#SeasonStats1_dgSeason3_ctl00").first();
		Elements rows = battedBallTable.select("tr");
		
		// Always start from the second row. First row is the header row.
		for(int i=1; i<rows.size(); i++){
			Element row = rows.get(i);
			Elements cols = row.select("td");
			BatterStats stats = findBatterStats(row, cols, player, statsMap);
			if(stats==null){break;} // We've reached the Total row
			
			// Season, Team, GB/FB, LD%, GB%, FB%, IFFB%, HR/FB, IFH%, BUH%, Pull%, Cent%, Oppo%, Soft%, Med%, Hard%
			int currCol=2;
			stats.setGbPerFb(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 2));
			stats.setLdPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setGbPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setFbPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setIffbPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setHrPerFb(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setIfhPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setBuhPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setPullPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setCentPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setOppoPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setSoftPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setMedPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setHardPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
		}
		
	}
	
	
	/**
	 * 
	 * @param doc
	 * @param player
	 * @param statsList
	 */
	protected void parseMoreBattedBallStats(Document doc, Player player, Map<String, BatterStats> statsMap){
		Element battedBallTable = doc.select("table#SeasonStats1_dgSeason4_ctl00").first();
		Elements rows = battedBallTable.select("tr");
		
		// Always start from the second row. First row is the header row.
		for(int i=1; i<rows.size(); i++){
			Element row = rows.get(i);
			Elements cols = row.select("td");
			BatterStats stats = findBatterStats(row, cols, player, statsMap);
			if(stats==null){break;} // We've reached the Total row
			
			//Season, Team, GB, FB, LD, IFFB, IFH, BU, BUH, Balls, Strikes, Pitches
			int currCol=2;
			stats.setGb(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setFb(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setLd(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setIffb(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setIfh(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setBu(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setBuh(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setBalls(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setStrikes(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setPitches(ConversionUtil.toInteger(cols.get(currCol++).text()));
		}
	}
	
	
	/**
	 * 
	 * @param doc
	 * @param player
	 * @param statsList
	 */
	protected void parseWinProbabilityStats(Document doc, Player player, Map<String, BatterStats> statsMap){
		Element battedBallTable = doc.select("table#SeasonStats1_dgSeason5_ctl00").first();
		Elements rows = battedBallTable.select("tr");
		
		// Always start from the second row. First row is the header row.
		for(int i=1; i<rows.size(); i++){
			Element row = rows.get(i);
			Elements cols = row.select("td");
			BatterStats stats = findBatterStats(row, cols, player, statsMap);
			if(stats==null){break;} // We've reached the Total row
			
			// Season, Team, WPA, -WPA, +WPA, RE24, REW, pLI, phLI, PH, WPA/LI, Clutch
			int currCol=2;
			stats.setWpa(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 2));
			stats.setWpaMinus(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 2));
			stats.setWpaPlus(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 2));
			stats.setRe24(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 2));
			stats.setRew(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 2));
			stats.setPLi(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 2));
			stats.setPhLi(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 2));
			stats.setPh(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setWpaPerLi(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 2));
			stats.setClutch(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 2));
		}
	}
	
	
	/**
	 * 
	 * @param doc
	 * @param player
	 * @param statsList
	 */
	protected void parsePitchTypeStats(Document doc, Player player, Map<String, BatterStats> statsMap){
		Element battedBallTable = doc.select("table#SeasonStats1_dgSeason6_ctl00").first();
		Elements rows = battedBallTable.select("tr");
		
		// Always start from the second row. First row is the header row.
		for(int i=1; i<rows.size(); i++){
			Element row = rows.get(i);
			Elements cols = row.select("td");
			BatterStats stats = findBatterStats(row, cols, player, statsMap);
			if(stats==null){break;} // We've reached the Total row
			int currCol=2;
			
			String[] parsedStr = parseBisPitchTypeStatsStr(cols.get(currCol++).text());
			if(parsedStr!=null){
				stats.setBisFbPerc(ConversionUtil.toBigDecimal(parsedStr[0], 3));
				stats.setBisFbVelocity(ConversionUtil.toBigDecimal(parsedStr[1], 1));
			}
			parsedStr = parseBisPitchTypeStatsStr(cols.get(currCol++).text());
			if(parsedStr!=null){
				stats.setBisSlPerc(ConversionUtil.toBigDecimal(parsedStr[0], 3));
				stats.setBisSlVelocity(ConversionUtil.toBigDecimal(parsedStr[1], 1));
			}
			parsedStr = parseBisPitchTypeStatsStr(cols.get(currCol++).text());
			if(parsedStr!=null){
				stats.setBisCtPerc(ConversionUtil.toBigDecimal(parsedStr[0], 3));
				stats.setBisCtVelocity(ConversionUtil.toBigDecimal(parsedStr[1], 1));
			}
			parsedStr = parseBisPitchTypeStatsStr(cols.get(currCol++).text());
			if(parsedStr!=null){
				stats.setBisCbPerc(ConversionUtil.toBigDecimal(parsedStr[0], 3));
				stats.setBisCbVelocity(ConversionUtil.toBigDecimal(parsedStr[1], 1));
			}
			parsedStr = parseBisPitchTypeStatsStr(cols.get(currCol++).text());
			if(parsedStr!=null){
				stats.setBisChPerc(ConversionUtil.toBigDecimal(parsedStr[0], 3));
				stats.setBisChVelocity(ConversionUtil.toBigDecimal(parsedStr[1], 1));
			}
			parsedStr = parseBisPitchTypeStatsStr(cols.get(currCol++).text());
			if(parsedStr!=null){
				stats.setBisSfPerc(ConversionUtil.toBigDecimal(parsedStr[0], 3));
				stats.setBisSfVelocity(ConversionUtil.toBigDecimal(parsedStr[1], 1));
			}
			parsedStr = parseBisPitchTypeStatsStr(cols.get(currCol++).text());
			if(parsedStr!=null){
				stats.setBisKnPerc(ConversionUtil.toBigDecimal(parsedStr[0], 3));
				stats.setBisKnVelocity(ConversionUtil.toBigDecimal(parsedStr[1], 1));
			}
			stats.setBisXxPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
		}
	}
	
	/**
	 * Parse Baseball Info Solutions' pitch type data representation
	 * @param str
	 * @return
	 */
	protected String[] parseBisPitchTypeStatsStr(String str){
		if(ConversionUtil.isEmpty(str)){
			return null;
		}
		Matcher matcher = BIS_PITCH_TYPE_STATS_STRING_PATTERN.matcher(str);
		if(matcher.find()){
			return new String[]{matcher.group(1), matcher.group(2)};
		}
		else{
			throw new RuntimeException("Unable to parse pitch type stats expecting xx.x% (xx.x), instead got: "+str);
		}
	}
	
	
	/**
	 * 
	 * @param doc
	 * @param player
	 * @param statsList
	 */
	protected void parsePitchfxPitchTypeStats(Document doc, Player player, Map<String, BatterStats> statsMap){
		Element battedBallTable = doc.select("table#SeasonStats1_dgSeason16_ctl00").first();
		Elements rows = battedBallTable.select("tr");
		
		// Always start from the second row. First row is the header row.
		for(int i=1; i<rows.size(); i++){
			Element row = rows.get(i);
			Elements cols = row.select("td");
			BatterStats stats = findBatterStats(row, cols, player, statsMap);
			if(stats==null){break;} // We've reached the Total row
			int currCol=2;
			
			stats.setPfxFaPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setPfxFtPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setPfxFcPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setPfxFsPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setPfxFoPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setPfxSiPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setPfxSlPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setPfxCuPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setPfxKcPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setPfxEpPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setPfxChPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setPfxScPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setPfxKnPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setPfxUnPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
		}
	}
	
	
	/**
	 * 
	 * @param doc
	 * @param player
	 * @param statsList
	 */
	protected void parsePitchfxPitchVelocityStats(Document doc, Player player, Map<String, BatterStats> statsMap){
		Element battedBallTable = doc.select("table#SeasonStats1_dgSeason17_ctl00").first();
		Elements rows = battedBallTable.select("tr");
		
		// Always start from the second row. First row is the header row.
		for(int i=1; i<rows.size(); i++){
			Element row = rows.get(i);
			Elements cols = row.select("td");
			BatterStats stats = findBatterStats(row, cols, player, statsMap);
			if(stats==null){break;} // We've reached the Total row
			int currCol=2;
			
			stats.setPfxFaVelocity(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 1));
			stats.setPfxFtVelocity(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 1));
			stats.setPfxFcVelocity(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 1));
			stats.setPfxFsVelocity(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 1));
			stats.setPfxFoVelocity(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 1));
			stats.setPfxSiVelocity(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 1));
			stats.setPfxSlVelocity(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 1));
			stats.setPfxCuVelocity(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 1));
			stats.setPfxKcVelocity(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 1));
			stats.setPfxEpVelocity(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 1));
			stats.setPfxChVelocity(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 1));
			stats.setPfxScVelocity(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 1));
			stats.setPfxKnVelocity(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 1));
		}
	}
	
	

	/**
	 * 
	 * @param doc
	 * @param player
	 * @param statsList
	 */
	protected void parsePitchValues(Document doc, Player player, Map<String, BatterStats> statsMap){
		Element battedBallTable = doc.select("table#SeasonStats1_dgSeason10_ctl00").first();
		Elements rows = battedBallTable.select("tr");
		
		// Always start from the second row. First row is the header row.
		for(int i=1; i<rows.size(); i++){
			Element row = rows.get(i);
			Elements cols = row.select("td");
			BatterStats stats = findBatterStats(row, cols, player, statsMap);
			if(stats==null){break;} // We've reached the Total row
			int currCol=2;
			
			stats.setBisWFb(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 1));
			stats.setBisWSl(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 1));
			stats.setBisWCt(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 1));
			stats.setBisWCb(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 1));
			stats.setBisWCh(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 1));
			stats.setBisWSf(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 1));
			stats.setBisWKn(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 1));
			
			stats.setBisWFbPer100(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 2));
			stats.setBisWSlPer100(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 2));
			stats.setBisWCtPer100(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 2));
			stats.setBisWCbPer100(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 2));
			stats.setBisWChPer100(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 2));
			stats.setBisWSfPer100(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 2));
			stats.setBisWKnPer100(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 2));
		}
	}
	
	
	/**
	 * 
	 * @param doc
	 * @param player
	 * @param statsList
	 */
	protected void parsePitchfxPitchValues(Document doc, Player player, Map<String, BatterStats> statsMap){
		Element battedBallTable = doc.select("table#SeasonStats1_dgSeason19_ctl00").first();
		Elements rows = battedBallTable.select("tr");
		
		// Always start from the second row. First row is the header row.
		for(int i=1; i<rows.size(); i++){
			Element row = rows.get(i);
			Elements cols = row.select("td");
			BatterStats stats = findBatterStats(row, cols, player, statsMap);
			if(stats==null){break;} // We've reached the Total row
			int currCol=2;
			
			stats.setPfxWFa(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 1));
			stats.setPfxWFt(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 1));
			stats.setPfxWFc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 1));
			stats.setPfxWFs(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 1));
			stats.setPfxWFo(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 1));
			stats.setPfxWSi(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 1));
			stats.setPfxWSl(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 1));
			stats.setPfxWCu(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 1));
			stats.setPfxWKc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 1));
			stats.setPfxWEp(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 1));
			stats.setPfxWCh(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 1));
			stats.setPfxWSc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 1));
			stats.setPfxWKn(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 1));
		}
	}
	
	
	/**
	 * 
	 * @param doc
	 * @param player
	 * @param statsList
	 */
	protected void parsePitchfxPitchValuesPer100(Document doc, Player player, Map<String, BatterStats> statsMap){
		Element battedBallTable = doc.select("table#SeasonStats1_dgSeason20_ctl00").first();
		Elements rows = battedBallTable.select("tr");
		
		// Always start from the second row. First row is the header row.
		for(int i=1; i<rows.size(); i++){
			Element row = rows.get(i);
			Elements cols = row.select("td");
			BatterStats stats = findBatterStats(row, cols, player, statsMap);
			if(stats==null){break;} // We've reached the Total row
			int currCol=2;
			
			stats.setPfxWFaPer100(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 2));
			stats.setPfxWFtPer100(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 2));
			stats.setPfxWFcPer100(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 2));
			stats.setPfxWFsPer100(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 2));
			stats.setPfxWFoPer100(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 2));
			stats.setPfxWSiPer100(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 2));
			stats.setPfxWSlPer100(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 2));
			stats.setPfxWCuPer100(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 2));
			stats.setPfxWKcPer100(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 2));
			stats.setPfxWEpPer100(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 2));
			stats.setPfxWChPer100(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 2));
			stats.setPfxWScPer100(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 2));
			stats.setPfxWKnPer100(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 2));
		}
	}
	
	
	/**
	 * 
	 * @param doc
	 * @param player
	 * @param statsList
	 */
	protected void parsePlateDiscipline(Document doc, Player player, Map<String, BatterStats> statsMap){
		Element battedBallTable = doc.select("table#SeasonStats1_dgSeason7_ctl00").first();
		Elements rows = battedBallTable.select("tr");
		
		// Always start from the second row. First row is the header row.
		for(int i=1; i<rows.size(); i++){
			Element row = rows.get(i);
			Elements cols = row.select("td");
			
			// Skip over the league average rows
			String team = cols.get(1).text();
			if("Average".equals(team)){
				continue;
			}
			
			BatterStats stats = findBatterStats(row, cols, player, statsMap);
			if(stats==null){break;} // We've reached the Total row
			int currCol=2;
			
			stats.setBisOSwingPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setBisZSwingPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setBisSwingPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setBisOContactPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setBisZContactPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setBisContactPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setBisZonePerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setBisFStrikePerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setBisSwStrPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
		}
	}
	
	
	/**
	 * 
	 * @param doc
	 * @param player
	 * @param statsList
	 */
	protected void parsePitchfxPlateDiscipline(Document doc, Player player, Map<String, BatterStats> statsMap){
		Element battedBallTable = doc.select("table#SeasonStats1_dgSeason18_ctl00").first();
		Elements rows = battedBallTable.select("tr");
		
		// Always start from the second row. First row is the header row.
		for(int i=1; i<rows.size(); i++){
			Element row = rows.get(i);
			Elements cols = row.select("td");

			// Skip over the league average rows
			String team = cols.get(1).text();
			if("Average".equals(team)){
				continue;
			}
			
			BatterStats stats = findBatterStats(row, cols, player, statsMap);
			if(stats==null){break;} // We've reached the Total row
			int currCol=2;
			
			stats.setPfxOSwingPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setPfxZSwingPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setPfxSwingPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setPfxOContactPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setPfxZContactPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setPfxContactPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setPfxZonePerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setPfxPace(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 1));
		}
	}
	
	/**
	 * 
	 * @param doc
	 * @param player
	 * @param statsMap
	 */
	protected void parseFielding(Document doc, Player player, Map<String, BatterStats> statsMap){
		Element battedBallTable = doc.select("SeasonStats1_dgSeason8_ctl00").first();
		Elements rows = battedBallTable.select("tr");
		
		// Always start from the second row. First row is the header row.
		for(int i=1; i<rows.size(); i++){
			Element row = rows.get(i);
			Elements cols = row.select("td");

			// Skip over the total rows
			String season = cols.get(0).text();
			if("Total".equals(season)){
				continue;
			}
			
			BatterStats stats = findBatterStats(row, cols, player, statsMap);
			
			// Season, Team, Pos, G, GS, Inn, PO, A, E, FE, TE, DP, DPS, DPT, DPF, Scp, SB, CS, PB, WP, FP, TZ
			int currCol=2;
			
			
		}
	}
	
	

	/**
	 * 
	 * @param cols
	 * @param currCol
	 * @param stats
	 * @param sectionName
	 * @return
	 */
	protected int validateStatsRow(Elements cols, int currCol, BatterStats stats, String sectionName){
		String seasonStr = cols.get(currCol++).text().replace("\u00a0", "");
		Integer season = Integer.parseInt(seasonStr);
		String team = cols.get(currCol++).text();
		checkState(stats.getSeason()-season==0 && stats.getTeam().equals(team), "Season and/or team mismatch in: "+sectionName+" Actual season: "+season+", team: "+team+", expected stats: "+stats);
		return currCol;
	}
	
	/**
	 * 
	 * @param rowCssClass
	 * @param seasonStr
	 * @return
	 */
	protected BatterStats createBatterStatsBaseOnRowCssClass(String rowCssClass, String seasonStr){
		BatterStats stats;
		if("rgRow grid_postseason".equals(rowCssClass)){
			stats = new BatterPostSeasonStats();
		}
		else if("rgRow grid_minors_show".equals(rowCssClass)){
			stats = new BatterMinorsSeasonStats();
		}
		else if("rgRow grid_multi".equals(rowCssClass)){
			stats = new BatterRegularSeasonPartialStats();
		}
		else if("2016".equals(seasonStr)){
			stats = new BatterRegularSeasonProjectedStats();
		}
		else{ // class name: "rgRow"
			stats = new BatterRegularSeasonStats();
		}
		return stats;
	}

	/**
	 * 
	 * @param row
	 * @param cols
	 * @param player
	 * @param statsMap
	 * @return
	 */
	protected BatterStats findBatterStats(Element row, Elements cols, Player player, Map<String, BatterStats> statsMap){
		String rowCssClass = row.attr("class");
		
		int currCol=0;
		
		// Season, Team, GB/FB, LD%, GB%, FB%, IFFB%, HR/FB, IFH%, BUH%, Pull%, Cent%, Oppo%, Soft%, Med%, Hard%, SIERA, xFIP-, xFIP
		String seasonStr = cols.get(currCol++).text().replace("\u00a0", "");
		if(Ints.tryParse(seasonStr)==null){
			return null;
		}
		Integer season = Integer.parseInt(seasonStr);
		String team = cols.get(currCol++).text();
		
		BatterStats tempStats = createBatterStatsBaseOnRowCssClass(rowCssClass, seasonStr);
		tempStats.setPlayer(player);
		tempStats.setSeason(season);
		tempStats.setTeam(team);
		
		// Some stats in batted ball section may be missing, but for whatever is there, it must also exist in the dashboard section.
		BatterStats stats = statsMap.get(tempStats.getUid());
		if(stats == null){
			throw new RuntimeException("Cannot find stats: "+tempStats+", from stats map: "+statsMap);
		}
		
		return stats;
	}

}
