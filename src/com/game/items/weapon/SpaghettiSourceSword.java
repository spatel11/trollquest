package com.game.items.weapon;

import com.client.ResourceLoader;
import com.client.Graphics.DrawableResource;

/**
 * A type of {@link Weapon} that will be used in TrollQuest
 * 
 * @author TBworkstation
 * 
 */
public class SpaghettiSourceSword extends Weapon {
	
	/**
	 * Creates a new SpaghettiSourceSword which is wildly unpredictable
	 * and really weighs you down. In practice, it is also worth NOTHING
	 */
	public SpaghettiSourceSword() {
		super(NAME, VAL, ENC, STRENGTH_MOD, DMG_LOWER + DMG_RANGE, DMG_LOWER);
	}

	@Override
	public DrawableResource getResource() {
		return ResourceLoader.loadImageResource(
				ResourceLoader.DIRECTORY_IMAGES_GUI_ITEMS + "sword.png", 0.5f);
	}

	/* ========================= PRIVATE PARTS ============================ */

	private static final String NAME = "SpaghettiSourceSword";
	private static final int VAL = 0;
	private static final int ENC = 75;
	private static final double STRENGTH_MOD = -.5;
	private static final int DMG_LOWER = 0;
	private static final int DMG_RANGE = 50;
	
	private static final long serialVersionUID = 5274813864243539837L;
	
}
