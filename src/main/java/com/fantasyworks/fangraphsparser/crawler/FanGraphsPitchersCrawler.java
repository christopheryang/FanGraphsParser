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
import com.fantasyworks.util.IOUtil;
import com.google.common.collect.Lists;

@Service
public class FanGraphsPitchersCrawler {
	
	private static final Logger logger = LoggerFactory.getLogger(FanGraphsPitchersCrawler.class);
	
	public static final String PLAYER_INDEX_PAGE="http://www.fangraphs.com/players.aspx";
	public static final String PLAYER_INDEX_SUB_PAGE="http://www.fangraphs.com/players.aspx?letter=${prefix}";

	public List<String> crawPlayerIndexPages(){
		List<String> playerIndexSubPages = Lists.newArrayList();
		
		String fileName = "./download/playerIndex/playerIndex.html";
		if(!IOUtil.isExistingFile(fileName)){
			String content = DownloadUtil.downloadPage(PLAYER_INDEX_PAGE);
			IOUtil.writeToFile(fileName, content);
		}
		
		Document doc = Jsoup.parse(IOUtil.readFileToString(fileName));
		Elements links = doc.select("a[href]");
		
		
		Map<String, String> filteredLinks = links
			.stream()
			.filter(e->e.attr("href").startsWith("players.aspx?"))
			.collect(Collectors.toMap(Element::text, e->e.attr("href")));
		
		for (String linkName: filteredLinks.keySet()) {
			logger.info("Processing player sub page: "+linkName);
			String content = DownloadUtil.downloadPage(PLAYER_INDEX_PAGE.replace("players.aspx", filteredLinks.get(linkName)));
			String outputFileName = "./download/playerIndex/players"+linkName+".html";
			IOUtil.writeToFile(outputFileName, content);
			playerIndexSubPages.add(outputFileName);
        }
		
		return playerIndexSubPages;
	}
	

    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }

}
