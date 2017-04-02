package com.game.characters.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.game.characters.GameCharacter;
import com.game.characters.NPC;
import com.game.characters.content.npcs.ProfessorCastellanos;

/**
 * Tests the validity of the NPC's functionality in our TrollQuest. 
 * @author tberge01
 *
 */
public class TestNPC {

	/**
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
    npc= ProfessorCastellanos.getProf();
	}

	/**
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		npc = null;
	}
	
	
	/**
	 * Since NPC's are essentially just {@link GameCharacter} that can be 
	 * instantiated. Testing the creation of such characters has already been
	 * done in {@link GameCharacter} 
	 */
	@Test
	public void testCreation() {
		fail("any changes to a standard NPC must be tested!");
	}

	/* ========================== PRIVATE PARTS ============================= */
	private NPC npc;
}
