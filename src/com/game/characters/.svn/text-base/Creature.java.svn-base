package com.game.characters;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import com.game.characters.spells.SpellInventory;
import com.game.items.IllegalInventoryException;
import com.game.items.Loot;
import com.game.items.Swag;
import com.game.items.armor.Armor;
import com.game.items.weapon.Weapon;
import com.game.quest.QuestEvent;
import com.server.command.DieCommand;
import com.server.command.LevelPlayerCommand;

/**
 * @author TBworkstation
 * @author Ian
 */
public abstract class Creature extends GameCharacter {

	/** Creature's serial ID */
	private static final long serialVersionUID = -570654500856412099L;

	/**
	 * Creates a new creature with a unique ID and name, Also defines gender,
	 * the base stats for the Creature, and the inventory
	 * 
	 * @param ID
	 *            integer ID number of the character
	 * @param name
	 *            a String name of the character, visible to other characters in
	 *            the world
	 * @param gender
	 *            the gender of the character
	 * @param baseStats
	 *            the base Statistics of the character all current statistics
	 *            will be calculated by this
	 * @param inventory
	 *            the current inventory of the character
	 */
	public Creature(String name, Gender gender, String anim_directory,
			int baseHP, int baseMP, int baseSPD, int baseSTR, int baseINT,
			int baseXP, int hpMult, int mpMult, Weapon baseWeap, QuestEvent qe) {
		super(name, gender, anim_directory, qe);
		baseStats = new int[Stat.values().length];
		baseStats[Stat.LEVEL.ordinal()] = 1;
		baseStats[Stat.EXPERIENCE.ordinal()] = baseXP;
		baseStats[Stat.HEALTHPOINTS.ordinal()] = baseHP;
		baseStats[Stat.MAGICPOINTS.ordinal()] = baseMP;
		baseStats[Stat.SPEED.ordinal()] = baseSPD;
		baseStats[Stat.STRENGTH.ordinal()] = baseSTR;
		baseStats[Stat.INTELLIGENCE.ordinal()] = baseINT;
		baseStats[Stat.ENCUMBERANCE.ordinal()] = 0;
		baseStats[Stat.DEFENSEVALUE.ordinal()] = 0;

		weapon = null;
		armor = null;

		HP_MULT = hpMult;
		MP_MULT = mpMult;

		currStats = baseStats.clone();
		currStats[Stat.EXPERIENCE.ordinal()] = 0;

		spellSet = new SpellInventory();

		isFly = false;

		try {
			this.getInventory().add(baseWeap);
		} catch (IllegalInventoryException e1) {
		}

		try {
			equipWeapon(baseWeap);
		} catch (NullPointerException e) {

		} catch (IllegalInventoryException e) {

		}

		// hopefully this gets us around the java bug with serializing hashsets
		attackerSet = Collections
				.newSetFromMap(new HashMap<Creature, Boolean>());
	}

	/**
	 * Equips a {@link Weapon} to this creature. If the Creature's current
	 * {@link Weapon} is not null, it removes that {@link Weapon} and puts it
	 * back into inventory
	 * 
	 * @param weapon
	 *            the {@link Weapon} to equipped. The {@link Weapon} must be in
	 *            the characters {@link Loot}
	 * @throws IllegalInventoryException
	 *             if the {@link Weapon} is not in the characters {@link Loot}
	 */
	public void equipWeapon(Weapon weapon) throws IllegalInventoryException {
		Weapon temp = unequipWeapon();
		try {
			getInventory().remove(weapon);
			this.weapon = weapon;
			currStats[Stat.STRENGTH.ordinal()] += baseStats[Stat.STRENGTH
					.ordinal()] * weapon.getStrMod();
		} catch (IllegalInventoryException e) {
			weapon = temp;
			throw new IllegalInventoryException(
					"That weapon is not in inventory!");
		}
	}

	/**
	 * Removes the currently equipped {@link Weapon} from the creature. If the
	 * creature doesn't have a {@link Weapon} equipped, this silently does
	 * nothing
	 * 
	 * @return returns the weapon unequipped by the creature
	 * @throws IllegalInventoryException
	 *             if the inventory is full.
	 */
	public Weapon unequipWeapon() throws IllegalInventoryException {
		Weapon temp = weapon;
		if (weapon != null) {
			getInventory().add(weapon);
			currStats[Stat.STRENGTH.ordinal()] -= baseStats[Stat.STRENGTH
					.ordinal()] * weapon.getStrMod();
		}
		weapon = null;
		return temp;
	}

	/**
	 * Returns the character's current {@link Weapon}. If the character has no
	 * {@link Weapon} equipped, it will return null
	 * 
	 * @return a {@link Weapon} the {@link Creature} has equipped at the moment
	 *         null if there is nothing equipped.
	 */
	public Weapon getCurrentWeapon() {
		return weapon;
	}

	/**
	 * Equips an armor from the player's inventory. If the Creature's current
	 * armor is not null, it removes that {@link Armor} and puts it back into
	 * inventory. Then the specified armor is equipped
	 * 
	 * @param armor
	 * @throws IllegalInventoryException
	 */
	public void equipArmor(Armor armor) throws IllegalInventoryException {
		Armor temp = unequipArmor();
		try {
			getInventory().remove(armor);
			this.armor = armor;
			baseStats[Stat.DEFENSEVALUE.ordinal()] = armor.getArmorVal();
			currStats[Stat.DEFENSEVALUE.ordinal()] = armor.getArmorVal();
		} catch (IllegalInventoryException e) {
			armor = temp;
			throw new IllegalInventoryException(armor.NAME
					+ " is not in inventory");
		}
	}

	/**
	 * Removes the currently equipped {@link Armor} from the creature and puts
	 * it back into its {@link Loot} inventory. If the creature has no armor
	 * equipped, it will silently do nothing and return null
	 * 
	 * @return the {@link Armor} the creature is trying to remove
	 * @throws IllegalInventoryException
	 *             the the inventory is full;
	 */
	public Armor unequipArmor() throws IllegalInventoryException {
		Armor temp = armor;
		if (armor != null) {
			getInventory().add(armor);
			baseStats[Stat.DEFENSEVALUE.ordinal()] = 0;
			currStats[Stat.DEFENSEVALUE.ordinal()] = 0;
		}
		armor = null;
		return temp;
	}

	/**
	 * Gets the currently equipped {@link Armor} or null if no {@link Armor} is
	 * currently equipped
	 * 
	 * @return Null or the current {@link Armor} equipped by the player
	 */
	public Armor getArmor() {
		return armor;
	}

	/**
	 * Gets the current equipped {@link Weapon} or null if no {@link Weapon} is
	 * currently equipped
	 * 
	 * @return null or the current {@link Weapon} equipped by the player
	 */
	public Weapon getWeapon() {
		return weapon;
	}

	/**
	 * Per the system Spec, this will remove ALL {@link Swag} and moolah from a
	 * player's inventory, EXCEPT for their currently equipped {@link Weapon}
	 * and {@link Armor}.
	 * 
	 * @return the player's inventory contents, including moolah and any
	 *         {@link Swag}
	 */
	public Loot dropInventory() {
		Loot currInventory = this.getInventory();
		Loot dropped = new Loot(0);

		try {
			int moolah = currInventory
					.makeWithdrawal(currInventory.getMoolah());
			dropped.makeDeposit(moolah);

			for (Swag swag : currInventory.getSwagList()) {
				dropped.add(currInventory.remove(swag));
			}
		} catch (IllegalInventoryException e1) {
			return dropped; // return what we have so far
		}

		return dropped;
	}

	/**
	 * Returns an integer value of the base statistics of the character
	 * 
	 * @param stat
	 *            the {@link Stat} to be queried
	 * @return the base integer value of the stat specified
	 */
	public int getBaseStat(Stat stat) {
		return baseStats[stat.ordinal()];
	}

	/**
	 * Changes the player's {@link Stat} by a specified amount. Will not let the
	 * {@link Stat} drop below 0. If the {@link Stat} parameter is HEALTHPOINTS
	 * or MAGICPOINTS this will not allow the {@link Stat} to go above their
	 * maximum.
	 * 
	 * @param stat
	 *            the {@link Stat} to change
	 * @param amount
	 *            the integer amount to change it by. Can be either negative or
	 *            positive
	 */
	public void changeStat(Stat stat, int amount) {
		currStats[stat.ordinal()] += amount;

		if (currStats[stat.ordinal()] < 0) {
			currStats[stat.ordinal()] = 0;
		}
		if (stat == Stat.HEALTHPOINTS
				&& currStats[stat.ordinal()] > baseStats[stat.ordinal()]) {
			currStats[stat.ordinal()] = baseStats[stat.ordinal()];
		} else if (stat == Stat.MAGICPOINTS
				&& currStats[stat.ordinal()] > baseStats[stat.ordinal()]) {
			currStats[stat.ordinal()] = baseStats[stat.ordinal()];
		}

	}

	/**
	 * Will only change the base HP or MP of a {@link Creature} by the indicated
	 * amount. This is mainly used for buffing purposes only. If the
	 * {@link Stat} is anything but HEALTHPOINTS or MAGICPOINTS, it will be
	 * ignored.
	 * 
	 * @param stat
	 *            Stat.HEALTHPOINTS or Stat.MAGICPOINTS
	 * @param amount
	 *            the amount the change the stat by
	 */
	public void changeBaseHPorMP(Stat stat, int amount) {
		if (stat == Stat.HEALTHPOINTS) {
			baseStats[Stat.HEALTHPOINTS.ordinal()] += amount;
			currStats[Stat.HEALTHPOINTS.ordinal()] += amount;
		} else if (stat == Stat.MAGICPOINTS) {
			baseStats[Stat.MAGICPOINTS.ordinal()] += amount;
			currStats[Stat.MAGICPOINTS.ordinal()] += amount;
		}
	}

	/**
	 * Returns the value of the current stat specified
	 * 
	 * @param stat
	 *            the {@link Stat} to be queried
	 * @return the crrent integer value of the stat specified
	 */
	public int getCurrStat(Stat stat) {
		if (stat == Stat.ENCUMBERANCE) {
			int enc = this.getInventory().getEncumberance();
			if (weapon != null) {
				enc += weapon.ENCUMBERANCE;
			}
			if (armor != null) {
				enc += armor.ENCUMBERANCE;
			}
			return enc;
		} else if (stat == Stat.DEFENSEVALUE) {
      int def= currStats[Stat.DEFENSEVALUE.ordinal()];
			if (armor != null) {
				def += armor.getArmorVal();
			}
			def += currStats[Stat.SPEED.ordinal()];
      def+= currStats[Stat.LEVEL.ordinal()];
      // changed from adding in the multiplier otherwise level 1 players can't
      // hit anything.
			return def;
		}
		return currStats[stat.ordinal()];
	}

	/** Gets the part of a Stat that the cresture currently has. */
	public final double getCurrStatPart(Stat stat) {
		return (double) (getCurrStat(stat)) / (double) (getBaseStat(stat));
	}

	/**
	 * Returns the spell inventory of the creature. Allows access to the
	 * SpellInventory's add/getSet methods.
	 * 
	 * @return a {@link SpellInventory} object belonging to the current creature
	 */
	public SpellInventory getSpellInventory() {
		return spellSet;
	}

	/**
	 * A recovery function specified in the system specs on page 15 for monsters
	 * this should be specified in the Monster class
	 * 
	 */
	public void recovery() {
		int hpIndex = Stat.HEALTHPOINTS.ordinal();
		int mpIndex = Stat.MAGICPOINTS.ordinal();

		currStats[hpIndex] += HP_MULT * currStats[Stat.LEVEL.ordinal()];
		currStats[mpIndex] += MP_MULT * currStats[Stat.LEVEL.ordinal()];

		if (currStats[hpIndex] > baseStats[hpIndex]) {
			currStats[hpIndex] = baseStats[hpIndex];
			attackerSet.clear(); // remove any attackers if they recover to 100%
									// hp
		}
		if (currStats[mpIndex] > baseStats[mpIndex]) {
			currStats[mpIndex] = baseStats[mpIndex];
		}
	}

	/**
	 * Adds the creature to the creature's attackerSet. This allows each monster
	 * to keep track of who attacked it until it recovers fully. After it
	 * recovers fully, it will remove all creatures from the set and start over
	 * 
	 * @param creature
	 *            the attacking {@link Creature}
	 */
	public void addToAttackerSet(Creature creature) {
		attackerSet.add(creature);
	}

	  /**
   * Returns a set of the creatures currently attacking this creature. This
   * method will not clear the attackerSet, so if called within the
   * {@link DieCommand}, a set.clear() should be called after xp has been
   * distributed;
   * 
   * @return the set of all attackers that have damaged this creature
   */
	public Set<Creature> getAttackerSet() {
		return attackerSet;
	}

	/**
	 * Sets the isFlying flag to true or false
	 * 
	 * @param b
	 *            the boolean to set isFlying to
	 */
	public void setIsFlying(boolean b) {
		isFly = b;
	}

	/**
	 * Returns if the character is flying or not.
	 * 
	 * @return true if the creature is flying, false otherwise
	 */
	public boolean isFlying() {
		return isFly;
	}

	/**
	 * Returns the respawn time according to the type of creature it is. If it
	 * is a player, it will be shorter than a monster's respawn time
	 * 
	 * @return the respawn time as an integer in game ticks
	 */
	public abstract int getRespawnTime();

  /** Returns the additional HEALTHPOINTS a creature gets upon level up */
	public abstract int levelHP();

  /** Returns the additional MAGICPOINTS a creature gets upon level up */
	public abstract int levelMP();

  /** Returns the additional STRENGTH a creature gets upon level up */
	public abstract int levelSTR();

  /** Returns the additional SPEED a creature gets upon level up */
	public abstract int levelSPD();

  /** Returns the additional INTELLIGENCE a creature gets upon level up */
	public abstract int levelINT();

  /** Returns the new target XP for a character */
	public abstract int levelXP();

  /**
   * Returns the integer level rate that determines when a character can learn
   * new spells such that LEVEL % SPELL_RATE == 0
   */
	public abstract int getSpellRate();

  /**
   * Called ONLY by the {@link LevelPlayerCommand} to keep both the server's
   * view of the player and the clients view of the player the same
   * 
   * @param amount
   *          the amount to increase its base HEALTHPOINTS by
   */
	public void levelIncreaseHP(int amount) {
		baseStats[Stat.HEALTHPOINTS.ordinal()] += amount;
		currStats[Stat.HEALTHPOINTS.ordinal()] = baseStats[Stat.HEALTHPOINTS
				.ordinal()];
	}

  /**
   * Called ONLY by the {@link LevelPlayerCommand} to keep both the server's
   * view of the player and the clients view of the player the same
   * 
   * @param amount
   *          the amount to increase its base MAGICPOINTS by
   */
	public void levelIncreaseMP(int amount) {
		baseStats[Stat.MAGICPOINTS.ordinal()] += amount;
		currStats[Stat.MAGICPOINTS.ordinal()] = baseStats[Stat.MAGICPOINTS
				.ordinal()];

	}

  /**
   * Called ONLY by the {@link LevelPlayerCommand} to keep both the server's
   * view of the player and the clients view of the player the same
   * 
   * @param amount
   *          the amount to increase its base STRENGTH by
   */
	public void levelIncreaseSTR(int amount) {
		int strUp = amount;
		int strBuff = currStats[Stat.STRENGTH.ordinal()]
				- baseStats[Stat.STRENGTH.ordinal()];

		baseStats[Stat.STRENGTH.ordinal()] += strUp;
		if (baseStats[Stat.STRENGTH.ordinal()] > 100) {
			baseStats[Stat.STRENGTH.ordinal()] = 100; // max stat value
			currStats[Stat.STRENGTH.ordinal()] = 100 + strBuff;
		} else {
			currStats[Stat.STRENGTH.ordinal()] = baseStats[Stat.STRENGTH
					.ordinal()] + strBuff;
		}
	}

  /**
   * Called ONLY by the {@link LevelPlayerCommand} to keep both the server's
   * view of the player and the clients view of the player the same
   * 
   * @param amount
   *          the amount to increase its base INTELLIGENCE by
   */
	public void levelIncreaseINT(int amount) {
		int intUp = amount;
		int intBuff = currStats[Stat.INTELLIGENCE.ordinal()]
				- baseStats[Stat.INTELLIGENCE.ordinal()];

		baseStats[Stat.INTELLIGENCE.ordinal()] += intUp;
		if (baseStats[Stat.INTELLIGENCE.ordinal()] > 100) {
			baseStats[Stat.INTELLIGENCE.ordinal()] = 100; // max stat value
			currStats[Stat.INTELLIGENCE.ordinal()] = 100 + intBuff;
		} else {
			currStats[Stat.INTELLIGENCE.ordinal()] = baseStats[Stat.INTELLIGENCE
					.ordinal()] + intBuff;
		}

	}

  /**
   * Called ONLY by the {@link LevelPlayerCommand} to keep both the server's
   * view of the player and the clients view of the player the same
   * 
   * @param amount
   *          the amount to increase its base SPEED by
   */
	public void levelIncreaseSPD(int amount) {
		int speedUp = amount;
		int speedBuff = currStats[Stat.SPEED.ordinal()]
				- baseStats[Stat.SPEED.ordinal()];

		baseStats[Stat.SPEED.ordinal()] += speedUp;
		if (baseStats[Stat.SPEED.ordinal()] > 100) {
			baseStats[Stat.SPEED.ordinal()] = 100; // max stat value
			currStats[Stat.SPEED.ordinal()] = 100 + speedBuff;
			// make sure we keep our buffs
		} else {
			currStats[Stat.SPEED.ordinal()] = baseStats[Stat.SPEED.ordinal()]
					+ speedBuff;
		}
	}

  /**
   * Called ONLY by the {@link LevelPlayerCommand} to keep both the server's
   * view of the player and the clients view of the player the same
   * 
   * @param amount
   *          the amount to set the player's target XP to
   */
	public void levelSetXP(int amount) {
		currStats[Stat.EXPERIENCE.ordinal()] = currStats[Stat.EXPERIENCE
				.ordinal()] % baseStats[Stat.EXPERIENCE.ordinal()];
		baseStats[Stat.EXPERIENCE.ordinal()] = amount;
	}

  /**
   * Increases the players level by one. THIS SHOULD ONLY BE CALLED BY THE
   * {@link LevelPlayerCommand} to keep synchronization of the server's player
   * and client's player
   */
	public void levelUp() {
		baseStats[Stat.LEVEL.ordinal()]++;
		currStats[Stat.LEVEL.ordinal()]++;
	}

	/**
	 * Enumeration of every STAT required by the system specification. Used to
	 * define any debuff effects, damage taken, total encumberance, etc of the
	 * player. Because these are used for two different, parallel arrays,
	 * definitions for both arrays will be included for the base (Base:) and the
	 * altered stats (Curr:)
	 * 
	 * @author taylor
	 * 
	 */
	public enum Stat {
		    /**
     * Base: The underlying Strength attribute for the character, ranges from
     * 0-100 and scales according to the player's type
     * 
     * Curr: The strength stat used to determine the damage done when executing
     * attack commands
     */
		STRENGTH,

		    /**
     * Base: The underlying Intelligence attribute for the character, ranges
     * from 0-100 and scales according to the player's type
     * 
     * Curr: the intelligence stat used to determine the damage done when
     * executing cast comands
     */
		INTELLIGENCE,

		    /**
     * Base: The underlying Speed attribute for the character, ranges from 0-100
     * and scales according to the player's type
     * 
     * Curr: the speed attribute for the character, affect movement speed and
     * defense in combat
     */
		SPEED,

		/**
		 * Base: The maximum amount of health a player can have (with a full
		 * health bar) Recovery is given by the functions defined on page 15
		 * 
		 * Curr: The current health a player has. If this reaches 0, a player
		 * should be declared dead and respawn
		 */
		HEALTHPOINTS,

		/**
		 * Base: The maximum amount of magic points a player can have (with a
		 * full magic bar) Recovery is given by the functions defined on page 15
		 * 
		 * Curr: The current magic points a player has left. For each cast, it
		 * should deplete a specified amount of magic points.
		 */
		MAGICPOINTS,

		/**
		 * Base: 0
		 * 
		 * Curr: The 'actual' amount of 'weight' a character's inventory has.
		 */
		ENCUMBERANCE,

		/**
		 * Base: The total experience need to reach the next level
		 * 
		 * Curr: The total amount of experience the player has at the moment
		 */
		EXPERIENCE,

		/**
		 * Base: The total defense value of the creature given by armor only
		 * 
		 * Curr: The total defense value of the creature given by armor and any
		 * spell or actions buffs
		 */
		DEFENSEVALUE,

		/**
		 * Base: The current level of the Creature
		 * 
		 * Curr: The current level of the Creature
		 */
		LEVEL;
	}

	/* ================================PRIVATE PARTS ========================= */

	private int[] baseStats;
	private int[] currStats;
	private Weapon weapon;
	private Armor armor;
	private int HP_MULT;
	private int MP_MULT;

	private boolean isFly;

	protected SpellInventory spellSet;

	private final Set<Creature> attackerSet;
}
