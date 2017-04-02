package com.game.characters;

import java.io.File;

import com.game.items.weapon.LeadToothbrush;

/**
 * A thief playable character in TrollQuest
 * 
 * @author tberge01
 *
 */
public class Thief extends Player {

	/**Thief serial ID */
	private static final long serialVersionUID = 5980120701499141796L;

	/**
	 * Creates a new Thief character with the specified name and {@link Gender}
	 * 
	 * @param name The name of the thief
	 * @param gender The {@link Gender} of the thief
	 */
	public Thief(String name, Gender gender) {
    super(name, gender, "Thief" + File.separator, LEVEL_FACTOR, SPELL_RATE, HP_LOWER, HP_RANGE, MP_LOWER,
				MP_RANGE, SPD_LOWER, SPD_RANGE, STR_LOWER, STR_RANGE, INT_LOWER,
				INT_RANGE, BASE_HP, BASE_MP, BASE_SPD, BASE_STR, BASE_INT, BASE_XP,
				HP_MULT, MP_MULT, new LeadToothbrush());
	}


	/* ====================== PRIVATE PARTS ===================== */

	private static final double LEVEL_FACTOR = 1.45;
	private static final int    SPELL_RATE   = 2;
	private static final int    HP_LOWER     = 8;
	private static final int    HP_RANGE     = 4;
	private static final int    MP_LOWER     = 5;
	private static final int    MP_RANGE     = 5;
	private static final int    SPD_LOWER    = 3;
	private static final int    SPD_RANGE    = 5;
	private static final int    STR_LOWER    = 2;
	private static final int    STR_RANGE    = 3;
	private static final int    INT_LOWER    = 2;
	private static final int    INT_RANGE    = 3;

	private static final int    HP_MULT      = 2;
	private static final int    MP_MULT      = 2;

	private static final int    BASE_XP      = (int) (1000 * LEVEL_FACTOR * LEVEL_FACTOR);
	private static final int    BASE_HP      = 15;
	private static final int    BASE_MP      = 10;
	private static final int    BASE_SPD     = 15;
	private static final int    BASE_STR     = 5;
	private static final int    BASE_INT     = 8;
}
