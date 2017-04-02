/**
 * @author Stephen Patel (spatel11@unm.edu)
 */
package com.server.factory.classes;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import com.server.command.ComparableCommand;
import com.server.factory.Traces;
import com.server.interfaces.Sender;
import com.server.interfaces.Trace;

/**
 * This class is responsible for sending commands from the server to the Client.
 * Contains a Queue of commands to send. The game loop may access this queue to
 * add commands. The sender keeps sending commands, while commands are
 * available. If there are no commands available, then the run method blocks
 * until commands become available.
 * 
 * @author Stephen Patel
 * 
 */
public class DebugServerSender implements Sender {

	/**
	 * The ObjectOutputStream this sender uses to send Commands.
	 */
	private ObjectOutputStream oos;
	/**
	 * The Trace used to log messages.
	 */
	private final Trace log;
	/**
	 * The socket this Client uses to communicate.
	 */
	private final Socket out;
	/**
	 * The Queue of commands to send.
	 */
	private final BlockingDeque<ComparableCommand> commandsToSend;

	/**
	 * Creates a new Sender with the specified Socket. Initializes the command
	 * queue.
	 * 
	 * @param outSocket
	 */
	public DebugServerSender(Socket outSocket) {
		this.log = Traces.standardTrace("DebugServerSender");
		log.infoMessage("Creating . . .");
		this.out = outSocket;
		try {
			this.oos = new ObjectOutputStream(outSocket.getOutputStream());
			log.infoMessage("ObjectOutputStream created!");
		} catch (IOException e) {
			log.exceptionMessage(e);
		}
		this.commandsToSend = new LinkedBlockingDeque<ComparableCommand>();
		log.infoMessage("Created!");
	}

	/**
	 * Sends commands as long as commands are available to send. If no commands
	 * are available, this method blocks until there are.
	 */
	@Override
	public void run() {
		while (!out.isClosed()) {
			ComparableCommand com;
			try {
				com = commandsToSend.takeFirst();
				log.infoMessage("Sending " + com);
				oos.writeObject(com);
				oos.flush();
				oos.reset();
				log.infoMessage("Sent " + com);
			} catch (IOException e) {
				log.exceptionMessage(e);
				break;
			} catch (InterruptedException e) {
				log.exceptionMessage(e);
				break;
			}
		}
		try {
			out.close();
			oos.close();
		} catch (IOException e) {
			log.exceptionMessage(e);
		}
	}

	/**
	 * Adds a Command to the end of the queue.
	 */
	@Override
	public void addToCommandQueue(ComparableCommand com) {
		try {
			commandsToSend.putLast(com);
			log.infoMessage("Added " + com + " to the send queue");
		} catch (InterruptedException e) {
			log.exceptionMessage(e);
		}
	}

	/**
	 * ServerSenders do not support this operation.
	 */
	@Override
	public void removeCommand(ComparableCommand com) {
		throw new UnsupportedOperationException(
				"The DebugServerSender does not support the removeCommand method!");
	}
}
