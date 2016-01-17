package com.fantasyworks.fangraphsparser.util;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import org.junit.Test;

import com.fantasyworks.util.ConversionUtil;

public class ConversionUtilTest {
	
	@Test
	public void testConvertBigDecimal1(){
		BigDecimal value = ConversionUtil.toBigDecimal(" 0.6100000 ");
		assertThat(value.toString(), equalTo("0.61"));
	}
	
	@Test
	public void testConvertBigDecimal2(){
		BigDecimal value = ConversionUtil.toBigDecimal(" 0.6150000 ");
		assertThat(value.toString(), equalTo("0.62"));
	}
	
	@Test
	public void testConvertBigDecimal3(){
		BigDecimal value = ConversionUtil.toBigDecimal(" 10 ");
		assertThat(value.toString(), equalTo("10.00"));
	}

	@Test
	public void testConvertBigDecimalNoLeadingZero(){
		BigDecimal value = ConversionUtil.toBigDecimal(" .10 ");
		assertThat(value.toString(), equalTo("0.10"));
	}

	@Test
	public void testConvertBigDecimalWithSpecificDecimalPrecision(){
		BigDecimal value = ConversionUtil.toBigDecimal(" 0.6100000 ", 1);
		assertThat(value.toString(), equalTo("0.6"));
	}
	

	@Test
	public void testConvertPercStr1(){
		BigDecimal value = ConversionUtil.toBigDecimal("15%");
		assertThat(value.toString(), equalTo("0.15"));
		
		value = ConversionUtil.toBigDecimal(" 15 % ");
		assertThat(value.toString(), equalTo("0.15"));
	}
	
	@Test
	public void testConvertPercStr2(){
		BigDecimal value = ConversionUtil.toBigDecimal("15.55%");
		assertThat(value.toString(), equalTo("0.16"));
		
		value = ConversionUtil.toBigDecimal(" 15.55 % ");
		assertThat(value.toString(), equalTo("0.16"));
	}
	
	@Test
	public void testConvertPercStrWithSpecificDecimalPrecision(){
		BigDecimal value = ConversionUtil.toBigDecimal("15.55%", 3);
		assertThat(value.toString(), equalTo("0.156"));
		
		value = ConversionUtil.toBigDecimal(" 15.55 % ", 3);
		assertThat(value.toString(), equalTo("0.156"));
	}

	@Test
	public void testConvertInvalidPercStr(){
		BigDecimal value = ConversionUtil.toBigDecimal("%");
		assertThat(value, nullValue());
	}
	
	@Test
	public void testScientificStr(){
		BigDecimal value = ConversionUtil.toBigDecimal("0.00155E2");
		assertThat(value.toString(), equalTo("0.16"));
		
		value = ConversionUtil.toBigDecimal("155.5E-3");
		assertThat(value.toString(), equalTo("0.16"));
	}
}
