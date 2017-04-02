package com.game.environment.test;

import static org.junit.Assert.*;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.game.characters.Creature;
import com.game.characters.Thief;
import com.game.characters.GameCharacter.Gender;
import com.game.environment.GameMap;
import com.game.environment.tiles.TileException;
import com.game.environment.tiles.terrain.TileDesert;
import com.game.environment.tiles.terrain.TileForest;
import com.game.environment.tiles.terrain.TileMountain;
import com.game.environment.tiles.terrain.TilePlain;
import com.game.environment.tiles.terrain.TilePortal;
import com.game.environment.tiles.terrain.TileSwamp;
import com.game.environment.tiles.terrain.TileWater;

/**
 * Testing the {@link GameMap} class
 * 
 * @author tberge01
 *
 */
public class TestGameMap {

	/**
	 * Accessing the map files. 
	 * 
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
	
		Desert20x20File = new File(GameMap.TEST_MAP_DIRECTORY + "20x20Desert.map");
		Desert3x5File= new File(GameMap.TEST_MAP_DIRECTORY + "3x5Desert.map");
		testMapFile= new File(GameMap.TEST_MAP_DIRECTORY + "testMap.map");
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
    desertMap= new GameMap(Desert3x5File);
    desert20x20= new GameMap(Desert20x20File);
    testMap= new GameMap(testMapFile);
	}

	/** */
	@After
	public void tearDown() throws Exception {
		desertMap = null;
	    desert20x20= null;
	    testMap= null;
	}

	/**
	 * Tests whether getting the game map from a file correctly reads in the 
	 * right tiles
	 */
	@Test
	public void testGameMapFile() {
    assertEquals(3, desertMap.getMapWidth());
    assertEquals(5, desertMap.getMapHeight());
		
    assertEquals("3x5Desert.map", desertMap.getName());
		
    for (int i= 0; i < desertMap.getMapWidth(); i++) {
      for (int j= 0; j < desertMap.getMapHeight(); j++) {
        assertTrue(desertMap.getTile(i, j) instanceof TileDesert);
			}
		}
    assertEquals(0, desertMap.getSpawnLoc().x);
    assertEquals(0, desertMap.getSpawnLoc().y);

	}

	/**
	 * Tests if the spawn location is correct for all of the test maps
	 */
	@Test
	public void testGetSpawnLoc() {
    assertEquals(13, desert20x20.getSpawnLoc().x);
    assertEquals(13, desert20x20.getSpawnLoc().y);

    assertEquals(1, testMap.getSpawnLoc().x);
    assertEquals(4, testMap.getSpawnLoc().y);

    Creature player= new Thief("dev", Gender.MALE);
    player.setMap(testMap.getName());
    try {
      testMap.getTile(testMap.getSpawnLoc().x, testMap.getSpawnLoc().y).setOccupant(player);
    }
    catch (TileException e) {
      fail();
    }

    // perform a manual move of a character onto the spawn tile
    Creature enemy= new Thief("enemy", Gender.IT);
    Point p= testMap.getSpawnLoc();
    try {
      testMap.getTile(p.x, p.y).setOccupant(enemy);
    }
    catch (TileException e) {
      fail("Should generate a new tile with no one on it");
    }
	}

	/**
	 * Tests to ensure you are getting the correct tile using the getting method
	 * after the map has been read in from a file
	 */
	@Test
	public void testGetTile() {
    assertTrue(testMap.getTile(3, 0) instanceof TileDesert);
    assertTrue(testMap.getTile(4, 0) instanceof TileForest);
    assertTrue(testMap.getTile(8, 0) instanceof TilePortal);
    assertEquals("testMap.map", ((TilePortal) testMap.getTile(8, 0)).getDestination());
    assertTrue(testMap.getTile(9, 0) instanceof TilePlain);
    assertTrue(testMap.getTile(11, 0) instanceof TilePlain);
    assertTrue(testMap.getTile(12, 0) instanceof TileMountain);
    assertTrue(testMap.getTile(17, 0) instanceof TileSwamp);

    assertTrue(testMap.getTile(8, 3) instanceof TileWater);
    assertTrue(testMap.getTile(0, 13) instanceof TilePortal);
    assertEquals("testMap.map", ((TilePortal) testMap.getTile(0, 13)).getDestination());
    assertTrue(testMap.getTile(testMap.getMapWidth() - 1, testMap.getMapHeight() - 1) instanceof TilePlain);
	}
	
	/**
	 * Tests to ensure writing the map to a string writes the correct string
	 * map data. does this simply by comparing the strings using String.equals()
	 */
	@Test
	public void testToString() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(testMapFile));
		} catch (FileNotFoundException e) {
			fail();
		}
		
		String line;
		String[] mapLines = testMap.toString().split("\n|\r");
		try {
			int i =0;
			while ((line = reader.readLine() )!= null) {
				if (!line.equals(mapLines[i])) {
					fail();
				}
				i++;
			}
			
		} catch (IOException e) {
			fail();
		}
	}
	
	/**
	 * Makes sure that through object serialization, the map is rebuilt correctly
	 * on the other side as this will form the basis of how the map is transfered
	 * across the network. Both maps should be equivalent after writing to a 
	 * {@link ObjectOutputStream} and reading from the {@link ObjectInputStream}
	 */
	@Test
	public void testRebuildMap() {
		
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		
		File testFile = new File(GameMap.TEST_MAP_DIRECTORY + "FileWillBeOverwritten.map");
		
		try {
			oos = new ObjectOutputStream(new FileOutputStream(testFile));
			ois = new ObjectInputStream(new FileInputStream(testFile));
		} catch (IOException e) {
			fail();
		}
		
		
		try {
			oos.writeObject(testMap);
			GameMap temp = (GameMap) ois.readObject();
			if (!temp.toString().equals(testMap.toString())) {
				fail("OIS did not work");
			}
		} catch (IOException e) {
			fail();
		} catch (ClassNotFoundException e) {
			fail();
		}
		
		
	}
	
	/* ========================== private parts ============================= */
	
  private static File Desert20x20File, Desert3x5File,
      testMapFile;
  private GameMap     desertMap, desert20x20, testMap;

}
