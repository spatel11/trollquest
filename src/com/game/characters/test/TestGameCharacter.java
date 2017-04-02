package com.game.characters.test;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.client.Graphics.AnimationsOfACharacter.ActionType;
import com.game.characters.GameCharacter;
import com.game.characters.Thief;
import com.game.characters.GameCharacter.Gender;
import com.game.environment.Directions;

/**
 * Tests the creation of a gameCharacter
 * @author tberge01
 *
 */
public class TestGameCharacter {
	
	/**
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		generalCharacter = new Thief("GameChar", Gender.MALE);	
	}
	
	/**
	 * All other methods in the {@link GameCharacter} class are trivial getters and 
	 * setters (see testGettersAndSetters). Meaning the only thing to really 
	 * test is the creation state of the {@link GameCharacter}.
	 */
	@Test
	public void testCreation() {
		assertEquals("GameChar", generalCharacter.name);
		assertEquals(Gender.MALE, generalCharacter.gender);
		
		assertEquals(ActionType.STAND, generalCharacter.getActionState());
		assertEquals(0, generalCharacter.getPosition().x);
		assertEquals(0, generalCharacter.getPosition().y);
		
		assertEquals(0, generalCharacter.getInventory().getMoolah());
		assertEquals(1, generalCharacter.getInventory().getNonNullElementCount());
	}
	
	/**
	 * Testing the simple getters and setters. These are trivial
	 * methods since the {@link GameCharacter} is a stand alone class and
	 * should not ever need to check anything. The checks should happen before
	 * the getters/setters are called. These are just to make sure they really
	 * are working.
	 */
	@Test
	public void testGetterAndSetters() {
		generalCharacter.setMap("testMap.map");
		assertEquals("testMap.map", generalCharacter.getMap());
		
		generalCharacter.setPosition(new Point(1, 1));
		assertEquals(1, generalCharacter.getPosition().x);
		assertEquals(1, generalCharacter.getPosition().y);
		
		generalCharacter.setDirection(Directions._D);
		assertEquals(Directions._D, generalCharacter.getDirection());
		
		generalCharacter.setActionState(ActionType.ATTACK);
		assertEquals(ActionType.ATTACK, generalCharacter.getActionState());
	}

	/**
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		generalCharacter = null;
	}

	
	/* ========================== private parts ============================= */
	private GameCharacter generalCharacter ;
}
