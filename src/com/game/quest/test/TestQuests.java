/**
 * 
 */
package com.game.quest.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.game.characters.NPC;
import com.game.characters.GameCharacter.Gender;
import com.game.characters.Monster;
import com.game.characters.Player;
import com.game.characters.Thief;
import com.game.characters.content.monsters.Spider;
import com.game.characters.content.npcs.ProfessorCastellanos;
import com.game.items.Loot;
import com.game.items.weapon.PipeCleanerOfDoom;
import com.game.quest.Quest;
import com.game.quest.QuestEvent;

/**
 * @author TBworkstation
 * 
 */
public class TestQuests {

	/**
	 * sets up the player, npc, and monster as well as the {@link QuestEvent}s for
	 * each one.
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		npc= ProfessorCastellanos.getProf();
		player= new Thief("Dev", Gender.MALE);
		monster= new Spider(1);

		talk1= npc.getQuestEvent();
		kill1= monster.getQuestEvent();
		suicide1= player.getQuestEvent();

	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

		steps= new ArrayList<QuestEvent>();
		steps.add(talk1);
		steps.add(kill1);
		steps.add(suicide1);

		reward= new Loot(new PipeCleanerOfDoom());
		reward.makeDeposit(100000);

		mainQuest= new Quest("Main Quest", steps, reward);
	}

	/**
	 * Ensures that only the top {@link QuestEvent} is the only event that polls
	 * the top of the {@link Queue} in the {@link Quest}
	 */
	@Test
	public void testQuestMechanics() {
		mainQuest.checkTop(suicide1);
		assertTrue(mainQuest.getCurrentStep().equals(talk1));
		assertEquals(false, mainQuest.isFinished());

		mainQuest.checkTop(kill1);
		assertTrue(mainQuest.getCurrentStep().equals(talk1));
		assertEquals(false, mainQuest.isFinished());

		mainQuest.checkTop(talk1);
		assertTrue(mainQuest.getCurrentStep().equals(kill1));
		assertEquals(false, mainQuest.isFinished());

		mainQuest.checkTop(suicide1);
		assertTrue(mainQuest.getCurrentStep().equals(kill1));
		assertEquals(false, mainQuest.isFinished());

		mainQuest.checkTop(kill1);
		assertTrue(mainQuest.getCurrentStep().equals(suicide1));
		assertEquals(false, mainQuest.isFinished());

		mainQuest.checkTop(suicide1);
		assertTrue(mainQuest.getCurrentStep() == null);
		assertEquals(true, mainQuest.isFinished());

	}
	
	
	/**
	 * make sure every playable character has the main quest and the main quest
	 * can be completed by the player with the correct QuestEvents
	 */
	@Test
	public void testMainQuest() {
		fail("not implemented yet");
	}

	/* ========================= private parts ================================ */

	private static Quest mainQuest;
	private static Loot reward;
	private static List<QuestEvent> steps;
	private static QuestEvent talk1, kill1, suicide1;
	private static NPC npc;
	private static Player player;
	private static Monster monster;
}
