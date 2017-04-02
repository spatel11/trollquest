/**
 * 
 */
package com.game.environment.test;

import static org.junit.Assert.*;

import java.awt.Point;
import java.io.File;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.game.characters.Creature;
import com.game.characters.Fighter;
import com.game.characters.GameCharacter;
import com.game.characters.Player;
import com.game.characters.Thief;
import com.game.characters.GameCharacter.Gender;
import com.game.environment.GameMap;
import com.game.environment.tiles.Tile;
import com.game.environment.tiles.TileException;
import com.game.environment.tiles.overlay.TileArrow_D;
import com.game.environment.tiles.overlay.TileOverlay;
import com.game.environment.tiles.terrain.TileDesert;
import com.game.environment.tiles.terrain.TileForest;
import com.game.environment.tiles.terrain.TileMountain;
import com.game.environment.tiles.terrain.TilePlain;
import com.game.environment.tiles.terrain.TilePortal;
import com.game.environment.tiles.terrain.TileWater;
import com.game.items.IllegalInventoryException;
import com.game.items.Loot;
import com.game.items.weapon.RustyDagger;

/**
 * @author TBworkstation
 * 
 */
public class TestTile {

	/** */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		map = new GameMap(new File(GameMap.TEST_MAP_DIRECTORY + "testMap.map"));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		water = new TileWater(0, 0);
		desert = new TileDesert(0, 0);
		forest = new TileForest(0, 0);
		mountain = new TileMountain(1, 2);
		plain = new TileMountain(12, 496);
		portal = new TilePortal(0, 0, "testMap.map");

		player = new Thief("dev", Gender.MALE);
	}


	/**
	 * Tests loot is properly managed on the tile
	 */
	@Test
	public void testLootActions() {
		try {
			water.putLoot(new Loot(100000));
		} catch (IllegalInventoryException e) {
			fail();
		}

		try {
			water.putLoot(new Loot(1111));
			assertEquals(101111, water.pickupLoot().getMoolah());
			assertEquals(null, water.pickupLoot()); // should be null here
		} catch (IllegalInventoryException e) {
			fail();
		}

		try {
			desert.putLoot(new Loot(new RustyDagger()));
			desert.putLoot(new Loot(new RustyDagger()));
			assertEquals(2, desert.pickupLoot().getNonNullElementCount());
		} catch (IllegalInventoryException e) {
			fail();
		}

		int i = 0;
		try {
			for (i = 0; i < Loot.MAX_INVENTORY; i++) {
				forest.putLoot(new Loot(new RustyDagger()));
			}
			Loot temp = new Loot(new RustyDagger());
			forest.putLoot(temp);
			fail();
		} catch (IllegalInventoryException e) {
			assertEquals(Loot.MAX_INVENTORY, i);
			// correct as the loot is full and cannot accept any more
		}
	}

	/**
	 * Ensures no two {@link GameCharacter}s can occupy the same tile at the
	 * same time.
	 */
	@Test
	public void testOccupants() {
		try {
			mountain.setOccupant(player);
		} catch (TileException e1) {
			fail(); // should be no one on the tile
		}
		assertEquals(1, player.getPosition().x);
		assertEquals(2, player.getPosition().y);

		Player p = new Fighter("dev1", Gender.FEMALE);
		try {
			mountain.setOccupant(player);
			fail();
		} catch (TileException e) {
			// should not be able to put a character here as there is already
			// one here
		}

		// removing occupants
		assertEquals(player, mountain.removeOccupant()); // make sure these are
		// the
		// same

		try {
			mountain.setOccupant(p);
		} catch (TileException e) {
			fail(); // should work just fine
		}

	}

	/**
	 * Ensures the tile's positions are correct when read in from a file and
	 * that overlay tiles can change positions
	 */
	@Test
	public void testPosition() {
		assertEquals(12, plain.getPosition().x);
		assertEquals(496, plain.getPosition().y);
		plain = new TilePlain(215, 59281);
		assertEquals(215, plain.getPosition().x);
		assertEquals(59281, plain.getPosition().y);

		TileOverlay arrow = new TileArrow_D(0, 1);
		assertEquals(0, arrow.getPosition().x);
		assertEquals(1, arrow.getPosition().y);
		arrow.setPosition(new Point(1, 2));
		assertEquals(1, arrow.getPosition().x);
		assertEquals(2, arrow.getPosition().y);
	}


	/**
	 * Tests to see if the portal tile's desination has been correctly read in
	 * from a file.
	 */
	@Test
	public void testSpecialPortal() {
		assertTrue(((TilePortal) portal).getDestination().equals(map.getName()));
	}

	/* ======================== PRIVATE PARTS ================================= */
	private Tile water, desert, forest, mountain, plain, portal;
	private Creature player;
	private static GameMap map;
}
