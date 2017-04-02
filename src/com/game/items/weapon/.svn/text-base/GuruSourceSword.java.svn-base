package com.game.items.weapon;

import com.client.ResourceLoader;
import com.client.Graphics.DrawableResource;

/**
 * A type of {@link Weapon} that will be used in TrollQuest
 * 
 * @author TBworkstation
 * 
 */
public class GuruSourceSword extends Weapon {

	private static final long serialVersionUID = 6457737022131842566L;

	/**
	 * Creates a GuruSourceSword that is an incredibly successful weapon
	 * following all correct design patterns to give you optimal damage range
	 * for the price
	 */
	public GuruSourceSword() {
		super(NAME, VAL, ENC, STRENGTH_MOD, DMG_LOWER + DMG_RANGE, DMG_LOWER);
	}

	@Override
	public DrawableResource getResource() {
		return ResourceLoader.loadImageResource(
				ResourceLoader.DIRECTORY_IMAGES_GUI_ITEMS + "sword.png", 0.5f);
	}

	/* ========================= PRIVATE PARTS ============================ */

	private static final String NAME = "GuruSourceSword";
	private static final int VAL = 1000;
	private static final int ENC = 45;
	private static final double STRENGTH_MOD = .3;
	private static final int DMG_LOWER = 25;
	private static final int DMG_RANGE = 25;
}
