package com.fantasyworks.fangraphsparser.parser;

import static com.google.common.base.Preconditions.checkState;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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
import com.fantasyworks.fangraphsparser.entity.PitcherStats;
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
		
//		parseBattedBallStats(doc, player, filteredSeasonStatsMap);
//		parseMoreBattedBallStats(doc, player, filteredSeasonStatsMap);
//		parseWinProbabilityStats(doc, player, filteredSeasonStatsMap);
//		parsePitchTypeStats(doc, player, filteredSeasonStatsMap);
//		parsePitchfxPitchTypeStats(doc, player, filteredSeasonStatsMap);
//		parsePitchfxPitchVelocityStats(doc, player, filteredSeasonStatsMap);
//		parsePitchValues(doc, player, filteredSeasonStatsMap);
//		parsePitchfxPitchValues(doc, player, filteredSeasonStatsMap);
//		parsePitchfxPitchValuesPer100(doc, player, filteredSeasonStatsMap);
//		parsePlateDiscipline(doc, player, filteredSeasonStatsMap);
//		parsePitchfxPlateDiscipline(doc, player, filteredSeasonStatsMap);
		
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

}
