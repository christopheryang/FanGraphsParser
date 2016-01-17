package com.fantasyworks.fangraphsparser.parser;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fantasyworks.fangraphsparser.entity.PitcherSeasonStats;
import com.fantasyworks.fangraphsparser.entity.Player;
import com.fantasyworks.util.ConversionUtil;
import com.fantasyworks.util.FilesUtil;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;

@Service
public class PitcherPageParser extends PlayerProfileParser {

	private static final Logger logger = LoggerFactory.getLogger(PitcherPageParser.class);
	

	public List<PitcherSeasonStats> parsePitcherSeasonStats(String fileName, Player player){
		logger.debug("Parsing pitcher season stats: "+fileName);
		List<PitcherSeasonStats> statsList = Lists.newArrayList();
		
		Document doc = Jsoup.parse(FilesUtil.readFileToString(fileName));
		Element dashboardTable = doc.select("table#SeasonStats1_dgSeason11_ctl00").first();
		Elements rows = dashboardTable.select("tr");
		
		// First row is always the header row. Terminate when we see the aggregated total row.
		for(int i=1; i<rows.size(); i++){
			Element row = rows.get(i);
			PitcherSeasonStats stats = new PitcherSeasonStats();
			stats.setPlayer(player);
			Elements cols = row.select("td");
			int currCol=0;
			
			// Break out of the loop when we've reached the "Total" row or "Postseason" row
			if(Ints.tryParse(cols.get(0).text())==null){
				break;
			}
			
			// Season, Team, W, L, Sv, G, Gs, IP, K/9, BB/9, HR/9, BABIP, LOB%, GB%, HR/FB, ERA, FIP, xFIP, WAR
			stats.setSeason(Integer.parseInt(cols.get(currCol++).text()));
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
		
		return statsList;
	}
}
