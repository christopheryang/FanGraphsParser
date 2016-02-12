package com.fantasyworks.fangraphsparser.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fantasyworks.fangraphsparser.test.SpringEnabledTest;

public class PlayerStatsOutputServiceTest extends SpringEnabledTest {

	@Autowired
	protected PitcherStatsOutputService psOutputService;
	
	@Autowired
	protected BatterStatsOutputService bsOutputService;
	
	@Test
	public void testProducePitcherRegularSeasonStatsCSV(){
		psOutputService.producePitcherRegularSeasonStatsCSV();
	}
	
	@Test
	public void testProduceBatterRegularSeasonStatsCSV(){
		bsOutputService.produceBatterRegularSeasonStatsCSV();
	}
}
