/**
 * @author Stephen Patel (spatel11@unm.edu)
 */
package com.server.factory;

import com.server.interfaces.Trace;

/**
 * Factory for Traces. Provides various implementations of Traces for debugging
 * purposes.
 * 
 * @author Stephen Patel
 * 
 */
public final class Traces {

	/**
	 * A Trace that logs it's messages to the console.
	 * 
	 * @param name
	 *            the name to log messages under.
	 * @return a standard trace.
	 */
	public static Trace standardTrace(final String name) {
		return new Trace() {

			@Override
			public void infoMessage(String message) {
				//System.out.println(name + "INFO: " + message);
			}

			@Override
			public void errMessage(String message) {
				//System.err.println(name + "ERROR: " + message);
			}

			@Override
			public void exceptionMessage(Exception e) {
//				System.out.print(name + ":");
//				e.printStackTrace();
			}

		};
	}

}
