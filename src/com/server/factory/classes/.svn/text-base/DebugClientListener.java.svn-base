/**
 * @author Stephen Patel (spatel11@unm.edu)
 */
package com.server.factory.classes;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.concurrent.Executors;

import javax.net.SocketFactory;
import javax.swing.JOptionPane;

import com.server.State;
import com.server.command.ComparableCommand;
import com.server.factory.Senders;
import com.server.factory.Traces;
import com.server.interfaces.Listener;
import com.server.interfaces.Sender;
import com.server.interfaces.Trace;

/**
 * This class is responsible for listening for commands from the server. It runs
 * on it's own thread. It notifies the GUI with commands that are sent from the
 * server.
 * 
 * @author Stephen Patel
 * 
 */
public class DebugClientListener extends Observable implements Listener {

	/**
	 * Socket this Listener is listening on.
	 */
	private Socket inSocket;
	/**
	 * The sender this Listener contains.
	 */
	private Sender sender;
	/**
	 * The ObjectInputStream this Listener listens to.
	 */
	private ObjectInputStream ois;
	/**
	 * Trace used to log debug messages.
	 */
	private final Trace log;
	/**
	 * State of the game.
	 */
	private State state;
	/**
	 * Player this listener belongs too.
	 */
	private String playerName;
	/**
	 * The name of the user this client is tied to.
	 */
	private String userName;
	/**
	 * The ip this Listener is connected to.
	 */
	private final String ip;
	/**
	 * The port this listener is connected on.
	 */
	private final int port;

	/**
	 * Constructs DebugClientListener with the input Sockets.
	 * 
	 * @param in
	 *            the socket to read on.
	 * @param out
	 *            the socket to write too.
	 */
	public DebugClientListener(String ip, int port, String userName) {
		this.log = Traces.standardTrace("DebugClientListener");
		log.infoMessage("Creating . . .");
		this.state = new State(0, 0);
		this.ip = ip;
		this.port = port;
		this.userName = userName;
		log.infoMessage("Created!");
	}

	private void connect() {
		Socket out = null;
		log.infoMessage("Connecting");
		try {
			out = SocketFactory.getDefault().createSocket(ip, port);
			this.inSocket = SocketFactory.getDefault().createSocket(ip,
					port + 1);
			inSocket.setReceiveBufferSize(65536); //Used to fix serialization on Ians machine
			inSocket.setSendBufferSize(65536);  //Used to fix serialization on Ians machine
			this.sender = Senders.debugClientSender(out);
			this.ois = new ObjectInputStream(inSocket.getInputStream());
			log.infoMessage("ObjectInputStream created!");
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, e.toString(), "PORT ERROR", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.toString(), "PORT ERROR", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		Executors.newSingleThreadExecutor().execute(sender);
		log.infoMessage("Connected");
	}

	/**
	 * Listens for commands from the server. When a command is received, if it
	 * exists in the set of Sent commands, it is removed. If was successfully
	 * executed on the server, then it is executed here.
	 */
	@Override
	public void run() {
		connect();
		while (isConnected()) {
			ComparableCommand initCom = null;
			try {
				initCom = (ComparableCommand) ois.readObject();
				log.infoMessage("Received " + initCom);
				sender.removeCommand(initCom);
				initCom.setState(state);
				this.setChanged();
				if (initCom.getSuccess()) {
					log.infoMessage("Executing: " + initCom);
					initCom.execute();
				}
				this.notifyObservers(initCom);
			} catch (IOException e) {
				log.exceptionMessage(e);
				break;
			} catch (ClassNotFoundException e) {
				log.exceptionMessage(e);
				break;
			}
		}
		log.infoMessage("Disconnecting");
		try {
			ois.close();
			inSocket.close();
		} catch (IOException e) {
			log.exceptionMessage(e);
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
}
