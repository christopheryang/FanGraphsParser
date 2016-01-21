package com.fantasyworks.fangraphsparser.crawler;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fantasyworks.util.DownloadUtil;
import com.fantasyworks.util.FilesUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

@Service
public class PlayerPagesCrawler {
	
	private static final Logger logger = LoggerFactory.getLogger(PlayerPagesCrawler.class);
	
	public static final String PLAYER_INDEX_PAGE="http://www.fangraphs.com/players.aspx";
	public static final String PLAYER_INDEX_SUB_PAGE="http://www.fangraphs.com/players.aspx?letter=${prefix}";
	
	/**
	 * Download player index page and sub pages.
	 * 
	 * @return A list of sub pages e.g. Aa, Ab, Ac
	 */
	public List<String> crawlPlayerIndexPages(){
		List<String> playerIndexSubPages = Lists.newArrayList();
		
		String fileName = "./download/playerIndex/playerIndex.html";
		if(!FilesUtil.isExistingFile(fileName)){
			String content = DownloadUtil.downloadPage(PLAYER_INDEX_PAGE);
			FilesUtil.writeToFile(fileName, content);
		}
		
		Document doc = Jsoup.parse(FilesUtil.readFileToString(fileName));
		Elements links = doc.select("a[href]");
		
		
		Map<String, String> filteredLinks = links
			.stream()
			.filter(e->e.attr("href").startsWith("players.aspx?"))
			.collect(Collectors.toMap(Element::text, e->e.attr("href")));
		
		for (String linkName: filteredLinks.keySet()) {
			String outputFileName = "./download/playerIndex/players"+linkName+".html";
			if(!FilesUtil.isExistingFile(outputFileName)){
				logger.info("Downloading player sub page: "+linkName);
				String content = DownloadUtil.downloadPage(PLAYER_INDEX_PAGE.replace("players.aspx", filteredLinks.get(linkName)));
				FilesUtil.writeToFile(outputFileName, content);
			}
			playerIndexSubPages.add(outputFileName);
        }
		
		return playerIndexSubPages;
	}
	
	/**
	 * Download and return a list of player season stats pages
	 * 
	 * @return 
	 */
    public Set<String> crawlPlayerSeasonStatsPages(){
    	Set<String> playerPages = Sets.newHashSet();
		
    	List<String> playerSubPages = crawlPlayerIndexPages();
    	for(String subPage: playerSubPages){
    		Document doc = Jsoup.parse(FilesUtil.readFileToString(subPage));
    		
    		// CSS select: dev#PlayerSearch1_panSearch > table[0] > tr[2] > td[0] > table[0]
    		Element mlbPlayerTable = doc.select("div#PlayerSearch1_panSearch").first()
    										.select("table").first()
    										.select("tr").get(2)
    										.select("td").first()
    										.select("table").first();
    		
    		Elements rows = mlbPlayerTable.select("tr");
    		for(Element row: rows){
    			Elements cols = row.select("td");
    			
    			// Only process if the player is active in the current season
    			String playerActivePeriod = cols.get(1).text();
    			if(!playerActivePeriod.endsWith("2014")){
    				continue;
    			}
    			
    			// Player page's URL (e.g. statss.aspx?playerid=4994&position=P) and player's name (e.g. Fernando Abad)
    			Element link = cols.get(0).select("a[href]").first();
    			String playerPageLink = link.attr("href");
    			String playerName = link.text();
    			
    			// Player's position e.g. P, SS, 2B/SS
    			String playerPosition = cols.get(2).text();
    			
    			String outputFileName = "./download/players/2015/"+(playerPosition.equals("P")? "pitchers/": "batters/")+playerName+".html";
    			
    			if(!FilesUtil.isExistingFile(outputFileName)){
    				String playerPageUrl = PLAYER_INDEX_PAGE.replace("players.aspx", playerPageLink);
    				logger.info("Downloading player page for "+playerName+", "+playerPageUrl);
    				FilesUtil.writeToFile(outputFileName, DownloadUtil.downloadPage(playerPageUrl));
    			}
    			
    			playerPages.add(outputFileName);
    		}
    		
    	}
    	
    	return playerPages;
    }

}
