package com.game.items.armor;

/**
 * A chain mail bikini 
 * @author tberge01
 *
 */
public class ChainMailBikini extends Armor {

	private static final long serialVersionUID = 8347785708456123797L;
	
	/**
	 * You don't know how stylish you are
	 */
	public ChainMailBikini() {
		super(NAME, VALUE, ENC, AF);
	}
	
	/* ================================ PRIVATE PARTS =============================== */
	
	private static final String NAME = "Chain Male Bikini";
	private static final int VALUE = 200000;
	private static final int ENC = 5;
	private static final int AF = 200;
}
