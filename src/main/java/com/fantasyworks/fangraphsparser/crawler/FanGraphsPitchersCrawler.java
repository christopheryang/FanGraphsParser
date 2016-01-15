package com.fantasyworks.fangraphsparser.crawler;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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

@Service
public class FanGraphsPitchersCrawler {
	
	private static final Logger logger = LoggerFactory.getLogger(FanGraphsPitchersCrawler.class);
	
	public static final String PLAYER_INDEX_PAGE="http://www.fangraphs.com/players.aspx";
	public static final String PLAYER_INDEX_SUB_PAGE="http://www.fangraphs.com/players.aspx?letter=${prefix}";
	
	/**
	 * Download player index page and sub pages.
	 * 
	 * @return A list of sub pages
	 */
	public List<String> crawPlayerIndexPages(){
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
	

    public List<String> crawPlayerPages(){
    	List<String> playerPages = Lists.newArrayList();
		
    	List<String> playerSubPages = crawPlayerIndexPages();
    	for(String subPage: playerSubPages){
    		Document doc = Jsoup.parse(FilesUtil.readFileToString(subPage));
    		Elements links = doc.select("a[href]");
    		
    		// Sample player page URL: "statss.aspx?playerid=4994&position=P", "statss.aspx?playerid=1009829&position=OF"
    		Map<String, String> filteredLinks = links
    				.stream()
    				.filter(e->e.attr("href").startsWith("statss.aspx?"))
    				.collect(Collectors.toMap(Element::text, e->e.attr("href")));
    		
    		for (String linkName: filteredLinks.keySet()) {
    			String url = filteredLinks.get(linkName);
    			String outputFileName = url.endsWith("position=P")? "./download/players/pitchers/"+linkName+".html": "./download/players/batters/"+linkName+".html";
    			if(!FilesUtil.isExistingFile(outputFileName)){
    				logger.info("Downloading player page: "+linkName);
    				String content = DownloadUtil.downloadPage(PLAYER_INDEX_PAGE.replace("players.aspx", url));
    				FilesUtil.writeToFile(outputFileName, content);
    			}
    			playerPages.add(outputFileName);
            }
    	}
    	
    	return playerPages;
    }

}
