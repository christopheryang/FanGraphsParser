package com.fantasyworks.fangraphsparser.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.fantasyworks.fangraphsparser.test.SpringEnabledTest;

public class PlayerSeasonStatsServiceTest extends SpringEnabledTest {

	@Autowired
	protected PlayerSeasonStatsService service;
	
	/**
	 * Has minors, regular, postseason stats
	 */
	@Test
	public void testDownloadAndPersistPlayer_ClaytonKershaw(){
		String fileName = "./download/players/2015/pitchers/Clayton Kershaw.html";
		service.downloadAndPersistPlayer(fileName);
	}
	
	/**
	 * Has regular season partial stats
	 */
	@Test
	public void testDownloadAndPersistPlayer_NeftaliFeliz(){
		String fileName = "./download/players/2015/pitchers/Neftali Feliz.html";
		service.downloadAndPersistPlayer(fileName);
	}
	
	/**
	 * Has averages in Advanced section
	 */
	@Test
	public void testDownloadAndPersistPlayer_WillSmith(){
		String fileName = "./download/players/2015/pitchers/Will Smith.html";
		service.downloadAndPersistPlayer(fileName);
	}
	
	/**
	 * Has no batted ball stats for 2000 and 2001
	 */
	@Test
	public void testDownloadAndPersistPlayer_JasonMarquis(){
		String fileName = "./download/players/2015/pitchers/Jason Marquis.html";
		service.downloadAndPersistPlayer(fileName);
	}
	
	/**
	 * Has an empty Batted Ball section, potential IndexOutOfBoundsException.
	 */
	@Test
	public void testDownloadAndPersistPlayer_SukMinYoon(){
		String fileName = "./download/players/2015/pitchers/Suk-Min Yoon.html";
		service.downloadAndPersistPlayer(fileName);
	}
	
	
	@Test
	@Rollback(false)
	public void testDownloadAndPersistAllPlayers(){
		service.downloadAndPersistAllPlayers();
	}
}
