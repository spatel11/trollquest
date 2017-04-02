/**
 * 
 */
package com.game.characters.content.monsters;

import java.io.File;

import com.game.characters.Monster;
import com.game.items.IllegalInventoryException;
import com.game.items.weapon.ShortSword;
import com.game.items.weapon.Weapon;
import com.game.quest.QuestEvent;
import com.game.quest.QuestEvent.QuestAction;

/**
 * @author TBworkstation
 * 
 */
public class MushroomMan extends Monster {

  /**
   * 
   */
  public MushroomMan(int level) {
    // (NAME, GENDER, anim_dir, bHP, bMP, bSPD, bSTR, bINT, bXP, hpMult,
    // mpMult, baseWeap, e, xp, hpRange, mpRange, speedRange, strRange,
    // intRange)
    super(NAME + mushNumber, GENDER, ANIM_DIR, 15, 15, 5, 5, 15, XP_PER_KILL,
        2, 3, null, DIE_EVENT, HP_RANGE, MP_RANGE, SPEED_RANGE, STR_RANGE,
        INT_RANGE);
    Weapon w= new ShortSword();
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

    mushNumber++;
  }

  /* =========================== private parts ============================= */
  private static final String NAME= "MushroomMan"; // TODO
  private static final Gender GENDER= Gender.MALE;
  private static final String ANIM_DIR= NAME + File.separatorChar;
  private static final int XP_PER_KILL= 400; // TODO
  private static final QuestEvent DIE_EVENT= new QuestEvent(NAME, QuestAction.KILL, null, null);
  private static final long serialVersionUID= 9051003142119010405L;

  private static int mushNumber= 0; // TODO

  private static final int HP_RANGE= 2; // TODO
  private static final int MP_RANGE= 5; // TODO
  private static final int SPEED_RANGE= 1; // TODO
  private static final int STR_RANGE= 1; // TODO
  private static final int INT_RANGE= 5; // TODO
}
