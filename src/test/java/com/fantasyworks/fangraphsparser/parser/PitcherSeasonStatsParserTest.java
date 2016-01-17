package com.fantasyworks.fangraphsparser.parser;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fantasyworks.fangraphsparser.entity.PitcherSeasonStats;
import com.fantasyworks.fangraphsparser.entity.Player;
import com.fantasyworks.fangraphsparser.repo.PlayerRepo;
import com.fantasyworks.fangraphsparser.test.SpringEnabledTest;

public class PitcherSeasonStatsParserTest extends SpringEnabledTest {

	@Autowired
	protected PitcherPageParser parser;
	
	@Autowired
	protected PlayerRepo playerRepo;
	
	@Test
	public void testParseSeasonStats(){
		String fileName = "./download/players/2015/pitchers/Clayton Kershaw.html";
		Player player = parser.parsePlayerProfile(fileName);
		player = playerRepo.save(player);
		
		List<PitcherSeasonStats> statsList = parser.parsePitcherSeasonStats(fileName, player);
		assertThat(statsList.size(), greaterThanOrEqualTo(15));
	}
}
