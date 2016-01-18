package com.fantasyworks.fangraphsparser.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.fantasyworks.fangraphsparser.test.SpringEnabledTest;

public class PlayerSeasonStatsServiceTest extends SpringEnabledTest {

	@Autowired
	protected PlayerSeasonStatsService service;
	
	@Test
	//@Rollback(false)
	public void testDownloadAndPersistPlayers(){
		service.downloadAndPersistPlayers();
	}
}
