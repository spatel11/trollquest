package com.game.characters.content.monsters;

import java.io.File;

import com.game.characters.Monster;
import com.game.items.IllegalInventoryException;
import com.game.items.weapon.BearClaws;
import com.game.items.weapon.Weapon;
import com.game.quest.QuestEvent;
import com.game.quest.QuestEvent.QuestAction;

/**
 * A Bear monster that is a slow, but strong enemy
 * 
 * @author tberge01
 * 
 */
public class Bear extends Monster {

 
	/**
	 * Creates a new bear with the name: "Bear" + the bearNumber so it is
	 * differentiated between the other bears in the game
	 * 
	 * @param level
	 *          the integer level of the bear
	 */
	public Bear(int level) {
    // (NAME, GENDER, anim_dir, bHP, bMP, bSPD, bSTR, bINT, bXP, hpMult,
    // mpMult, baseWeap, e, xp, hpRange, mpRange, speedRange, strRange,
    // intRange)
    super(NAME + bearNumber, GENDER, ANIM_DIR, 20, 0, 2, 20, 0, XP_PER_KILL, 2,
        2, null, DIE_EVENT, HP_RANGE, MP_RANGE, SPEED_RANGE, STR_RANGE,
        INT_RANGE);
		Weapon w = new BearClaws();
		try {
			this.getInventory().add(w);
			this.equipWeapon(w);
		} catch (IllegalInventoryException e) {
			e.printStackTrace();
		}
		
		if (level < 1) {
			throw new IllegalArgumentException("level must be above 0");
		}
		for (int i=1; i< level; i++) {
			this.levelUp();
		}

		bearNumber++;
	}

	  /**
   * Returns the bear's {@link QuestEvent}
   * 
   * @return the kill {@link QuestEvent} for a bear
   */
	public static QuestEvent getBearEvent() {
		return DIE_EVENT;
	}
	
	/* =========================== private parts ============================= */
	private static final String NAME= "Bear";
	private static final Gender GENDER= Gender.FEMALE;
	private static final String ANIM_DIR=NAME+File.separatorChar;
	private static final int XP_PER_KILL= 200;
	private static final QuestEvent DIE_EVENT= new QuestEvent(NAME,
	    QuestAction.KILL, null, null);
	 private static final long serialVersionUID= 5594035814702419145L;
  
  private static int bearNumber =0;
  
  private static final int HP_RANGE =5;
  private static final int MP_RANGE=1;
  private static final int SPEED_RANGE=1;
  private static final int STR_RANGE=3;
  private static final int INT_RANGE=1;
}
