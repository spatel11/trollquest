package com.game.items.armor;

/**
 * A type of {@link Armor} that will be included inside the TrollQuest game
 * 
 * @author TBworkstation
 * 
 */
public class GuruSourceArmor extends Armor {

	/**
	 * Creates a tricky GuruSourceArmor, which offers almost as much protection
	 * as a chain mail bikini
	 */
	public GuruSourceArmor() {
		super(NAME, VALUE, ENC, AF);
	}

	/* ====================== PRIVATE PARTS ======================= */

	private static final String NAME = "Guru Source Armor";
	private static final int VALUE = 100000;
	private static final int ENC = 40;
	private static final int AF = 175;	

	private static final long serialVersionUID = 3382955400967176998L;

}
