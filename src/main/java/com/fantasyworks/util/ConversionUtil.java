package com.fantasyworks.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ConversionUtil {
	
	public static Integer toInteger(String str){
		// \u00a0 is "&nbsp;", it is different from space
		if(str==null || str.trim().length()==0 || "\u00a0".equals(str)){
			return null;
		}
		
		str = str.trim();
		
		try{
			return Integer.parseInt(str);
		}
		catch(Exception ex){
			throw new RuntimeException("Unable to parse integer: "+str, ex);
		}
	}
	
	/**
	 * Default decimal precision to 2
	 * @param str
	 * @return
	 */
	public static BigDecimal toBigDecimal(String str){
		return toBigDecimal(str, 2);
	}
	
	/**
	 * 
	 * @param str
	 * @param decimalPrecision
	 * @return
	 */
	public static BigDecimal toBigDecimal(String str, int decimalPrecision){
		// \u00a0 is "&nbsp;", it is different from space
		if(str==null || str.trim().length()==0 || "\u00a0".equals(str)){
			return null;
		}
		
		decimalPrecision = (decimalPrecision<0)? 0: decimalPrecision;
		str = str.trim();
		
		// Treat % strings specially
		if(str.endsWith("%")){
			String substr = str.substring(0, str.length()-1).trim();
			return (substr.length()==0)? null:
					new BigDecimal(substr).divide(BigDecimal.valueOf(100)).setScale(decimalPrecision, RoundingMode.HALF_UP);
		}
		
		try{
			return new BigDecimal(str).setScale(decimalPrecision, RoundingMode.HALF_UP);
		}
		catch(Exception ex){
			throw new RuntimeException("Unable to parse big decimal: "+str+", precision: "+decimalPrecision, ex);
		}
	}
	
}
