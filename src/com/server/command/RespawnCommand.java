/**
 * @author Stephen Patel (spatel11@unm.edu)
 */
package com.server.command;

import java.awt.Point;

import com.client.Message;
import com.client.Graphics.AnimationsOfACharacter.ActionType;
import com.game.characters.Creature;
import com.game.characters.Creature.Stat;
import com.game.characters.Monster;
import com.game.characters.Player;
import com.game.environment.GameMap;
import com.game.environment.GameMap.BFSIterator;
import com.game.environment.tiles.Tile;
import com.game.environment.tiles.TileException;
import com.server.State;

/**
 * Respawns a {@link Creature}. This characters {@link Stat}s are reset, and
 * it's spawn location is the default {@link GameMap}s spawn location.
 * 
 * @author Stephen Patel
 * 
 */
public class RespawnCommand extends ComparableCommand {

	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = -3749186193597882123L;
	/**
	 * The {@link Creature} to respawn.
	 */
	private final Creature creature;
	private Point newSpawn;

	/**
	 * Creates a Command to respawn a {@link Creature}.
	 * 
	 * @param s
	 *            reference to the {@link State} object to get the Creature.
	 * @param userName
	 *            the name of the user if the creature is a player.
	 * @param creatureName
	 *            the name of the creature to respawn.
	 * @param spawnTime
	 *            the numbe of game ticks that must elapse before the creature
	 *            is respawned.
	 */
	public RespawnCommand(Creature creature) {
		super(creature.name, creature.getRespawnTime(), 0, 0, true);
		this.creature = creature;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValid() {
		GameMap m = state.getGameMap(creature.getMap());
		if (creature instanceof Player) {
			newSpawn = m.getSpawnLoc();
		} else if (creature instanceof Monster) {
			Point spawn = ((Monster) creature).getSpawnloc();
			Tile start = m.getTile(spawn.x, spawn.y);
			BFSIterator iter = m.getBFSIterator(start, Math.min(m.getMapHeight(), m.getMapWidth()));
			while (iter.hasNext()) {
				Tile t = iter.next().getNode();
				if (t.getOccupant() == null) {
					newSpawn = t.getPosition();
					return true;
				}
			}
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RespawnCommand [");
		if (creature != null) {
			builder.append("creature=");
			builder.append(creature);
			builder.append(", ");
		}
		if (charName != null) {
			builder.append("charName=");
			builder.append(charName);
			builder.append(", ");
		}
		if (getClass() != null) {
			builder.append("getClass()=");
			builder.append(getClass());
		}
		builder.append("]");
		return builder.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {
		return super.equals(o);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ComparableCommand execute() {
		synchronized (state) {
			state.removeCharacter(creature.name);
			GameMap m = state.getGameMap(creature.getMap());
			creature.changeStat(Stat.HEALTHPOINTS, creature.getBaseStat(Stat.HEALTHPOINTS));
			System.err.println(Stat.HEALTHPOINTS + " " + creature.getCurrStat(Stat.HEALTHPOINTS));
			creature.changeStat(Stat.MAGICPOINTS, creature.getBaseStat(Stat.MAGICPOINTS));
			System.err.println(Stat.MAGICPOINTS + " " + creature.getCurrStat(Stat.MAGICPOINTS));
			System.err.println(Stat.EXPERIENCE + " "+ creature.getCurrStat(Stat.EXPERIENCE));
			System.err.println(Stat.LEVEL + " "+ creature.getCurrStat(Stat.LEVEL));
			try {
				m.getTile(newSpawn.x, newSpawn.y).setOccupant(creature);
			} catch (TileException e) {
				e.printStackTrace();
			}
			Point pLoc = creature.getPosition();
			Message msg = new Message("SERVER", charName
					+ " has respawned at location " + pLoc.x + ", " + pLoc.y
					+ "!");
			msg.setComplete();
			state.addMessage(msg);
			creature.setSubX(0.0);
			creature.setSubY(0.0);
			creature.setSubXvel(0.0);
			creature.setSubYvel(0.0);
			creature.setActionState(ActionType.STAND);
			state.addCharacter(creature);
		}
		return null;
	}
}
