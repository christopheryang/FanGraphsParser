package com.fantasyworks.fangraphsparser.repo;

import org.springframework.data.repository.CrudRepository;

import com.fantasyworks.fangraphsparser.entity.PitcherRegularSeasonStats;
import com.fantasyworks.fangraphsparser.entity.Player;

public interface PitcherRegularSeasonStatsRepo extends CrudRepository<PitcherRegularSeasonStats, Long> {

	public PitcherRegularSeasonStats findBySeasonAndPlayer(int season, Player player);
	
}
