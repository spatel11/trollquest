package com.game.characters.spells;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.game.characters.Creature;
import com.game.characters.Creature.Stat;

/**
 * A spell inventory object that lets a {@link Creature} know what spells they
 * can cast
 * 
 * @author tberge01
 * 
 */
public class SpellInventory implements Serializable {

	/**
   * 
   */
	private static final long serialVersionUID = 7941219686913607939L;

	/**
	 * Creates a new Spell inventory with Zot and GoodDrugs as the only spells
	 * in the list
	 */
	public SpellInventory() {
		spellSet = new ArrayList<AbstractSpell>();
		spellSet.add(new Zot());
		spellSet.add(new GoodDrugs());
    spellSet.add(new Defend());
	}

	/**
	 * Returns a set of all the spells a {@link Creature} knows
	 * 
	 * @return returns an unmodifiable set of the spells a character has in its
	 *         inventory
	 */
	public List<AbstractSpell> getSpellSet() {
		return Collections.unmodifiableList(spellSet);
	}

	/**
	 * Adds the next, learn-able spell in the list of game Spells.
	 */
	public void addNextSpell(Creature c) {

		boolean valid = true;
		for (AbstractSpell s : allSpells) {
			if (contains(s))
				continue;

			// make sure requirements have been met
			for (AbstractSpell req : s.getSpellRequirements()) {
				if (!contains(req)) {
					valid = false;
					break;
				}
			}

      if (s.getLevelRequirement() > c.getBaseStat(Stat.LEVEL)) {
				valid = false;
			}

			// if invalid, continue
			if (!valid) {
				valid = true;
				continue;
			} else {
				spellSet.add(s.newInstance());
				return;
			}
		}
	}

	/**
	 * Adds the targeted spell to the spell inventory. Mainly used in the
	 * instantiation of monsters
	 * 
	 * @param spell
	 *            the spell to add to the spell list. If the List already
	 *            contains the spell, the addition will be silently ignored
	 */
	public void addSpell(AbstractSpell spell) {
		if (!spellSet.contains(spell)) {
			spellSet.add(spell);
		}
	}

	// checks to make sure the spellSet doesn't already contain the spell
	private boolean contains(AbstractSpell spell) {
		for (AbstractSpell s : spellSet) {
			if (s.getClass() == spell.getClass()) {
				return true;
			}
		}
		return false;
	}

	/* ===========================PRIVATE PARTS ========================= */
	private List<AbstractSpell> spellSet;

	private static List<AbstractSpell> buildAllSpells() {
		List<AbstractSpell> temp = new ArrayList<AbstractSpell>();
		temp.add(new BuffSpeed());
    temp.add(new BuffStrength());
		temp.add(new Zorch());
		temp.add(new BuffIntelligence());
		temp.add(new BuffMagic());
		temp.add(new BuffHealth());
		temp.add(new IFeelMuchBetterNow());
		temp.add(new GreatDrugs());
		temp.add(new Fly());
		temp.add(new FlameThrower());
		temp.add(new ArmageddonAndThenSome());

		return temp;
	}

	private static final List<AbstractSpell> allSpells = buildAllSpells();

}
