package com.game.items.weapon;

import com.client.ResourceLoader;
import com.client.Graphics.DrawableResource;
/**
 * A short sword {@link Weapon}
 * @author tberge01
 *
 */
public class ShortSword extends Weapon {
	private static final long serialVersionUID = 2034163805754351273L;

	/**
	 * Creates a new short sword {@link Weapon} that can be equipped
	 */
	public ShortSword() {
		super(NAME, VAL, ENC, STRENGTH_MOD, DMG_LOWER+DMG_RANGE, DMG_LOWER);
	}
	
	@Override public DrawableResource getResource() {
		return ResourceLoader.loadImageResource(ResourceLoader.DIRECTORY_IMAGES_GUI_ITEMS+"sword.png",0.5f);
	}

	/* ========================= PRIVATE PARTS ============================ */

	private static final String NAME         = "Short Sword";
	private static final int    VAL          = 30;
	private static final int    ENC          = 25;
	private static final double STRENGTH_MOD = 0.2;
	private static final int    DMG_LOWER    = 6;
	private static final int    DMG_RANGE    = 4;

	

}
