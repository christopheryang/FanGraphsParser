package com.fantasyworks.fangraphsparser.repo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fantasyworks.fangraphsparser.entity.Player;
import com.fantasyworks.fangraphsparser.test.SpringEnabledTest;
import com.fantasyworks.fangraphsparser.test.TestDataGenerator;

public class PlayerRepoTest extends SpringEnabledTest {

	@Autowired
	protected PlayerRepo playerRepo;
	
	@Test
	public void testCreatePlayer(){
		Player player = TestDataGenerator.generateTestPlayer();
		assertThat(player.isIdInitialized(), equalTo(false));
		player = playerRepo.save(player);
		assertThat(player.isIdInitialized(), equalTo(true));
		
	}
}
