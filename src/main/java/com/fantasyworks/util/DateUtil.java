package com.fantasyworks.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	public static Date parseDDMMYYYY(String dateStr){
		if(dateStr == null || dateStr.trim().length() == 0){
			return null;
		}
		DateFormat MMDDYYYY = new SimpleDateFormat("MM/dd/yyyy");
		MMDDYYYY.setLenient(false);
		try{
			return MMDDYYYY.parse(dateStr);
		}
		catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	
}
