/**
 * @author Stephen Patel (spatel11@unm.edu)
 */
package com.server.factory.classes;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

import com.server.command.ComparableCommand;
import com.server.command.PlayerQuitCommand;
import com.server.factory.Traces;
import com.server.interfaces.Sender;
import com.server.interfaces.Trace;

/**
 * This class is responsible for sending Commands to the server. It runs on it's
 * own thread and sends commands from a Queue that UI may access to add commands
 * to. If there are no commands to send, the sender blocks until commands become
 * available to send.
 * 
 * @author Stephen Patel
 * 
 */
public class DebugClientSender implements Sender {

	/**
	 * The ObjectOutputStream this Sender uses to send.
	 */
	private ObjectOutputStream oos;
	/**
	 * Trace used to log messages.
	 */
	private final Trace log;
	/**
	 * The Socket this Sender uses to communicate.
	 */
	private final Socket out;
	/**
	 * Set of Commands that this Sender has sent, that it has not received back.
	 * If a command is sent, it is added to this set. Another equal command may
	 * not be sent until the original command returns.
	 */
	private final Set<ComparableCommand> sentCommands;
	/**
	 * The Commands that are Queued up to send.
	 */
	private final BlockingDeque<ComparableCommand> commandsToSend;

	/**
	 * Creates a new Sender with the input socket. Initializes Data structures.
	 * 
	 * @param outSocket
	 *            the socket for this sender to send on.
	 */
	public DebugClientSender(Socket outSocket) {
		this.log = Traces.standardTrace("DebugClientSender");
		log.infoMessage("Creating . . .");
		this.out = outSocket;
		try {
			outSocket.setReceiveBufferSize(65536);  //Used to fix serialization on Ians machine
			outSocket.setSendBufferSize(65536);  //Used to fix serialization on Ians machine
			this.oos = new ObjectOutputStream(outSocket.getOutputStream());
			log.infoMessage("ObjectOutputStream created!");
		} catch (IOException e) {
			log.exceptionMessage(e);
		}
		this.sentCommands = Collections
				.newSetFromMap(new ConcurrentHashMap<ComparableCommand, Boolean>());
		this.commandsToSend = new LinkedBlockingDeque<ComparableCommand>();
		log.infoMessage("Created!");
	}

	/**
	 * Takes Commands from the front of the queue and sends them if they have
	 * not already been sent. If the Queue is empty, this method will block
	 * until a command becomes available.
	 */
	@Override
	public void run() {
		while (!out.isClosed()) {
			ComparableCommand com;
			try {
				com = commandsToSend.takeFirst();
				if(com instanceof PlayerQuitCommand)
					out.close();
				if (sentCommands.contains(com)) {
					log.infoMessage(com + " has already been sent");
					continue;
				}
				sentCommands.add(com);
				oos.writeObject(com);
				oos.flush();
				oos.reset();
				log.infoMessage("Sending " + com);
			} catch (IOException e) {
				log.exceptionMessage(e);
				break;
			} catch (InterruptedException e) {
				log.exceptionMessage(e);
				break;
			}
		}
		try {
			oos.close();
			out.close();
		} catch (IOException e) {
			log.exceptionMessage(e);
		}
		System.exit(0);
	}

	/**
	 * Adds a command to the end of the queue to be sent.
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
	 * Removes a command from the set of sent commands. Meant to be called by
	 * the this senders listener.
	 */
	@Override
	public void removeCommand(ComparableCommand com) {
		sentCommands.remove(com);
		log.infoMessage("Removed " + com + " from the sent set");
	}
}
