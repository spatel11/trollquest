/**
 * @author Stephen Patel (spatel11@unm.edu)
 */
package com.game.items.weapon;

import com.client.ResourceLoader;
import com.client.Graphics.DrawableResource;

/**
 * @author Stephen Patel
 *
 */
public class BearClaws extends Weapon {

	private static final long serialVersionUID = 2022144799988486208L;

	/**
	 * 
	 */
	public BearClaws() {
		super(NAME, VAL, ENC, STRENGTH_MOD, DMG_LOWER+DMG_RANGE, DMG_LOWER);
	}


	@Override public DrawableResource getResource() {
		return ResourceLoader.loadImageResource(ResourceLoader.DIRECTORY_IMAGES_GUI_ITEMS+"sword.png",0.5f);
	}
	/* ========================= PRIVATE PARTS ============================ */

	private static final String NAME         = "BearClaws";
	private static final int    VAL          = 100;
	private static final int    ENC          = 25;
	private static final double STRENGTH_MOD = .2;
	private static final int    DMG_LOWER    = 3;
	private static final int    DMG_RANGE    = 3;
}
