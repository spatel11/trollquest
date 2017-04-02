package com.game.items.weapon;

import com.client.ResourceLoader;
import com.client.Graphics.DrawableResource;

/**
 * The legendary excalibur {@link Weapon}
 * @author tberge01
 *
 */
public class Excalibur extends Weapon {
	private static final long serialVersionUID = -1144063114975045453L;
	
	/**
	 * Creates a new Excalibur {@link Weapon}... though really there's only one
	 * true Excalibur
	 */
	public Excalibur() {
		super(NAME, VAL, ENC, STRENGTH_MOD, DMG_LOWER+DMG_RANGE, DMG_LOWER);
	}


	@Override public DrawableResource getResource() {
		return ResourceLoader.loadImageResource(ResourceLoader.DIRECTORY_IMAGES_GUI_ITEMS+"sword.png",0.5f);
	}
	/* ========================= PRIVATE PARTS ============================ */

	private static final String NAME         = "Excalibur";
	private static final int    VAL          = 10000;
	private static final int    ENC          = 30;
	private static final double STRENGTH_MOD = 1.2;
	private static final int    DMG_LOWER    = 50;
	private static final int    DMG_RANGE    = 50;

}
