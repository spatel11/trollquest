package com.ai.reference;

import java.io.Serializable;

/**
 * From p1. 
 * 
 * @author Terran Lane
 *
 */
public class ParseException extends Exception implements Serializable{

	/**
	 * prints generic error message
	 */
	public ParseException(){
		super();
		System.out.println("Error during parsing");
	}
	/**
	 * Prints specific message when exception is thrown
	 * @param s message to be printed when exception is thrown.
	 */
	public ParseException(String s) {
		super(s);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2524166449376963534L;

	

}
