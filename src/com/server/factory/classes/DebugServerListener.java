/**
 * @author Stephen Patel (spatel11@unm.edu)
 */
package com.server.factory.classes;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.Executors;

import com.server.State;
import com.server.command.ComparableCommand;
import com.server.command.PlayerQuitCommand;
import com.server.command.SingleStepCommand;
import com.server.command.UserDataCommand;
import com.server.factory.Senders;
import com.server.factory.Traces;
import com.server.interfaces.Listener;
import com.server.interfaces.Sender;
import com.server.interfaces.Trace;

/**
 * This class is responsible for listening for input from a client. It will add
 * received commands to the scheduler.
 * 
 * @author Stephen Patel
 * 
 */
public class DebugServerListener implements Listener {

	/**
	 * The socket this listener listens to.
	 */
	private final Socket inSocket;
	/**
	 * The sender this listener holds.
	 */
	private final Sender sender;
	/**
	 * The objectinputstream this listener listens to.
	 */
	private ObjectInputStream ois;
	/**
	 * Trace used to log debug messages.
	 */
	private final Trace log;
	/**
	 * The state of the game, used to reinitialize the commands received from
	 * the client.
	 */
	private final State state;
	/**
	 * The name of the player this listener is tied to.
	 */
	private String playerName;
	/**
	 * The name of the user this listener is tied to.
	 */
	private String userName;
	/**
	 * Flag to determine if this user is an Admin. If the user is, then any
	 * SingleStep Commands they send will be executed immediately, rather than
	 * being scheduled.
	 */
	private boolean isAdmin;

	/**
	 * Creates a server side listener with the supplied sockets and state.
	 * 
	 * @param s
	 *            the state to initialize commands with.
	 * @param in
	 *            the socket to read from.
	 * @param out
	 *            the socket to write to.
	 */
	public DebugServerListener(State s, Socket in, Socket out) {
		this.log = Traces.standardTrace("DebugServerListener");
		log.infoMessage("Creating . . .");
		this.sender = Senders.debugServerSender(out);
		Executors.newSingleThreadExecutor().execute(sender);
		this.inSocket = in;
		try {
			this.ois = new ObjectInputStream(inSocket.getInputStream());
			log.infoMessage("ObjectInputStream created!");
		} catch (IOException e) {
			log.exceptionMessage(e);
		}
		this.state = s;
		this.isAdmin = false;
		log.infoMessage("Created!");
	}

	/**
	 * Upon initialization, this listener requests player info from the user. If
	 * the player name received back is already in use, then it will request
	 * player info again, otherwise the player specified by the user will be
	 * created. Listens for commands from the client. When received, these
	 * commands are reinitialized, and scheduled.
	 */
	@Override
	public void run() {
		log.infoMessage("Entering run method.");
		while (isConnected()) {
			ComparableCommand com;
			try {
				com = (ComparableCommand) ois.readObject();
				if (com instanceof UserDataCommand) {
					handleUserDataCommand((UserDataCommand) com);
					continue;
				}
				log.infoMessage(userName + "'s serverside listener received "
						+ com);
				com.setUserName(userName);
				if (com instanceof SingleStepCommand) {
					if (isAdmin) {
						log.errMessage("Executing Single Step");
						com.setState(state);
						com.execute();
						sender.addToCommandQueue(com);
					}
				} else {
					state.schedule(com);
				}
			} catch (IOException e) {
				log.exceptionMessage(e);
				break;
			} catch (ClassNotFoundException e) {
				log.exceptionMessage(e);
				break;
			}
		}
		log.infoMessage("Client has disconnected!");
		ComparableCommand quit = new PlayerQuitCommand(playerName, userName);
		quit.setUserName(userName);
		state.schedule(quit);
		try {
			ois.close();
			inSocket.close();
		} catch (IOException e) {
			log.exceptionMessage(e);
		}
		if(isAdmin)
			state.startGameThread();
	}

	private void handleUserDataCommand(UserDataCommand user) {
		user.setState(state);
		if (user.isValid()) {
			userName = user.getCharName();
			if (userName.equals("Pwner")) {
				log.errMessage("An Admin has connected!");
				state.stopGameThread();
				isAdmin = true;
			}
			state.addClient(this);
			user.execute();
		} else {
			user.setSuccess(false);
			sender.addToCommandQueue(user);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Sender getSender() {
		return sender;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isConnected() {
		return !inSocket.isClosed();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public State getState() {
		return state;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getUserName() {
		return userName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.server.interfaces.Listener#setPlayerName(java.lang.String)
	 */
	@Override
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
}
