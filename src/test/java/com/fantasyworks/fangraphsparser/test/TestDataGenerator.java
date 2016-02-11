package com.fantasyworks.fangraphsparser.test;

import com.fantasyworks.fangraphsparser.entity.PitcherRegularSeasonStats;
import com.fantasyworks.fangraphsparser.entity.PitcherStats;
import com.fantasyworks.fangraphsparser.entity.Player;
import com.fantasyworks.fangraphsparser.enumeration.HandednessEnum;
import com.fantasyworks.fangraphsparser.enumeration.PlayerTypeEnum;
import com.fantasyworks.util.ConversionUtil;
import com.fantasyworks.util.DateUtil;

public class TestDataGenerator {

	public static Player generateTestPlayer(){
		Player player = new Player();
		player.setName("Clayton Kershaw");
		player.setUid("2036");
		player.setBirthdate(DateUtil.parseDDMMYYYY("3/19/1988"));
		player.setBatsOn(HandednessEnum.L);
		player.setThrowsWith(HandednessEnum.L);
		player.setHeight(76);
		player.setWeight(225);
		player.setPlayerType(PlayerTypeEnum.PITCHER);
		player.setPositions("P");

		return player;
	}
	
	
	public static PitcherRegularSeasonStats generateTestPitcherRegularSeasonStats(){
		PitcherRegularSeasonStats stats = new PitcherRegularSeasonStats();
		stats.setSeason(2015);
		stats.setTeam("Dodgers");
		stats.setWin(16);
		stats.setLoss(7);
		stats.setSave(0);
		stats.setGames(33);
		stats.setGs(33);
		stats.setIp(ConversionUtil.toBigDecimal("232.2"));
		stats.setKPer9(ConversionUtil.toBigDecimal("11.64"));
		stats.setBbPer9(ConversionUtil.toBigDecimal("1.62"));
		stats.setHrPer9(ConversionUtil.toBigDecimal("0.58"));
		stats.setBabip(ConversionUtil.toBigDecimal(".272"));
		stats.setLobPerc(ConversionUtil.toBigDecimal("78.3%"));
		stats.setGbPerc(ConversionUtil.toBigDecimal("50.0%"));
		stats.setHrPerFb(ConversionUtil.toBigDecimal("10.1%"));
		stats.setEra(ConversionUtil.toBigDecimal("2.13"));
		stats.setFip(ConversionUtil.toBigDecimal("1.99"));
		stats.setXFip(ConversionUtil.toBigDecimal("2.09"));
		stats.setWar(ConversionUtil.toBigDecimal("8.6"));
		return stats;
	}
}
