package com.fantasyworks.fangraphsparser.service;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fantasyworks.fangraphsparser.crawler.PlayerPagesCrawler;
import com.fantasyworks.fangraphsparser.entity.Player;
import com.fantasyworks.fangraphsparser.parser.PlayerProfileParser;
import com.fantasyworks.fangraphsparser.repo.PlayerRepo;

@Service
public class PlayerSeasonStatsService {
	
	private static final Logger logger = LoggerFactory.getLogger(PlayerSeasonStatsService.class);
	
	@Autowired
	protected PlayerPagesCrawler crawler;
	
	@Autowired
	@Qualifier("playerProfileParser")
	protected PlayerProfileParser playerParser;
	
	@Autowired
	protected PlayerRepo playerRepo;
	
	public void downloadAndPersistPlayers(){
		playerRepo.deleteAll();
		
		Set<String> files = crawler.crawlPlayerSeasonStatsPages();
		for(String file: files){
			logger.info("Parsing player profile: "+file);
			Player player = playerParser.parsePlayerProfile(file);
			if(player==null){
				continue;
			}
			playerRepo.save(player);
		}
	}
}
