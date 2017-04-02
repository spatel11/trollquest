/**
 * @author Stephen Patel (spatel11@unm.edu)
 */
package com.server.factory;

import java.net.Socket;

import com.server.factory.classes.DebugClientSender;
import com.server.factory.classes.DebugServerSender;
import com.server.interfaces.Sender;

/**
 * Factory class for {@link Senders}. Provides various Senders.
 * 
 * @author Stephen Patel
 * 
 */
public final class Senders {

	/**
	 * Creates a new {@link Sender} for the client side.
	 * 
	 * @param out
	 *            the socket for sending
	 * @return a client side sender.
	 */
	public static Sender debugClientSender(Socket out) {
		return new DebugClientSender(out);
	}

	/**
	 * Creates a new {@link Sender} for the server side.
	 * 
	 * @param out
	 *            the socket for sending
	 * @return a server side sender.
	 */
	public static Sender debugServerSender(Socket out) {
		return new DebugServerSender(out);
	}
}
