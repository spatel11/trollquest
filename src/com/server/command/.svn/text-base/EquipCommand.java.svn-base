/**
 * @author Stephen Patel (spatel11@unm.edu)
 */
package com.server.command;

import com.game.characters.Creature;
import com.game.items.IllegalInventoryException;
import com.game.items.Swag;
import com.game.items.armor.Armor;
import com.game.items.weapon.Weapon;

/**
 * Used to Equip a piece of Armor or a Weapon.
 * 
 * @author Stephen Patel
 * 
 */
public class EquipCommand extends ComparableCommand {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 7143181520414152439L;

	/**
	 * The piece of swag to equip.
	 */
	private final Swag equip;

	/**
	 * Creates a new Equip Command to equip a piece of swag to a Creature.
	 * 
	 * @param charName
	 *            the name of the Creature to equip to.
	 * @param equip
	 *            the piece of swag to equip.
	 */
	public EquipCommand(String charName, Swag equip) {
		super(charName, 0, 0, 0, false);
		this.equip = equip;
	}

	/**
	 * The Equip Commandis valid if the Creature exists, if the equip is not
	 * nulls, if the creatures inventory contains it, and if it's a piece of
	 * Armor or a weapon.
	 */
	@Override
	public boolean isValid() {
		Creature c = (Creature) state.getCharacter(charName);
		if (c == null)
			return false;
		if (equip == null)
			return false;
		if (!c.getInventory().getSwagList().contains(equip))
			return false;
		return (equip instanceof Weapon || equip instanceof Armor);
	}

	/**
	 * Equips the equip item to the input Creature.
	 */
	@Override
	public ComparableCommand execute() {
		synchronized (state) {
			Creature c = (Creature) state.getCharacter(charName);
			if (equip instanceof Armor) {
				try {
					c.equipArmor((Armor) equip);
				} catch (IllegalInventoryException e) {
					return null;
				}
			} else if (equip instanceof Weapon) {
				try {
					c.equipWeapon((Weapon) equip);
				} catch (IllegalInventoryException e) {
					return null;
				}
			}
		}
		return null;
	}
}
