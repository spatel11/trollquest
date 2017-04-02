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
public class RolledUpSock extends Weapon {
	private static final long serialVersionUID = -472192673212300494L;
	
	/**
	 * Creates a new instance of a Rolled Up Sock {@link Weapon}... very ineffective in a
	 * combat situation
	 */
	public RolledUpSock() {
		super(NAME, VAL, ENC, STRENGTH_MOD, DMG_LOWER+DMG_RANGE, DMG_LOWER);
	}

	@Override public DrawableResource getResource() {
		return ResourceLoader.loadImageResource(ResourceLoader.DIRECTORY_IMAGES_GUI_ITEMS+"sword.png",0.5f);
	}

	/* ========================= PRIVATE PARTS ============================ */

	private static final String NAME         = "Rolled Up Sock";
	private static final int    VAL          = 1;
	private static final int    ENC          = 10;
	private static final double STRENGTH_MOD = 0.05;
	private static final int    DMG_LOWER    = 1;
	private static final int    DMG_RANGE    = 1;
}
