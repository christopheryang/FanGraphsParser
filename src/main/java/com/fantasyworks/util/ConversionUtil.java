package com.fantasyworks.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ConversionUtil {
	
	private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);
	
	public static Integer toInteger(String str){
		// \u00a0 is "&nbsp;", it is different from space
		if(isEmpty(str)){
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
		if(isEmpty(str)){
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
	
	/**
	 * Treat &nbsp; as empty
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		return str==null || str.trim().length()==0 || "\u00a0".equals(str);
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public static String toPercentageStr(BigDecimal value){
		return toPercentageStr(value, 1);
	}
	
	/**
	 * 
	 * @param value
	 * @param decimalPrecision
	 * @return
	 */
	public static String toPercentageStr(BigDecimal value, int decimalPrecision){
		return value==null? null: value.multiply(ONE_HUNDRED).setScale(decimalPrecision, RoundingMode.HALF_UP)+"%";
	}
}
