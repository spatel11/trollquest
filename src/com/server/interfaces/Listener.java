/**
 * @author Stephen Patel (spatel11@unm.edu)
 */
package com.server.interfaces;

import com.server.State;

/**
 * Listeners are responsible for listening for input from a {@link Sender} over
 * a Socket connection. It must also provide a {@link Sender} in order to
 * transmit data over a Socket connection to another Listener. In addition, a
 * Listener must provide access to the {@link State} datastructure it provides
 * to {@link ComparableCommand}s. Listeners are intended to be run on separate
 * {@link Thread}s, and as such, implement {@link Runnable}.
 * 
 * @author Stephen Patel
 * 
 */
public interface Listener extends Runnable {

	/**
	 * Get's the {@link Sender} this {@link Listener} uses to send
	 * {@link ComparableCommands}.
	 * 
	 * @return
	 */
	public Sender getSender();

	/**
	 * Get's the connection status of this {@link Listener}.
	 * 
	 * @return true if this Listeners {@link Socket} is connected, false
	 *         otherwise.
	 */
	public boolean isConnected();

	/**
	 * Get's the {@link State} object, that this {@link Listener} passes to
	 * {@link ComparableCommand}s.
	 * 
	 * @return the State object contained in this Listener.
	 */
	public State getState();

	/**
	 * Get's the name of {@link Player} of this listener.
	 * 
	 * @return name of the player this listener belongs to.
	 */
	public String getPlayerName();

	/**
	 * Set's this Listeners player name.
	 * 
	 * @param playerName
	 *            the name of the player.
	 */
	public void setPlayerName(String playerName);

	/**
	 * Get's the username of this {@link Listener}.
	 * 
	 * @return the username of this Listener.
	 */
	public String getUserName();

	/**
	 * Set's the username of this listener.
	 * 
	 * @param userName
	 *            the name of the user of this Listener.
	 */
	public void setUserName(String userName);

}
