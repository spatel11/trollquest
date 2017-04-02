/**
 * @author Stephen Patel (spatel11@unm.edu)
 */
package com.server.factory.classes;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ServerSocketFactory;

import com.game.characters.content.monsters.Bear;
import com.game.characters.content.monsters.Spider;
import com.game.environment.GameMap;
import com.game.environment.tiles.TileException;
import com.server.State;
import com.server.factory.Listeners;
import com.server.factory.Traces;
import com.server.interfaces.Listener;
import com.server.interfaces.Server;
import com.server.interfaces.Trace;
import com.utilities.GameInitializer;

/**
 * This class is responsible for listening for clients, and giving clients
 * handlers. A new handler is given to each connecting client. Each handler has
 * a reference to the game state.
 * 
 * @author Stephen Patel
 * 
 */
public class DebugServer implements Server {

	/**
	 * The ExecutorService that provides threads for Listeners.
	 */
	private final ExecutorService exec;
	/**
	 * The port this server is listening on.
	 */
	private final int port;
	/**
	 * The ServerSocket this Server uses to connect clients. The socket created
	 * is meant to be read from.
	 */
	private ServerSocket inServer;
	/**
	 * The ServerSocket this Server uses to connect clients. The socket created
	 * is meant to be written to.
	 */
	private ServerSocket outServer;
	/**
	 * Trace used to log errors.
	 */
	private final Trace log;
	/**
	 * The global state.
	 */
	private final State globalState;

	/**
	 * Creates a server that listens on the specified port, with a game loop
	 * that executes every gameSpeed milliseconds.
	 * 
	 * @param port
	 *            the port to listen on.
	 * @param gameSpeed
	 *            the Speed of the game. The lower the number, the faster the
	 *            gameloop executes.
	 */
	public DebugServer(int port, int gameSpeed) {
		this.log = Traces.standardTrace("DebugServer");
		log.infoMessage("Creating . . .");
		this.globalState = new State(3, 1);
		GameInitializer.initGame(globalState);
		// this.globalState = generateState();
		this.exec = Executors.newCachedThreadPool();
		this.port = port;
		try {
			this.inServer = ServerSocketFactory.getDefault()
					.createServerSocket(port);
			this.outServer = ServerSocketFactory.getDefault()
					.createServerSocket(port + 1);
			inServer.setReceiveBufferSize(65536);  //Used to fix serialization on Ians machine
			outServer.setReceiveBufferSize(65536);  //Used to fix serialization on Ians machine
			log.infoMessage("Created ServerSocket listener");
		} catch (IOException e) {
			log.exceptionMessage(e);
		}
		this.globalState.startGameThread();
		log.infoMessage("Created!");
	}

	/**
	 * Builds the gamestate at initialization. For Testing purposes.
	 * 
	 * @return the state of the game.
	 */
	@SuppressWarnings("unused")
	private State generateState() {
		State s = new State(3, 1);
		log.infoMessage("State created.");
		GameMap g = new GameMap(new File(GameMap.TEST_MAP_DIRECTORY
				+ "testMap.map"));
		s.addGameMap(g);
		Bear b = new Bear(1);
		Spider pig = new Spider(1);
		b.setMap("testMap.map");
		pig.setMap("testMap.map");
		try {
			g.getTile(12, 12).setOccupant(b);
			g.getTile(14, 14).setOccupant(pig);
		} catch (TileException e) {
			e.printStackTrace();
		}
		s.addCharacter(b);
		s.addCharacter(pig);
		log.infoMessage("GameMap added.");
		return s;
	}

	/**
	 * Listens for Client connections, and creates client handlers for them.
	 */
	@Override
	public void run() {
		log.infoMessage("Running . . .");
		while (true) {
			try {
				// Create a read socket for a connecting client.
				Socket inSocket = inServer.accept();
				log.infoMessage("inSocket created!");
				// Create a write Socket for a connecting client.
				Socket outSocket = outServer.accept();
				log.infoMessage("outSocket created!");
				// Create a server side listener for a connecting client.
				Listener l = Listeners.debugServerListener(globalState,
						inSocket, outSocket);
				// Begin running that listener on a separate thread.
				exec.execute(l);
			} catch (IOException e) {
				log.exceptionMessage(e);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getPort() {
		return port;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SocketAddress getAddress() {
		return inServer.getLocalSocketAddress();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public State getState() {
		return globalState;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.server.interfaces.Server#isRunning()
	 */
	@Override
	public boolean isRunning() {
		return !inServer.isClosed();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.server.interfaces.Server#shutDown()
	 */
	@Override
	public void shutDown() {
		synchronized (globalState) {
			for (Listener l : globalState.getClients()) {
				globalState.removeClient(l.getUserName());
			}
			try {
				inServer.close();
				outServer.close();
			} catch (IOException e) {
				log.exceptionMessage(e);
			} finally {
				System.exit(0);
			}
		}
	}
}
