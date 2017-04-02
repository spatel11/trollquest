package com.game.items.armor;

/**
 * Snail mail {@link Armor}
 * @author tberge01
 *
 */
public class SnailMail extends Armor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5342124966037353367L;
	/**
	 * Creates a new snail mail {@link Armor}
	 */
	public SnailMail() {
		super(NAME, VALUE, ENC, AF);
	}
	
	/* ================================ PRIVATE PARTS =============================== */
	
	private static final String NAME = "Snail Mail";
	private static final int VALUE = 20000;
	private static final int ENC = 40;
	private static final int AF = 75;
}
