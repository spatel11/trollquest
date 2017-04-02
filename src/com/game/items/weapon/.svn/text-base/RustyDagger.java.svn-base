/**
 * 
 */
package com.game.items.weapon;

import com.client.ResourceLoader;
import com.client.Graphics.DrawableResource;

/**
 * @author TBworkstation
 *
 */
public class RustyDagger extends Weapon {
	private static final long serialVersionUID = -7353435382650408122L;
	
	/**
	 * Creates a new Rusty Dagger for combat... At least it has an edge
	 */
	public RustyDagger() {
		super(NAME, VAL, ENC, STRENGTH_MOD, DMG_LOWER+DMG_RANGE, DMG_LOWER);
	}

	@Override public DrawableResource getResource() {
		return ResourceLoader.loadImageResource(ResourceLoader.DIRECTORY_IMAGES_GUI_ITEMS+"sword.png",0.5f);
	}

	/* ========================= PRIVATE PARTS ============================ */

	private static final String NAME         = "Rusty Dagger";
	private static final int    VAL          = 5;
	private static final int    ENC          = 20;
	private static final double STRENGTH_MOD = 0.1;
	private static final int    DMG_LOWER    = 2;
	private static final int    DMG_RANGE    = 2;

}
