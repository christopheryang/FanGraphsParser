package com.fantasyworks.fangraphsparser.service;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fantasyworks.fangraphsparser.crawler.PlayerPagesCrawler;
import com.fantasyworks.fangraphsparser.entity.PitcherSeasonStats;
import com.fantasyworks.fangraphsparser.entity.Player;
import com.fantasyworks.fangraphsparser.enumeration.PlayerTypeEnum;
import com.fantasyworks.fangraphsparser.parser.BatterPageParser;
import com.fantasyworks.fangraphsparser.parser.PitcherPageParser;
import com.fantasyworks.fangraphsparser.repo.PitcherSeasonStatsRepo;
import com.fantasyworks.fangraphsparser.repo.PlayerRepo;

@Service
public class PlayerSeasonStatsService {
	
	private static final Logger logger = LoggerFactory.getLogger(PlayerSeasonStatsService.class);
	
	@Autowired
	protected PlayerPagesCrawler crawler;
	
	@Autowired
	protected PitcherPageParser pitcherPageParser;
	
	@Autowired
	protected BatterPageParser batterPageParser;
	
	@Autowired
	protected PlayerRepo playerRepo;
	
	@Autowired
	protected PitcherSeasonStatsRepo pitcherSeasonStatsRepo;
	
	public void downloadAndPersistPlayers(){
		playerRepo.deleteAll();
		
		Set<String> files = crawler.crawlPlayerSeasonStatsPages();
		for(String file: files){
			Player player = pitcherPageParser.parsePlayerProfile(file);
			
			// Some players appear in the index page but have no player page
			if(player==null){
				continue;
			}
			
			player = playerRepo.save(player);
			if(PlayerTypeEnum.PITCHER.equals(player.getPlayerType())){
				List<PitcherSeasonStats> statsList = pitcherPageParser.parsePitcherSeasonStats(file, player);
				pitcherSeasonStatsRepo.save(statsList);
			}
			
		}
	}
}
