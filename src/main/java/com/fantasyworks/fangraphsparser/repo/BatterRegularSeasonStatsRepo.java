package com.fantasyworks.fangraphsparser.repo;

import org.springframework.data.repository.CrudRepository;

import com.fantasyworks.fangraphsparser.entity.BatterRegularSeasonStats;
import com.fantasyworks.fangraphsparser.entity.Player;

public interface BatterRegularSeasonStatsRepo extends CrudRepository<BatterRegularSeasonStats, Long> {

	public BatterRegularSeasonStats findBySeasonAndPlayer(int season, Player player);
	
}
