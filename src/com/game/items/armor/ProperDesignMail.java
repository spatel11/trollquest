package com.game.items.armor;

/**
 * A type of {@link Armor} that will be included inside the TrollQuest game
 * 
 * @author TBworkstation
 * 
 */
public class ProperDesignMail extends Armor {

	/**
	 * Creates a new armor made from proper design making this one
	 * of the best armors in the game.
	 */
	public ProperDesignMail() {
		super(NAME, VALUE, ENC, AF);
	}
	
	/* ================================ PRIVATE PARTS =============================== */
	
	private static final String NAME = "Proper Design Mail";
	private static final int VALUE = 60000;
	private static final int ENC = 30;
	private static final int AF = 150;
	
	private static final long serialVersionUID = 5695695162711501320L;
	
}
