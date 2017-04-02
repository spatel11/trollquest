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
public class Olga extends NPC {
	/**
	 * Returns the only instance of Helga that should be in memory.
	 * 
	 * @return Helga as he is the only Helga that can exist
	 */
	public static Olga getOlga() {
		return Olga;
	}

	/* ========================= PRIVATE STUFF ================================ */

	private Olga() {
		super(
				NAME,
				GENDER,
				ANIM_DIR,
				new Loot(0),
				new QuestEvent(
						NAME,
						QuestAction.TALK,
						"You are our savior! Those Orcs would"
								+ "have killed us if we stayed any longer. I had a gift for you but,"
								+ "there was a professor who came here while you were gone"
								+ "weilding awesome haxxor powers and took everything we had to"
								+ "give you. He said he would be in the mountains waiting for you."/* TODO */,
						null));
	}

	private static final String NAME = "Olga";
	private static final Gender GENDER = Gender.FEMALE;
	private static final String ANIM_DIR = "npc_female" + File.separatorChar;
	private static Olga Olga = new Olga();
	private static final long serialVersionUID = -1984042889896998029L;

}
