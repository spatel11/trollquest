/**
 * @author Stephen Patel (spatel11@unm.edu)
 */
package com.server.interfaces;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

import com.server.State;

/**
 * Servers are responsible for accepting {@link Socket} connections, and
 * spinning off {@link Listener}s and {@link Sender}s to communicate with
 * Clients. It is also responsible for providing access to Clients, Scheduling
 * services, and the State.
 * 
 * @author Stephen Patel
 * 
 */
public interface Server extends Runnable {

	/**
	 * Get the port this {@link Server} is listening on.
	 * 
	 * @return the port this server is listening on.
	 */
	public int getPort();

	/**
	 * Get the {@link SocketAddress} of this {@link Server}s
	 * {@link ServerSocket}.
	 * 
	 * @return the ServerSockets SocketAddress.
	 */
	public SocketAddress getAddress();

	/**
	 * Get the {@link State} of this {@link Server}.
	 * 
	 * @return the State stored in this server.
	 */
	public State getState();

	/**
	 * Checks to see if this server is running.
	 * 
	 * @return true if the server is running, false otherwise.
	 */
	public boolean isRunning();

	/**
	 * Shuts down the server.
	 */
	public void shutDown();

}
