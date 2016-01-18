package com.fantasyworks.fangraphsparser.parser;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fantasyworks.fangraphsparser.entity.PitcherStats;
import com.fantasyworks.fangraphsparser.entity.Player;
import com.fantasyworks.fangraphsparser.repo.PlayerRepo;
import com.fantasyworks.fangraphsparser.test.SpringEnabledTest;

public class PitcherSeasonStatsParserTest extends SpringEnabledTest {

	@Autowired
	protected PitcherPageParser parser;
	
	@Autowired
	protected PlayerRepo playerRepo;
	
	@Test
	public void testParseSeasonStats_ClaytonKershaw(){
		String fileName = "./download/players/2015/pitchers/Clayton Kershaw.html";
		Player player = parser.parsePlayerProfile(fileName);
		player = playerRepo.save(player);
		
		List<PitcherStats> statsList = parser.parsePitcherSeasonStats(fileName, player);
		assertThat(statsList.size(), greaterThanOrEqualTo(15));
	}
	
	/**
	 * Caleb Thielbar has two entries in 2009, both with team "Brewers (R)"
	 */
	@Test
	public void testParseSeasonStats_CalebThielbar(){
		String fileName = "./download/players/2015/pitchers/Caleb Thielbar.html";
		Player player = parser.parsePlayerProfile(fileName);
		player = playerRepo.save(player);
		
		List<PitcherStats> statsList = parser.parsePitcherSeasonStats(fileName, player);
		assertThat(statsList.size(), greaterThanOrEqualTo(15));
	}
	
}
