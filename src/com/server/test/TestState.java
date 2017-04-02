/**
 * @author Stephen Patel (spatel11@unm.edu)
 */
package com.server.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Deque;

import org.junit.Before;
import org.junit.Test;

import com.client.Message;
import com.game.characters.GameCharacter.Gender;
import com.game.characters.Mage;
import com.game.characters.Player;
import com.game.characters.content.monsters.Bear;
import com.game.characters.content.monsters.Spider;
import com.game.environment.GameMap;
import com.server.GameScheduler;
import com.server.State;
import com.server.factory.classes.Profile;

/**
 * @author Stephen Patel
 *
 */
public class TestState {

	State s;
	Player player;
	GameMap map;
	int numChars;
	
	/**
	 * 
	 */
	@Before
	public void setUp(){
		s = new State(3, 0);
		Profile p = s.getProfile("Herp");
		player = new Mage("Herp's Male Mage", Gender.MALE);
		p.addNewPlayer(player);
		s.addCharacter(player);
		map = new GameMap("AMAP", 10, 10);
		s.addGameMap(map);
		numChars = 1;
	}
	/**
	 * Test method for {@link com.server.State#getProfile(java.lang.String)}.
	 */
	@Test
	public void testGetProfile() {
		Profile herp = s.getProfile("Herp");
		assertFalse(herp == null);
		assertTrue(herp.getUserName().equals("Herp"));
		assertTrue(herp.getPlayer("player1") == null);
		assertTrue(herp.getPlayer("Herp's Male Mage") != null);
		Profile derp = s.getProfile("Derp");
		assertFalse(derp == null);
		assertTrue(derp.getUserName().equals("Derp"));
	}

	/**
	 * Test method for {@link com.server.State#getScheduler()}.
	 */
	@Test
	public void testGetScheduler() {
		GameScheduler g = s.getScheduler();
		assertTrue(g != null);
	}

	/**
	 * Test method for {@link com.server.State#getGameMap(java.lang.String)}.
	 */
	@Test
	public void testGetGameMap() {
		assertTrue(s.getGameMap("Maptacular") == null);
		assertTrue(s.getGameMap(map.getName()) != null);
	}

	/**
	 * Test method for {@link com.server.State#addGameMap(com.game.environment.GameMap)}.
	 */
	@Test
	public void testAddGameMap() {
		assertTrue(s.getGameMap("Maptacular") == null);
		s.addGameMap(new GameMap("Maptacular", 5, 5));
		assertTrue(s.getGameMap("Maptacular") != null);
	}

	/**
	 * Test method for {@link com.server.State#addCharacter(com.game.characters.GameCharacter)}.
	 */
	@Test
	public void testAddCharacter() {
		assertTrue(s.getCharacter("Bear0") == null);
		s.addCharacter(new Bear(0));
		assertTrue(s.getCharacter("Bear0") != null);
	}

	/**
	 * Test method for {@link com.server.State#getCharacter(java.lang.String)}.
	 */
	@Test
	public void testGetCharacter() {
		assertTrue(s.getCharacter(player.name) != null);
		assertTrue(s.getCharacter("Caboose") == null);
	}

	/**
	 * Test method for {@link com.server.State#getCharacters()}.
	 */
	@Test
	public void testGetCharacters() {
		assertTrue(numChars == s.getCharacters().size());
		s.addCharacter(new Spider(0));
		assertFalse(numChars == s.getCharacters().size());
		assertTrue(numChars+1 == s.getCharacters().size());
	}

	/**
	 * Test method for {@link com.server.State#removeCharacter(java.lang.String)}.
	 */
	@Test
	public void testRemoveCharacter() {
		assertTrue(s.getCharacter(player.name) != null);
		s.removeCharacter(player.name);
		assertTrue(s.getCharacter(player.name) == null);
	}

	/**
	 * Test method for {@link com.server.State#addMessage(com.client.Message)}.
	 */
	@Test
	public void testAddMessage() {
		Message message = new Message("Forever Alone", "This is a test Message!");
		s.addMessage(message);
		assertTrue(s.getChatLog().getLast().equals(message));
	}

	/**
	 * Test method for {@link com.server.State#getChatLog()}.
	 */
	@Test
	public void testGetChatLog() {
		Deque<Message> log = s.getChatLog();
		assertTrue(log != null);
		assertTrue(log.isEmpty());
	}

	/**
	 * Test method for {@link com.server.State#getChatLogSize()}.
	 */
	@Test
	public void testGetChatLogSize() {
		assertTrue(s.getChatLogSize() == 3);
	}
}
