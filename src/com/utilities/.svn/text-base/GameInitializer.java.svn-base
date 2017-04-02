/**
 * 
 */
package com.utilities;

import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.client.ResourceLoader;
import com.game.characters.Creature;
import com.game.characters.Monster;
import com.game.characters.content.monsters.Bear;
import com.game.characters.content.monsters.CrocWarrior;
import com.game.characters.content.monsters.FireDragon;
import com.game.characters.content.monsters.GnomeWarrior;
import com.game.characters.content.monsters.MushroomMan;
import com.game.characters.content.monsters.Orc;
import com.game.characters.content.monsters.ProfessorLane;
import com.game.characters.content.monsters.Spider;
import com.game.characters.content.monsters.Troll;
import com.game.characters.content.npcs.Gerald;
import com.game.characters.content.npcs.Harold;
import com.game.characters.content.npcs.Helga;
import com.game.characters.content.npcs.Merchant;
import com.game.characters.content.npcs.Olga;
import com.game.characters.content.npcs.ProfessorCastellanos;
import com.game.environment.GameMap;
import com.game.environment.GameMap.BFSIterator;
import com.game.environment.tiles.Tile;
import com.game.environment.tiles.TileException;
import com.game.items.IllegalInventoryException;
import com.game.items.Loot;
import com.game.items.Swag;
import com.game.items.SwagFactory;
import com.game.items.armor.ScaleMail;
import com.game.items.weapon.ShortSword;
import com.server.State;

/**
 * @author TBworkstation
 * 
 */
public abstract class GameInitializer {

	/**
	 * Initializes the entire JWorld in this one method and properly adds it to
	 * the state. Counts on the {@link State} being initialized properly before
	 * reference is passed into this method
	 * 
	 * @param state
	 *            the {@link State} of the server
	 */
	public static void initGame(State state) {
		File mapDir = new File(ResourceLoader.DIRECTORY_DATA + "mapFiles"
				+ File.separator);
		System.out.println(mapDir.getAbsolutePath());
		for (File f : mapDir.listFiles()) {
			if (f.getName().endsWith(".map")) {
				GameMap m = new GameMap(f);
				state.addGameMap(m);
			}
		}
		GameMap m;

		// initialize the NPC's in their proper locations
		// ==========================
		m = state.getGameMap("CenterMap.map");

		Harold h = Harold.getHarold();
		h.setMap("CenterMap.map");
		h.setPosition(new Point(1, 19));

		Gerald g = Gerald.getGerald();
		g.setMap("CenterMap.map");
		g.setPosition(new Point(10, 20));

		try {
			Tile t = m.getTile(h.getPosition().x, h.getPosition().y);
			t.setOccupant(h);
			t = m.getTile(g.getPosition().x, g.getPosition().y);
			t.setOccupant(g);
		} catch (TileException e) {
		}

		m = state.getGameMap("BottomMap.map");

		Helga helga = Helga.getHelga();
		Olga olga = Olga.getOlga();

		helga.setPosition(new Point(18, 18));
		olga.setPosition(new Point(16, 17));

		helga.setMap("BottomMap.map");
		olga.setMap("BottomMap.map");

		try {
			Tile t = m.getTile(helga.getPosition().x, helga.getPosition().y);
			t.setOccupant(helga);
			t = m.getTile(olga.getPosition().x, olga.getPosition().y);
			t.setOccupant(olga);
		} catch (TileException e) {
		}

		ProfessorCastellanos cast = ProfessorCastellanos.getProf();
		cast.setMap("BottomMap.map");
		cast.setPosition(new Point(13, 37));

		try {
			Tile t = m.getTile(cast.getPosition().x, cast.getPosition().y);
			t.setOccupant(cast);
		} catch (TileException e) {
		}

		// special monster
		ProfessorLane lane = ProfessorLane.getProfLane();
		lane.setMap("BottomMap.map");
		lane.anchorSpawnLoc(new Point(39, 1));
		// cant forget to do this. :D
		state.addCharacter(lane);
		state.addCharacter(olga);
		state.addCharacter(helga);
		state.addCharacter(g);
		state.addCharacter(h);

		// init all merchants
		// =====================================================

		Merchant Stephen = new Merchant("Trollchant");
		Merchant Ian = new Merchant("Ian");
		Merchant Martin = new Merchant("BuffaloFight");
		Merchant Taylor = new Merchant("Taylor");
		Merchant Blargh = new Merchant("Blargh");

		Stephen.setMap("CenterMap.map");
		Ian.setMap("LeftMap.map");
		Martin.setMap("RightMap.map");
		Taylor.setMap("TopMap.map");
		Blargh.setMap("BottomMap.map");

    Stephen.setPosition(new Point(5, 21));
    Ian.setPosition(new Point(23, 32));
		Martin.setPosition(new Point(4, 36));
		Taylor.setPosition(new Point(6, 36));
		Blargh.setPosition(new Point(21, 21));

		try {
			m = state.getGameMap(Stephen.getMap());
			Tile t = m
					.getTile(Stephen.getPosition().x, Stephen.getPosition().y);
			t.setOccupant(Stephen);

			m = state.getGameMap(Ian.getMap());
			t = m.getTile(Ian.getPosition().x, Ian.getPosition().y);
			t.setOccupant(Ian);

			m = state.getGameMap(Martin.getMap());
			t = m.getTile(Martin.getPosition().x, Martin.getPosition().y);
			t.setOccupant(Martin);

			m = state.getGameMap(Taylor.getMap());
			t = m.getTile(Taylor.getPosition().x, Taylor.getPosition().y);
			t.setOccupant(Taylor);

			m = state.getGameMap(Blargh.getMap());
			t = m.getTile(Blargh.getPosition().x, Blargh.getPosition().y);
			t.setOccupant(Blargh);

		} catch (TileException e) {

		}

		state.addCharacter(Stephen);
		state.addCharacter(Ian);
		state.addCharacter(Martin);
		state.addCharacter(Taylor);
		state.addCharacter(Blargh);

		// init all monsters
		// ======================================================

		Random rand = new Random(System.currentTimeMillis());

		m = state.getGameMap("CenterMap.map");
		int moolah = 0;
		List<Swag> swags = new ArrayList<Swag>();
		
		swags.add(new Swag("broken teeth", 20, 1));
		swags.add(new Swag("expensive golden statue", 2, 100));
		swags.add(new Swag("broken sword hilt", 50, 10));
		swags.add(new Swag("ye flask", 25, 5));
		swags.add(new Swag("lump of gold", 150, 25));
		swags.add(new Swag("the empty string", 500, 25));
		swags.add(new Swag("indistinguishable modern art", 2000, 15));
		swags.add(new Swag("pumpkin hat", 200, 10));
		swags.add(new Swag("dunce hat", 5000, 5));
		swags.add(new Swag("Koala Bear", 10000, 500));
		swags.add(new Swag("baby tears", 10, 1));
		swags.add(new Swag("Chuck Norris beard", 1000, 10));
		swags.add(new ScaleMail());
		swags.add(new ShortSword());
		
		Loot loot;
		Swag tempSwag;
		
		for (int i = 0; i < 5; i++) {
			
			
			Bear b = new Bear(rand.nextInt(5) + 1);
			b.setMap(m.getName());
			b.anchorSpawnLoc(new Point(10, 36));
			moolah = rand.nextInt(500)+1;
			loot = new Loot(moolah);
			tempSwag = swags.get(rand.nextInt(swags.size()));
      try {
        loot.add(SwagFactory.getNewInstance(tempSwag));
        b.getInventory().merge(loot);
      }
      catch (IllegalInventoryException e) {
        System.err.println("Warning: " + b.name
            + "'s loot was not initialized proplery.");
      }

			state.addCharacter(b);

			Spider s = new Spider(rand.nextInt(5) + 1);
			s.setMap(m.getName());
			s.anchorSpawnLoc(new Point(8, 12));
			moolah = rand.nextInt(500)+1;
			loot = new Loot(moolah);
			tempSwag = swags.get(rand.nextInt(swags.size()));
      try {
        loot.add(SwagFactory.getNewInstance(tempSwag));
        s.getInventory().merge(loot);
      }
      catch (IllegalInventoryException e) {
        System.err.println("Warning: " + s.name
            + "'s loot was not initialized proplery.");
      }
			state.addCharacter(s);

			MushroomMan mm = new MushroomMan(rand.nextInt(5) + 1);
			mm.setMap(m.getName());
			mm.anchorSpawnLoc(new Point(37, 12));
			moolah = rand.nextInt(500)+1;
			loot = new Loot(moolah);
			tempSwag = swags.get(rand.nextInt(swags.size()));
      try {
        loot.add(SwagFactory.getNewInstance(tempSwag));
        mm.getInventory().merge(loot);
      }
      catch (IllegalInventoryException e) {
        System.err.println("Warning: " + mm.name
            + "'s loot was not initialized proplery.");
      }
			state.addCharacter(mm);

			Orc o = new Orc(rand.nextInt(5) + 1);
			o.setMap(m.getName());
			o.anchorSpawnLoc(new Point(25, 47));
			moolah = rand.nextInt(500)+1;
			loot = new Loot(moolah);
			tempSwag = swags.get(rand.nextInt(swags.size()));
      try {
        loot.add(SwagFactory.getNewInstance(tempSwag));
        o.getInventory().merge(loot);
      }
      catch (IllegalInventoryException e) {
        System.err.println("Warning: " + o.name
            + "'s loot was not initialized proplery.");
      }
			state.addCharacter(o);

			GnomeWarrior gw = new GnomeWarrior(rand.nextInt(5) + 1);
			gw.setMap(m.getName());
			gw.anchorSpawnLoc(new Point(36, 32));
			moolah = rand.nextInt(500)+1;
			loot = new Loot(moolah);
			tempSwag = swags.get(rand.nextInt(swags.size()));
			try {
        loot.add(SwagFactory.getNewInstance(tempSwag));
        gw.getInventory().merge(loot);
			} catch (IllegalInventoryException e) {
        System.err.println("Warning: " + gw.name
            + "'s loot was not initialized proplery.");
			}
			state.addCharacter(gw);
		}

		m = state.getGameMap("BottomMap.map");

		for (int i = 0; i < 5; i++) {
			FireDragon b = new FireDragon(rand.nextInt(5) + 5);
			b.setMap(m.getName());
			b.anchorSpawnLoc(new Point(5, 5));
			moolah = rand.nextInt(500)+1;
			loot = new Loot(moolah);
			tempSwag = swags.get(rand.nextInt(swags.size()));
      try {
        loot.add(SwagFactory.getNewInstance(tempSwag));
        b.getInventory().merge(loot);
      }
      catch (IllegalInventoryException e) {
        System.err.println("Warning: " + b.name
            + "'s loot was not initialized proplery.");
      }
			state.addCharacter(b);

			Spider s = new Spider(rand.nextInt(5) + 5);
			s.setMap(m.getName());
			s.anchorSpawnLoc(new Point(35, 35));
			moolah = rand.nextInt(500)+1;
			loot = new Loot(moolah);
			tempSwag = swags.get(rand.nextInt(swags.size()));
      try {
        loot.add(SwagFactory.getNewInstance(tempSwag));
        s.getInventory().merge(loot);
      }
      catch (IllegalInventoryException e) {
        System.err.println("Warning: " + s.name
            + "'s loot was not initialized proplery.");
      }
			state.addCharacter(s);

			MushroomMan mm = new MushroomMan(rand.nextInt(5) + 5);
			mm.setMap(m.getName());
			mm.anchorSpawnLoc(new Point(18, 31));
			moolah = rand.nextInt(500)+1;
			loot = new Loot(moolah);
			tempSwag = swags.get(rand.nextInt(swags.size()));
      try {
        loot.add(SwagFactory.getNewInstance(tempSwag));
        mm.getInventory().merge(loot);
      }
      catch (IllegalInventoryException e) {
        System.err.println("Warning: " + mm.name
            + "'s loot was not initialized proplery.");
      }
			state.addCharacter(mm);

			Orc o = new Orc(rand.nextInt(5) + 5);
			o.setMap(m.getName());
			o.anchorSpawnLoc(new Point(35, 5));
			moolah = rand.nextInt(500)+1;
			loot = new Loot(moolah);
			tempSwag = swags.get(rand.nextInt(swags.size()));
      try {
        loot.add(SwagFactory.getNewInstance(tempSwag));
        o.getInventory().merge(loot);
      }
      catch (IllegalInventoryException e) {
        System.err.println("Warning: " + o.name
            + "'s loot was not initialized proplery.");
      }
			state.addCharacter(o);
		}

		m = state.getGameMap("LeftMap.map");

		for (int i = 0; i < 5; i++) {
			FireDragon b = new FireDragon(rand.nextInt(5) + 7);
			b.setMap(m.getName());
			b.anchorSpawnLoc(new Point(20, 22));
			moolah = rand.nextInt(500)+1;
			loot = new Loot(moolah);
			tempSwag = swags.get(rand.nextInt(swags.size()));
      try {
        loot.add(SwagFactory.getNewInstance(tempSwag));
        b.getInventory().merge(loot);
      }
      catch (IllegalInventoryException e) {
        System.err.println("Warning: " + b.name
            + "'s loot was not initialized proplery.");
      }
			state.addCharacter(b);

			Spider s = new Spider(rand.nextInt(5) + 7);
			s.setMap(m.getName());
			s.anchorSpawnLoc(new Point(20, 12));
			moolah = rand.nextInt(500)+1;
			loot = new Loot(moolah);
			tempSwag = swags.get(rand.nextInt(swags.size()));
      try {
        loot.add(SwagFactory.getNewInstance(tempSwag));
        s.getInventory().merge(loot);
      }
      catch (IllegalInventoryException e) {
        System.err.println("Warning: " + s.name
            + "'s loot was not initialized proplery.");
      }
			state.addCharacter(s);

			Troll mm = new Troll(rand.nextInt(5) + 5);
			mm.setMap(m.getName());
			mm.anchorSpawnLoc(new Point(28, 4));
			moolah = rand.nextInt(500)+1;
			loot = new Loot(moolah);
			tempSwag = swags.get(rand.nextInt(swags.size()));
      try {
        loot.add(SwagFactory.getNewInstance(tempSwag));
        mm.getInventory().merge(loot);
      }
      catch (IllegalInventoryException e) {
        System.err.println("Warning: " + mm.name
            + "'s loot was not initialized proplery.");
      }
			state.addCharacter(mm);

			CrocWarrior o = new CrocWarrior(rand.nextInt(5) + 7);
			o.setMap(m.getName());
			o.anchorSpawnLoc(new Point(4, 9));
			moolah = rand.nextInt(500)+1;
			loot = new Loot(moolah);
			tempSwag = swags.get(rand.nextInt(swags.size()));
      try {
        loot.add(SwagFactory.getNewInstance(tempSwag));
        o.getInventory().merge(loot);
      }
      catch (IllegalInventoryException e) {
        System.err.println("Warning: " + o.name
            + "'s loot was not initialized proplery.");
      }
			state.addCharacter(o);

		}

		m = state.getGameMap("TopMap.map");

		for (int i = 0; i < 5; i++) {
			Bear b = new Bear(rand.nextInt(5) + 3);
			b.setMap(m.getName());
			b.anchorSpawnLoc(new Point(35, 35));
			moolah = rand.nextInt(500)+1;
			loot = new Loot(moolah);
			tempSwag = swags.get(rand.nextInt(swags.size()));
      try {
        loot.add(SwagFactory.getNewInstance(tempSwag));
        b.getInventory().merge(loot);
      }
      catch (IllegalInventoryException e) {
        System.err.println("Warning: " + b.name
            + "'s loot was not initialized proplery.");
      }
			state.addCharacter(b);

			Spider s = new Spider(rand.nextInt(5) + 3);
			s.setMap(m.getName());
			s.anchorSpawnLoc(new Point(27, 19));
			moolah = rand.nextInt(500)+1;
			loot = new Loot(moolah);
			tempSwag = swags.get(rand.nextInt(swags.size()));
      try {
        loot.add(SwagFactory.getNewInstance(tempSwag));
        s.getInventory().merge(loot);
      }
      catch (IllegalInventoryException e) {
        System.err.println("Warning: " + s.name
            + "'s loot was not initialized proplery.");
      }
			state.addCharacter(s);

			MushroomMan mm = new MushroomMan(rand.nextInt(5) + 3);
			mm.setMap(m.getName());
			mm.anchorSpawnLoc(new Point(4, 9));
			moolah = rand.nextInt(500)+1;
			loot = new Loot(moolah);
			tempSwag = swags.get(rand.nextInt(swags.size()));
      try {
        loot.add(SwagFactory.getNewInstance(tempSwag));
        s.getInventory().merge(loot);
      }
      catch (IllegalInventoryException e) {
        System.err.println("Warning: " + s.name
            + "'s loot was not initialized proplery.");
      }
			state.addCharacter(mm);

			CrocWarrior o = new CrocWarrior(rand.nextInt(5) + 3);
			o.setMap(m.getName());
			o.anchorSpawnLoc(new Point(28, 5));
			moolah = rand.nextInt(500)+1;
			loot = new Loot(moolah);
			tempSwag = swags.get(rand.nextInt(swags.size()));
      try {
        loot.add(SwagFactory.getNewInstance(tempSwag));
        o.getInventory().merge(loot);
      }
      catch (IllegalInventoryException e) {
        System.err.println("Warning: " + o.name
            + "'s loot was not initialized proplery.");
      }
			state.addCharacter(o);

		}

		m = state.getGameMap("RightMap.map");

		for (int i = 0; i < 5; i++) {
			FireDragon b = new FireDragon(rand.nextInt(5) + 10);
			b.setMap(m.getName());
			b.anchorSpawnLoc(new Point(36, 32));
			moolah = rand.nextInt(500)+1;
			loot = new Loot(moolah);
			tempSwag = swags.get(rand.nextInt(swags.size()));
      try {
        loot.add(SwagFactory.getNewInstance(tempSwag));
        b.getInventory().merge(loot);
      }
      catch (IllegalInventoryException e) {
        System.err.println("Warning: " + b.name
            + "'s loot was not initialized proplery.");
      }
			state.addCharacter(b);

			CrocWarrior s = new CrocWarrior(rand.nextInt(5) + 10);
			s.setMap(m.getName());
			s.anchorSpawnLoc(new Point(4, 3));
			moolah = rand.nextInt(500)+1;
			loot = new Loot(moolah);
			tempSwag = swags.get(rand.nextInt(swags.size()));
      try {
        loot.add(SwagFactory.getNewInstance(tempSwag));
        s.getInventory().merge(loot);
      }
      catch (IllegalInventoryException e) {
        System.err.println("Warning: " + s.name
            + "'s loot was not initialized proplery.");
      }
			state.addCharacter(s);

			Troll mm = new Troll(rand.nextInt(5) + 10);
			mm.setMap(m.getName());
			mm.anchorSpawnLoc(new Point(31, 14));
			moolah = rand.nextInt(500)+1;
			loot = new Loot(moolah);
			tempSwag = swags.get(rand.nextInt(swags.size()));
      try {
        loot.add(SwagFactory.getNewInstance(tempSwag));
        mm.getInventory().merge(loot);
      }
      catch (IllegalInventoryException e) {
        System.err.println("Warning: " + mm.name
            + "'s loot was not initialized proplery.");
      }
			state.addCharacter(mm);

			Orc o = new Orc(rand.nextInt(5) + 10);
			o.setMap(m.getName());
			o.anchorSpawnLoc(new Point(15, 23));
			moolah = rand.nextInt(500)+1;
			loot = new Loot(moolah);
			tempSwag = swags.get(rand.nextInt(swags.size()));
      try {
        loot.add(SwagFactory.getNewInstance(tempSwag));
        o.getInventory().merge(loot);
      }
      catch (IllegalInventoryException e) {
        System.err.println("Warning: " + o.name
            + "'s loot was not initialized proplery.");
      }
			state.addCharacter(o);

		}

		Set<Creature> allMonsters = state.getCreatures();

		for (Creature c : allMonsters) {
			if (c instanceof Monster) {
				Monster monster = (Monster) c;
				m = state.getGameMap(c.getMap());
				
				int x = monster.getSpawnloc().x;
				int y = monster.getSpawnloc().y;
				Tile start = m.getTile(x, y);
				System.out.println(monster + " " + m.getName() + " " + x + " "
						+ y);
				BFSIterator iter = m.getBFSIterator(start, 32);

				int sparsity = 0;
				while (iter.hasNext()) {
					Tile placement = iter.next().getNode();
					if (sparsity % 32 == 0) {
						if (placement.getOccupant() == null) {
							try {
								placement.setOccupant(monster);
								break;
							} catch (TileException e) {
								e.printStackTrace();
							}
						}
					}
					sparsity++;
				}

			}
		}
	}
}
