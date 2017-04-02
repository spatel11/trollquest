/**
 * 
 */
package com.game.characters;

import java.util.Random;

import com.game.characters.content.QuestList;
import com.game.items.IllegalInventoryException;
import com.game.items.weapon.Weapon;
import com.game.quest.Quest;
import com.game.quest.QuestEvent;
import com.game.quest.QuestEvent.QuestAction;

/**
 * This is a controllable character with a specified starting inventory. The
 * behavior of each the player's defined class ({@link Thief}, {@link Mage}, or
 * {@link Fighter}) on level-up has been defined for each individual Enum type.
 * Every player should be represented by an avatar of a specific class and
 * gender and can be drawn on screen
 * 
 * @author TBworkstation
 * 
 */
public abstract class Player extends Creature {

  /** Player serial ID */
  private static final long serialVersionUID= -4388341363805322511L;

  /**
   * Creates a new Player with a unique ID and Name. Also defines the player's
   * {@link Gender} as well as the type of player ({@link Thief}, {@link Mage},
   * or {@link Fighter}).
   * 
   * @param name
   */
  public Player(String name, Gender gender, String anim_directory,
      double levelFact, int spellRate, int hpLower, int hpRange, int mpLower,
      int mpRange, int spdLower, int spdRange, int strLower, int strRange,
      int intLower, int intRange, int baseHP, int baseMP, int baseSPD,
      int baseSTR, int baseINT, int baseXP, int hpMult, int mpMult,
      Weapon baseWeap) {

    super(name, gender, anim_directory, baseHP, baseMP, baseSPD, baseSTR,
        baseINT, baseXP, hpMult, mpMult, baseWeap, DIE_EVENT);

    LEVEL_FACTOR= levelFact;
    SPELL_RATE= spellRate;
    HP_LOWER= hpLower;
    HP_RANGE= hpRange;
    MP_LOWER= mpLower;
    MP_RANGE= mpRange;
    SPD_LOWER= spdLower;
    SPD_RANGE= spdRange;
    STR_LOWER= strLower;
    STR_RANGE= strRange;
    INT_LOWER= intLower;
    INT_RANGE= intRange;

    this.playersQuest= QuestList.getMainQuest();

    try {
      this.equipWeapon(baseWeap);
    }
    catch (IllegalInventoryException e) {
      // System.out.println(e.getMessage());
    }
  }

  /**
   * Returns the player's active {@link Quest}
   * 
   * @return the player's active {@link Quest}
   */
  public Quest getPlayersQuest() {
    return playersQuest;
  }

  /**
   * Returns the players current status of their quest
   * 
   * @return returns player current {@link QuestEvent} they need to complete to
   *         move on with their {@link Quest}
   */
  public QuestEvent getCurrentQuestEvent() {
    return playersQuest.getCurrentStep();
  }

  @Override
  public int getRespawnTime() {
    return PLAYER_RESPAWN_TIME;
  }

  @Override
  public int levelHP() {
    return HP_LOWER + rand.nextInt(HP_RANGE);
  }

  @Override
  public int levelMP() {
    return MP_LOWER + rand.nextInt(MP_RANGE);
  }

  @Override
  public int levelSPD() {
    return SPD_LOWER + rand.nextInt(SPD_RANGE);
  }

  @Override
  public int levelSTR() {
    return STR_LOWER + rand.nextInt(STR_RANGE);
  }

  @Override
  public int levelINT() {
    return INT_LOWER + rand.nextInt(INT_RANGE);
  }

  @Override
  public int levelXP() {
    return (int) (1000 * Math.pow(LEVEL_FACTOR,
        this.getBaseStat(Stat.LEVEL) + 1));
  }

  @Override
  public int getSpellRate() {
    return SPELL_RATE;
  }

  /* ======================= PRIVATE PARTS ========================== */

  private static final QuestEvent DIE_EVENT= new QuestEvent("player",
      QuestAction.KILL, null, null);
  private final Quest playersQuest;

  private final int PLAYER_RESPAWN_TIME= 6000;

  private final double LEVEL_FACTOR;
  private final int SPELL_RATE;
  private final int HP_LOWER;
  private final int HP_RANGE;
  private final int MP_LOWER;
  private final int MP_RANGE;
  private final int SPD_LOWER;
  private final int SPD_RANGE;
  private final int STR_LOWER;
  private final int STR_RANGE;
  private final int INT_LOWER;
  private final int INT_RANGE;

  private Random rand= new Random(System.currentTimeMillis());

}
