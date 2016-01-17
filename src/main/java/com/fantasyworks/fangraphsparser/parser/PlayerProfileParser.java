package com.fantasyworks.fangraphsparser.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fantasyworks.fangraphsparser.entity.Player;
import com.fantasyworks.fangraphsparser.enumeration.HandednessEnum;
import com.fantasyworks.fangraphsparser.enumeration.PlayerTypeEnum;
import com.fantasyworks.util.DateUtil;
import com.fantasyworks.util.FilesUtil;

public class PlayerProfileParser {

	private static final Logger logger = LoggerFactory.getLogger(PlayerProfileParser.class);
	
	// Example: Clayton Kershaw Birthdate: 3/19/1988 (27 y, 9 m, 27 d)     Bats/Throws: L/L     Height/Weight: 6-4/225     Position: P Drafted: 2006 June Amateur Draft - Round: 1, Pick: 7, Overall: 7, Team: Los Angeles Dodgers Contract: $215M / 7 Years (2014 - 2020)
	public static final Pattern PLAYER_PROFILE_PATTERN = Pattern.compile("(.+) Birthdate: (\\d+/\\d+/\\d+).+Bats/Throws: (\\w+)/(\\w+).+Height/Weight: ([\\d-]+)/(\\d+).+Position: ([\\S]+) .+");
	
	// Exmple: ./statss.aspx?playerid=2036&amp;position=P
	public static final Pattern SEASON_STATS_LINK_PATTERN = Pattern.compile(".+playerid=([\\d\\w]+)&.+");
	
	/**
	 * Parse player profile only.
	 * 
	 * @param fileName
	 * @return
	 */
	public Player parsePlayerProfile(String fileName){
		logger.debug("Parsing player profile: "+fileName);
		
		Player player = new Player();
		player.setPlayerType(fileName.contains("pitcher")? PlayerTypeEnum.PITCHER: PlayerTypeEnum.BATTER);
		
		Document doc = Jsoup.parse(FilesUtil.readFileToString(fileName));
		
		Elements tables = doc.select("body").first().select("table");
		if(tables.size()<15){
			logger.warn("Fewer than 15 tables in player's page: "+fileName);
			return null;
		}
		
		// CSS select: tr#sol1 > parent > tr[0] > td[0] > 
		Element playerProfileTD = doc.select("body").first().select("table").get(14)
//				doc.select("tr#so1").first().parent()
										.select("tr").first()
										.select("td").first();
		
		String playerProfile = playerProfileTD.text();
		Matcher matcher = PLAYER_PROFILE_PATTERN.matcher(playerProfile);
		if(matcher.find()){
			player.setName(matcher.group(1).trim());
			player.setBirthdate(DateUtil.parseDDMMYYYY(matcher.group(2).trim()));
			player.setBatsOn(HandednessEnum.valueOf(matcher.group(3).trim()));
			player.setThrowsWith(HandednessEnum.valueOf(matcher.group(4).trim()));
			String[] heightTokens = matcher.group(5).split("-");
			player.setHeight(Integer.parseInt(heightTokens[0])*12+Integer.parseInt(heightTokens[1]));
			player.setWeight(Integer.parseInt(matcher.group(6)));
			player.setPositions(matcher.group(7).trim());
		}
		else{
			throw new RuntimeException("Unable to parse player profile for: "+fileName);
		}
		
		Element firstForm = doc.select("body").first().select("form").first();
		String action = firstForm.attr("action");
		matcher = SEASON_STATS_LINK_PATTERN.matcher(action);
		if(matcher.find()){
			player.setUid(matcher.group(1));
		}
		
		return player;
	}
	
}
