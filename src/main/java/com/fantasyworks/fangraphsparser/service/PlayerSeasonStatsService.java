package com.fantasyworks.fangraphsparser.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fantasyworks.fangraphsparser.crawler.PlayerPagesCrawler;
import com.fantasyworks.fangraphsparser.entity.PitcherMinorsSeasonStats;
import com.fantasyworks.fangraphsparser.entity.PitcherPostSeasonStats;
import com.fantasyworks.fangraphsparser.entity.PitcherRegularSeasonStats;
import com.fantasyworks.fangraphsparser.entity.PitcherStats;
import com.fantasyworks.fangraphsparser.entity.Player;
import com.fantasyworks.fangraphsparser.enumeration.PlayerTypeEnum;
import com.fantasyworks.fangraphsparser.parser.BatterPageParser;
import com.fantasyworks.fangraphsparser.parser.PitcherPageParser;
import com.fantasyworks.fangraphsparser.repo.PitcherMinorsSeasonStatsRepo;
import com.fantasyworks.fangraphsparser.repo.PitcherPostSeasonStatsRepo;
import com.fantasyworks.fangraphsparser.repo.PitcherRegularSeasonStatsRepo;
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
	protected PitcherRegularSeasonStatsRepo pitcherRegularSeasonStatsRepo;
	
	@Autowired
	protected PitcherPostSeasonStatsRepo pitcherPostSeasonStatsRepo;
	
	@Autowired
	protected PitcherMinorsSeasonStatsRepo pitcherMinorsSeasonStatsRepo;
	
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
				List<PitcherStats> statsList = pitcherPageParser.parsePitcherSeasonStats(file, player);
				pitcherRegularSeasonStatsRepo.save(statsList.stream().filter(s->s instanceof PitcherRegularSeasonStats).map(s->(PitcherRegularSeasonStats) s).collect(Collectors.toList()));
				pitcherPostSeasonStatsRepo.save(statsList.stream().filter(s->s instanceof PitcherPostSeasonStats).map(s->(PitcherPostSeasonStats) s).collect(Collectors.toList()));
				pitcherMinorsSeasonStatsRepo.save(statsList.stream().filter(s->s instanceof PitcherMinorsSeasonStats).map(s->(PitcherMinorsSeasonStats) s).collect(Collectors.toList()));
			}
			
		}
	}
}
