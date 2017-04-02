package com.ai.test;

import static org.junit.Assert.*;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

import com.ai.NanoParser;
import com.ai.VM;
import com.ai.ASTCells.ASTCell;
import com.ai.ASTCells.NumberASTCell;
import com.ai.reference.ExecutionException;
import com.ai.reference.TypeException;
import com.game.characters.Creature;
import com.game.characters.Fighter;
import com.game.characters.GameCharacter;
import com.game.characters.Mage;
import com.game.characters.Monster;
import com.game.characters.NPC;
import com.game.characters.Player;
import com.game.characters.Creature.Stat;
import com.game.characters.GameCharacter.Gender;
import com.game.characters.Thief;
import com.game.characters.content.monsters.Bear;
import com.game.characters.content.monsters.CrocWarrior;
import com.game.characters.content.monsters.FireDragon;
import com.game.characters.content.monsters.MushroomMan;
import com.game.characters.content.monsters.ProfessorLane;
import com.game.characters.content.monsters.Spider;
import com.game.characters.content.npcs.Gerald;
import com.game.characters.content.npcs.Harold;
import com.game.characters.content.npcs.Helga;
import com.game.characters.content.npcs.Olga;
import com.game.characters.spells.AbstractSpell;
import com.game.characters.spells.FlameThrower;
import com.game.characters.spells.Zot;
import com.game.environment.GameMap;
import com.game.items.weapon.Weapon;
import com.server.GameScheduler;
import com.server.State;
import com.server.command.ComparableCommand;

/**
 * 
 * @author Martin Tice
 *
 */
public class TestVM {
	/** a {@link State} object for testing */
	private State _gs;
	/** a {@link VM} object for testing*/
	private VM _c;
	/** a {@link NanoParser} object for testing*/
	private NanoParser _parser;
	/** The characters inside {@link #_gs}*/
	private Collection<GameCharacter> _characters;
	//different characters to test
	/** a Male {@link Thief} character */
	private GameCharacter _thiefMale1;
	/** an "it" gender {@link Fighter} character */
	private GameCharacter _fighterIt1;
	/** a Male {@link Mage} character */
	private GameCharacter _mageMale1;
	/** a Female {@link Thief} character */
	private GameCharacter _thiefFemale1;
	/** a {@link Spider} Monster */
	private GameCharacter _spider1;
	/** a {@link Spider} Monster */
	private GameCharacter _spider2;
	/** a {@link Bear} Monster */
	private GameCharacter _bear1;
	/** a {@link Bear} Monster */
	private GameCharacter _bear2;
	/**an {@link NPC} Harold instance */
	private NPC _npcHarold = Harold.getHarold();
	/**an {@link NPC} Gerald instance */
	private NPC _npcGerald = Gerald.getGerald();
	/**an {@link NPC} Helga instance */
	private NPC _npcHelga = Helga.getHelga();
	/**an {@link NPC} Olga instance */
	private NPC _npcOlga = Olga.getOlga();
	/**a {@link GameMap} object*/
	private GameMap _map = new GameMap("testMap.map", 50, 50);
	//private ArrayList<Point> _playerLocations = new ArrayList<Point>();
	/**used to check if a strategy was executed in {@link #monsterMainLoop(State)} */
	private Boolean _executed;
	/**Used to hold a parsed strategy in the form of an {@link SequenceASTCell}*/
	private ASTCell _result;
	

	/**
	 * Initializes {@link #_gs} and {@link #_c}.
	 * 
	 * @throws Exception if there is an error in initialization.
	 */
	@Before
	public void setUp() throws Exception {
		_gs = new State(3, 0);
		_c = new VM();
		_thiefMale1 = new Thief("Martin",Gender.MALE);
		_thiefMale1.setPosition(new Point(1,1));
		_thiefMale1.setMap(_map.getName());
		
		_fighterIt1 = new Fighter("Pat",Gender.IT);
		_fighterIt1.setPosition(new Point(8,8));
		_fighterIt1.setMap(_map.getName());
		
		_mageMale1 = new Mage("AmazingLarry",Gender.MALE);
		_mageMale1.setPosition(new Point(3,3));
		_mageMale1.setMap(_map.getName());
		
		_thiefFemale1 = new Thief("BigBertha",Gender.FEMALE);
		_thiefFemale1.setPosition(new Point(5,5));
		_thiefFemale1.setMap(_map.getName());
		
		_spider1 = new Spider(1);
		_spider1.setPosition(new Point(10,10));
		_spider1.setMap(_map.getName());
		
		_spider2 = new Spider(2);
		_spider2.setPosition(new Point(20,20));
		_spider2.setMap(_map.getName());
		
		_bear1 = new Bear(1);
		_bear1.setPosition(new Point(50,50));
		_bear1.setMap(_map.getName());
		
		_bear2 = new Bear(2);
		_bear2.setPosition(new Point(70,70));
		_bear2.setMap(_map.getName());
		
		_npcHarold.setMap(_map.getName());
		_npcGerald.setMap(_map.getName());
		_npcHelga.setMap(_map.getName());
		_npcOlga.setMap(_map.getName());
		
		
	}

	
	/**
	 * Tests a {@link State} with one Monster and one Character that are out of range.
	 */
	@Test
	public void outOfRange() {
	_gs.addGameMap(_map);
	_gs.addCharacter(_thiefMale1);
	_gs.addCharacter(_spider1);
	_executed = true;
	monsterMainLoop(_gs);
	assertEquals(_executed, false);
	}
	
	/**
	 * Tests a situation with one player in range and one player out of range for correctness of {@link VM#inRange()}
	 */
	@Test
	public void oneInRange(){
		_gs.addGameMap(_map);
		_gs.addCharacter(_thiefMale1);
		_gs.addCharacter(_fighterIt1);
		_gs.addCharacter(_spider1);
		
		_executed = false;
		try {
			_parser = new NanoParser("data/NanoScripts/testScripts/VMtestScript.txt");
			_parser.parse();
			_result = _parser.getResult();
		} catch (IOException e1) {
			System.out.println("VM failed to parse script");
			e1.printStackTrace();
		}
		monsterMainLoop(_gs);
		try {
			assertEquals(_executed, true);
			System.out.println("b "+ _c.getCell("b").numVal());
			System.out.println("c "+ _c.getCell("c").numVal());
			System.out.println("d "+ _c.getCell("d").numVal());
			System.out.println("e "+ _c.getCell("e").numVal());
			System.out.println("f "+ _c.getCell("f").numVal());
			assertEquals(_c.getCell("a").numVal(), new Double(15.0));
			assertEquals(_c.getCell("b").numVal(), new Double(5.0));
			assertEquals(_c.getCell("c").numVal(), new Double(20.0));
			assertEquals(_c.getCell("d").numVal(), new Double(0.0));
			assertEquals(_c.getCell("e").numVal(), new Double(15.0));
			assertEquals(_c.getCell("f").numVal(), new Double(10.0));
			
		} catch (TypeException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Tests correct calculation of distances. Executes all monsters strategies, regardless of 
	 * player range. After each execution, the {@link VM#clearData()} is NOT called..
	 * After all executions, {@link VM#getDistances()} is called and checked. 
	 */
	@Test
	public void testDistances(){
		_gs.addGameMap(_map);
		//pc's
		_gs.addCharacter(_thiefMale1);
		_gs.addCharacter(_fighterIt1);
		_gs.addCharacter(_mageMale1);
		//monsters
		_gs.addCharacter(_bear1);
		_gs.addCharacter(_bear2);
		_gs.addCharacter(_spider1);
		_gs.addCharacter(_spider2);
		_executed = false;
		Set<Double> solution = new TreeSet<Double>();
		solution.add(4.0);
		solution.add(14.0);
		solution.add(18.0);
		solution.add(24.0);
		solution.add(34.0);
		solution.add(38.0);
		solution.add(84.0);
		solution.add(94.0);
		solution.add(98.0);
		solution.add(124.0);
		solution.add(134.0);
		solution.add(138.0);
		
		try {
			_parser = new NanoParser("data/NanoScripts/testScripts/VMtestScript.txt");
			_parser.parse();
			_result = _parser.getResult();
		} catch (IOException e1) {
			System.out.println("VM failed to parse script");
			e1.printStackTrace();
		}
		_characters = _gs.getCharacters();
		_c.loadPCs(_gs, _map.getName());
		for(GameCharacter monster : _characters){
			if(!(monster instanceof Player)){
					_c.loadSelf((Monster) monster);
		    }
	    }
		
		TreeMap<Double, Player> t = _c.getDistances();
		System.out.println("Distances :::::"+t.keySet().toString());
		assertEquals(t.keySet(), solution);
		
	}
	
	/**
	 * Tests the setNearAndFar method as well as the setRandom method in {@link VM}
	 * Stores the name of nearest, farthest, random player each time a monster executes
	 * a strategy in a {@link ArrayList}, and afterwards, compares it with another {@link ArrayList}.
	 */
	@Test
	public void testNearAndFar(){
		_gs.addGameMap(_map);
		//pc's
		_gs.addCharacter(_thiefMale1);
		_gs.addCharacter(_fighterIt1);
		_gs.addCharacter(_mageMale1);
		//monsters
		_gs.addCharacter(_bear1);
		_gs.addCharacter(_bear2);
		_gs.addCharacter(_spider1);
		_gs.addCharacter(_spider2);
		
		_executed = false;
		ArrayList<String> solution = new ArrayList<String>();
		solution.add("Pat");
		solution.add("Martin");
		solution.add("Pat");
		solution.add("Martin");
		solution.add("Pat");
		solution.add("Martin");
		solution.add("Pat");
		solution.add("Martin");
		
		ArrayList<String> actual = new ArrayList<String>();
		
		try {
			_parser = new NanoParser("data/NanoScripts/testScripts/VMtestScript.txt");
			_parser.parse();
			_result = _parser.getResult();
		} catch (IOException e1) {
			System.out.println("VM failed to parse script");
			e1.printStackTrace();
		}
		_characters = _gs.getCharacters();
		_c.loadPCs(_gs, _map.getName());
		for(GameCharacter monster : _characters){
			if(!(monster instanceof Player)){
					_c.loadSelf((Monster) monster);
					try {
						actual.add(_c.getExternal("nearest.name").stringVal());
						actual.add(_c.getExternal("furthest.name").stringVal());
					} catch (TypeException e) {
						
					}
				    
		    }
	    }
		System.out.println(" actual : "+ actual.toString());
		assertEquals(solution, actual);
	}
	
	@Test
	public void testNPCHarold(){
		_gs.addGameMap(_map);
		//pc's
		_gs.addCharacter(_thiefMale1); //1,1
		_gs.addCharacter(_fighterIt1);//8,8
		_gs.addCharacter(_mageMale1);//3,3
		_npcHarold.setPosition(new Point(5,5));
		_gs.addCharacter(_npcHarold);
		
		
		try {
			_parser = new NanoParser("data/NanoScripts/gameScripts/Harold.nano");
			_parser.parse();
			_result = _parser.getResult();
			
			System.out.println(_c.getCell(""));
		} catch (IOException e1) {
			System.out.println("VM failed to parse script");
			e1.printStackTrace();
		}
		monsterMainLoop(_gs);
		_c.getCommand();
	}
	
	/**
	 * tests the script for a fireDragon
	 */
	@Test
	public void fireDragonTest(){
		_gs.addGameMap(_map);
		//characters
		_gs.addCharacter(_thiefMale1); //1,1
		_gs.addCharacter(_fighterIt1);//8,8
		_gs.addCharacter(_mageMale1);//3,3
		//adjacent above level 5 dragon
	  FireDragon _fire10 = new FireDragon(10);
	  _fire10.setPosition(new Point(8,9));
	  _fire10.setMap(_map.getName());
	  _fire10.getMap();
	  _gs.addCharacter(_fire10);
	  //adjacent level 3 dragon
	  FireDragon _fire3 = new FireDragon(3);
	  _fire3.setPosition(new Point(1,2));
	  _fire3.setMap(_map.getName());
	  _fire3.getMap();
	  _gs.addCharacter(_fire3);
	 
	// gets spell lists from monsters
    List<AbstractSpell> spellList10 = _fire10.getSpellInventory().getSpellSet();
    List<AbstractSpell> spellList03 = _fire10.getSpellInventory().getSpellSet();

	  parseScript("data/NanoScripts/gameScripts/FireDragon.nano");
	  _c.loadPCs(_gs, _map.getName());
	  _c.loadSelf(_fire10);
	  executeScript();
	  _c.getCommand();
	  
	  try {
		  double index = _c.getCell("castChoice").numVal();
		  String com = _c.getCell("command").stringVal().replaceAll("\"", "");
		  
	    assertEquals(com, "cast");
	    assertEquals( _c.getCell("castChoice").numVal(), new Double(0.0));
	    assertTrue(spellList10.get((int)index) instanceof Zot);
	    System.out.println(spellList10.get((int)index));
	} catch (TypeException e) {
		e.printStackTrace();
	}
			
	
}
	/**
	 * tests the script for a CrocWarrior
	 */
	@Test
	public void crocWarriorTest(){
		_gs.addGameMap(_map);
		//characters
		while(((Creature) _mageMale1).getCurrStat(Stat.LEVEL) < 10){
		((Creature) _mageMale1).levelUp();
		}
		_gs.addCharacter(_thiefMale1); //1,1
		_gs.addCharacter(_fighterIt1);//8,8
		_gs.addCharacter(_mageMale1);//3,3
		//adjacent above level 5 crocWarrior high health
	  CrocWarrior _crocHigh = new CrocWarrior(8);
	  _crocHigh.setPosition(new Point(8,9));
	  _crocHigh.setMap(_map.getName());
	  _crocHigh.getMap();
	  _gs.addCharacter(_crocHigh);
	  //adjacent level 3 crocWarrior low health
	  CrocWarrior _crocLow = new CrocWarrior(10);
	  _crocLow.setPosition(new Point(3,4));
	  _crocLow.setMap(_map.getName());
	  _crocLow.getMap();
	  _crocLow.changeStat(Stat.HEALTHPOINTS, -15);
	  _gs.addCharacter(_crocLow);
	 
	// gets spell lists from monsters
    List<AbstractSpell> spellListHigh = _crocHigh.getSpellInventory().getSpellSet();
    List<AbstractSpell> spellListLow = _crocLow.getSpellInventory().getSpellSet();

	  parseScript("data/NanoScripts/gameScripts/CrocWarrior.nano");
	  _c.loadPCs(_gs, _map.getName());
	  _c.loadSelf(_crocHigh);
	  executeScript();
	  _c.getCommand();
	  
	  try {
		  String com = _c.getCell("command").stringVal().replaceAll("\"", "");
		  assertEquals(com, "attack");
	} catch (TypeException e) {
		e.printStackTrace();
	}
	  _c.clearData();
// low health croc high level PC
	  parseScript("data/NanoScripts/gameScripts/CrocWarrior.nano");
	  _c.loadPCs(_gs,_map.getName());
	  _c.loadSelf(_crocLow);
	  _c.setExternal("self.mp", new NumberASTCell(10));
	  _c.setExternal("self.hp", new NumberASTCell(4));
	  executeScript();
	  
	  try {
		  System.out.println("self.level : "+ _c.getExternal("self.level").numVal());
		  System.out.println("nearest.level : "+ _c.getExternal("nearest.level").numVal());
		  System.out.println("self.mp : "+ _c.getExternal("self.mp").numVal());
		  System.out.println("self.hp : "+ _c.getExternal("nearest.hp").numVal());
		  System.out.println("self.fullHp : "+ _c.getExternal("self.fullHp").numVal());
		  System.out.println("lowHealthSelf : "+ _c.getCell("lowHealthSelf").numVal());
		  double index = _c.getCell("castChoice").numVal();
		  String com = _c.getCell("command").stringVal().replaceAll("\"", "");
		  AbstractSpell spell = spellListHigh.get((int)index);
		  System.out.println(" crocHigh castChoice: "+ com+" == "+ spell);
	} catch (TypeException e) {
		e.printStackTrace();
	}
			
	
}
	
	/**
	 * tests the script for a mushroomMan
	 */
	@Test
	public void mushroomManTest(){
		_gs.addGameMap(_map);
		//characters
		while(((Creature) _mageMale1).getCurrStat(Stat.LEVEL) < 10){
		((Creature) _mageMale1).levelUp();
		}
		_gs.addCharacter(_thiefMale1); //1,1
		_gs.addCharacter(_fighterIt1);//8,8
		_gs.addCharacter(_mageMale1);//3,3
		//adjacent above level 5 crocWarrior high health
	  MushroomMan _mushHigh = new MushroomMan(8);
	  _mushHigh.setPosition(new Point(8,9));
	  _mushHigh.setMap(_map.getName());
	  _mushHigh.getMap();
	  _gs.addCharacter(_mushHigh);
	  //adjacent level 3 crocWarrior low health
	  MushroomMan _mushLow = new MushroomMan(8);
	  _mushLow.setPosition(new Point(3,4));
	  _mushLow.setMap(_map.getName());
	  _mushLow.getMap();
	  _mushLow.changeStat(Stat.HEALTHPOINTS, -15);
	  _gs.addCharacter(_mushLow);
	 
	// gets spell lists from monsters
    List<AbstractSpell> spellListHigh = _mushHigh.getSpellInventory().getSpellSet();
    List<AbstractSpell> spellListLow = _mushLow.getSpellInventory().getSpellSet();

	  parseScript("data/NanoScripts/gameScripts/CrocWarrior.nano");
	  _c.loadPCs(_gs, _map.getName());
	  _c.loadSelf(_mushHigh);
	  executeScript();
	  _c.getCommand();
//High Health mushroom man	  
	  try {
		  String com = _c.getCell("command").stringVal().replaceAll("\"", "");
		  System.out.println("mushHigh command is "+ com);
		 // assertEquals(com, "attack");
	} catch (TypeException e) {
		e.printStackTrace();
	}
	  _c.clearData();
// low health MushroomMan high level PC
	  parseScript("data/NanoScripts/gameScripts/CrocWarrior.nano");
	  _c.loadPCs(_gs,_map.getName());
	  _c.loadSelf(_mushLow);
	  _c.setExternal("self.mp", new NumberASTCell(10));
	  _c.setExternal("self.hp", new NumberASTCell(4));
	  executeScript();
	  
	  try {
		  System.out.println("self.level : "+ _c.getExternal("self.level").numVal());
		  System.out.println("nearest.level : "+ _c.getExternal("nearest.level").numVal());
		  System.out.println("self.mp : "+ _c.getExternal("self.mp").numVal());
		  System.out.println("self.hp : "+ _c.getExternal("nearest.hp").numVal());
		  System.out.println("self.fullHp : "+ _c.getExternal("self.fullHp").numVal());
		  System.out.println("lowHealthSelf : "+ _c.getCell("lowHealthSelf").numVal());
		  //double index = _c.getCell("castChoice").numVal();
		  String com = _c.getCell("command").stringVal().replaceAll("\"", "");
		  System.out.println("mushLow command is "+ com);
		  //AbstractSpell spell = spellListHigh.get((int)index);
		  //System.out.println(" mushLow castChoice: "+ com+" == "+ spell);
	} catch (TypeException e) {
		e.printStackTrace();
	}
			
	
}
	/**
	 * tests the script for a Lane
	 */
	@Test
	public void LaneTest(){
		_gs.addGameMap(_map);
		//characters
		_gs.addCharacter(_thiefMale1); //1,1
		_gs.addCharacter(_fighterIt1);//8,8
		_gs.addCharacter(_mageMale1);//3,3
		//adjacent above level 5 dragon
	  ProfessorLane _Lane = ProfessorLane.getProfLane();
	  _Lane.setPosition(new Point(8,9));
	  _Lane.setMap(_map.getName());
	  _Lane.getMap();
	  _gs.addCharacter(_Lane);
	 
	// gets spell lists from monsters
    List<AbstractSpell> spellList10 = _Lane.getSpellInventory().getSpellSet();
    List<AbstractSpell> spellList03 = _Lane.getSpellInventory().getSpellSet();

	  parseScript("data/NanoScripts/gameScripts/ProfessorLane.nano");
	  _c.loadPCs(_gs, _map.getName());
	  _c.loadSelf(_Lane);
	  executeScript();
	  _c.getCommand();
	  
	  try {
		  double index = _c.getCell("castChoice").numVal();
		  String com = _c.getCell("command").stringVal().replaceAll("\"", "");
		  
	    assertEquals(com, "cast");
	    assertEquals( _c.getCell("castChoice").numVal(), new Double(0.0));
	   // assertTrue(spellList10.get((int)index) instanceof Zot);
	    System.out.println("Prof lane chooses to defeat you by means of : "+ spellList10.get((int)index));
	} catch (TypeException e) {
		e.printStackTrace();
	}
			
	
}
	
//HELPER METHODS=========================
	
	/**
	 * used to parse a script and store the result in {@link #_result}
	 * @param scriptPath is the path of the script to be parsed.
	 */
	private void parseScript(String scriptPath){
		try {
			_parser = new NanoParser(scriptPath);
			_parser.parse();
			_result = _parser.getResult();
			
		} catch (IOException e1) {
			System.out.println("VM failed to parse script");
			e1.printStackTrace();
		}
	}
	/**
	 * used to execute a parsed script and update values in {@link #_c};
	 */
	private void executeScript(){
		try {
			_result.execute(_c);
		} catch (ExecutionException e) {
			System.out.println("failed to execute strategy");
			e.printStackTrace();
		}
	}
	/**
	 * Similar to the getMonsterMoves method in {@link GameScheduler} except it doesn't need to add
	 * a command to the queue, and it gets a testScript instead of a real script
	 * @param gs is the current gameState
	 */
	private void monsterMainLoop(State gs){
		_characters = gs.getCharacters();
		_c.loadPCs(_gs, _map.getName());
		for(GameCharacter monster : _characters){
			if(!(monster instanceof Player)){
					_c.loadSelf(monster);
					if(_c.inRange()){
						_executed = true;
						System.out.println("MOnsterMainLoop found monsters in range");
						try {
							_result.execute(_c);
						} catch (ExecutionException e) {
							System.out.println("monsterMainLoop failed to execute strategy");
							e.printStackTrace();
						}
						//ComparableCommand com = _c.getCommand();
					}else{_executed = false;}
					//_gs.executeStrategy(monster.makeDecision());
					_c.loadPCs(_gs, _map.getName());
				
		    }
	    }
    }
	
}
