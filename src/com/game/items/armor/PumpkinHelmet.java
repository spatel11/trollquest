package com.game.items.armor;

/**
 * A type of {@link Armor} that will be included inside the TrollQuest game
 * 
 * @author TBworkstation
 * 
 */
public class PumpkinHelmet extends Armor {

	/**
	 * Well... anything is better than a wet towel. 
	 */
	public PumpkinHelmet() {
		super(NAME, VALUE, ENC, AF);
	}

	/* ====================== PRIVATE PARTS ======================= */

	private static final String NAME = "Pumpkin Helmet";
	private static final int VALUE = 0;
	private static final int ENC = 3;
	private static final int AF = 10;
	
	private static final long serialVersionUID = 9112526346241718514L;

}
