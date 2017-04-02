package com.server.command;

import com.game.characters.Creature;
import com.game.characters.NPC;
import com.game.characters.content.npcs.Merchant;
import com.game.items.IllegalInventoryException;
import com.game.items.Loot;
import com.game.items.Swag;

/**
 * The BuyCommand is used to buy things from Merchant NPC's.
 * 
 * @author tberg01
 * 
 */
public class BuyCommand extends ComparableCommand {

	/** BuyCommand serial ID */
	private static final long serialVersionUID = 4757768706936917484L;

	/**
	 * Creates a BuyCommand that purchases an item from a merchant and transfers
	 * it to a player.
	 * 
	 * @param player
	 *            the player who is buying.
	 * @param NPC
	 *            the NPC who is selling.
	 * @param s
	 *            the index of the item to buy.
	 */
	public BuyCommand(String player, String NPC, int s) {
		super(player, 10, 0, 0, false);
		this.npc = NPC;
		this.itemIndex = s;
	}

	/**
	 * Buys an item from an NPC Merchant.
	 */
	@Override
	public ComparableCommand execute() {
		Merchant npc = (Merchant) state.getCharacter(this.npc);
		Creature creature = (Creature) state.getCharacter(charName);
		
		Loot merchantInventory = npc.getInventory();
		Swag item = merchantInventory.getSwagAt(itemIndex);
		Loot playerInventory = creature.getInventory();
		
		if (item != null) {
			
			if (playerInventory.getMoolah() < 1.5*item.VALUE) {
				return null;
			}
			Swag newSwag;
			try {
				newSwag = npc.sellSwag(item);
				playerInventory.add(newSwag);
				playerInventory.makeWithdrawal((int) (newSwag.VALUE*1.5));
				
			}
			catch (IllegalInventoryException e) {
				newSwag = null; //remove the reference
				
			}
		}
		
		return null;
	}

	/**
	 * Checks to see if an item can be bought.
	 */
	@Override
	public boolean isValid() {
		NPC npc = (NPC) state.getCharacter(this.npc);
		Creature creature = (Creature) state.getCharacter(charName);
		if (npc == null)
			return false;
		if (creature == null)
			return false;
		if (npc.getInventory().getSwagAt(itemIndex) != null) {
			return false;
		}

		if (creature.getInventory().getMoolah() < npc.getInventory().getSwagAt(
				itemIndex).VALUE * 1.5) {
			return false;
		}

		if (Math.abs(npc.getPosition().x - creature.getPosition().x) > 1
				|| Math.abs(npc.getPosition().y - creature.getPosition().y) > 1)
			return false;

		return true;
	}

	/* ========================== PRIVATE PARTS ============================= */

	private final String npc;
	private final int itemIndex;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.server.command.ComparableCommand#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		return super.equals(o);
	}
}
