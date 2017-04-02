package com.ai.test;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

import com.ai.AST;
import com.ai.VM;
import com.ai.ASTCells.ASTCell;
import com.ai.reference.TypeException;
import com.game.characters.Fighter;
import com.game.characters.GameCharacter;
import com.game.characters.Mage;
import com.game.characters.Monster;
import com.game.characters.Player;
import com.game.characters.Thief;
import com.game.characters.GameCharacter.Gender;
import com.game.characters.content.monsters.Bear;
import com.game.characters.content.monsters.Spider;
import com.game.environment.GameMap;
import com.server.State;

public class TestAST {

	/**{@link AST} object for all tests*/
	private AST _ast;
	/**{@link VM} to retrieve results from*/
	private VM _c;
	/**a {@link State} object to hold test information*/
	private State _gs = new State(3, 0);
	/**game map to add to state*/
	private GameMap _map = new GameMap("TestMap", 50, 50);
	/**all the characters to add to the game state for testing*/
	private Collection<GameCharacter> _characters;
	private GameCharacter _thiefMale1;
	private GameCharacter _fighterIt1;
	private GameCharacter _mageMale1;
	private GameCharacter _thiefFemale1;
	private GameCharacter _spider1;
	private GameCharacter _spider2;
	private GameCharacter _bear1;
	private GameCharacter _bear2;
	
	@Before
	public void setUp() throws Exception {
		_ast = new AST("data/NanoScripts/gameScripts");
		_c = new VM();
		_gs.addGameMap(_map);
		_thiefMale1 = new Thief("Martin",Gender.MALE);
		_thiefMale1.setPosition(new Point(1,1));
		
		_fighterIt1 = new Fighter("Pat",Gender.IT);
		_fighterIt1.setPosition(new Point(2,1));
		
		_mageMale1 = new Mage("AmazingLarry",Gender.MALE);
		_mageMale1.setPosition(new Point(3,3));
		
		_thiefFemale1 = new Thief("BigBertha",Gender.FEMALE);
		_thiefFemale1.setPosition(new Point(5,5));
		
		_spider1 = new Spider(1);
		_spider1.setPosition(new Point(10,10));
		
		_spider2 = new Spider(2);
		_spider2.setPosition(new Point(20,20));
		
		_bear1 = new Bear(1);
		_bear1.setPosition(new Point(50,50));
		
		_bear2 = new Bear(2);
		_bear2.setPosition(new Point(70,70));
		
	}

	/**
	 * Tests {@link AST} constructor for correct initialization
	 */
	@Test
	public void TestParseCreation() {
	 
	Set<String> solution = new TreeSet<String>();
	solution.add("data\\NanoScripts\\gameScripts\\GnomeWarrior.nano");
	solution.add("data\\NanoScripts\\gameScript\\FireDragon.nano");
	solution.add("data\\NanoScripts\\gameScripts\\GnomeWarrior.nano");
	solution.add("data\\NanoScripts\\gameScript\\CrocWarrior.nano");
	solution.add("data\\NanoScripts\\gameScripts\\Spider.nano");
	solution.add("data\\NanoScripts\\gameScripts\\Bear.nano");
	solution.add("data\\NanoScripts\\gameScripts\\ProfessorCastellanos.nano");
	solution.add("data\\NanoScripts\\gameScripts\\Orc.nano");
	solution.add("data\\NanoScripts\\gameScripts\\MushroomMan.nano");
	solution.add("data\\NanoScripts\\gameScripts\\Troll.nano");
      Set<String> actual = _ast.getStrategies().keySet();
      System.out.println(actual.toString());
      for(String s : solution){
    	  System.out.println(s);
    	  assertEquals(true, actual.contains(s));
      }
	}

}
