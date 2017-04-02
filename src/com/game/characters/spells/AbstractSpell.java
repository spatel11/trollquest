package com.game.characters.spells;


import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * A dummy spell, used to register a player's ability to cast a particular spell
 * 
 * @author tberge01 
 *
 */
public abstract class AbstractSpell implements Serializable, SpellCommand {

  /** Quiet already! */
  private static final long serialVersionUID = -5759389766599952565L;

  /**
   * Creates a new DummySpell with the proper level requirements and DummySpell
   * requirements
   * 
   * @param levelReq
   *          the integer level requirements
   * @param spellReq
   *          a set of all spells that must be known before this spell can be
   *          learned
   */
  protected AbstractSpell(int levelReq, List<AbstractSpell> spellReq, int mpCost,
      int difficulty, int range) {
    this.spellReq= spellReq;
    this.levelReq= levelReq;
    this.mpCost= mpCost;
    this.difficulty= difficulty;
    this.range= range;
  }

  /**
   * Returns an immutable set of the spell requirements for this DummySpell
   * @return an unmodifiable set of the spell requirements 
   */
  public List<AbstractSpell> getSpellRequirements() {
    return Collections.unmodifiableList(spellReq);
  }

  /**
   * Returns the level requirement of the spell 
   * 
   * @return an integer level requirement for the spell
   */
  public int getLevelRequirement() {
    return levelReq;
  }

  /**
   * Returns a new instance of the {@link AbstractSpell} to give to another
   * character
   * 
   * @return a new instance of the {@link AbstractSpell}
   */
  public abstract AbstractSpell newInstance();

// /**
// * Returns the damage of the spell or -1 if the spell does not damage anything
// *
// * @return integer value of the damage the spell will cause of -1 if it will
// * not damage anything
// */
// public int getDamage() {
// return damage;
// }

  /**
   * Returns the mp cost of the spell
   * 
   * @return the integer mp cost of the spell
   */
  public int getMPCost() {
    return mpCost;
  }

  /**
   * returns the integer range of the spell or -1 if it has no specified range
   * 
   * @return the integer range of the spell or -1 if it has no specified range
   */
  public int getRange() {
    return range;
  }

  /**
   * Returns the difficulty of the spell
   * 
   * @return the difficulty of the spell
   */
  public int getDifficulty() {
    return difficulty;
  }

  /* =========================== PRIVATE PARTS ======================== */

  private final List<AbstractSpell> spellReq;
  private final int             levelReq;
  private final int mpCost, range, difficulty;


}
