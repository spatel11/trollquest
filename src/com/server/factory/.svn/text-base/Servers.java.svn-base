/**
 * @author Stephen Patel (spatel11@unm.edu)
 */
package com.server.factory;

import com.server.factory.classes.DebugServer;
import com.server.interfaces.Server;

/**
 * Factory class for {@link Servers}. Provides various server implementations.
 * 
 * @author Stephen Patel
 * 
 */
public final class Servers {

	/**
	 * A Server that logs it's messages to the console.
	 * 
	 * @param port
	 *            the port for this server to listen on.
	 * @param gameSpeed
	 *            the number of milliseconds between each clock tick.
	 * @return a debug server.
	 */
	public static Server debugServer(int port, int gameSpeed) {
		return new DebugServer(port, gameSpeed);
	}
}
