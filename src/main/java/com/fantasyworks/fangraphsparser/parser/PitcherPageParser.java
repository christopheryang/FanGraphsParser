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

import com.fantasyworks.fangraphsparser.entity.PitcherMinorsSeasonStats;
import com.fantasyworks.fangraphsparser.entity.PitcherPostSeasonStats;
import com.fantasyworks.fangraphsparser.entity.PitcherRegularSeasonPartialStats;
import com.fantasyworks.fangraphsparser.entity.PitcherRegularSeasonProjectedStats;
import com.fantasyworks.fangraphsparser.entity.PitcherRegularSeasonStats;
import com.fantasyworks.fangraphsparser.entity.PitcherStats;
import com.fantasyworks.fangraphsparser.entity.Player;
import com.fantasyworks.util.ConversionUtil;
import com.fantasyworks.util.FilesUtil;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;

@Service
public class PitcherPageParser extends PlayerProfileParser {

	private static final Logger logger = LoggerFactory.getLogger(PitcherPageParser.class);
	
	private static final Pattern BIS_PITCH_TYPE_STATS_STRING_PATTERN = Pattern.compile("([\\d.%]+) \\(([\\d.]+)\\)");
	
	/**
	 * 
	 * @param fileName
	 * @param player
	 * @return
	 */
	public Collection<PitcherStats> parsePitcherSeasonStats(String fileName, Player player){
		logger.debug("Parsing pitcher season stats: "+fileName);
		
		List<PitcherStats> statsList = Lists.newArrayList();
		Document doc = Jsoup.parse(FilesUtil.readFileToString(fileName));
		
		// Parse the stats one section at a time. Store the result in class variables
		parseDashboardStats(doc, player, statsList);
		parseStandardStats(doc, player, statsList);
		parseAdvancedStats(doc, player, statsList);
		
		Map<String, PitcherStats> filteredSeasonStatsMap = statsList.stream()
				.filter(s->(s instanceof PitcherRegularSeasonStats || s instanceof PitcherRegularSeasonPartialStats || s instanceof PitcherPostSeasonStats))
				.collect(Collectors.toMap(PitcherStats::getUid, Function.identity()));
		
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
	protected void parseDashboardStats(Document doc, Player player, List<PitcherStats> statsList){
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
			PitcherStats stats = createPitcherStatsBaseOnRowCssClass(rowCssClass, seasonStr);
			stats.setPlayer(player);
			
			// Season, Team, W, L, Sv, G, Gs, IP, K/9, BB/9, HR/9, BABIP, LOB%, GB%, HR/FB, ERA, FIP, xFIP, WAR
			stats.setSeason(Integer.parseInt(seasonStr));
			stats.setTeam(cols.get(currCol++).text());
			stats.setWin(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setLoss(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setSave(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setGames(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setGs(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setIp(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 1));
			stats.setkPer9(ConversionUtil.toBigDecimal(cols.get(currCol++).text()));
			stats.setBbPer9(ConversionUtil.toBigDecimal(cols.get(currCol++).text()));
			stats.setHrPer9(ConversionUtil.toBigDecimal(cols.get(currCol++).text()));
			stats.setBabip(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setLobPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setGbPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setHrPerFb(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setEra(ConversionUtil.toBigDecimal(cols.get(currCol++).text()));
			stats.setFip(ConversionUtil.toBigDecimal(cols.get(currCol++).text()));
			stats.setxFip(ConversionUtil.toBigDecimal(cols.get(currCol++).text()));
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
	protected void parseStandardStats(Document doc, Player player, List<PitcherStats> statsList){
		Element standardStatsTable = doc.select("table#SeasonStats1_dgSeason1_ctl00").first();
		Elements rows = standardStatsTable.select("tr");
		
		if(rows.size()-1<statsList.size()){
			throw new RuntimeException("Rows in Standard section: "+(rows.size()-1)+" is fewer than that in Dashboard section: "+statsList.size()+" for player: "+player);
		}
		
		// Always start from the second row. First row is the header row.
		for(int i=1; i<=statsList.size(); i++){
			Element row = rows.get(i);
			Elements cols = row.select("td");
			
			PitcherStats stats = statsList.get(i-1);
			int currCol=0;
			
			// Season, Team, W, L, ERA, G, GS, CG, ShO, SV, HLD, BS, IP, TBF, H, R, ER, HR, BB, IBB, HBP, WP, BK, SO
			currCol++;
			currCol++;
			currCol++;
			currCol++;
			currCol++;
			currCol++;
			currCol++;
			stats.setCg(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setSho(ConversionUtil.toInteger(cols.get(currCol++).text()));
			currCol++; // saves
			stats.setHld(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setBs(ConversionUtil.toInteger(cols.get(currCol++).text()));
			currCol++; // IP
			stats.setTbf(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setHits(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setRuns(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setEr(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setHr(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setBb(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setIbb(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setHbp(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setWp(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setBk(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setSo(ConversionUtil.toInteger(cols.get(currCol++).text()));
		}
	}
	
	/**
	 * 
	 * @param doc
	 * @param player
	 * @param statsList
	 */
	protected void parseAdvancedStats(Document doc, Player player, List<PitcherStats> statsList){
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
			
			PitcherStats stats = statsList.get(i-skipCount-1);
			int currCol=0;
			
			// In the Advanced section, there are the "average" stats rows
			if("Average".equals(cols.get(1).text())){
				skipCount++;
				continue;
			}
			
			// Season, Team, K/9, BB/9, K/BB, HR/9, K%, BB%, K-BB%, AVG, WHIP, BABIP, LOB%, ERA-, FIP-, FIP
			currCol = validateStatsRow(cols, currCol, stats, "Advanced stats"); // Season and Team must match
			currCol++;
			currCol++;
			currCol++;
			currCol++;
			stats.setkPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setBbPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			currCol++; // K-BB%
			stats.setAvg(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setWhip(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 2));
			currCol++; // BABIP
			currCol++; // LOB%
			stats.setEraMinus(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setFipMinus(ConversionUtil.toInteger(cols.get(currCol++).text()));
			currCol++; // FIP
		}
	}
	
	/**
	 * 
	 * @param doc
	 * @param player
	 * @param statsList
	 */
	protected void parseBattedBallStats(Document doc, Player player, Map<String, PitcherStats> statsMap){
		Element battedBallTable = doc.select("table#SeasonStats1_dgSeason3_ctl00").first();
		Elements rows = battedBallTable.select("tr");
		
		// Always start from the second row. First row is the header row.
		for(int i=1; i<=rows.size(); i++){
			Element row = rows.get(i);
			Elements cols = row.select("td");
			PitcherStats stats = findPitcherStats(row, cols, player, statsMap);
			if(stats==null){break;} // We've reached the Total row
			int currCol=2;
			
			stats.setGbPerFb(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 2));
			stats.setLdPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			currCol++; // GB %
			stats.setFbPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setIffbPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			currCol++; // HR/FB
			stats.setIfhPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setBuhPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setPullPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setCentPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setOppoPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setSoftPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setMedPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setHardPerc(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 3));
			stats.setSiera(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 2));
			stats.setxFipMinus(ConversionUtil.toInteger(cols.get(currCol++).text()));
			currCol++; // xFIP
		}
		
	}
	
	
	/**
	 * 
	 * @param doc
	 * @param player
	 * @param statsList
	 */
	protected void parseMoreBattedBallStats(Document doc, Player player, Map<String, PitcherStats> statsMap){
		Element battedBallTable = doc.select("table#SeasonStats1_dgSeason4_ctl00").first();
		Elements rows = battedBallTable.select("tr");
		
		// Always start from the second row. First row is the header row.
		for(int i=1; i<=rows.size(); i++){
			Element row = rows.get(i);
			Elements cols = row.select("td");
			PitcherStats stats = findPitcherStats(row, cols, player, statsMap);
			if(stats==null){break;} // We've reached the Total row
			int currCol=2;
			
			stats.setGb(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setFb(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setLd(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setIffb(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setIfh(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setBu(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setBuh(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setRs(ConversionUtil.toInteger(cols.get(currCol++).text()));
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
	protected void parseWinProbabilityStats(Document doc, Player player, Map<String, PitcherStats> statsMap){
		Element battedBallTable = doc.select("table#SeasonStats1_dgSeason5_ctl00").first();
		Elements rows = battedBallTable.select("tr");
		
		// Always start from the second row. First row is the header row.
		for(int i=1; i<=rows.size(); i++){
			Element row = rows.get(i);
			Elements cols = row.select("td");
			PitcherStats stats = findPitcherStats(row, cols, player, statsMap);
			if(stats==null){break;} // We've reached the Total row
			int currCol=2;
			
			stats.setWpa(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 2));
			stats.setWpaMinus(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 2));
			stats.setWpaPlus(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 2));
			stats.setRe24(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 2));
			stats.setRew(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 2));
			stats.setPLi(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 2));
			stats.setInLi(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 2));
			stats.setGmLi(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 2));
			stats.setExLi(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 2));
			stats.setPulls(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setWpaPerLi(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 2));
			stats.setClutch(ConversionUtil.toBigDecimal(cols.get(currCol++).text(), 2));
			stats.setSd(ConversionUtil.toInteger(cols.get(currCol++).text()));
			stats.setMd(ConversionUtil.toInteger(cols.get(currCol++).text()));
		}
	}
	
	
	/**
	 * 
	 * @param doc
	 * @param player
	 * @param statsList
	 */
	protected void parsePitchTypeStats(Document doc, Player player, Map<String, PitcherStats> statsMap){
		Element battedBallTable = doc.select("table#SeasonStats1_dgSeason6_ctl00").first();
		Elements rows = battedBallTable.select("tr");
		
		// Always start from the second row. First row is the header row.
		for(int i=1; i<=rows.size(); i++){
			Element row = rows.get(i);
			Elements cols = row.select("td");
			PitcherStats stats = findPitcherStats(row, cols, player, statsMap);
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
	protected void parsePitchfxPitchTypeStats(Document doc, Player player, Map<String, PitcherStats> statsMap){
		Element battedBallTable = doc.select("table#SeasonStats1_dgSeason16_ctl00").first();
		Elements rows = battedBallTable.select("tr");
		
		// Always start from the second row. First row is the header row.
		for(int i=1; i<=rows.size(); i++){
			Element row = rows.get(i);
			Elements cols = row.select("td");
			PitcherStats stats = findPitcherStats(row, cols, player, statsMap);
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
	protected void parsePitchfxPitchVelocityStats(Document doc, Player player, Map<String, PitcherStats> statsMap){
		Element battedBallTable = doc.select("table#SeasonStats1_dgSeason17_ctl00").first();
		Elements rows = battedBallTable.select("tr");
		
		// Always start from the second row. First row is the header row.
		for(int i=1; i<=rows.size(); i++){
			Element row = rows.get(i);
			Elements cols = row.select("td");
			PitcherStats stats = findPitcherStats(row, cols, player, statsMap);
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
	protected void parsePitchValues(Document doc, Player player, Map<String, PitcherStats> statsMap){
		Element battedBallTable = doc.select("table#SeasonStats1_dgSeason10_ctl00").first();
		Elements rows = battedBallTable.select("tr");
		
		// Always start from the second row. First row is the header row.
		for(int i=1; i<=rows.size(); i++){
			Element row = rows.get(i);
			Elements cols = row.select("td");
			PitcherStats stats = findPitcherStats(row, cols, player, statsMap);
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
	protected void parsePitchfxPitchValues(Document doc, Player player, Map<String, PitcherStats> statsMap){
		Element battedBallTable = doc.select("table#SeasonStats1_dgSeason19_ctl00").first();
		Elements rows = battedBallTable.select("tr");
		
		// Always start from the second row. First row is the header row.
		for(int i=1; i<=rows.size(); i++){
			Element row = rows.get(i);
			Elements cols = row.select("td");
			PitcherStats stats = findPitcherStats(row, cols, player, statsMap);
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
	protected void parsePitchfxPitchValuesPer100(Document doc, Player player, Map<String, PitcherStats> statsMap){
		Element battedBallTable = doc.select("table#SeasonStats1_dgSeason20_ctl00").first();
		Elements rows = battedBallTable.select("tr");
		
		// Always start from the second row. First row is the header row.
		for(int i=1; i<=rows.size(); i++){
			Element row = rows.get(i);
			Elements cols = row.select("td");
			PitcherStats stats = findPitcherStats(row, cols, player, statsMap);
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
	protected void parsePlateDiscipline(Document doc, Player player, Map<String, PitcherStats> statsMap){
		Element battedBallTable = doc.select("table#SeasonStats1_dgSeason7_ctl00").first();
		Elements rows = battedBallTable.select("tr");
		
		// Always start from the second row. First row is the header row.
		for(int i=1; i<=rows.size(); i++){
			Element row = rows.get(i);
			Elements cols = row.select("td");
			
			// Skip over the league average rows
			String team = cols.get(1).text();
			if("Average".equals(team)){
				continue;
			}
			
			PitcherStats stats = findPitcherStats(row, cols, player, statsMap);
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
	protected void parsePitchfxPlateDiscipline(Document doc, Player player, Map<String, PitcherStats> statsMap){
		Element battedBallTable = doc.select("table#SeasonStats1_dgSeason18_ctl00").first();
		Elements rows = battedBallTable.select("tr");
		
		// Always start from the second row. First row is the header row.
		for(int i=1; i<=rows.size(); i++){
			Element row = rows.get(i);
			Elements cols = row.select("td");

			// Skip over the league average rows
			String team = cols.get(1).text();
			if("Average".equals(team)){
				continue;
			}
			
			PitcherStats stats = findPitcherStats(row, cols, player, statsMap);
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
	 * @param cols
	 * @param currCol
	 * @param stats
	 * @param sectionName
	 * @return
	 */
	protected int validateStatsRow(Elements cols, int currCol, PitcherStats stats, String sectionName){
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
	protected PitcherStats createPitcherStatsBaseOnRowCssClass(String rowCssClass, String seasonStr){
		PitcherStats stats;
		if("rgRow grid_postseason".equals(rowCssClass)){
			stats = new PitcherPostSeasonStats();
		}
		else if("rgRow grid_minors_show".equals(rowCssClass)){
			stats = new PitcherMinorsSeasonStats();
		}
		else if("rgRow grid_multi".equals(rowCssClass)){
			stats = new PitcherRegularSeasonPartialStats();
		}
		else if("2016".equals(seasonStr)){
			stats = new PitcherRegularSeasonProjectedStats();
		}
		else{ // class name: "rgRow"
			stats = new PitcherRegularSeasonStats();
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
	protected PitcherStats findPitcherStats(Element row, Elements cols, Player player, Map<String, PitcherStats> statsMap){
		String rowCssClass = row.attr("class");
		
		int currCol=0;
		
		// Season, Team, GB/FB, LD%, GB%, FB%, IFFB%, HR/FB, IFH%, BUH%, Pull%, Cent%, Oppo%, Soft%, Med%, Hard%, SIERA, xFIP-, xFIP
		String seasonStr = cols.get(currCol++).text().replace("\u00a0", "");
		if(Ints.tryParse(seasonStr)==null){
			return null;
		}
		Integer season = Integer.parseInt(seasonStr);
		String team = cols.get(currCol++).text();
		
		PitcherStats tempStats = createPitcherStatsBaseOnRowCssClass(rowCssClass, seasonStr);
		tempStats.setPlayer(player);
		tempStats.setSeason(season);
		tempStats.setTeam(team);
		
		// Some stats in batted ball section may be missing, but for whatever is there, it must also exist in the dashboard section.
		PitcherStats stats = statsMap.get(tempStats.getUid());
		if(stats == null){
			throw new RuntimeException("Cannot find stats: "+tempStats+", from stats map: "+statsMap);
		}
		
		return stats;
	}
}
