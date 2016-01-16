package com.fantasyworks.fangraphsparser.repo;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fantasyworks.fangraphsparser.entity.Player;
import com.fantasyworks.fangraphsparser.enumeration.HandednessEnum;
import com.fantasyworks.fangraphsparser.enumeration.PlayerTypeEnum;
import com.fantasyworks.fangraphsparser.test.SpringEnabledTest;
import com.fantasyworks.util.DateUtil;

public class PlayerRepoTest extends SpringEnabledTest {

	@Autowired
	protected PlayerRepo playerRepo;
	
	protected Player player;
	
	@Before
	public void setUp(){
		player = new Player();
		player.setName("Clayton Kershaw");
		player.setUid("2036");
		player.setBirthdate(DateUtil.parseDDMMYYYY("3/19/1988"));
		player.setBatsOn(HandednessEnum.L);
		player.setThrowsWith(HandednessEnum.L);
		player.setHeight(76);
		player.setWeight(225);
		player.setPlayerType(PlayerTypeEnum.PITCHER);
		player.setPositions("P");
	}
	
	@Test
	public void testCreatePlayer(){
		assertThat(player.isIdInitialized(), equalTo(false));
		player = playerRepo.save(player);
		assertThat(player.isIdInitialized(), equalTo(true));
		
	}
}
