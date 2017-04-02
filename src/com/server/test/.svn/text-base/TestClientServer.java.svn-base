/**
 * @author Stephen Patel (spatel11@unm.edu)
 */
package com.server.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.server.command.UserDataCommand;
import com.server.factory.Listeners;
import com.server.factory.Servers;
import com.server.interfaces.Listener;
import com.server.interfaces.Server;

/**
 * Class for testing Client/Server/Command interactions.
 * 
 * @author Stephen Patel
 * 
 */
public final class TestClientServer {

	/**
	 * Tests Initial connection, and data requests from the server.
	 * 
	 * @param args
	 *            unused
	 */
	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		String ip = "127.0.0.1";
		int port = 1337;
		Server server = Servers.debugServer(port, 1000);
		exec.execute(server);
		Listener client = Listeners.debugClientListener(ip, port, "Stephen");
		exec.execute(client);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		client.getSender().addToCommandQueue(new UserDataCommand("Stephen"));
	}
}
