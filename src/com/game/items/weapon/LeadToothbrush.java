package com.game.items.weapon;

import com.client.ResourceLoader;
import com.client.Graphics.DrawableResource;

/**
 * A lead toothbrush {@link Weapon}
 * @author tberge01
 *
 */
public class LeadToothbrush extends Weapon {
	private static final long serialVersionUID = -5872557591230360937L;
	
	/**
	 * Creates a new Lead Toothbrush {@link Weapon}... kinda heavy for a weapon you
	 * think?
	 */
	public LeadToothbrush() {
		super(NAME, VAL, ENC, STRENGTH_MOD, DMG_LOWER+DMG_RANGE, DMG_LOWER);
	}

	
	@Override public DrawableResource getResource() {
		return ResourceLoader.loadImageResource(
				ResourceLoader.DIRECTORY_IMAGES_GUI_ITEMS + "sword.png", 0.5f);
	}

	/* ========================= PRIVATE PARTS ============================ */

	private static final String NAME = "Lead Toothbrush";
	private static final int VAL = 3;
	private static final int ENC = 35;
	private static final double STRENGTH_MOD = 0.1;
	private static final int DMG_LOWER = 1;
	private static final int DMG_RANGE = 2;
}
