/**
 * 
 */
package com.game.items.armor;

import com.client.ResourceLoader;
import com.client.Graphics.DrawableResource;
import com.game.items.Swag;

/**
 * @author TBworkstation
 */
public abstract class Armor extends Swag {

	/** Armor serial id */
	private static final long serialVersionUID = 2678405386435220680L;

	/**
	 * Creates a new piece of equippable armor with the designated Name,
	 * value, encumberance, and Armor Factor specified
	 * 
	 * @param name the name of the armor
	 * @param value the integer value of the armor
	 * @param encumberance how much the armor 'weighs' 
	 * @param armorVal how much defense the armor provides as an int
	 */
	public Armor(String name, int value, int encumberance, int armorVal) {
		super(name, value, encumberance);
		ARMOR_VALUE= armorVal;
		// TODO Auto-generated constructor stub
	}

	  /**
   * Gets the integer value of the defense of the armor
   * 
   * @return returns the integer armor value of the piece of armor
   */
	public int getArmorVal() {
		return ARMOR_VALUE;
	}
	
  @Override
  public DrawableResource getResource() {
		return ResourceLoader.loadImageResource(ResourceLoader.DIRECTORY_IMAGES_GUI_ITEMS+"shield.png",0.5f);
	}

	/* ================================ PRIVATE PARTS ========================= */

	private final int ARMOR_VALUE;
}
