package com.fantasyworks.fangraphsparser.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.fantasyworks.fangraphsparser.enumeration.HandednessEnum;
import com.fantasyworks.fangraphsparser.enumeration.PlayerTypeEnum;

@Entity
public class Player extends IdEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Column(nullable=false, unique=true)
	protected String uid;
	@Column(nullable=false)
	protected String name;
	protected String firstName;
	protected String lastName;
	protected Date birthdate;
	protected HandednessEnum batsOn;
	protected HandednessEnum throwsWith;
	protected Integer height;
	protected Integer weight;
	// e.g. 2B/SS
	protected String positions;
	
	// Either PITCHER or BATTER
	protected PlayerTypeEnum playerType;
	
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	public HandednessEnum getBatsOn() {
		return batsOn;
	}
	public void setBatsOn(HandednessEnum batsOn) {
		this.batsOn = batsOn;
	}
	public HandednessEnum getThrowsWith() {
		return throwsWith;
	}
	public void setThrowsWith(HandednessEnum throwsWith) {
		this.throwsWith = throwsWith;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	public String getPositions() {
		return positions;
	}
	public void setPositions(String positions) {
		this.positions = positions;
	}
	public PlayerTypeEnum getPlayerType() {
		return playerType;
	}
	public void setPlayerType(PlayerTypeEnum playerType) {
		this.playerType = playerType;
	}
}
