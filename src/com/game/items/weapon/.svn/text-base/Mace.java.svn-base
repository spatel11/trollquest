package com.game.items.weapon;

import com.client.ResourceLoader;
import com.client.Graphics.DrawableResource;

/**
 * A mace {@link Weapon}
 * @author tberge01
 *
 */
public class Mace extends Weapon {
	private static final long serialVersionUID = 1050991821066616165L;

	/**
	 * Creates a new mace {@link Weapon} that can be equipped
	 */
	public Mace() {
		super(NAME, VAL, ENC, STRENGTH_MOD, DMG_LOWER+DMG_RANGE, DMG_LOWER);
	}

	@Override public DrawableResource getResource() {
		return ResourceLoader.loadImageResource(ResourceLoader.DIRECTORY_IMAGES_GUI_ITEMS+"sword.png",0.5f);
	}
	/* ========================= PRIVATE PARTS ============================ */

	private static final String NAME         = "Mace";
	private static final int    VAL          = 50;
	private static final int    ENC          = 40;
	private static final double STRENGTH_MOD = 0.1;
	private static final int    DMG_LOWER    = 10;
	private static final int    DMG_RANGE    = 5;

}
