package com.fantasyworks.fangraphsparser.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.google.common.base.MoreObjects;
import com.google.common.base.MoreObjects.ToStringHelper;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility=Visibility.ANY, getterVisibility=Visibility.NONE, isGetterVisibility=Visibility.NONE, setterVisibility=Visibility.NONE)
public class BaseDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * To be overridden by child classes to combine the toString() output.
	 * 
	 * @return
	 */
	protected ToStringHelper toStringHelper() {
		return MoreObjects.toStringHelper(this);
	}
	
	@Override
	public String toString(){
		return toStringHelper().toString();
	}

}
