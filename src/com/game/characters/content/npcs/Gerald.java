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
public class Gerald extends NPC {
	/**
	 * Returns the only instance of Gerald that should be in memory.
	 * 
	 * @return Gerald as he is the only Gerald that
	 *         can exist
	 */
	public static Gerald getGerald() {
		return gerald;
	}

	
	
	/* ========================= PRIVATE STUFF ================================ */
	
	

	private Gerald() {
		super(NAME, GENDER, ANIM_DIR, new Loot(0), new QuestEvent(NAME,
		    QuestAction.TALK, "You killed one of those bears?!" + 
		    "Those bears have gotten incredibly ferocious lately so thank" +
		    "you for removing one. Say, I have heard of some strange monsters" +
		    "coming out of the northern lands. You should go find my sister, " +
		    "Helga and see if they need help."/* TODO */,
		    null));
	}

	private static final String NAME= "Gerald";
	private static final Gender GENDER= Gender.MALE;
	private static final String ANIM_DIR= "npc_male" + File.separatorChar;
	private static final long serialVersionUID= -4866737626811968257L;
	private static Gerald gerald = new Gerald();
}
