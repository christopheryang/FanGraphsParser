package com.fantasyworks.fangraphsparser.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.fantasyworks.fangraphsparser.test.SpringEnabledTest;

public class PlayerSeasonStatsServiceTest extends SpringEnabledTest {

	@Autowired
	protected PlayerSeasonStatsService service;
	
	@Test
	public void testDownloadAndPersistPlayer_ClaytonKershaw(){
		String fileName = "./download/players/2015/pitchers/Clayton Kershaw.html";
		service.downloadAndPersistPlayer(fileName);
	}
	
	@Test
	public void testDownloadAndPersistPlayer_NeftaliFeliz(){
		String fileName = "./download/players/2015/pitchers/Neftali Feliz.html";
		service.downloadAndPersistPlayer(fileName);
	}
	
	@Test
	public void testDownloadAndPersistPlayer_WillSmith(){
		String fileName = "./download/players/2015/pitchers/Will Smith.html";
		service.downloadAndPersistPlayer(fileName);
	}
	
	@Test
	@Rollback(false)
	public void testDownloadAndPersistAllPlayers(){
		service.downloadAndPersistAllPlayers();
	}
}
