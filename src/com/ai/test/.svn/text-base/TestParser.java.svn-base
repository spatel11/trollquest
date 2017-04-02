package com.ai.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.ai.NanoLexer;
import com.ai.NanoParser;
import com.ai.NanoTokens;
import com.ai.VM;
import com.ai.ASTCells.ASTCell;
import com.ai.ASTCells.BooleanASTCell;
import com.ai.ASTCells.NumberASTCell;
import com.ai.ASTCells.StringASTCell;
import com.ai.reference.ExecutionException;
import com.ai.reference.ParseException;
import com.ai.reference.TypeException;
import com.game.characters.Fighter;
import com.game.characters.GameCharacter;
import com.game.characters.Mage;
import com.game.characters.Thief;
import com.game.characters.GameCharacter.Gender;

/**
 * <p>Each test runs its own script inside {data/NanoScripts/testScripts/*.txt}</p>
 * <p>Most of the tests are designed to perform actions to a set of variables and then test the outcome</p>
 * <p>The general process is:</p>
 * <p> parse the file..</p>
 * <p> execute the result</p>
 * <p> test all variables created by execution vs. expected results.</p>
 * 
 * 
 * <p><b>AS OF M2, MOST TESTS ARE WAITING FOR THE EXECUTE() METHODS TO BE COMPLETED</b></p>
 * @author Martin Tice
 *
 */
public class TestParser {
	
   // private NanoLexer _lexer;
   private NanoParser _parser;
   private VM _c;
   // private File _tokenList = new File("data/NanoScripts/fullTokenSet.txt");
   
	@Before
	public void setUp() throws Exception {
		//_parser = new NanoParser("data/NanoScripts/fullTokenSet.txt");
		_c = new VM();
		 	
	}
	
	/**
	 * <p>Tests boolean Assignments to "internally" created variables.</p> 
	 * <p>These are variables of type boolean created during parsing of a nanoC script.</p>
	 * <p>Tested by creating variables:
	 * <p>First by straight assignment:</p>
	 * <p>       a = true;b = false;</p>
	 * <p>Then by previously stored</p>
	 * <p>       a = b;...</p>
	 * <p>Then by various comparisons operators and combinations of the above two:</p>
	 * <p> a = true && true; c = a || b; ...</p>
	 * <p></p>
	 * <p>Script for this test is "data/NanoScrips/testScripts/boolAss.txt"</p>
	 */
	@Test
	public void testBooleanAssignment(){
		ASTCell _ifResult;
		ASTCell _ifRex = null;
		try {
			_parser = new NanoParser("data/NanoScripts/testScripts/parserBoolAss.txt");
			_parser.parse();
			_ifResult = _parser.getResult();
			try {
				_ifRex = _ifResult.execute(_c);
				System.out.println("result: "+_ifRex.print());
			} catch (ExecutionException e1) {
				System.out.println("execution exception");
			}
			try {
				assertEquals(_c.getCell("a").boolVal(), true);
				assertEquals(_c.getCell("b").boolVal(), true);
				assertEquals(_c.getCell("c").boolVal(), true);
				assertEquals(_c.getCell("d").boolVal(), false);
				assertEquals(_c.getCell("e").boolVal(), false);
				assertEquals(_c.getCell("f").boolVal(), false);
				assertEquals(_c.getCell("g").boolVal(), true);
				assertEquals(_c.getCell("h").boolVal(), true);
				assertEquals(_c.getCell("i").boolVal(), true);
				assertEquals(_c.getCell("j").boolVal(), false);
				assertEquals(_c.getCell("k").boolVal(), true);
				assertEquals(_c.getCell("l").boolVal(), false);
				assertEquals(_c.getCell("m").boolVal(), true);
				assertEquals(_c.getCell("n").boolVal(), true);
				assertEquals(_c.getCell("o").boolVal(), false);
				assertEquals(_c.getCell("p").boolVal(), false);
				assertEquals(_c.getCell("q").boolVal(), true);
				assertEquals(_c.getCell("r").boolVal(), true);
				
			} catch (TypeException e) {
				System.out.println("not a bool value");
			}
			
		} catch (IOException e) {
			System.out.println("testBoolassignment didn't find file");
		}
	}
	/**
	 * <p>Tests equality of "internally" created variables.</p> 
	 * <p>These are variables of type number created during parsing of a nanoC script.</p>
	 * <p>Tested by creating variables:
	 * <p>First by straight assignment:</p>
	 * <p>       a = 5;b = 4;</p>
	 * <p>Then by previously stored</p>
	 * <p>       a == b; a != b;...</p>
	 * <p>Then by various combinations of the above two:</p>
	 * <p> a != b-3; c == a+b;...</p>
	 * <p></p>
	 * <p>Script for this test is "data/NanoScrips/testScripts/boolAss.txt"</p>
	 */
	@Test
	public void testInternalEquality(){
		ASTCell _ifResult;
		ASTCell _ifRex = null;
		try {
			_parser = new NanoParser("data/NanoScripts/testScripts/parserEqualityTests.txt");

			_parser.parse();
			_ifResult = _parser.getResult();
			try {
				_ifRex = _ifResult.execute(_c);
				System.out.println("result: "+_ifRex.print());
			} catch (ExecutionException e1) {
				System.out.println("execution exception");
			}
			try {
				assertEquals(_c.getCell("a").boolVal(), false);
				assertEquals(_c.getCell("b").boolVal(), true);
				assertEquals(_c.getCell("c").boolVal(), false);
				assertEquals(_c.getCell("d").boolVal(), true);
				assertEquals(_c.getCell("e").boolVal(), true);
				assertEquals(_c.getCell("f").boolVal(), true);
				assertEquals(_c.getCell("g").boolVal(), false);
				Double d = 4e5;
				assertEquals(_c.getCell("h").numVal(), d);
				assertEquals(_c.getCell("i").boolVal(), true);
				assertEquals(_c.getCell("j").boolVal(), false);
				Double k = 193.0;
				assertEquals(_c.getCell("k").numVal(), k);
				
			} catch (TypeException e) {
				System.out.println("not a bool value");
			}
			
		} catch (IOException e) {
			System.out.println("testBoolassignment didn't find file");
		}
	}
	/**
	 * Tests the parsing of the <code>CreatureDef</code>non-terminal
	 */
	@Test
	public void testCreatureDef(){
		ASTCell _ifResult;
		ASTCell _ifRex = null;
		try {
			_parser = new NanoParser("data/NanoScripts/testScripts/parserCreatureTest.txt");

			_parser.parse();
			_ifResult = _parser.getResult();
			try {
				_ifRex = _ifResult.execute(_c);
				System.out.println("result: "+_ifRex.print());
			} catch (ExecutionException e1) {
				System.out.println("execution exception");
			}
			try {
			    System.out.println("name : "+_c.getCell("BadAsh").stringVal());
			    System.out.println("s1 : "+_c.getCell("BadAshHp").numVal());
			    System.out.println("policy : "+_c.getCell("BadAshSpell").stringVal());
			    System.out.println("finalizer : "+_c.getCell("BadAshDie").stringVal());
			} catch (TypeException e) {
				System.out.println("not a bool value");
			}
			
		} catch (IOException e) {
			System.out.println("testBoolassignment didn't find file");
		}
	}
	/**
	 * series of if statements, increasingly more complicated, changing values of numbers from 1 to
	 *  3.. if test passes, all numbers should be 3.
	 *  
	 */
	@Test
	public void testifs(){
		ASTCell _ifResult;
		ASTCell _ifRex = null;
		try {
			_parser = new NanoParser("data/NanoScripts/testScripts/parserEmbeddedIfTest.txt");
			_parser.parse();
			_ifResult = _parser.getResult();
			try {
				_ifRex = _ifResult.execute(_c);
				System.out.println("result: "+_ifRex.print());
			} catch (ExecutionException e1) {
				System.out.println("execution exception");
			}
			try {
				// run through variables created and test for expected values.
				Double three = 3.0;
				assertEquals(_c.getCell("a").numVal(),three);
				assertEquals(_c.getCell("b").numVal(),three);
				assertEquals(_c.getCell("c").numVal(),three);
				assertEquals(_c.getCell("d").numVal(),three);
				assertEquals(_c.getCell("e").numVal(),three);
				assertEquals(_c.getCell("f").numVal(),three);
				
			} catch (TypeException e) {
				System.out.println("not a number value");
			}
			
		} catch (IOException e) {
			System.out.println("testIFStatements() didn't find file");
		}
           System.out.println("FINISHED IF TESTS================================FINISHEDIFconditionalTESTS");
	}
	
	/**
	 * Similar to {@link #testifs()}, Sets variables initially to 1. Runs a series of embedded while statements.
	 * 
	 * at end, all variables should equal 10.
	 */
	@Test
	public void testWhileScript(){
		ASTCell _ifResult;
		ASTCell _ifRex = null;
		try {
			_parser = new NanoParser("data/NanoScripts/testScripts/parserWhileTest.txt");
			_parser.parse();
			_ifResult = _parser.getResult();
			try {
				_ifRex = _ifResult.execute(_c);
				System.out.println("result: "+_ifRex.print());
			} catch (ExecutionException e1) {
				System.out.println("execution exception");
			}
			try {
				// run through variables created and test for expected values.
				Double ten = 10.0;
				assertEquals(_c.getCell("a").numVal(), ten);
				assertEquals(_c.getCell("b").numVal(), ten);
				assertEquals(_c.getCell("c").numVal(), ten);
				assertEquals(_c.getCell("d").numVal(), ten);
				assertEquals(_c.getCell("e").numVal(), ten);
				assertEquals(_c.getCell("f").numVal(), ten);
				
				
			} catch (TypeException e) {
				System.out.println("not a number value");
			}
			
		} catch (IOException e) {
			System.out.println("testIFStatements() didn't find file");
		}
           System.out.println("FINISHED IF TESTS================================FINISHEDIFconditionalTESTS");
	}
    /**
     * Tests the ifWhileTestScripts.txt for correct evaluation and parsing of if and while statements.
     * 
     */
	@Test
	public void testIfWhileTestScript(){
		ASTCell _ifResult;
		ASTCell _ifRex = null;
		//TODO re-write ifTests.txt
		try {
			_parser = new NanoParser("data/NanoScripts/testScripts/parserIfWhileTest.txt");
			_parser.parse();
			_ifResult = _parser.getResult();
			try {
				_ifRex = _ifResult.execute(_c);
				System.out.println("result: "+_ifRex.print());
			} catch (ExecutionException e1) {
				System.out.println("execution exception");
			}
			try {
				// run through variables created and test for expected values.
				Double alpha = 1.500001E9;
				Double beta = 1.5e9;
				Double z = 5.0;
                
				assertEquals(_c.getCell("alpha").numVal(), alpha);
				assertEquals(_c.getCell("beta").numVal(), beta);
				assertEquals(_c.getCell("z").numVal(), z);
				assertEquals(_c.getCell("x").boolVal(), false);
				assertEquals(_c.getCell("y").boolVal(), false);
				assertEquals(_c.getCell("valid").boolVal(), true);
				
				
			} catch (TypeException e) {
				System.out.println("not a number value");
			}
			
		} catch (IOException e) {
			System.out.println("testIFStatements() didn't find file");
		}
           System.out.println("FINISHED IF TESTS======FINISHEDIFwhileTESTS");
          
	}
	
	/**
     * Tests parserRelationalTests.txt for correct evaluation and parsing of relational statements.
     * 
     */
	@Test
	public void testRelational(){
		ASTCell _ifResult;
		ASTCell _ifRex = null;
		try {
			_parser = new NanoParser("data/NanoScripts/testScripts/parserRelationalTest.txt");
			_parser.parse();
			_ifResult = _parser.getResult();
			try {
				_ifRex = _ifResult.execute(_c);
				System.out.println("result: "+_ifRex.print());
			} catch (ExecutionException e1) {
				System.out.println("execution exception");
			}
			try {
				// run through variables created and test for expected values.
				assertEquals(_c.getCell("a").boolVal(), true);
				assertEquals(_c.getCell("b").boolVal(), false);
				assertEquals(_c.getCell("c").boolVal(), false);
				assertEquals(_c.getCell("d").boolVal(), true);
				assertEquals(_c.getCell("e").boolVal(), true);
				assertEquals(_c.getCell("f").boolVal(), false);
				assertEquals(_c.getCell("g").boolVal(), false);
				assertEquals(_c.getCell("h").boolVal(), false);
				assertEquals(_c.getCell("i").boolVal(), true);
				assertEquals(_c.getCell("j").boolVal(), false);
				assertEquals(_c.getCell("k").boolVal(), true);
				assertEquals(_c.getCell("l").boolVal(), false);
				assertEquals(_c.getCell("m").boolVal(), false);
				assertEquals(_c.getCell("n").boolVal(), true);
				assertEquals(_c.getCell("o").boolVal(), true);
				assertEquals(_c.getCell("p").boolVal(), true);
				assertEquals(_c.getCell("q").boolVal(), true);
				assertEquals(_c.getCell("r").boolVal(), true);
				
			} catch (TypeException e) {
				System.out.println("not a number value");
			}
			
		} catch (IOException e) {
			System.out.println("testIFStatements() didn't find file");
		}
           System.out.println("FINISHED IF TESTS============================FINISHEDrelationalTESTS");
          
	}
	
	
	/**
	 * tests for correct recognition and assignment of internal identifiers.
	 */
	@Test
	public void testInternalAssignment(){
		ASTCell _ifResult;
		ASTCell _ifRex = null;
		try {
			_parser = new NanoParser("data/NanoScripts/testScripts/parserInternalAssignmentTest.txt");
			_parser.parse();
			_ifResult = _parser.getResult();
			try {
				_ifRex = _ifResult.execute(_c);
				System.out.println("result: "+_ifRex.print());
			} catch (ExecutionException e1) {
				System.out.println("execution exception");
			}
			try {
				assertTrue(_c.getCell("a").numVal() == 5);
				assertTrue(_c.getCell("b").numVal() == 2);
				assertEquals(_c.getCell("c").stringVal(), "\"martin\"");
				assertTrue(_c.getCell("d").numVal() == 5);
				assertTrue(_c.getCell("e").numVal() == 6);
				assertTrue(_c.getCell("f").numVal() == 8);
				assertTrue(_c.getCell("g").numVal() == 13);
				assertTrue(_c.getCell("h").numVal() == 10);
				assertTrue(_c.getCell("i").numVal() == 10);
				assertTrue(_c.getCell("j").numVal() == 21);
				assertTrue(_c.getCell("k").numVal() == 27);
				assertTrue(_c.getCell("l").numVal() == 14);
				assertTrue(_c.getCell("m").numVal() == 28);
				assertEquals(_c.getCell("n").boolVal(), false);
				assertTrue(_c.getCell("a[1.0]").numVal() == 500000);
				assertEquals(_c.getCell("o"), _c.getCell("a[1.0]"));
				
			} catch (TypeException e) {
				System.out.println("not a number value");
			}
			
		} catch (IOException e) {
			System.out.println("InternalAssignments() didn't find file");
		}
	}
	
	/**
	 * Tests the correct recognition and retrevial of external gamestate variables.
	 * Uses script parserExternalAccessText.txt
	 */
	@Test
	public void TestExternalAccess(){
		ASTCell _ifResult;
		ASTCell _ifRex = null;
		GameCharacter _nearest = new Thief("Martin",Gender.MALE);
		GameCharacter _farthest = new Fighter("Bryan",Gender.MALE);
		GameCharacter _random = new Mage("Paul",Gender.MALE);
		_c.setExternal("self.hp", new NumberASTCell(100));
		_c.setExternal("self.mp", new NumberASTCell(100));
		_c.setExternal("nearest.hp", new NumberASTCell(200));
		_c.setExternal("nearest.mp", new NumberASTCell(200));
		_c.setExternal("nearest.class", new StringASTCell(_nearest.getClass().toString()));
		_c.setExternal("nearest.moolah", new NumberASTCell(200));
		_c.setExternal("farthest.hp", new NumberASTCell(300));
		_c.setExternal("farthest.mp", new NumberASTCell(300));
		_c.setExternal("farthest.class", new StringASTCell(_farthest.getClass().toString()));
		_c.setExternal("farthest.moolah", new NumberASTCell(300));
		_c.setExternal("random.hp", new NumberASTCell(4));
		_c.setExternal("random.mp", new NumberASTCell(400));
		_c.setExternal("random.class", new StringASTCell(_random.getClass().toString()));
		_c.setExternal("random.moolah", new NumberASTCell(400));
		try {
			_parser = new NanoParser("data/NanoScripts/testScripts/parserExternalAccessTest.txt");
			_parser.parse();
			_ifResult = _parser.getResult();
			try {
				_ifRex = _ifResult.execute(_c);
				System.out.println("result: "+_ifRex.print());
			} catch (ExecutionException e1) {
				System.out.println("execution exception");
			}
			try {
				
				assertEquals(_c.getCell("a").numVal(), new Double(100.0));
				assertEquals(_c.getCell("b").numVal(), new Double(100.0));
				assertEquals(_c.getCell("c").numVal(), new Double(200.0));
				assertEquals(_c.getCell("d").numVal(), new Double(200.0));
				assertEquals(_c.getCell("e").numVal(), new Double(200.0));
				System.out.println("nearest.class : "+ _c.getCell("f").stringVal());
				assertEquals(_c.getCell("f").numVal(), "com.game.characters.Thief");
				assertEquals(_c.getCell("g").numVal(), new Double(300.0));
				assertEquals(_c.getCell("h").numVal(), new Double(300.0));
				assertEquals(_c.getCell("i").numVal(), new Double(300.0));
				System.out.println("farthest.class : "+ _c.getCell("j").stringVal());
				assertEquals(_c.getCell("j").numVal(), "com.game.characters.Fighter");
				assertEquals(_c.getCell("k").numVal(), new Double(4.0));
				assertEquals(_c.getCell("l").numVal(), new Double(400.0));
				assertEquals(_c.getCell("m").numVal(), new Double(400.0));
				System.out.println("farthest.class : "+ _c.getCell("j").stringVal());
				assertEquals(_c.getCell("n").numVal(), "com.game.characters.Mage");
				
				
			} catch (TypeException e) {
				System.out.println("not a number value");
			}
			
		} catch (IOException e) {
			System.out.println("testIFStatements() didn't find file");
		}
           System.out.println("FINISHED EXTERNALaccess==============================FINISHEDexternalTESTS");
	}
	/**
	 * tests the FireDragon
	 */
	@Test
	public void testFireDragon(){
		ASTCell _ifResult;
		ASTCell _ifRex = null;
		GameCharacter _nearest = new Thief("Martin",Gender.MALE);
		GameCharacter _farthest = new Fighter("Bryan",Gender.MALE);
		GameCharacter _random = new Mage("Paul",Gender.MALE);
		_c.setExternal("self.hp", new NumberASTCell(100));
		_c.setExternal("self.mp", new NumberASTCell(100));
		_c.setExternal("self.level", new NumberASTCell(1));
		_c.setExternal("nearest.hp", new NumberASTCell(200));
		_c.setExternal("nearest.mp", new NumberASTCell(200));
		_c.setExternal("nearest.name", new StringASTCell("Martin"));
		_c.setExternal("nearest.level", new NumberASTCell(1));
		_c.setExternal("nearest.fullHp", new NumberASTCell(100));
		_c.setExternal("nearest.class", new StringASTCell(_nearest.getClass().toString()));
		_c.setExternal("nearest.moolah", new NumberASTCell(200));
		try {
			_parser = new NanoParser("data/NanoScripts/gameScripts/FireDragon.nano");
			_parser.parse();
			_ifResult = _parser.getResult();
			
		}catch (IOException e) {
			System.out.println("testFireDragon() didn't find file");
		}
	}
	/**
	 * tests the crocWarrior script
	 */
	@Test
	public void testCrocWarrior(){
		ASTCell _ifResult;
		ASTCell _ifRex = null;
		GameCharacter _nearest = new Thief("Martin",Gender.MALE);
		GameCharacter _farthest = new Fighter("Bryan",Gender.MALE);
		GameCharacter _random = new Mage("Paul",Gender.MALE);
		_c.setExternal("self.hp", new NumberASTCell(100));
		_c.setExternal("self.mp", new NumberASTCell(100));
		_c.setExternal("self.level", new NumberASTCell(1));
		_c.setExternal("nearest.hp", new NumberASTCell(200));
		_c.setExternal("nearest.mp", new NumberASTCell(200));
		_c.setExternal("nearest.name", new StringASTCell("Martin"));
		_c.setExternal("nearest.level", new NumberASTCell(1));
		_c.setExternal("nearest.fullHp", new NumberASTCell(100));
		_c.setExternal("nearest.class", new StringASTCell(_nearest.getClass().toString()));
		_c.setExternal("nearest.moolah", new NumberASTCell(200));
		try {
			_parser = new NanoParser("data/NanoScripts/gameScripts/CrocWarrior.nano");
			_parser.parse();
			_ifResult = _parser.getResult();
			
		}catch (IOException e) {
			System.out.println("testCrocWarrior() didn't find file");
		}
	}
	/**
	 * Tests the MushroomMan script
	 */
	@Test
	public void testMushroomMan(){
		ASTCell _ifResult;
		ASTCell _ifRex = null;
		GameCharacter _nearest = new Thief("Martin",Gender.MALE);
		GameCharacter _farthest = new Fighter("Bryan",Gender.MALE);
		GameCharacter _random = new Mage("Paul",Gender.MALE);
		_c.setExternal("self.hp", new NumberASTCell(100));
		_c.setExternal("self.mp", new NumberASTCell(100));
		_c.setExternal("self.level", new NumberASTCell(1));
		_c.setExternal("nearest.hp", new NumberASTCell(200));
		_c.setExternal("nearest.mp", new NumberASTCell(200));
		_c.setExternal("nearest.name", new StringASTCell("Martin"));
		_c.setExternal("nearest.level", new NumberASTCell(1));
		_c.setExternal("nearest.fullHp", new NumberASTCell(100));
		_c.setExternal("nearest.class", new StringASTCell(_nearest.getClass().toString()));
		_c.setExternal("nearest.moolah", new NumberASTCell(200));
		try {
			_parser = new NanoParser("data/NanoScripts/gameScripts/MushroomMan.nano");
			_parser.parse();
			_ifResult = _parser.getResult();
			
		}catch (IOException e) {
			System.out.println("testMushroomMan() didn't find file");
		}
	}
	
	/**
	 * Tests the MushroomMan script
	 */
	@Test
	public void testLane(){
		ASTCell _ifResult;
		ASTCell _ifRex = null;
		GameCharacter _nearest = new Thief("Martin",Gender.MALE);
		GameCharacter _farthest = new Fighter("Bryan",Gender.MALE);
		GameCharacter _random = new Mage("Paul",Gender.MALE);
		_c.setExternal("self.hp", new NumberASTCell(100));
		_c.setExternal("self.mp", new NumberASTCell(100));
		_c.setExternal("self.level", new NumberASTCell(1));
		_c.setExternal("nearest.hp", new NumberASTCell(200));
		_c.setExternal("nearest.mp", new NumberASTCell(200));
		_c.setExternal("nearest.name", new StringASTCell("Martin"));
		_c.setExternal("nearest.level", new NumberASTCell(1));
		_c.setExternal("nearest.fullHp", new NumberASTCell(100));
		_c.setExternal("nearest.class", new StringASTCell(_nearest.getClass().toString()));
		_c.setExternal("nearest.moolah", new NumberASTCell(200));
		try {
			_parser = new NanoParser("data/NanoScripts/gameScripts/ProfessorLane.nano");
			_parser.parse();
			_ifResult = _parser.getResult();
			
		}catch (IOException e) {
			System.out.println("testLane() didn't find file");
		}
	}
	
	/**
	 * tests if exceptions are thrown correctly
	 * @throws ExecutionException
	 */
	@Test
	(expected = ExecutionException.class)public void testException() throws ExecutionException{
		ASTCell _ifResult;
		ASTCell _ifRex = null;
		_c.setExternal("self.hp", new NumberASTCell(100));
		try {
			_parser = new NanoParser("data/NanoScripts/testScripts/parserExternalExceptionTest.txt");
			_parser.parse();
			_ifResult = _parser.getResult();
			_ifRex = _ifResult.execute(_c);
			
		} catch (IOException e) {
			System.out.println("testIFStatements() didn't find file");
		}
	}
	
	
}

