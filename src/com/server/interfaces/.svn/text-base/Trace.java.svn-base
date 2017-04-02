/**
 * @author Stephen Patel (spatel11@unm.edu)
 */
package com.server.interfaces;

/**
 * Traces are used for logging purposes. The way in which Trace messages are
 * handled is up to the implementation. For example, perhaps one implementation
 * would log messages to the console, while another would log messages to a
 * textbox or chatoverlay.
 * 
 * @author Stephen Patel
 * 
 */
public interface Trace {

	/**
	 * Log an informational message.
	 * 
	 * @param message
	 *            the message to log.
	 */
	public void infoMessage(String message);

	/**
	 * Log an error message.
	 * 
	 * @param message
	 *            the message to log.
	 */
	public void errMessage(String message);

	/**
	 * Handle an exception message.
	 * 
	 * @param e
	 *            the exception to log the stack trace of.
	 */
	public void exceptionMessage(Exception e);

}
