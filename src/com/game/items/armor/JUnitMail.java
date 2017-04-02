package com.game.items.armor;

/**
 * A type of {@link Armor} that will be included inside the TrollQuest game
 * 
 * @author TBworkstation
 * 
 */
public class JUnitMail extends Armor {
	/**
	 * Creates a new JUnit Mail armor that has decent protection, but heavy encumberance
	 */
	public JUnitMail() {
		super(NAME, VALUE, ENC, AF);
	}
	
	/* ================================ PRIVATE PARTS =============================== */
	
	private static final String NAME = "JUnit Mail";
	private static final int VALUE = 5000;
	private static final int ENC = 75;
	private static final int AF = 65;
	
	private static final long serialVersionUID = 4501135080583598078L;
}

