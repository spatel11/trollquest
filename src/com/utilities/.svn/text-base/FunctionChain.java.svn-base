package com.utilities;

import java.util.LinkedList;

/**
 * Encapsulates a chain of functions.  Can be executes in forward or backward order, so
 * as to do or undo the chain of operations.
 * @author Ian
 */
public final class FunctionChain {
	/** Ordered list of the functions. */
	private final LinkedList<Function> functions_forward = new LinkedList<Function>();
	/** Ordered list of the functions, inverted. */
	private final LinkedList<Function> functions_backward = new LinkedList<Function>();
	
	/**
	 * Adds a function to the chain.
	 * @param f
	 */
	public final void add(Function f) {
		functions_forward.addFirst(f);
		functions_backward.addFirst(f);
	}
	
	/**
	 * Executes the function chain.
	 * @param arguments
	 */
	public final void execute(Object... arguments) {
		for (Function f : functions_forward) {
			f.execute(arguments);
		}
	}
	/**
	 * Executes the inverse functions, backwards, undoing the function chain.
	 * @param arguments
	 */
	public final void executeInverse(Object... arguments) {
		for (Function f : functions_backward) {
			f.executeInverse(arguments);
		}
	}
}
