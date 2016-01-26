package com.fantasyworks.fangraphsparser.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fantasyworks.fangraphsparser.test.SpringEnabledTest;

public class PitcherStatsOutputServiceTest extends SpringEnabledTest {

	@Autowired
	protected PitcherStatsOutputService outputService;
	
	@Test
	public void testProducePitcherRegularSeasonStatsCSV(){
		outputService.producePitcherRegularSeasonStatsCSV();
	}
}
