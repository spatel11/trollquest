package com.server.command;

import com.game.characters.Creature;
import com.game.characters.NPC;
import com.game.characters.Player;
import com.game.characters.content.npcs.Merchant;
import com.game.items.IllegalInventoryException;
import com.game.items.Loot;
import com.game.items.Swag;

/**
 * A Sell command used to exchange {@link Swag} between a {@link Merchant} and a
 * {@link Player}
 * 
 * @author TBworkstation
 * 
 */
public class SellCommand extends ComparableCommand {

	/** SellCommand serial ID */
	private static final long serialVersionUID = -3583988580363094994L;

  /**
   * Creates a new command to sell a piece of {@link Swag} to the targeted
   * {@link NPC}. This will fail if the {@link NPC} is not a {@link Merchant} or
   * if the {@link Player} does not have the right {@link Swag} in their
   * inventory
   * 
   * @param player
   *          the player's selling the item
   * @param npc
   *          the {@link Merchant}'s name buying the item
   * @param s
   *          the swag being exchanged
   */
	public SellCommand(String player, String npc, Swag s) {
		super(player, 10, 0, 0, false);

		this.npc = npc;
		this.item = s;
	}

	@Override
	public ComparableCommand execute() {
		Creature creature = (Creature) state.getCharacter(charName);
		Merchant npc = (Merchant) state.getCharacter(this.npc);

		Loot playerInventory = creature.getInventory();
		
		Swag s;
		try {
			s = playerInventory.remove(item);
			int amount = npc.purchaseSwag(s);
			playerInventory.makeDeposit(amount);
		} catch (IllegalInventoryException e) {
			// if we get here, we bypassed the things we didn't want to do
			// ie: purchasing the item, and making the deposit to the player's
			// moolah
		}
		
		return null;
	}

	@Override
	public boolean isValid() {
		Creature creature = (Creature) state.getCharacter(charName);
		if (!creature.getInventory().getSwagList().contains(item)) {
			return false;
		}

		if (!(state.getCharacter(this.npc) instanceof Merchant)) {
			return false;
		}

		Merchant npc = (Merchant) state.getCharacter(this.npc);
		if (Math.abs(npc.getPosition().x - creature.getPosition().x) > 1
				|| Math.abs(npc.getPosition().y - creature.getPosition().y) > 1) {
			return false;
		}

		return true;
	}

	@Override
	public boolean equals(Object o) {
		return super.equals(o);
	}


	/* ========================= PRIVATE PARTS ============================== */

	private final String npc;
	private final Swag item;

}
