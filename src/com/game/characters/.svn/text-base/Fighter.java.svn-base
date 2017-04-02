package com.game.characters;

import java.io.File;

import com.game.items.weapon.RustyDagger;

/**
 * @author TBworkstation
 * 
 */
public class Fighter extends Player {

  /** Fighter serial ID */
  private static final long serialVersionUID= -1172069384342557241L;

  /**
   * Creates a new playable Fighter with the specified name and {@link Gender}
   * 
   * @param name
   *          the name of the fighter
   * @param gender
   *          the {@link Gender} of the Fighter
   */
  public Fighter(String name, Gender gender) {
    super(name, gender, "Fighter" + File.separator, LEVEL_FACTOR, SPELL_RATE,
        HP_LOWER, HP_RANGE, MP_LOWER, MP_RANGE, SPD_LOWER, SPD_RANGE,
        STR_LOWER, STR_RANGE, INT_LOWER, INT_RANGE, BASE_HP, BASE_MP, BASE_SPD,
        BASE_STR, BASE_INT, BASE_XP, HP_MULT, MP_MULT, new RustyDagger());
  }

  /* ========================== PRIVATE PARTS ======================= */

  private static final double LEVEL_FACTOR= 1.55;
  private static final int SPELL_RATE= 5;
  private static final int HP_LOWER= 10;
  private static final int HP_RANGE= 5;
  private static final int MP_LOWER= 1;
  private static final int MP_RANGE= 4;
  private static final int SPD_LOWER= 2;
  private static final int SPD_RANGE= 3;
  private static final int STR_LOWER= 3;
  private static final int STR_RANGE= 5;
  private static final int INT_LOWER= 1;
  private static final int INT_RANGE= 2;

  private static final int HP_MULT= 3;
  private static final int MP_MULT= 1;

  private static final int BASE_XP= (int) (1000 * LEVEL_FACTOR * LEVEL_FACTOR);
  private static final int BASE_HP= 20;
  private static final int BASE_MP= 1;
  private static final int BASE_SPD= 10;
  private static final int BASE_STR= 20;
  private static final int BASE_INT= 5;
}
