package com.fantasyworks.fangraphsparser.service;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fantasyworks.fangraphsparser.entity.PitcherRegularSeasonProjectedStats;
import com.fantasyworks.fangraphsparser.entity.PitcherRegularSeasonStats;
import com.fantasyworks.fangraphsparser.entity.PitcherStats;
import com.fantasyworks.fangraphsparser.entity.Player;
import com.fantasyworks.fangraphsparser.repo.PitcherRegularSeasonProjectedStatsRepo;
import com.fantasyworks.fangraphsparser.repo.PitcherRegularSeasonStatsRepo;
import com.fantasyworks.util.ConversionUtil;
import com.google.common.collect.Lists;
import com.opencsv.CSVWriter;

@Service
public class PitcherStatsOutputService {
	
	private static Logger logger = LoggerFactory.getLogger(PitcherStatsOutputService.class);
	
	@Autowired
	protected PitcherRegularSeasonStatsRepo rsStatsRepo;
	
	@Autowired
	protected PitcherRegularSeasonProjectedStatsRepo projRsStatsRepo;
	
	protected Set<Player> findProjectedPitchers(){
		List<PitcherRegularSeasonProjectedStats> allProjectedStats = Lists.newArrayList(projRsStatsRepo.findAll());
		return allProjectedStats.stream()
				.filter(s->s.getGs()!=null && s.getGs()>10) // Projected to start more than 10 games
				.map(s->s.getPlayer())
				.collect(Collectors.toSet());
	}
	
	/**
	 * 
	 */
	public void producePitcherRegularSeasonStatsCSV(){
		Set<Player> projectedPlayers = findProjectedPitchers();
		List<PitcherRegularSeasonStats> rsStats = projectedPlayers.stream()
				.map(p->rsStatsRepo.findBySeasonAndPlayer(2015, p)) // find last season's stats
				.filter(s->s!=null) // some players may exist in projections but not the previous season
				.collect(Collectors.toList());
		try{
			CSVWriter cw = new CSVWriter(new FileWriter("./output/pitchers2015.csv"));
			cw.writeNext(getHeaderRow());
			for(PitcherStats stats: rsStats){
				cw.writeNext(toTuple(stats));
			}
			cw.close();
		}
		catch(IOException ex){
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	protected String[] getHeaderRow(){
		return new String[]{
			"Name",
			"Age",
			"Team",
			"Win",
			"Loss",
			"Save",
			"Gm",
			"Gs",
			"IP",
			"K",
			"ERA",
			"WHIP",
			"FIP",
			"xFIP",
			"SIERA",
			"K/9",
			"K%",
			"BB/9",
			"BB%",
			"HR/9",
			"HR%",
			"GB%",
			"FB%",
			"GB/FB",
			"IFFB%",
			"OFFB%",
			"LOB%",
			"AVG",
			"BABIP",
			"Soft%",
			"Med%",
			"Hard%",
			"vFB",
			"vFBfx"
		};
	}
	
	protected String[] toTuple(PitcherStats stats){
		List<Object> tuple = Lists.newArrayList();
		tuple.add(stats.getName());
		tuple.add(stats.getAge().add(BigDecimal.ONE));
		tuple.add(stats.getTeam());
		tuple.add(stats.getWin());
		tuple.add(stats.getLoss());
		tuple.add(stats.getSave());
		tuple.add(stats.getGames());
		tuple.add(stats.getGs());
		tuple.add(stats.getIp());
		tuple.add(stats.getSo());
		tuple.add(stats.getEra());
		tuple.add(stats.getWhip());
		tuple.add(stats.getFip());
		tuple.add(stats.getxFip());
		tuple.add(stats.getSiera());
		tuple.add(stats.getkPer9());
		tuple.add(ConversionUtil.toPercentageStr(stats.getkPerc()));
		tuple.add(stats.getBbPer9());
		tuple.add(ConversionUtil.toPercentageStr(stats.getBbPerc()));
		tuple.add(stats.getHrPer9());
		tuple.add(stats.getHrPerFb());
		tuple.add(ConversionUtil.toPercentageStr(stats.getGbPerc()));
		tuple.add(ConversionUtil.toPercentageStr(stats.getFbPerc()));
		tuple.add(stats.getGbPerFb());
		tuple.add(ConversionUtil.toPercentageStr(stats.getIffbPerc()));
		BigDecimal offbPerc = (stats.getFbPerc()==null||stats.getIffbPerc()==null)? null: stats.getFbPerc().subtract(stats.getIffbPerc());
		tuple.add(ConversionUtil.toPercentageStr(offbPerc));
		tuple.add(ConversionUtil.toPercentageStr(stats.getLobPerc()));
		tuple.add(stats.getAvg());
		tuple.add(stats.getBabip());
		tuple.add(ConversionUtil.toPercentageStr(stats.getSoftPerc()));
		tuple.add(ConversionUtil.toPercentageStr(stats.getMedPerc()));
		tuple.add(ConversionUtil.toPercentageStr(stats.getHardPerc()));
		tuple.add(stats.getBisFbVelocity());
		tuple.add(stats.getPfxFaVelocity());

		return tuple.stream()
				.map(o -> (o == null)? null: o.toString()) // Don't want to print out "null"
				.collect(Collectors.toList())
				.toArray(new String[tuple.size()]);
	}
	
}
