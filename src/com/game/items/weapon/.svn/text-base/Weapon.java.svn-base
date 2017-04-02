package com.game.items.weapon;

import java.util.Random;

import com.game.items.Swag;

/**
 * @author TBworkstation
 * 
 */
public abstract class Weapon extends Swag {
	private static final long serialVersionUID = 2383763306549711644L;
	
	/**
	 * Creates a new Weapon object that is a subclass of {@link Swag}
	 * 
	 * @param name the name of the weapon
	 * @param value the value of the weapon
	 * @param encumberance how much the weapon 'weighs'
	 * @param strengthMod the strength modifier the weapon gives 
	 * @param dmgUpperBound the upper bound of the damage this weapon deals
	 * @param dmgLowerBound the lower bound of the damage this weapon deals
	 */
	public Weapon(String name, int value, int encumberance, double strengthMod, int dmgUpperBound, int dmgLowerBound) {
		super(name, value, encumberance);
		STR_MOD = strengthMod;
		this.dmgLowerBound = dmgLowerBound;
		this.dmgUpperBound = dmgUpperBound;
		

		rand = new Random(System.currentTimeMillis());
	}

	/**
	 * Gets a random damage integer value between this weapon's lower and upper
	 * damage bounds 
	 * @return the damage this weapon does during that swing
	 */
	public int getDamage() {
		if(rand == null)
			rand = new Random(System.currentTimeMillis());
		return (rand.nextInt(dmgUpperBound - dmgLowerBound+1) + dmgLowerBound);
	}

	/**
	 * Returns the double strength value multiplier for this weapon
	 * @return the strength value multiplier for this weapon
	 */
	public double getStrMod() {
		return STR_MOD;
	}

	/* ================== PRIVATE PARTS ===================== */


	private final double STR_MOD;
	private final int dmgLowerBound;
	private final int dmgUpperBound;
	private transient Random rand;
}
