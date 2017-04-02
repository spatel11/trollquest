/**
 * @author Stephen Patel (spatel11@unm.edu)
 */
package com.server.factory.classes;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.game.characters.Player;

/**
 * A Profile represents data about a user. This includes their name, and the
 * characters they have created in the past.
 * 
 * @author Stephen Patel
 * 
 */
public class Profile implements Serializable {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 1218041851227091839L;
	/**
	 * The name of the user.
	 */
	private final String userName;
	/**
	 * The {@link Player}s this user has available.
	 */
	private final Map<String, Player> characters;

	/**
	 * Creates a new {@link Profile} with the input username. This Profile will
	 * have no {@link Player}s.
	 * 
	 * @param userName
	 */
	public Profile(String userName) {
		this.userName = userName;
		this.characters = new HashMap<String, Player>();
	}

	/**
	 * Gets the name of the user of this {@link Profile}.
	 * 
	 * @return the user name of this profile.
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Adds a new {@link Player} to this {@link Profile}.
	 * 
	 * @param p
	 *            the player to add.
	 */
	public void addNewPlayer(Player p) {
		characters.put(p.name, p);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Profile [");
		if (userName != null) {
			builder.append("userName=");
			builder.append(userName);
			builder.append(", ");
		}
		if (characters != null) {
			builder.append("characters=");
			builder.append(characters);
		}
		builder.append("]");
		return builder.toString();
	}

	/**
	 * Gets a {@link Player} with the input player name.
	 * 
	 * @param playerName
	 *            the name of the player you want.
	 * @return the player with the input name.
	 */
	public Player getPlayer(String playerName) {
		return characters.get(playerName);
	}

	/**
	 * Get a {@link Collection} of the {@link Player}s this user has available/
	 * 
	 * @return a collection of available players.
	 */
	public Collection<Player> getPlayers() {
		return characters.values();
	}
}
