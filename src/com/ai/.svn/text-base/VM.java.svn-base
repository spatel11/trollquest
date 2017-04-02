package com.ai;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.TreeSet;

import com.ai.ASTCells.ASTCell;
import com.ai.ASTCells.ActionASTCell;
import com.ai.ASTCells.BooleanASTCell;
import com.ai.ASTCells.NumberASTCell;
import com.ai.ASTCells.StringASTCell;
import com.ai.reference.TypeException;
import com.client.Graphics.AnimationsOfACharacter.ActionType;
import com.game.characters.Creature.Stat;
import com.game.characters.GameCharacter;
import com.game.characters.Monster;
import com.game.characters.NPC;
import com.game.characters.Player;
import com.game.characters.spells.AbstractSpell;
import com.game.characters.spells.SpellInventory;
import com.game.environment.Directions;
import com.game.environment.GameMap;
import com.game.environment.tiles.Tile;
import com.game.quest.Quest;
import com.game.quest.QuestEvent;
import com.server.State;
import com.server.command.AttackCommand;
import com.server.command.CastCommand;
import com.server.command.CommandChangeState;
import com.server.command.ComparableCommand;
import com.server.command.MessageCommand;
import com.server.command.MoveCommand;
import com.utilities.DijkstraMap;

/**
 * This Class holds all of the variables created during traversal of a monsters
 * script. Fields created during execution of a script are stored in
 * {@link #_internals}. Scripts access outside {@link State} fields from
 * {@link #_externals}.
 * 
 * <p>
 * For scripts can access a finite amount of specific information about the
 * gameState. This information is stored in {@link #_externals}.
 * {@link #_externals} is created immediately before running of a script by
 * {@link #loadPCs(State, String)} and {@link #loadSelf(Monster)}.Scripts cannot
 * change information inside {@link #_externals}. They can only access it.
 * </p>
 * <p>
 * </p>
 * 
 * @author Martin Tice
 * 
 */
public class VM {

	/** used to toggle debug print statements on and off */
	private static final boolean DEBUG = false;
	/**
	 * Holds all of the variables created during execution of an {@link ASTCell}
	 * syntax tree
	 */
	private static HashMap<String, ASTCell> _internals;
	/** Initial attempt at External data reference */
	private static HashMap<String, ASTCell> _externals;
	/** Game state to access values from */
	private State _gs;
	/** Name of current monster that is running strategy */
	private String _self;
	/** Maps the distance between current npc and player, to reference to player */
	private TreeMap<Double, Player> _distancesPlayers = new TreeMap<Double, Player>();
	/** list of all PC's */
	private ArrayList<Player> _players = new ArrayList<Player>();
	/** Holds all of the PC's location in the form of a {@link Point} object */
	private ArrayList<Point> _playerLocations = new ArrayList<Point>();
	/** Holds all of the PC's in range of attack */
	private ArrayList<Player> _inRange = new ArrayList<Player>();
	/**
	 * manhatten distance value to determine a {@link Player}s addition to
	 * {@link #_inRange}
	 */
	private int _range = 6;
	/**
	 * set in {@link #parseAction(String, String)}, x position of target command
	 */
	private int _targetX;
	/**
	 * set in {@link #parseAction(String, String)}, y position of target command
	 */
	private int _targetY;
	/**
	 * set in {@link #parseAction(String, String)}, x position of current
	 * monster
	 */
	private int _selfX;
	/**
	 * set in {@link #parseAction(String, String)}, y position of current
	 * monster
	 */
	private int _selfY;
	/** set in {@link #parseAction(String, String)}, name of target PC */
	private String _targetName;
	/** set in {@link #parseAction(String, String)}, {@link Tile} of target PC */
	private Tile _targetTile;
	/**
	 * set in {@link #parseAction(String, String)}, {@link Tile} current Monster
	 */
	private Tile _selfTile;
	/**
	 * set in {@link #parseAction(String, String)}, {@link ComparableCommand}
	 * Monster script decided on
	 */
	private String _command;
	/** the list of tiles on shortest dijkstra path from monster to target */
	private LinkedList<Tile> _moveResult = new LinkedList<Tile>();
	/** current {@link GameMap} */
	private GameMap _map;
	/** used to map the quest message to the last quest event */
	private Map<String, QuestEvent> _lastQuests;
	/** Used to find current shortest cost path to PC. {@link DijkstraMap} */
	private DijkstraMap dijkstraMap = new DijkstraMap();
	/** The current monsters spell list */
	private List<AbstractSpell> _spellList;

	// External GETTERS AND
	// SETTERSGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGSSSSSSSSSSSSSSSSSSSSSSSS
	/**
	 * Getter for {@link #_distancesPlayers}
	 * 
	 * @return {@link #_distancesPlayers}
	 */
	public TreeMap<Double, Player> getDistances() {
		return _distancesPlayers;
	}

	/**
	 * Sets the name of the monster about to run script Sets {@link #_self}.
	 */
	public void setName(String name) {
		_self = name;
	}

	/**
	 * Puts an entry in {@link #_externals}. Scripts do not have access to this
	 * method and therefore cannot change state fields.
	 * 
	 * @param key
	 *            {@link String} to be used for a key in {@link #_externals}.
	 * @param value
	 *            {@link ASTCell} to be used as a value in {@link #_externals}.
	 */
	public void setExternal(String key, ASTCell value) {
		if (DEBUG) {
			System.out
					.println("VM.setExternal : " + key + ", " + value.print());
		}
		_externals.put(key, value);
	}

	/**
	 * getter for {@link #_externals}
	 * 
	 * @param key
	 *            {@link String} key to try and match in {@link #_externals}.
	 * @return an {@link ASTCell} value in {@link #_externals} from given key
	 *         {@link String}
	 */
	public ASTCell getExternal(String key) {
		if (DEBUG) {
			System.out.println("VM.getExternal size= " + _externals.size()
					+ " contains key: " + key + " "
					+ _externals.containsKey(key));
			System.out
					.println("value for key = " + _externals.get(key).print());

		}
		return _externals.get(key);
	}

	// CONSTRUCTOR===============================================================
	/**
	 * Initializes data Structures for holding external and internal data.
	 * {@link #_internals} for internals {@link #_externals} for externals
	 */
	public VM() {
		_internals = new HashMap<String, ASTCell>();
		_externals = new HashMap<String, ASTCell>();
		_lastQuests = new HashMap<String, QuestEvent>();

	}// END CONSTRUCTOR

	/**
	 * Used to clear all data between monster strategy executions. Clears:
	 * {@link #_internals}, {@link #_externals}, {@link #_playerLocations},
	 * {@link #_players}, {@link #_distancesPlayers}, {@link #_inRange}.
	 */
	public void clearData() {
		_internals.clear();
		_externals.clear();
		_players.clear();
		_playerLocations.clear();
		_distancesPlayers.clear();
		_inRange.clear();
	}

	// INTERNAL DATA CREATION AND MANIPULATION
	// =====================================================================
	/**
	 * Setter for {@link #_internals} This sets a mapping between a string name
	 * and its data when created during execution of the syntax tree.
	 * 
	 * @param key
	 *            the name of a variable. Of type {@link String}
	 * @param value
	 *            the variables value. Of type {@link ASTCell}
	 */
	public void setInternal(String key, ASTCell value) {
		if (DEBUG) {
			System.out.println("VMsetInternal: " + key + " " + value.print());
		}
		_internals.put(key, value);

	}

	/**
	 * Getter for {@link #_internals}.
	 * 
	 * @param key
	 *            the name of the key. Of type {@link String}
	 * @return the value associated with the input key. Of Type {@link ASTCell}
	 */
	public ASTCell getCell(String key) {
		if (DEBUG) {
			System.out.println("VM.getCell: " + key);
		}
		return _internals.get(key);
	}

	/**
	 * Determines if a key-value pairing exist in {@link #_internals}.
	 * 
	 * For use when re-assigning script specific fields.
	 * 
	 * @param name
	 *            the name of the variable. Of type {@link String}
	 * @return true if field exists, false otherwise
	 */
	public boolean containsVar(ASTCell c) {
		String name;
		try {
			name = c.stringVal();
		} catch (TypeException te) {
			return false;
		}
		return _internals.containsKey(name);
	}

	/**
	 * Used for determining if there are any PC's in range of attack. Determined
	 * by size of {@link #_inRange}.
	 * 
	 * @return true if {@link #_inRange}.size() is > 0. false otherwise.
	 */
	public boolean inRange() {
		if (DEBUG) {
			System.out.println("VM.inRange size: " + _inRange.size() + ", "
					+ _inRange.toString());
		}
		if (_inRange.size() == 0)
			return false;
		return true;
	}

	// EXTERNAL DATA
	// ACCESS====================================================================================
	// To be accessed by a ExternalSymbolASTCell in strategy execution.
	// External DATA is not changed by a nanoC file.
	//

	/**
	 * loads a {@link State} object to {@link #_gs}
	 * 
	 * @param s
	 *            The current {@link State} to set.
	 */
	public void loadState(State s) {
		_gs = s;
	}

	/**
	 * Loads all players location into VM. Gets a list of players from
	 * {@link State#getLivingPlayers()}
	 * <p>
	 * adds each player to {@link #_players}
	 * </p>
	 * <p>
	 * adds each player location to {@link #_playerLocations}
	 * </p>
	 * 
	 * @param s
	 *            the current game state, a {@link State} object
	 */
	public void loadPCs(State s, String mapName) {
		_gs = s;
		// get all players and record location
		for (Player player : _gs.getLivingPlayersOnMap(mapName)) {

			if (DEBUG) {
				System.out.println("VM.loadPCs player found : " + player.name);
			}
			_playerLocations.add(player.getPosition());

			if (DEBUG) {
				System.out.println("position" + _playerLocations.toString());
			}
			_players.add(player);

			// if(DEBUG)System.out.println("VM.loadPCs spells" +
			// player.getCurrentQuestEvent());

		}

	}

	/**
	 * Does the following with a given non-player character:
	 * <p>
	 * loads its info under "self.*" into {@link #_externals}
	 * </p>
	 * <p>
	 * calculates all distances between other players and itself
	 * </p>
	 * <p>
	 * loads closest.* furthest.* and random.* attributes into
	 * {@link #_externals}
	 * </p>
	 * <p>
	 * loads the {@link #_spellList} of current monster. loads any other
	 * necessary attributes necessary for calculation of strategy
	 * </p>
	 * 
	 * @param monster
	 */
	public void loadSelf(GameCharacter scriptable) {
		if (DEBUG) {
			System.out.println("loading self..." + scriptable.name);
		}
		if (scriptable instanceof Monster) {
			Monster monster = (Monster) scriptable;
			_externals.put("self.hp",
					new NumberASTCell(monster.getCurrStat(Stat.HEALTHPOINTS)));
			_externals.put("self.mp",
					new NumberASTCell(monster.getCurrStat(Stat.MAGICPOINTS)));
			_externals.put("self.level",
					new NumberASTCell(monster.getCurrStat(Stat.LEVEL)));
			_externals.put("self.name", new StringASTCell(monster.name));
			_externals.put("self.fullHp",
					new NumberASTCell(monster.getBaseStat(Stat.HEALTHPOINTS)));
			_self = monster.name;
			SpellInventory si = monster.getSpellInventory();
			_spellList = si.getSpellSet();
		} else {
			_self = scriptable.name;
		}
		calculateDistances(scriptable.getPosition());
		setNearAndFarPlayers();
		setRandom();

	}

	/**
	 * Calculates the Manhatten distance between the given Monster position, and
	 * all of the players inside {@link #_playerLocations}. Stores all of the
	 * values inside the respective index in {@link #_playerDistances}. If any
	 * player distance is < {@link #_range}, the player is added to
	 * {@link #_inRange}.
	 * <p>
	 * creates an "isAdjacent" entry in {@link #_internals} and if a player is
	 * adjacent, sets it to true. will be set to true if a pc is anywhere in the
	 * 8 surrounding tile locations.
	 * </p>
	 * 
	 * @param position
	 *            current monster position. Of type {@link Point}.
	 * 
	 * @author Martin Tice
	 */
	private void calculateDistances(Point mpt) {
		_internals.put("isAdjacent", BooleanASTCell.getFalse());
		for (int i = 0; i < _players.size(); i++) {
			if (DEBUG) {
				System.out.println("mptx= " + mpt.x + " y=" + mpt.y);
				System.out.println("playerIx= " + _playerLocations.get(i).x
						+ " y=" + _playerLocations.get(i).y);
			}
			Point ppt = new Point(_playerLocations.get(i).x,
					_playerLocations.get(i).y);
			Double dist = (double) (Math.abs(mpt.x - _playerLocations.get(i).x) + Math
					.abs(mpt.y - _playerLocations.get(i).y));
			if (Point.distance(mpt.x, mpt.y, ppt.x, ppt.y) < 2)
				_internals.put("isAdjacent", BooleanASTCell.getTrue());

			if (DEBUG) {
				System.out.println("distanceCalculated::: " + dist);
			}
			_distancesPlayers.put(dist, _players.get(i));
			if (dist < _range) {
				if (DEBUG) {
					System.out.println("player in range!! " + dist);
				}

				_inRange.add(_players.get(i));
			}
		}
		_externals.put("players.inRange", new NumberASTCell(_inRange.size()));
	}

	/**
	 * Calculates the nearest and farthest {@link Player} in
	 * {@link #_distancesPlayers}. Does this by getting getting a
	 * {@link TreeSet} from the {@link TreeMap} {@link #_distancesPlayers}
	 * .keySet(). This arranges the keys in order of distance so nearest player
	 * can be retrieved by a .first() call and the furthest player can be
	 * retrieved by a .last() call.
	 * 
	 * <p>
	 * The key/value pairs to be put in {@link #_externals} are the following:
	 * </p>
	 * <ul>
	 * <li>"nearest.name" to name of nearest player
	 * <li>"nearest.hp" to health points of nearest player
	 * <li>"nearest.fullHp" to the highest amount of health the nearest player
	 * can have
	 * <li>"nearest.mp" to magic points of nearest player
	 * <li>"nearest.class" to string value of class of nearest player
	 * <li>"nearest.moolah" to moolah of nearest player
	 * <li>"furthest.name" to name of furthest player
	 * <li>"furthest.hp" to health points of furthest player
	 * <li>"furthest.fullHp" to the highest amount of health the furthest player
	 * can have
	 * <li>"furthest.mp" to magic points of furthest player
	 * <li>"furthest.class" to string value of class of furthest player
	 * <li>"furthest.moolah" to moolah of furthest player
	 * </ul>
	 */
	private void setNearAndFarPlayers() {
		if (_distancesPlayers.size() == 0) {

			return;
		}
		TreeSet<Double> keys = new TreeSet<Double>(_distancesPlayers.keySet());

		Player nearest = _distancesPlayers.get(keys.first());
		QuestEvent qe = nearest.getCurrentQuestEvent();
		String qeName = qe.toString();

		_externals.put("nearest.quest", new StringASTCell(qeName));
		_externals.put("nearest.name",
				new StringASTCell(_distancesPlayers.get(keys.first()).name));
		_externals.put("nearest.hp",
				new NumberASTCell(_distancesPlayers.get(keys.first())
						.getCurrStat(Stat.HEALTHPOINTS)));
		_externals.put("nearest.fullHp", new NumberASTCell(_distancesPlayers
				.get(keys.first()).getBaseStat(Stat.HEALTHPOINTS)));
		_externals.put("nearest.mp",
				new NumberASTCell(_distancesPlayers.get(keys.first())
						.getCurrStat(Stat.MAGICPOINTS)));
		_externals.put("nearest.class", new StringASTCell(_distancesPlayers
				.get(keys.first()).getClass().toString()));
		_externals.put("nearest.moolah", new NumberASTCell(_distancesPlayers
				.get(keys.first()).getInventory().getMoolah()));
		_externals.put("nearest.level", new NumberASTCell(_distancesPlayers
				.get(keys.first()).getCurrStat(Stat.LEVEL)));
		// furthest player
		if (DEBUG)
			System.out.println("adding furthest player: "
					+ _distancesPlayers.get(keys.last()).name);
		_externals.put("furthest.name",
				new StringASTCell(_distancesPlayers.get(keys.last()).name));
		// _externals.put("furthest.x",new
		// NumberASTCell(_distancesPlayers.get(keys.last()).getSubX()));
		// _externals.put("furthest.y",new
		// NumberASTCell(_distancesPlayers.get(keys.last()).getSubY()));
		_externals.put("furthest.hp",
				new NumberASTCell(_distancesPlayers.get(keys.last())
						.getCurrStat(Stat.HEALTHPOINTS)));
		_externals.put("furthest.fullHp", new NumberASTCell(_distancesPlayers
				.get(keys.last()).getBaseStat(Stat.HEALTHPOINTS)));
		_externals.put("furthest.mp",
				new NumberASTCell(_distancesPlayers.get(keys.last())
						.getCurrStat(Stat.MAGICPOINTS)));
		_externals.put("furthest.level", new NumberASTCell(_distancesPlayers
				.get(keys.last()).getCurrStat(Stat.LEVEL)));
		_externals.put("furthest.class", new StringASTCell(_distancesPlayers
				.get(keys.last()).getClass().toString()));
		_externals.put("furthest.moolah", new NumberASTCell(_distancesPlayers
				.get(keys.last()).getInventory().getMoolah()));// moolah

	}

	/**
	 * Picks a random player to for a script to access information about.
	 * <p>
	 * Creates the following key/value pairings in {@link #_externals}:
	 * </p>
	 * <ul>
	 * <li>"random.name" to name of random player
	 * <li>"random.hp" to health points of random player
	 * <li>"random.fullHp" to max health the random player can reach
	 * <li>"random.mp" to magic points of random player
	 * <li>"random.level" the level of the random player
	 * <li>"random.class" to string value of class of random player
	 * <li>"random.moolah" to moolah of random player
	 * </ul>
	 */
	private void setRandom() {
		if (_players.size() == 0)
			return;
		Random _random = new Random();
		int index = _random.nextInt(_players.size());
		_externals.put("random.x", new NumberASTCell(_players.get(index)
				.getSubX()));
		_externals.put("random.y", new NumberASTCell(_players.get(index)
				.getSubY()));
		_externals.put("random.name", new StringASTCell(
				_players.get(index).name));
		_externals.put("random.hp", new NumberASTCell(_players.get(index)
				.getCurrStat(Stat.HEALTHPOINTS)));
		_externals.put("random.fullHp", new NumberASTCell(_players.get(index)
				.getBaseStat(Stat.HEALTHPOINTS)));
		_externals.put("random.mp", new NumberASTCell(_players.get(index)
				.getCurrStat(Stat.MAGICPOINTS)));
		_externals.put("random.level", new NumberASTCell(_players.get(index)
				.getCurrStat(Stat.LEVEL)));
		_externals.put("random.class", new StringASTCell(_players.get(index)
				.getClass().toString()));
		_externals.put("random.moolah", new NumberASTCell(_players.get(index)
				.getInventory().getMoolah()));
	}

	/**
	 * Called by an {@link ActionASTCell}. This sets all the information needed
	 * to perform a desired command.
	 * <p>
	 * sets the following:
	 * </p>
	 * <ul>
	 * <li>{@link #_targetName}, {@link #_targetTile}, {@link #_targetX},
	 * {@link #_targetY}
	 * <li>{@link #_self}, {@link #_selfName}, {@link #_selfTile},
	 * {@link #_selftX}, {@link #_selfY}
	 * <li>{@link #_command}
	 * </ul>
	 * 
	 * @param command
	 *            the decided command number
	 * @param target
	 *            the name of the target pc.
	 */
	public void parseAction(String command, String target) {
		if (DEBUG)
			System.out.println("VM.parseAction command : " + command
					+ ", target: " + target);
		try {
			_targetName = _externals.get(target).stringVal();
			_self = _self.replaceAll("\"", "");
		} catch (TypeException e) {
			System.out.println("VM.parseAction failed to retrieve target Name");
			e.printStackTrace();
		}
		if (_gs.getCharacter(_targetName) == null)
			return;
		// System.out.println("VM.parseAction target: "+_targetName+", command: "+command+", self: "+_self);
		double tx = _gs.getCharacter(_targetName).getPosition().x;
		_targetX = (int) tx;
		double ty = _gs.getCharacter(_targetName).getPosition().y;
		_targetY = (int) ty;
		double sx = _gs.getCharacter(_self).getPosition().x;
		_selfX = (int) sx;
		double sy = _gs.getCharacter(_self).getPosition().y;
		_selfY = (int) sy;
		String map = _gs.getCharacter(_targetName).getMap();
		_map = _gs.getGameMap(_gs.getCharacter(_targetName).getMap());
		_targetTile = _map.getTile(_targetX, _targetY);
		_selfTile = _map.getTile(_selfX, _selfY);
		_command = command;
		if (DEBUG) {
			System.out.println("tx ty sx sy map command");
		}
		if (DEBUG) {
			System.out.println(_targetX + ", " + _targetY + ", " + _selfX
					+ ", " + _selfY + ", " + map + ", " + _command);
		}
	}

	/**
	 * <p>
	 * Returns the final command decided from a strategy. Commands are decided
	 * by the string value of {@link #_command} As Monster scripts require more
	 * commands, more commands will be able to be returned.Each command is
	 * returned in the following fashion:
	 * </p>
	 * <code>
	 * <p>     if(command.equals(eachCommand)){</p>
	 * <p>     make any required calculations with values created by {@link #parseAction(String, String)}</p>
	 *         return command
	 *     }
	 * </code>
	 * 
	 * 
	 * @return
	 */
	public ComparableCommand getCommand() {
		synchronized (_gs) {
			if (DEBUG) {
				System.out.println("VM.getCommand got: " + _command
						+ " at location: " + _targetX + " , " + _targetY);
			}

			if ("move".equals(_command)) {
				if (DEBUG) {
					System.out.println("VM.getCommand is move");
				}

				dijkstraMap.solve(_map, _selfTile, _targetTile, _moveResult);
				double cost = Tile.getCost(_moveResult.get(0),
						_moveResult.get(1));
				Tile t1 = null;
				Tile t2 = null;
				try{
					t1 = _moveResult.get(1);
					t2 = _moveResult.get(0);
					Point delta = Tile.getDelta(t1, t2);
					Directions direction = Tile.getDirection(delta);
					double velx = direction.subpos_delta.x / cost;
					double vely = direction.subpos_delta.y / cost;
					_gs.getScheduler().schedule(
							new CommandChangeState(_self, ActionType.WALK,
									direction, velx, vely));
				}catch (IndexOutOfBoundsException e){
					//Do Nothing
				}

				return new MoveCommand(_self,
						_moveResult.get(1).getPosition().x, _moveResult.get(1)
								.getPosition().y);
			}
			if ("attack".equals(_command)) {
				if (DEBUG) {
					System.out.println("VM.getCommand is attack");
				}
				dijkstraMap.solve(_map, _selfTile, _targetTile, _moveResult);
				if (_moveResult.size() < 2)
					return null;
				Tile t1 = null;
				Tile t2 = null;
				try{
					t1 = _moveResult.get(1);
					t2 = _moveResult.get(0);
					Point delta = Tile.getDelta(t1, t2);
					Directions direction = Tile.getDirection(delta);
					_gs.getScheduler().schedule(
							new CommandChangeState(_self, ActionType.ATTACK,
									direction, 0.0, 0.0));
				}catch (IndexOutOfBoundsException e){
					//Do Nothing
				}
				return new AttackCommand(_gs, _self, _targetX, _targetY);
			}
			if ("cast".equals(_command)) {
				if (DEBUG) {
					System.out.println("VM.getCommand is cast");
				}
				dijkstraMap.solve(_map, _selfTile, _targetTile, _moveResult);
				if (!_self.equals(_targetName)) {
					Tile t1 = null;
					Tile t2 = null;
					try{
						t1 = _moveResult.get(1);
						t2 = _moveResult.get(0);
						Point delta = Tile.getDelta(t1, t2);
						Directions direction = Tile.getDirection(delta);
						_gs.getScheduler().schedule(
								new CommandChangeState(_self, ActionType.ATTACK,
										direction, 0.0, 0.0));
					}catch (IndexOutOfBoundsException e){
						//Do Nothing
					}
				}

				try {
					double d = _internals.get("castChoice").numVal();
					AbstractSpell spell = _spellList.get((int) d);
					if (DEBUG)
						System.out.println("VM.getCommand spell choice : "
								+ spell);
					return new CastCommand(_gs, _self, _targetX, _targetY,
							spell);
					// TODO figure the rest out. small,med,large? numbers
					// instead of strings?

				} catch (TypeException e) {
					System.out.println("CastName was not a string ASTCell");
					e.printStackTrace();
				}

				return null;// new ZotCommand(_self, _targetX, _targetY, _gs);
			}
			if ("talk".equals(_command)) {

				if (DEBUG)
					System.out.println("VM.getCommand is talk");

				Player p = (Player) _gs.getCharacter(_targetName);
				GameCharacter currNPC = _gs.getCharacter(_self);

				Quest allQuestEvents = p.getPlayersQuest();
				QuestEvent event = currNPC.getQuestEvent();
				if (allQuestEvents != null)
					allQuestEvents.checkTop(event);
				QuestEvent pcQE = p.getCurrentQuestEvent();

				if (!_lastQuests.containsKey(_targetName)) {// no previous quest
					_lastQuests.put(_targetName, pcQE);
					NPC npc = (NPC) _gs.getCharacter(_self);
					QuestEvent qe = npc.getQuestEvent();
					String message = qe.getQuestDialog();
					Directions direction = Tile.getDirection(Tile.getDelta(
							_targetTile, _selfTile));

					_gs.getScheduler().schedule(
							new CommandChangeState(_self, ActionType.STAND,
									direction, 0.0, 0.0));

					return new MessageCommand(_self, p.name, message);

				} else if (_lastQuests.get(_targetName).equals(pcQE)) {

					// Directions direction =
					// Tile.getDirection(Tile.getDelta(_selfTile, _targetTile));
					// _gs.getScheduler().schedule(
					// new CommandChangeState(_self, ActionType.STAND,
					// direction, 0.0, 0.0));
					return null;
				} else {// previous quest != current quest
					_lastQuests.put(_targetName, pcQE);
					NPC npc = (NPC) _gs.getCharacter(_self);
					QuestEvent qe = npc.getQuestEvent();
					String message = qe.getQuestDialog();
					return new MessageCommand(_self, p.name, message);
				}

			}
			if ("talkMerchant".equals(_command)) {
				Directions direction = Tile.getDirection(Tile.getDelta(
						_targetTile, _selfTile));
				_gs.getScheduler().schedule(
						new CommandChangeState(_self, ActionType.STAND,
								direction, 0.0, 0.0));

			}
			if ("stareMerchant".equals(_command)) {
				// Directions direction =
				// Tile.getDirection(Tile.getDelta(_targetTile, _selfTile));
				// _gs.getScheduler().schedule(
				// new CommandChangeState(_self, ActionType.STAND,
				// direction, 0.0, 0.0));
				try {
					String message = _internals.get("merchantMessage")
							.stringVal();
					return new MessageCommand(_self, null, message);
				} catch (TypeException e) {
					System.out.println("Merchant script type exception");
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * Get's the number of players.
	 * 
	 * @return the number of players in the game.
	 */
	public int getPlayerCount() {
		return _players.size();
	}
}
