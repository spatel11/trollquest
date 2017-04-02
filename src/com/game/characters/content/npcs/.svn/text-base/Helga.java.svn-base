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
public class Helga extends NPC {
	/**
	 * Returns the only instance of Helga that should be in memory.
	 * 
	 * @return Helga as he is the only Helga that can exist
	 */
	public static Helga getHelga() {
		return Helga;
	}

	/* ========================= PRIVATE STUFF ================================ */

	private Helga() {
		super(
				NAME,
				GENDER,
				ANIM_DIR,
				new Loot(0),
				new QuestEvent(
						NAME,
						QuestAction.TALK,
						"You spoke to Harold and he asked you to"
								+ "help us? We are very grateful. These Orcs have been ravaging"
								+ "our lands for too long. Please eliminate five of them, that should"
								+ "help us defend our homes. Talk to Olga when you get back, she should"
								+ "have some information for you."/* TODO */,
						null));
	}

	private static final String NAME = "Helga";
	private static final Gender GENDER = Gender.FEMALE;
	private static final String ANIM_DIR = "npc_female" + File.separatorChar;
	private static Helga Helga = new Helga();
	private static final long serialVersionUID = 1068917864379152264L;

}
