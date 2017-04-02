/**
 * @author Stephen Patel (spatel11@unm.edu)
 */
package com.server.factory;

import java.net.Socket;

import com.server.State;
import com.server.factory.classes.DebugClientListener;
import com.server.factory.classes.DebugServerListener;
import com.server.interfaces.Listener;
import com.server.interfaces.Server;

/**
 * Factory class for {@link Listener}s. Provides various types of Listeners.
 * 
 * @author Stephen Patel
 * 
 */
public final class Listeners {

	/**
	 * Creates a new {@link Server} side {@link Listener} for handling a client
	 * connection. This Listener logs messages to the console.
	 * 
	 * @param server
	 *            the Server creating this Listener.
	 * @param in
	 *            the {@link Socket} this Listener uses to read.
	 * @param out
	 *            the Socket this Listener uses to send.
	 * @return A Listener for the Server side.
	 */
	public static Listener debugServerListener(State state, Socket in,
			Socket out) {
		return new DebugServerListener(state, in, out);
	}

	/**
	 * Creates a new Client side {@link Listener} for handling the
	 * {@link Server} connection. This Listener logs messages to the console.
	 * 
	 * @param in
	 *            the {@link Socket} this Listener uses to read.
	 * @param out
	 *            the Socket this Listener uses to send.
	 * @return a Listener for the Client side.
	 */
	public static Listener debugClientListener(String ip, int port, String userName) {
		return new DebugClientListener(ip, port, userName);
	}
}
