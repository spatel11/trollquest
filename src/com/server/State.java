/**
 * @author Stephen Patel (spatel11@unm.edu)
 */
package com.server;

import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.ai.AST;
import com.client.Message;
import com.game.characters.Creature;
import com.game.characters.Creature.Stat;
import com.game.characters.GameCharacter;
import com.game.characters.Monster;
import com.game.characters.NPC;
import com.game.characters.Player;
import com.game.characters.Scriptable;
import com.game.environment.GameMap;
import com.game.environment.tiles.TileException;
import com.server.command.ComparableCommand;
import com.server.factory.classes.Profile;
import com.server.interfaces.Listener;

/**
 * State objects are responsible for storing and providing access to the current
 * state of the game. State contains the {@link GameMap}s in the world, the
 * {@link GameCharacter}s in the world, and the chat log. On the server side,
 * the State contains the clients in the game, the AI ASTs, and the
 * {@link GameScheduler}.
 * 
 * @author Stephen Patel
 * 
 */
public class State implements Serializable {
	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = 7831837240382908971L;
	/**
	 * The world represented as a {@link Map} from map name to {@link GameMap}.
	 */
	private Map<String, GameMap> world;
	/**
	 * {@link Map} of the {@link GameCharacter}s in the world. Character name to
	 * GameCharacter.
	 */
	private Map<String, GameCharacter> characters;
	/**
	 * The chat log. Contains messages that have been received.
	 */
	private transient Deque<Message> chatLog;
	/**
	 * The number of messages that the chat log will store. If the chat log
	 * exceeds this limit, the oldest message will be removed, and the new
	 * message will be added.
	 */
	private int chatLogSize;
	/**
	 * Contains all the Clients who are currently connected. This is null on the
	 * client side, and is not serialized with the state. Username to Listener.
	 */
	private final transient Map<String, Listener> clients;
	/**
	 * The Scheduler for the game. This is null on the client side, and is not
	 * serialized with the state.
	 */
	private final transient GameScheduler scheduler;
	/**
	 * The users in the game. This is null on the client side and is not
	 * serialized with the state. Username to Listener.
	 */
	private final transient Map<String, Profile> users;
	/**
	 * The timer to perform the main game loop.
	 */
	private transient Timer execTimer;
	/**
	 * The speed of the game in milliseconds.
	 */
	private final int gameSpeed;
	/**
	 * The {@link AST} for AI. The Client does not have a copy.
	 */
	private final transient AST ast;
	/**
	 * Each client has their own schedule on the server side. Username to
	 * PriorityBlockingQueue.
	 */
	private final transient Map<String, PriorityBlockingQueue<ComparableCommand>> schedules;
	/**
	 * Provides access to a playercharacters username. charactername to
	 * Username.
	 */
	private final transient Map<String, String> charNameToUserName;
	/**
	 * Maps mapname to the Characters that are on that map.
	 */
	private final Map<String, List<GameCharacter>> charsOnMap;

	/**
	 * Constructs a {@link State} with the input chatLogSize, and initializes
	 * the data structures.
	 * 
	 * @param chatLogSize
	 *            the number of messages you want the chatLog to store.
	 */
	public State(int chatLogSize, int gameSpeed) {
		this.world = new ConcurrentHashMap<String, GameMap>();
		this.characters = new ConcurrentHashMap<String, GameCharacter>();
		this.chatLog = new LinkedBlockingDeque<Message>();
		this.chatLogSize = chatLogSize;
		this.gameSpeed = gameSpeed;
		this.clients = new ConcurrentHashMap<String, Listener>();
		this.scheduler = new GameScheduler(this);
		this.users = new ConcurrentHashMap<String, Profile>();
		this.execTimer = new Timer();
		this.ast = new AST("data" + File.separatorChar + "NanoScripts" + File.separatorChar +"gameScripts");
		this.schedules = new ConcurrentHashMap<String, PriorityBlockingQueue<ComparableCommand>>();
		this.charNameToUserName = new ConcurrentHashMap<String, String>();
		this.charsOnMap = new ConcurrentHashMap<String, List<GameCharacter>>();
	}
	
	public boolean containsUser(String userName){
		return users.containsKey(userName);
	}

	/**
	 * Adds an entry to the charnametousername map.
	 * 
	 * @param charName
	 *            the name of the users player character.
	 * @param userName
	 *            the name of the user with the input charactername.
	 */
	public void addCharNameToUserName(String charName, String userName) {
		charNameToUserName.put(charName, userName);
	}

	/**
	 * Get's the username of the input playername.
	 * 
	 * @param charName
	 *            the name of the character whose username you want.
	 * @return the username of the input playername, null if this is called on
	 *         the client side.
	 */
	public String getUserName(String charName) {
		if (charNameToUserName == null)
			return null;
		return charNameToUserName.get(charName);
	}

	/**
	 * Get's a users schedule.
	 * 
	 * @param userName
	 *            the name of the user whose schedule you want.
	 * @return the schedule of the input user, null if this is called on the
	 *         client side.
	 */
	public BlockingQueue<ComparableCommand> getSchedule(String userName) {
		if (schedules == null)
			return null;
		return schedules.get(userName);
	}

	/**
	 * This shouldonly be used for Player commands. Gives the command a state
	 * reference, updates it's preptime, and adds the input command to it's
	 * users schedule. Returns silently if the command is null.
	 * 
	 * @param com
	 *            the command to schedule.
	 */
	public void schedule(ComparableCommand com) {
		if (com == null)
			return;
		com.setState(this);
		com.updateTime(scheduler.getCurrentTime());
		schedules.get(com.getUserName()).put(com);
	}

	/**
	 * Get's the AST for the AI.
	 * 
	 * @return the AI AST.
	 */
	public AST getAST() {
		return ast;
	}

	/**
	 * Stops the Main Game execution for single stepping.
	 */
	public void stopGameThread() {
		if (execTimer == null)
			return;
		execTimer.cancel();
	}

	/**
	 * Starts the Main Game execution.
	 */
	public void startGameThread() {
		if (execTimer == null)
			return;
		this.execTimer.scheduleAtFixedRate(scheduler, 0, gameSpeed);
	}

	/**
	 * Performs one iteration of the Main Game loop.
	 */
	public void singleStep() {
		if (scheduler == null)
			return;
		scheduler.run();
	}

	/**
	 * Save's a Profile to a data\\playerdata\\username.prof
	 * 
	 * @param userName
	 *            the name of the profile to save.
	 */
	private void saveProfile(String userName) {
		if (users == null)
			return;
		Profile p = users.get(userName);
		if (p == null)
			return;
		ObjectOutputStream oos = null;
		File userData = new File(new File("").getAbsolutePath()
				+ "\\data\\playerdata\\" + userName + ".prof");
		try {
			userData.createNewFile();
			oos = new ObjectOutputStream(new FileOutputStream(userData));
			oos.writeObject(p);
			oos.flush();
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.err.println("Saving " + userName);
	}

	/**
	 * Gets a {@link Profile} with the input username. If such a profile does
	 * not exist, one will be created with the input username.
	 * 
	 * @param userName
	 *            the name of the user profile you want.
	 * @return the profile matching the input username or null if this is the
	 *         client side.
	 */
	public Profile getProfile(String userName) {
		if (users == null)
			return null;
		if (users.get(userName) != null)
			return users.get(userName);
		File userData = new File(new File("").getAbsolutePath()+ "\\data\\playerdata\\" + userName+".prof");
		Profile p = null;
		ObjectInputStream ois = null;
		if (!userData.exists())
			p = new Profile(userName);
		else {
			try {
				ois = new ObjectInputStream(new FileInputStream(userData));
				p = (Profile) ois.readObject();
				ois.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		users.put(p.getUserName(), p);
		schedules.put(p.getUserName(),
				new PriorityBlockingQueue<ComparableCommand>());
		return users.get(userName);
	}

	/**
	 * Gets the {@link GameScheduler}. If this is called on the Client side it
	 * will return null.
	 * 
	 * @return the scheduler for the game on the Server side, null on the client
	 *         side.
	 */
	public GameScheduler getScheduler() {
		return scheduler;
	}

	/**
	 * Removes the Client tied to the specified playername, as well as the
	 * {@link GameCharacter}. If this is called on the Client side it will
	 * return silently.
	 * 
	 * @param playerName
	 */
	public void removeClient(String userName) {
		if (clients == null)
			return;
		String playerName = clients.get(userName).getPlayerName();
		clients.remove(userName);
		// characters.remove(playerName);
		saveProfile(userName);
		users.remove(userName);
		schedules.remove(userName);
		if(playerName == null)
			return;
		charNameToUserName.remove(playerName);
	}

	/**
	 * Adds a Client to the game. If this is called on the client side it will
	 * silently return.
	 * 
	 * @param client
	 */
	public void addClient(Listener client) {
		if (clients == null)
			return;
		clients.put(client.getUserName(), client);
	}

	/**
	 * Get's the Collection of Clients currently connected.
	 * 
	 * @return the currently connected clients.
	 */
	public Collection<Listener> getClients() {
		if (clients == null)
			return Collections.emptyList();
		return clients.values();
	}

	/**
	 * Get's the client with the specifed playerName. If it does not exist or
	 * this is called on the client side, this method will return null.
	 * 
	 * @param playerName
	 *            the name of the player whose client you want.
	 * @return the client associated with the playerName. Or null if it does not
	 *         exist.
	 */
	public Listener getClient(String userName) {
		if (clients == null)
			return null;
		return clients.get(userName);
	}

	/**
	 * Gets a {@link GameMap} with the input name
	 * 
	 * @param mapName
	 *            the name of the GameMap you want.
	 * @return the GameMap with the specified name.
	 */
	public GameMap getGameMap(String mapName) {
		return world.get(mapName);
	}

	/**
	 * Adds the input {@link GameMap} to the world with the maps name as a key.
	 * 
	 * @param g
	 *            the GameMap you want to add to the world.
	 */
	public void addGameMap(GameMap g) {
		world.put(g.getName(), g);
		charsOnMap.put(g.getName(), new ArrayList<GameCharacter>());
	}

	/**
	 * Adds the input {@link GameCharacter} to the world with the characters
	 * name as a key.
	 * 
	 * @param c
	 *            the GameCharacter you want to add to the world.
	 */
	public void addCharacter(GameCharacter c) {
		characters.put(c.name, c);
		charsOnMap.get(c.getMap()).add(c);
	}

	/**
	 * Gets a {@link GameCharacter} from the world with the specified name.
	 * 
	 * @param name
	 *            the name of the GameCharacter you want.
	 * @return the GameCharacter with the specified name.
	 */
	public GameCharacter getCharacter(String name) {
		return characters.get(name);
	}

	/**
	 * Get's all the GameCharacters on a map.
	 * 
	 * @param mapName
	 *            the name of the GameMap you want the characters on.
	 * @return a List of all the gameCharacters on a map.
	 */
	public List<GameCharacter> getCharactersOnMap(String mapName) {
		return Collections.synchronizedList(Collections
				.unmodifiableList(charsOnMap.get(mapName)));
	}

	/**
	 * 
	 * @param mapName
	 * @return
	 */
	public List<Player> getLivingPlayersOnMap(String mapName) {
		List<Player> live = new ArrayList<Player>();
		for (GameCharacter g : getCharactersOnMap(mapName)) {
			if (g instanceof Player) {
				Player p = (Player) g;
				if (p.getCurrStat(Stat.HEALTHPOINTS) > 0)
					live.add(p);
			}
		}
		return live;
	}

	/**
	 * Gets a {@link Collection} of all characters in the game.
	 * 
	 * @return an unmodifiable collection of all characters in the game.
	 */
	public Collection<GameCharacter> getCharacters() {
		return Collections.unmodifiableCollection(characters.values());
	}

	/**
	 * Get's a {@link Set} of all characters who are controlled by the AI.
	 * 
	 * @return an unmodifiable Set of smart characters.
	 */
	public Set<GameCharacter> getIntelligentCharacters() {
		Set<GameCharacter> smart = new HashSet<GameCharacter>();
		for (GameCharacter c : getCharacters()) {
			if (c instanceof Scriptable)
				smart.add(c);
		}
		return Collections.unmodifiableSet(smart);
	}

	/**
	 * Get's a {@link Set} of all living {@link Creature}s in the game.
	 * 
	 * @return an unmodifiableSet of all Creature's in the game with above 0 hp.
	 */
	public Set<Creature> getLivingCreatures() {
		Set<Creature> live = new HashSet<Creature>();
		for (Creature c : getCreatures()) {
			if (c.getCurrStat(Stat.HEALTHPOINTS) > 0)
				live.add(c);
		}
		return Collections.unmodifiableSet(live);
	}

	/**
	 * Get's the Set of all Living Monsters in the game.
	 * 
	 * @return the Set of all Living Monsters in the game.
	 */
	public Set<Monster> getLivingMonsters() {
		Set<Monster> live = new HashSet<Monster>();
		for (Creature c : getCreatures()) {
			if (c.getCurrStat(Stat.HEALTHPOINTS) > 0 && c instanceof Monster)
				live.add((Monster) c);
		}
		return Collections.unmodifiableSet(live);
	}

	/**
	 * Get's a {@link Set} of all living {@link Player}s in the game.
	 * 
	 * @return an unmodifiableSet of all Players in the game with more than 0
	 *         hp.
	 */
	public Set<Player> getLivingPlayers() {
		Set<Player> live = new HashSet<Player>();
		for (Creature c : getLivingCreatures()) {
			if (c instanceof Player)
				live.add((Player) c);
		}
		return Collections.unmodifiableSet(live);
	}

	/**
	 * Get's a {@link Set} of all {@link NPC}s in the game.
	 * 
	 * @return an unmodifiable Set of all NPCs in the game.
	 */
	public Set<NPC> getNPCs() {
		Set<NPC> npcSet = new HashSet<NPC>();
		for (GameCharacter g : getCharacters()) {
			if (g instanceof NPC)
				npcSet.add((NPC) g);
		}
		return Collections.unmodifiableSet(npcSet);
	}

	/**
	 * Get's a {@link Set} of all {@link Creature}'s in the game.
	 * 
	 * @return an unmodifiable Set of all Creature's in the game.
	 */
	public Set<Creature> getCreatures() {
		Set<Creature> creatureSet = new HashSet<Creature>();
		for (GameCharacter g : getCharacters()) {
			if (g instanceof Creature)
				creatureSet.add((Creature) g);
		}
		return Collections.unmodifiableSet(creatureSet);
	}

	/**
	 * Get's a {@link Set} of all {@link Player}s in the game.
	 * 
	 * @return an unmodifiable Set of all Players in the game.
	 */
	public Set<Player> getPlayers() {
		Set<Player> playerSet = new HashSet<Player>();
		for (GameCharacter g : getCharacters()) {
			if (g instanceof Player)
				playerSet.add((Player) g);
		}
		return Collections.unmodifiableSet(playerSet);
	}

	/**
	 * Removes a {@link GameCharacter} with the specified name from the world.
	 * 
	 * @param name
	 *            the name of the GameCharacter you want to remove.
	 */
	public void removeCharacter(String name) {
		if(name == null)
			return;
		if(!characters.containsKey(name))
			return;
		GameCharacter g = characters.get(name);
		Point p = g.getPosition();
		GameMap map = world.get(g.getMap());
		map.getTile(p.x, p.y).removeOccupant();
		characters.remove(name);
		charsOnMap.get(g.getMap()).remove(g);
	}

	/**
	 * Adds a message to the chat log. If the chat log is full, then the oldest
	 * message will be removed.
	 * 
	 * @param message
	 *            the message to add.
	 */
	public void addMessage(Message message) {
		chatLog.addLast(message);
		if (chatLog.size() > chatLogSize)
			chatLog.pollFirst();
	}

	/**
	 * Get's the entire chatlog.
	 * 
	 * @return the chatlog.
	 */
	public Deque<Message> getChatLog() {
		return chatLog;
	}

	/**
	 * Gets the chat log size.
	 * 
	 * @return the size of the chat log.
	 */
	public int getChatLogSize() {
		return chatLogSize;
	}

	/**
	 * Get's the speed of the game.
	 * 
	 * @return the number of milliseconds between clock ticks.
	 */
	public int getGameSpeed() {
		return gameSpeed;
	}

	/**
	 * Reinitializes Character locations.
	 * 
	 * @param in
	 *            the object input stream that is reading in this object.
	 * @throws IOException
	 *             if something goes wrong during read in.
	 * @throws ClassNotFoundException
	 *             if this objects class is not found.
	 */
	private void readObject(ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		in.defaultReadObject();
		chatLog = new LinkedBlockingDeque<Message>();
		for (GameCharacter g : characters.values()) {
			GameMap m = world.get(g.getMap());
			Point p = g.getPosition();
			try {
				m.getTile(p.x, p.y).setOccupant(g);
			} catch (TileException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Copies one {@link State} into another.
	 * 
	 * @param to
	 *            the State you wish to replace. If null, it will be initialized
	 *            to a new State.
	 * @param from
	 *            the State you wish to copy.
	 */
	public static void copy(State to, State from) {
		if (to == null)
			to = new State(0, 0);
		to.chatLogSize = from.chatLogSize;
		to.characters.putAll(from.characters);
		to.world.putAll(from.world);
		to.chatLog.addAll(from.chatLog);
		to.charsOnMap.putAll(from.charsOnMap);
	}
}
