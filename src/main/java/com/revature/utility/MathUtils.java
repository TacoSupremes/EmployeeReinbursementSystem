package com.revature.utility;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtils 
{
	//public static final DecimalFormat DOLLARFORMAT = new DecimalFormat("###,###,###,##0.##");
//	private static final DecimalFormat ROUNDFORMAT = new DecimalFormat("0.##");
	
	public static double roundToNearestCent(double d)
	{
		// Use big decimal for rounding to nearest cent
		BigDecimal bd = new BigDecimal(Double.toString(d)).setScale(2, RoundingMode.HALF_UP);
		
		return bd.doubleValue();
	}
}
