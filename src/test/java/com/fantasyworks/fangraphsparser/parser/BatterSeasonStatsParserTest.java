package com.fantasyworks.fangraphsparser.parser;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import java.util.Collection;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fantasyworks.fangraphsparser.entity.BatterStats;
import com.fantasyworks.fangraphsparser.entity.Player;
import com.fantasyworks.fangraphsparser.test.SpringEnabledTest;

public class BatterSeasonStatsParserTest extends SpringEnabledTest {

	@Autowired
	protected BatterPageParser parser;
	
	@Test
	public void testParseSeasonStats_BryceHarper(){
		String fileName = "./download/players/2015/batters/Bryce Harper.html";
		Player player = parser.parsePlayerProfile(fileName);
		
		Collection<BatterStats> statsList = parser.parseBatterSeasonStats(fileName, player);
		assertThat(statsList.size(), greaterThanOrEqualTo(17));
	}

}
