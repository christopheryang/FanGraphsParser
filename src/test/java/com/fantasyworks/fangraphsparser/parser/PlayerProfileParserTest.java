package com.fantasyworks.fangraphsparser.parser;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fantasyworks.fangraphsparser.entity.Player;
import com.fantasyworks.fangraphsparser.test.SpringEnabledTest;

public class PlayerProfileParserTest extends SpringEnabledTest {

	@Autowired
	protected PitcherPageParser pitcherPageParser;
	
	
	@Test
	public void testParseClaytonKershaw(){
		Player player = pitcherPageParser.parsePlayerProfile("./download/players/2015/pitchers/Clayton Kershaw.html");
		
	}
	
	@Test
	public void testParsePauloOrlando(){
		Player player = pitcherPageParser.parsePlayerProfile("./download/players/2015/batters/Paulo Orlando.html");
	}
	
	// This player has no season stats page
	@Test
	public void testParseDianToscano(){
		Player player = pitcherPageParser.parsePlayerProfile("./download/players/2015/batters/Dian Toscano.html");
		MatcherAssert.assertThat(player, Matchers.nullValue());
	}
}
