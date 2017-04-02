package com.game.items.armor;

/**
 * A wet towel {@link Armor}
 * @author tberge01
 *
 */
public class WetTowel extends Armor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5274285602237887127L;
	/**
	 * You might as well go naked... it will give people an excuse as to why you
	 * died, besides 'he was crazy'
	 */
	public WetTowel() {
		super(NAME, VALUE, ENC, AF);
	}
	
	/* ================================ PRIVATE PARTS =============================== */
	
	private static final String NAME = "Wet Towel";
	private static final int VALUE = 5;
	private static final int ENC = 5;
	private static final int AF = 5;

}
