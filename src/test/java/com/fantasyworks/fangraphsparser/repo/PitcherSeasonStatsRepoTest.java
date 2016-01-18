package com.fantasyworks.fangraphsparser.repo;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fantasyworks.fangraphsparser.entity.PitcherRegularSeasonStats;
import com.fantasyworks.fangraphsparser.entity.PitcherStats;
import com.fantasyworks.fangraphsparser.entity.Player;
import com.fantasyworks.fangraphsparser.test.SpringEnabledTest;
import com.fantasyworks.fangraphsparser.test.TestDataGenerator;

public class PitcherSeasonStatsRepoTest extends SpringEnabledTest {

	@Autowired
	protected PlayerRepo playerRepo;
	
	@Autowired
	protected PitcherRegularSeasonStatsRepo pitcherRegularSeasonStatsRepo;
	
	@Test
	public void testCreatePitcherSeasonStats(){
		Player player = TestDataGenerator.generateTestPlayer();
		playerRepo.save(player);
		
		PitcherRegularSeasonStats stats =  TestDataGenerator.generateTestPitcherRegularSeasonStats();
		stats.setPlayer(player);
		assertThat(stats.isIdInitialized(), equalTo(false));
		stats = pitcherRegularSeasonStatsRepo.save(stats);
		assertThat(stats.isIdInitialized(), equalTo(true));
	}
}
