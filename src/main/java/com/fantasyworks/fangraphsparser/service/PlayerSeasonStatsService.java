package com.fantasyworks.fangraphsparser.service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fantasyworks.fangraphsparser.crawler.PlayerPagesCrawler;
import com.fantasyworks.fangraphsparser.entity.BatterMinorsSeasonStats;
import com.fantasyworks.fangraphsparser.entity.BatterPostSeasonStats;
import com.fantasyworks.fangraphsparser.entity.BatterRegularSeasonPartialStats;
import com.fantasyworks.fangraphsparser.entity.BatterRegularSeasonProjectedStats;
import com.fantasyworks.fangraphsparser.entity.BatterRegularSeasonStats;
import com.fantasyworks.fangraphsparser.entity.BatterStats;
import com.fantasyworks.fangraphsparser.entity.PitcherMinorsSeasonStats;
import com.fantasyworks.fangraphsparser.entity.PitcherPostSeasonStats;
import com.fantasyworks.fangraphsparser.entity.PitcherRegularSeasonPartialStats;
import com.fantasyworks.fangraphsparser.entity.PitcherRegularSeasonProjectedStats;
import com.fantasyworks.fangraphsparser.entity.PitcherRegularSeasonStats;
import com.fantasyworks.fangraphsparser.entity.PitcherStats;
import com.fantasyworks.fangraphsparser.entity.Player;
import com.fantasyworks.fangraphsparser.enumeration.PlayerTypeEnum;
import com.fantasyworks.fangraphsparser.parser.BatterPageParser;
import com.fantasyworks.fangraphsparser.parser.PitcherPageParser;
import com.fantasyworks.fangraphsparser.repo.BatterMinorsSeasonStatsRepo;
import com.fantasyworks.fangraphsparser.repo.BatterPostSeasonStatsRepo;
import com.fantasyworks.fangraphsparser.repo.BatterRegularSeasonPartialStatsRepo;
import com.fantasyworks.fangraphsparser.repo.BatterRegularSeasonProjectedStatsRepo;
import com.fantasyworks.fangraphsparser.repo.BatterRegularSeasonStatsRepo;
import com.fantasyworks.fangraphsparser.repo.PitcherMinorsSeasonStatsRepo;
import com.fantasyworks.fangraphsparser.repo.PitcherPostSeasonStatsRepo;
import com.fantasyworks.fangraphsparser.repo.PitcherRegularSeasonPartialStatsRepo;
import com.fantasyworks.fangraphsparser.repo.PitcherRegularSeasonProjectedStatsRepo;
import com.fantasyworks.fangraphsparser.repo.PitcherRegularSeasonStatsRepo;
import com.fantasyworks.fangraphsparser.repo.PlayerRepo;

@Service
public class PlayerSeasonStatsService {
	
	private static final Logger logger = LoggerFactory.getLogger(PlayerSeasonStatsService.class);
	
	@Autowired protected PlayerPagesCrawler crawler;
	
	@Autowired protected PitcherPageParser pitcherPageParser;
	
	@Autowired protected BatterPageParser batterPageParser;
	
	@Autowired protected PlayerRepo playerRepo;
	
	@Autowired protected PitcherRegularSeasonStatsRepo pitcherRegularSeasonStatsRepo;
	@Autowired protected PitcherRegularSeasonPartialStatsRepo pitcherRegularSeasonPartialStatsRepo;
	@Autowired protected PitcherRegularSeasonProjectedStatsRepo pitcherRegularSeasonProjectedStatsRepo;
	@Autowired protected PitcherPostSeasonStatsRepo pitcherPostSeasonStatsRepo;
	@Autowired protected PitcherMinorsSeasonStatsRepo pitcherMinorsSeasonStatsRepo;
	
	@Autowired protected BatterRegularSeasonStatsRepo batterRegularSeasonStatsRepo;
	@Autowired protected BatterRegularSeasonPartialStatsRepo batterRegularSeasonPartialStatsRepo;
	@Autowired protected BatterRegularSeasonProjectedStatsRepo batterRegularSeasonProjectedStatsRepo;
	@Autowired protected BatterPostSeasonStatsRepo batterPostSeasonStatsRepo;
	@Autowired protected BatterMinorsSeasonStatsRepo batterMinorsSeasonStatsRepo;

	public void downloadAndPersistAllPlayers(){
		playerRepo.deleteAll();
		
		Set<String> files = crawler.crawlPlayerSeasonStatsPages();
		for(String file: files){
			downloadAndPersistPlayer(file);
		}
	}
	
	/**
	 * 
	 * @param file
	 * @return True if the player's page contains profile and stats. False otherwise.
	 */
	public void downloadAndPersistPlayer(String file){
		Player player = pitcherPageParser.parsePlayerProfile(file);
		
		// Some players appear in the index page but have no player page
		if(player==null){
			return;
		}
		
		Optional<Player> existingPlayer = playerRepo.findPlayerByUid(player.getUid());
		player = existingPlayer.isPresent()? existingPlayer.get(): playerRepo.save(player);
		
//		if(PlayerTypeEnum.PITCHER.equals(player.getPlayerType())){
//			Collection<PitcherStats> statsList = pitcherPageParser.parsePitcherSeasonStats(file, player);
//			pitcherRegularSeasonStatsRepo.save(statsList.stream().filter(s->s instanceof PitcherRegularSeasonStats).map(s->(PitcherRegularSeasonStats) s).collect(Collectors.toList()));
//			pitcherRegularSeasonPartialStatsRepo.save(statsList.stream().filter(s->s instanceof PitcherRegularSeasonPartialStats).map(s->(PitcherRegularSeasonPartialStats) s).collect(Collectors.toList()));
//			pitcherRegularSeasonProjectedStatsRepo.save(statsList.stream().filter(s->s instanceof PitcherRegularSeasonProjectedStats).map(s->(PitcherRegularSeasonProjectedStats) s).collect(Collectors.toList()));
//			pitcherPostSeasonStatsRepo.save(statsList.stream().filter(s->s instanceof PitcherPostSeasonStats).map(s->(PitcherPostSeasonStats) s).collect(Collectors.toList()));
//			pitcherMinorsSeasonStatsRepo.save(statsList.stream().filter(s->s instanceof PitcherMinorsSeasonStats).map(s->(PitcherMinorsSeasonStats) s).collect(Collectors.toList()));
//		}
		
		if(PlayerTypeEnum.BATTER.equals(player.getPlayerType())){
			Collection<BatterStats> statsList = batterPageParser.parseBatterSeasonStats(file, player);
			batterRegularSeasonStatsRepo.save(statsList.stream().filter(s->s instanceof BatterRegularSeasonStats).map(s->(BatterRegularSeasonStats) s).collect(Collectors.toList()));
			batterRegularSeasonPartialStatsRepo.save(statsList.stream().filter(s->s instanceof BatterRegularSeasonPartialStats).map(s->(BatterRegularSeasonPartialStats) s).collect(Collectors.toList()));
			batterRegularSeasonProjectedStatsRepo.save(statsList.stream().filter(s->s instanceof BatterRegularSeasonProjectedStats).map(s->(BatterRegularSeasonProjectedStats) s).collect(Collectors.toList()));
			batterPostSeasonStatsRepo.save(statsList.stream().filter(s->s instanceof BatterPostSeasonStats).map(s->(BatterPostSeasonStats) s).collect(Collectors.toList()));
			batterMinorsSeasonStatsRepo.save(statsList.stream().filter(s->s instanceof BatterMinorsSeasonStats).map(s->(BatterMinorsSeasonStats) s).collect(Collectors.toList()));
		}
		
	}
}
