/**
 * 
 */
package com.game.characters.content.npcs;

import java.io.File;

import com.game.characters.NPC;
import com.game.items.Loot;
import com.game.quest.QuestEvent;
import com.game.quest.QuestEvent.QuestAction;

/**
 * @author TBworkstation
 * 
 */
public class ProfessorCastellanos extends NPC {

	/**
	 * Returns the only instance of the Professor that should be in memory.
	 * 
	 * @return ProfessorCastellanos as he is the only professorCastellanos that
	 *         can exist
	 */
	public static ProfessorCastellanos getProf() {
		return prof;
	}

	/* ========================= PRIVATE STUFF ================================ */

	private ProfessorCastellanos() {
		super(NAME, GENDER, ANIM_DIR, new Loot(0), new QuestEvent(NAME,
				QuestAction.TALK, "You must help me take Prof. Lane's job!\n"
						+ "Find him and kill him so I can assume\n"
						+ "the role of almighty overlord of cs351 and\n"
						+ "thwart all Computer Science student's plans to\n"
						+ "graduate!", null));
	}

	private static final String NAME = "ProfessorCastellanos";
	private static final Gender GENDER = Gender.MALE;
	private static final String ANIM_DIR = "professorCastellanos"
			+ File.separatorChar;
	private static ProfessorCastellanos prof = new ProfessorCastellanos();
	private static final long serialVersionUID = 3026553541191094976L;

}
