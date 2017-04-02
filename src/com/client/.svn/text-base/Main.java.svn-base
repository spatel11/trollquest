package com.client;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.UIManager;

import com.client.Dialog.JWorldDialogPortIP;
import com.client.Dialog.JWorldDialogUser;
import com.client.GUI.GUIMain;
import com.game.characters.Player;
import com.game.environment.GameMap;
import com.server.State;
import com.server.command.ComparableCommand;
import com.server.command.PlayerQuitCommand;
import com.server.command.UserDataCommand;
import com.server.factory.Listeners;
import com.server.factory.Servers;
import com.server.factory.classes.Profile;
import com.server.interfaces.Listener;
import com.server.interfaces.Server;
import com.utilities.Function;
import com.utilities.UtilityTime;

/**
 * Main class for the client.
 * @author Ian
 */
public final class Main {
	/** What action the player is doing. */
	public static MoveType move_type = MoveType.MOVE; //TODO: move to character attribute.
	/** Possible actions. */
	public static enum MoveType {
		MOVE,MOVE_TO_ATTACK,MOVE_TO_CAST;
	};
	
	//private final static int SOCKET_TIMEOUT = 1000;
	/** The IP, expressed as a string. */
	private volatile String ip = null;
	/** The port. */
	private volatile int port;
	/** Whether a server should be made. */
	private volatile boolean making_server;
	
	/** The listener. */
	public static volatile Listener client;
	/** The user's name. */
	public static volatile String username = null;
	/** The user's profile. */
	public static Profile userprofile;
	/** The player's name. */
	public static volatile String player_name = "";
	/** The map name. */
	private static String current_map_name = "CenterMap.map";
	
	/** Returns the character representing self. */
	public final static Player getSelf() {
		if(!(getState().getCharacter(player_name) instanceof Player))
			return null;
		return (Player)( getState().getCharacter(player_name) );
	}
	/** Returns self's name. */
	public final static String getSelfName() {
		return player_name;
	}
	/** Returns the current game map. */
	public final static GameMap getCurrentMap() {
		if (getSelf() == null) return getState().getGameMap(current_map_name);
		return getState().getGameMap(getSelf().getMap());
	}
	/** Returns the current state. */
	public final static State getState() {
		return client.getState();
	}
	/** Adds a command to be sent in the sending/receiving thread. */
	public final static void sendCommand(ComparableCommand cmd) {
		client.getSender().addToCommandQueue(cmd);
	}
	/** Quits the game.  The server will handle closing the connection and telling everyone. */
	public final static void quit() {
		sendCommand(new PlayerQuitCommand(getSelfName(),null));
	}
	
	/** Sets up the platform's look and feel. */
	private final void setupLookAndFeel() {
		try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}
	}
	/** Gets the IP and username from the user. */
	private final void setupPortIPUser() {
		new JWorldDialogPortIP(new Function() {
			@Override public void execute(Object... arguments) {
				ip = (String)(arguments[0]);
				port = (Integer)(arguments[1]);
				making_server = (Boolean)(arguments[2]);
			}
		});
		new JWorldDialogUser(new Function() {
			@Override public void execute(Object... arguments) {
				username = (String)(arguments[0]);
			}
		});
	}
	/** Sets up the connection. */
	private final void setupConnection() {
		ExecutorService exec = Executors.newCachedThreadPool();
		
		if (making_server) {
			Server server = Servers.debugServer(port,1);
			exec.execute(server);
		}
		
		/*try {
			//The order these sockets are created in matters!
			socket_out = new Socket(ip,port);
			socket_in  = new Socket(ip,port);
			//socket_out.setSoTimeout(SOCKET_TIMEOUT);
			//socket_in .setSoTimeout(SOCKET_TIMEOUT);
			client = Listeners.debugClientListener(socket_in,socket_out);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			new JWorldDialogError("An error occurred while attempting to connect.\nSee the console for a stack trace.\n"+e.getMessage(),"Connection Error",null);
		} catch (IOException e) {
			e.printStackTrace();
			new JWorldDialogError("An error occurred while attempting to connect.\nSee the console for a stack trace.\n"+e.getMessage(),"Connection Error",null);
		}*/
		client = Listeners.debugClientListener(ip,port,username);
		
		MainHandlerInput.getInstance(); //After intitialization of client, before client is run.
		
		exec.execute(client);
		
		while (client==null||client.getSender()==null);
		sendCommand(new UserDataCommand(username));
	}
	/** Main. */
	private Main() {
		setupLookAndFeel();
		
		setupPortIPUser(); while (username==null);

		setupConnection();
		
		while (getCurrentMap()==null) {
			//System.out.println("Not doing anything because the map is null.  Waiting.");
			UtilityTime.sleep(100);
		}
		while (getSelf()==null) {
			//System.out.println("Not doing anything because the player is null.  Waiting.");
			UtilityTime.sleep(100);
		}
		
		//while (getCurrentMap()==null||getSelf()==null);
		/*try {
			//None
			socket_out.setSoTimeout(0);
			socket_in .setSoTimeout(0);
		} catch (SocketException e) {
			e.printStackTrace();
		}*/

		GUIMain.getInstance();
	}
	
	/** Main method. */
	public final static void main(String[] args) {
		new Main();
	}
}