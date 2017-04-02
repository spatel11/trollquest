package com.game.characters.content.monsters;

import java.io.File;

import com.game.characters.Monster;
import com.game.items.IllegalInventoryException;
import com.game.items.weapon.GuruSourceSword;
import com.game.items.weapon.Weapon;
import com.game.quest.QuestEvent;
import com.game.quest.QuestEvent.QuestAction;

/**
 * @author tberge01
 *
 */
public class ProfessorLane extends Monster {


	/**
	 * Returns the only Professor Lane that should be in memory
	 * 
	 * @return a singular instance of Professor Lane
	 */
	public static ProfessorLane getProfLane() {
		return prof;
	}
	
	private ProfessorLane() {
    // (NAME, GENDER, anim_dir, bHP, bMP, bSPD, bSTR, bINT, bXP, hpMult,
    // mpMult, baseWeap, e, xp, hpRange, mpRange, speedRange, strRange,
    // intRange)
    super(NAME, GENDER, ANIM_DIR, 45, 45, 45, 45, 45, XP_PER_KILL, 10,
        10, null, DIE_EVENT, HP_RANGE, MP_RANGE, SPEED_RANGE, STR_RANGE,
        INT_RANGE);
    Weapon w= new GuruSourceSword(); // TODO
    try {
      this.getInventory().add(w);
      this.equipWeapon(w);
    }
    catch (IllegalInventoryException e) {
      e.printStackTrace(); //TODO proper handling
    }


    for (int i= 1; i < 15; i++) {
      this.levelUp();
    }

  }

  /* =========================== private parts ============================= */
  private static final String NAME= "ProfessorLane"; // TODO
  private static final Gender GENDER= Gender.MALE;
  private static final String ANIM_DIR= "professorLane" + File.separatorChar;
  private static final int XP_PER_KILL= 5000; // TODO
  private static final QuestEvent DIE_EVENT= new QuestEvent(NAME, QuestAction.KILL, null, null);

  private static final int HP_RANGE= 7; // TODO
  private static final int MP_RANGE= 7; // TODO
  private static final int SPEED_RANGE= 7; // TODO
  private static final int STR_RANGE= 7; // TODO
  private static final int INT_RANGE= 7; // TODO
  
  private static final ProfessorLane prof = new ProfessorLane();
  private static final long serialVersionUID= -2290890965606412923L;

}
