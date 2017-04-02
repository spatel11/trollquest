/**
 * @author Stephen Patel (spatel11@unm.edu)
 */
package com.server.interfaces;

import com.server.command.ComparableCommand;

/**
 * Senders are responsible for transmitting {@link ComparableCommand}s to the
 * connected {@link Listener} over a Socket connection.
 * 
 * @author Stephen Patel
 * 
 */
public interface Sender extends Runnable {

	/**
	 * Adds a command to the Senders queue.
	 * 
	 * @param com
	 *            the command to be added.
	 */
	public void addToCommandQueue(ComparableCommand com);

	/**
	 * Removes a command from the set of Sent commands. OPTIONAL METHOD.
	 * 
	 * @param com
	 *            the command to remove from the sent set.
	 */
	public void removeCommand(ComparableCommand com);

}
