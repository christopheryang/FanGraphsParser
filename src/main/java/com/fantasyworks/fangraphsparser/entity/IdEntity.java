package com.fantasyworks.fangraphsparser.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.google.common.base.MoreObjects.ToStringHelper;

@MappedSuperclass
public class IdEntity extends BaseDto {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * This method is used to check if the entity has been persisted and an ID assigned.
	 * 
	 * @return TRUE if id is not null, FALSE otherwise.
	 */
	public boolean isIdInitialized(){
		return id!=null;
	}
	
	/**
	 * To be overridden by child classes to combine the toString() output.
	 * 
	 * @return
	 */
	protected ToStringHelper toStringHelper() {
		return super.toStringHelper().add("id", id);
	}
	
}
