package com.server.command;

import com.client.Graphics.AnimationsOfACharacter.ActionType;
import com.game.characters.Creature;
import com.game.characters.GameCharacter;
import com.game.characters.Player;
import com.game.environment.GameMap;
import com.game.environment.tiles.Tile;
import com.game.environment.tiles.TileException;
import com.game.items.IllegalInventoryException;
import com.game.items.Loot;
import com.game.items.Swag;
import com.game.quest.Quest;
import com.server.State;
import com.utilities.TileRecord;

/**
 * Moves a {@link GameCharacter} to a {@link Tile} on the same {@link GameMap}.
 * 
 * @author Stephen Patel
 * 
 */
public class MoveCommand extends ComparableCommand {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4144170703144880920L;
	/**
	 * The x coordinate of the destination {@link Tile}.
	 */
	private final int newX;
	/**
	 * The y coordinate of the destination {@link Tile}.
	 */
	private final int newY;

	/**
	 * Creates a new {@link MoveCommand} that will move a {@link GameCharacter}
	 * to an adacent {@link Tile} on the characters current {@link GameMap}.
	 * 
	 * @param char_name
	 *            the name of the character to move.
	 * @param newX
	 *            the x coordinate of the destination.
	 * @param newY
	 *            the y coordinate of the destination.
	 */
	public MoveCommand(String char_name, int newX, int newY) {
		super(char_name, 0, 0, 0, false);
		this.newX = newX;
		this.newY = newY;
	}

	/**
	 * Set's the {@link State} this {@link MoveCommand} will act on. Also set's
	 * the preptime for this command.
	 */
	@Override
	public void setState(State state) {
		super.setState(state);

		synchronized (state) {
			GameCharacter c = state.getCharacter(charName);
			GameMap m = state.getGameMap(c.getMap());

			Tile tile_src = m.getTile(c.getPosition().x, c.getPosition().y);
			Tile tile_dst = m.getTile(newX, newY);
			// System.out.println("Source tile " + tile_src.getPosition()
			// + " Destination tile: " + tile_dst.getPosition());

			this.prepTime = TileRecord.getConnection(tile_src.tile_record,
					tile_dst.tile_record).cost;
			if (c instanceof Creature) {
				if (((Creature) c).isFlying()) {
					this.prepTime = 10;
				}
			}
		}
	}

	/**
	 * Move's a Character to the desired location, picking up any loot there and
	 * resetting the characters action state.
	 */
	@Override
	public ComparableCommand execute() {
		synchronized (state) {
			GameCharacter character = state.getCharacter(charName);

			GameMap m = state.getGameMap(character.getMap());

			Tile tile_src = m.getTile(character.getPosition().x,
					character.getPosition().y);
			Tile tile_dst = m.getTile(newX, newY);

			System.out.println("Setting " + charName + "'s position to "
					+ tile_dst.getPosition());
			Loot l = null;
			try {
				tile_dst.setOccupant(character);
				tile_src.removeOccupant();
				if (state.getCharacter(charName) instanceof Player) {
					l = tile_dst.pickupLoot();
					if (l != null) {
						for (Swag s : l.getSwagList()) {
							Quest q = ((Player) character).getPlayersQuest();
							if (q != null)
								q.checkTop(s.getQuestEvent());
						}
						character.getInventory().merge(l);
					}
				}
			} catch (TileException e) {
				e.printStackTrace(); // TODO properly handle
			} catch (IllegalInventoryException e) {
				try {
					tile_dst.putLoot(l);
				} catch (IllegalInventoryException e1) {
				}
			}
			character.setSubX(0.0);
			character.setSubY(0.0);
			character.setSubXvel(0.0);
			character.setSubYvel(0.0);
			character.setActionState(ActionType.STAND);
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MoveCommand [newX=");
		builder.append(newX);
		builder.append(", newY=");
		builder.append(newY);
		builder.append(", ");
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
	 * The MoveCommand is valid if the character exists, if the map exists, if
	 * the target location is not outside the map bounds, and if the tile is not
	 * already occupied.
	 */
	@Override
	public boolean isValid() {
		GameCharacter c = state.getCharacter(charName);
		if (c == null)
			return false;
		GameMap m = state.getGameMap(c.getMap());
		if (m == null)
			return false;
		if (newY >= m.getMapHeight() || newY < 0)
			return false;
		if (newX >= m.getMapWidth() || newX < 0)
			return false;
		Tile dest = m.getTile(newX, newY);
		if (dest.getOccupant() != null)
			return false;
		return true;
	}

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;

		if (o == null)
			return false;
		if (!(o instanceof MoveCommand))
			return false;
		MoveCommand mc = (MoveCommand) o;

		if (!charName.equals(mc.charName))
			return false;
		if (mc.newX != newX)
			return false;
		if (mc.newY != newY)
			return false;

		return true;
	}
}