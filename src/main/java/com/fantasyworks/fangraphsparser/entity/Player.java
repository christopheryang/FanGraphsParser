package com.fantasyworks.fangraphsparser.entity;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.fantasyworks.fangraphsparser.enumeration.HandednessEnum;
import com.fantasyworks.fangraphsparser.enumeration.PlayerTypeEnum;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data @EqualsAndHashCode(of={"uid"}, callSuper=false)
public class Player extends IdEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Column(nullable=false, unique=true)
	protected String uid;

	// Either PITCHER or BATTER
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	protected PlayerTypeEnum playerType;

	@Column(nullable=false)
	protected String name;
	
	protected String firstName;
	protected String lastName;
	protected Date birthdate;
	@Enumerated(EnumType.STRING)
	protected HandednessEnum batsOn;
	@Enumerated(EnumType.STRING)
	protected HandednessEnum throwsWith;
	protected Integer height;
	protected Integer weight;
	// e.g. 2B/SS
	protected String positions;
	
//	@Override
//	protected ToStringHelper toStringHelper() {
//		return super.toStringHelper()
//				.add("name", name)
//				.add("uid", uid)
//				;
//	}
	
//	@Override
//	public boolean equals(Object obj){
//		if(obj==null || obj instanceof Player == false){
//			return false;
//		}
//		if(this == obj){
//			return true;
//		}
//		Player instance = (Player) obj;
//		return Objects.equals(this.getUid(), instance.getUid());
//	}
	
	
}
