package com.utilities;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Encapsulates a bunch of string helper functions.
 * @author Ian
 */
public abstract class UtilityString {
	/** A decimal format, for the comma string. */
	private final static DecimalFormat df;
	/** Static initializer.  Sets up the decimal format for the comma string. */
	static {
		df = new DecimalFormat();
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setGroupingSeparator(',');
		df.setDecimalFormatSymbols(dfs);
	}
	
	/**
	 * Take a number and turns it into a string, adding commas as necessary.
	 * @param number the number.
	 * @return the string.
	 */
	public final static String toCommaString(int number) {
		//http://www.daniweb.com/software-development/java/threads/205639
		return df.format(number);
	}
}