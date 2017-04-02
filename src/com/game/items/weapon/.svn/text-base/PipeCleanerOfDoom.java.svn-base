package com.game.items.weapon;

import com.client.ResourceLoader;
import com.client.Graphics.DrawableResource;

/**
 * Yes... yes it is a pipe cleaner
 * @author tberge01
 *
 */
public class PipeCleanerOfDoom extends Weapon {
	private static final long serialVersionUID = -2963814295915188230L;
	
	/**
	 * FEAR ME
	 */
	public PipeCleanerOfDoom() {
		super(NAME, VAL, ENC, STRENGTH_MOD, DMG_LOWER+DMG_RANGE, DMG_LOWER);
	}

	@Override public DrawableResource getResource() {
		return ResourceLoader.loadImageResource(ResourceLoader.DIRECTORY_IMAGES_GUI_ITEMS+"sword.png",0.5f);
	}

	/* ========================= PRIVATE PARTS ============================ */

	private static final String NAME         = "Pipe Cleaner Of Doom";
	private static final int    VAL          = 100000;
	private static final int    ENC          = 5;
	private static final double STRENGTH_MOD = 3;
	private static final int    DMG_LOWER    = 100;
	private static final int    DMG_RANGE    = 100;

}
