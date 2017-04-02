package com.server.command;

import java.awt.Point;

import com.game.characters.Creature;
import com.game.environment.GameMap;
import com.game.environment.GameMap.BFSIterator;
import com.game.environment.tiles.Tile;
import com.game.items.IllegalInventoryException;
import com.game.items.Loot;
import com.server.State;

/**
 * Drop command used to drop loot onto a random tile next to the creature's tile
 * 
 * @author tberge01
 * 
 */
public class DropCommand extends ComparableCommand {

	/** DropCommand serial ID */
	private static final long serialVersionUID= -4267008670732143610L;

	/**
	 * Creates a new drop command the drops the {@link Loot} in a random position
	 * next to the player.
	 * 
	 * @param stateRef
	 *          a reference to the current {@link State}, so a {@link Tile} can be
	 *          selected
	 * @param creatureName
	 *          the name of the creature the {@link Loot} will be dropped by
	 * @param loot
	 *          the {@link Loot} that will be put on the selected {@link Tile}
	 */
	public DropCommand(State stateRef, String creatureName, Loot loot) {
		super(creatureName, 10, 0, 0, false);

		this._loot= loot;
		this.creatureName= creatureName;
		this.state = stateRef;

		// all this does is select a random tile next to the player.
		Creature creature= (Creature) state.getCharacter(creatureName);

		tileX= creature.getPosition().x;
		tileY= creature.getPosition().y;
		GameMap map= state.getGameMap(creature.getMap());
		Tile start= map.getTile(tileX, tileY);
    BFSIterator iter= map.getBFSIterator(start, 3);

		while (iter.hasNext()) {
			Tile t= iter.next().getNode();
			Loot tileLoot= t.pickupLoot();
      if (creature.getPosition().x == t.getPosition().x
          && creature.getPosition().y == t.getPosition().y) {
        continue;
      }

      if (tileLoot == null) {
				tileX= t.getPosition().x;
				tileY= t.getPosition().y;
				break;
			} else {
				try {
					t.putLoot(tileLoot);
				} catch (IllegalInventoryException e) {
				}
			}
		}
	}

	@Override
	public ComparableCommand execute() {
		Creature creature= (Creature) state.getCharacter(creatureName);
		String map= creature.getMap();

    if (_loot.getSwagList().size() == 0 && _loot.getMoolah() == 0) {
      return null;
    }

		try {
			state.getGameMap(map).getTile(tileX, tileY).putLoot(_loot);
		} catch (IllegalInventoryException e) {
		}

		return null;
	}

	@Override
	public boolean isValid() {
		Creature creature= (Creature) state.getCharacter(creatureName);
		Point position= creature.getPosition();

		if (tileX == position.x && tileY == position.y) {
			return false;
		}

		return true;
	}

	@Override
	public boolean equals(Object o) {
		return super.equals(o);
	}

	/* ===========================PRIVATE PARTS ============================= */

	private final Loot _loot;
	private int tileX;
	private int tileY;
	private final String creatureName;

}
