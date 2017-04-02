package com.game.characters.content.npcs;

import java.io.File;

import com.game.characters.NPC;
import com.game.items.Loot;
import com.game.quest.QuestEvent;
import com.game.quest.QuestEvent.QuestAction;

/**
 * @author tberge01
 * 
 */
public class Harold extends NPC {

	/**
	 * Returns the only instance of Gerald that should be in memory.
	 * 
	 * @return Gerald as he is the only Gerald that can exist
	 */
	public static Harold getHarold() {
		return Harold;
	}

	/* ========================= PRIVATE STUFF ================================ */

	private Harold() {
		super(
				NAME,
				GENDER,
				ANIM_DIR,
				new Loot(0),
				new QuestEvent(
						NAME,
						QuestAction.TALK,
						"Have you met my twin brother Gerald?\n"
								+ "You should go talk to him, he always like helping out adventures"
								+ "but he is a little particular about the people he helps. You should"
								+ "kill a bear to prove to him your worth"/* TODO */,
						null));
	}

	private static final String NAME = "Harold";
	private static final Gender GENDER = Gender.MALE;
	private static final String ANIM_DIR = "npc_male" + File.separatorChar;
	private static Harold Harold = new Harold();
	private static final long serialVersionUID = 6971983574828148397L;

}
