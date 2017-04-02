/**
 * 
 */
package com.game.characters;

import java.io.File;

import com.game.items.weapon.RolledUpSock;

/**
 * @author TBworkstation
 * 
 */
public class Mage extends Player {

  /** Mage serial ID */
  private static final long serialVersionUID = 3704494977894678921L;

  /**
   * Creates a new mage with the specified name and gender.
   * 
   * @param name
   *          the name of the new mage
   * @param gender
   *          the {@link Gender} of the mage
   */
  public Mage(String name, Gender gender) {
    super(name, gender, "Mage" + File.separator, LEVEL_FACTOR, SPELL_RATE,
        HP_LOWER, HP_RANGE, MP_LOWER, MP_RANGE, SPD_LOWER, SPD_RANGE,
        STR_LOWER, STR_RANGE, INT_LOWER, INT_RANGE, BASE_HP, BASE_MP, BASE_SPD,
        BASE_STR, BASE_INT, BASE_XP, HP_MULT, MP_MULT, new RolledUpSock());
  }

  /* ========================== PRIVATE PARTS ======================= */

  private static final double LEVEL_FACTOR = 1.5;
  private static final int    SPELL_RATE   = 1;
  private static final int    HP_LOWER     = 6;
  private static final int    HP_RANGE     = 4;
  private static final int    MP_LOWER     = 10;
  private static final int    MP_RANGE     = 5;
  private static final int    SPD_LOWER    = 2;
  private static final int    SPD_RANGE    = 4;
  private static final int    STR_LOWER    = 1;
  private static final int    STR_RANGE    = 2;
  private static final int    INT_LOWER    = 3;
  private static final int    INT_RANGE    = 5;

  private static final int    HP_MULT      = 1;
  private static final int    MP_MULT      = 3;

  private static final int    BASE_XP      = (int) (1000 * LEVEL_FACTOR * LEVEL_FACTOR);
  private static final int    BASE_HP      = 8;
  private static final int    BASE_MP      = 20;
  private static final int    BASE_SPD     = 8;
  private static final int    BASE_STR     = 5;
  private static final int    BASE_INT     = 20;
}
