package com.ai.reference;

import java.io.Serializable;

/**
 * Thrown when there is an error in an {@link ASTCell} method
 * @author Martin Tice
 *
 */
public class ExecutionException extends Exception implements Serializable {

	/**
	 * Throws an exception, and prints given string.
	 * @param s String to be printed when execution is thrown.
	 */
	public ExecutionException(String s) {
		super(s);
	}
	
	public ExecutionException(){
		super();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6947881436550252600L;

}
