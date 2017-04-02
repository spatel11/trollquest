package com.utilities;

/**
 * Encapsulates a function and its inverse.  The command pattern, yo.
 * Override the public methods with your own functionality.
 * @author Ian
 */
public abstract class Function {
	/**
	 * Warns that the called method has not be overriden.
	 * @param function_name
	 */
	private final void warnUnimplemented(String function_name) {
		System.out.println("Warning: function "+function_name+" was called but not overriden!");
	}
	
	/**
	 * Executes the function.
	 * @param arguments
	 */
	public void execute(Object... arguments) { warnUnimplemented("execute(Object... arguments)"); }
	/**
	 * Executes the inverse function.
	 * @param arguments
	 */
	public void executeInverse(Object... arguments) { warnUnimplemented("executeInverse(Object... arguments)"); }
}
