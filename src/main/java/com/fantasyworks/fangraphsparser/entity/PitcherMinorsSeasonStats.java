package com.fantasyworks.fangraphsparser.entity;

import javax.persistence.Entity;

import com.fantasyworks.fangraphsparser.enumeration.StatsTypeEnum;

@Entity
public class PitcherMinorsSeasonStats extends PitcherStats {

	private static final long serialVersionUID = 1L;

	public PitcherMinorsSeasonStats(){
		statsType = StatsTypeEnum.MINORS;
	}
}
