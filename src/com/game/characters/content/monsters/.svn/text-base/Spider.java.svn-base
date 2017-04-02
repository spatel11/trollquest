/**
 * 
 */
package com.game.characters.content.monsters;

import java.io.File;

import com.game.characters.Monster;
import com.game.items.IllegalInventoryException;
import com.game.items.weapon.RustyDagger;
import com.game.items.weapon.Weapon;
import com.game.quest.QuestEvent;
import com.game.quest.QuestEvent.QuestAction;

/**
 * @author TBworkstation
 * 
 */
public class Spider extends Monster {

  /**
   * 
   * 
   */
  public Spider(int level) {
    // (NAME, GENDER, anim_dir, bHP, bMP, bSPD, bSTR, bINT, bXP, hpMult,
    // mpMult, baseWeap, e, xp, hpRange, mpRange, speedRange, strRange,
    // intRange)
    super(NAME + spiderNumber, GENDER, ANIM_DIR, 15, 4, 4, 4, 4, XP_PER_KILL,
        2, 2, null, DIE_EVENT, HP_RANGE, MP_RANGE, SPEED_RANGE, STR_RANGE,
        INT_RANGE);
    Weapon w= new RustyDagger(); // TODO
    try {
      this.getInventory().add(w);
      this.equipWeapon(w);
    }
    catch (IllegalInventoryException e) {
      e.printStackTrace();
    }

    if (level < 1) {
      throw new IllegalArgumentException("level must be above 0");
    }
    for (int i= 1; i < level; i++) {
      this.levelUp();
    }

    spiderNumber++;
  }

  /* =========================== private parts ============================= */
  private static final String NAME= "Spider"; // TODO
  private static final Gender GENDER= Gender.MALE;
  private static final String ANIM_DIR= NAME + File.separatorChar;
  private static final int XP_PER_KILL= 200; // TODO
  private static final QuestEvent DIE_EVENT= new QuestEvent(NAME, QuestAction.KILL, null, null);

  private static final long serialVersionUID= -4925866545995165640L;

  private static int spiderNumber= 0; // TODO

  private static final int HP_RANGE= 2; // TODO
  private static final int MP_RANGE= 2; // TODO
  private static final int SPEED_RANGE= 2; // TODO
  private static final int STR_RANGE= 2; // TODO
  private static final int INT_RANGE= 2; // TODO
}
