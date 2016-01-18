package com.fantasyworks.fangraphsparser.parser;

import java.util.Collection;
import java.util.List;

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
		
//		return rowIdToStatsMap.values();
		return statsList;
	}
	
	/**
	 * Parse the stats in the dashboard section.
	 * 
	 * @param doc
	 * @param player
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
			PitcherStats stats;
			String statsClass = row.attr("class");
			if("rgRow grid_postseason".equals(statsClass)){
				stats = new PitcherPostSeasonStats();
			}
			else if("rgRow grid_minors_show".equals(statsClass)){
				stats = new PitcherMinorsSeasonStats();
			}
			else if("rgRow grid_multi".equals(statsClass)){
				stats = new PitcherRegularSeasonPartialStats();
			}
			else if("2016".equals(seasonStr)){
				stats = new PitcherRegularSeasonProjectedStats();
			}
			else{ // class name: "rgRow"
				stats = new PitcherRegularSeasonStats();
			}
			
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
	 * 
	 * @param doc
	 */
	protected void parseStandardStats(Document doc, Player player, List<PitcherStats> statsList){
		Element standardStatsTable = doc.select("table#SeasonStats1_dgSeason1_ctl00").first();
		Elements rows = standardStatsTable.select("tr");
		
		if(rows.size()-1<statsList.size()){
			throw new RuntimeException("Rows in standard section: "+(rows.size()-1)+" is fewer than that in dashboard section: "+statsList.size()+" for player: "+player);
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
	

}
