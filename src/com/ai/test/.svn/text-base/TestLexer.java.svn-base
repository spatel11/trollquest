package com.ai.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.ai.NanoLexer;
import com.ai.NanoTokens;
import com.ai.reference.ParseException;
import com.ai.reference.Token;

/**
 * Tests correct Lexing of all token types from file.
 * <p>Creates a key for two scripts for detailed tests.</p>
 * <p>for remaining tests, just checks that no {@link NanoTokens#UNKNOWN} are found.</p>
 * <p><b>NUMBER TOKEN NOT CORRECT YET</b></p>
 * 
 * @author Martin Tice
 *
 */
public class TestLexer {
    private File _tokenSetScript;
    private File _identifierTestScript;
    private File _numberTestScript;
    private File _prefixTestScript;
    private NanoLexer _lexer;
    private NanoTokens[] _tokenSetKey;
    private NanoTokens[] _ifconditionKey;
    private ArrayList<String> _files = new ArrayList<String>();
	@Before
	public void setUp() throws Exception {
		_tokenSetScript = new File("data/NanoScripts/testScripts/lexerFullTokenSetTest.txt");
		_tokenSetKey = createTokenSetKey();
		_identifierTestScript = new File("data/NanoScripts/testScripts/lexerIdentifierTest.txt");
		_numberTestScript = new File("data/NanoScripts/testScripts/lexerNumberTestScript.txt");
		_prefixTestScript = new File("data/NanoScripts/testScripts/lexerPrefixTest.txt");
		//Create a list of file paths
		File testDir = new File("data/NanoScripts");
		for(File f : testDir.listFiles()){
			if(f.isDirectory()){
				for(File f2 : f.listFiles()){
					if(!f2.isDirectory())
						_files.add(f2.getCanonicalPath());
			}	
		}
			if(!f.isDirectory())
				_files.add(f.getCanonicalPath());
		}
	}

	/**
	 * Runs through a list of all valid input tokens.
	 * If the token read doesn't match the cooresponding match in {@link #_tokenSetKey} key, it will
	 * fail the assertEquals() test
	 */
	@Test
	public void testTokenList() {
		Token<NanoTokens> tok;
		try {
			final NanoLexer lex=
			        new NanoLexer(_tokenSetScript.getPath(),NanoTokens.EOF);
			try {
				int i = 0;
				while ((tok=lex.nextTok()).getType() != NanoTokens.EOF) {
				    System.out.println(tok.getType() + ": " + tok.getContent());
				    assertEquals(tok.getType(), _tokenSetKey[i]);
				    i++;
				  }
			} catch (ParseException e) {
				System.out.println("SimpleNameTest ParseException");
			}
		} catch (IOException e) {
			System.out.println("SimpleNameTest: couldn't find file");
		}
		System.out.println("endTestTokenList==========================================");
	}
	/**
	 * tests variations on Legal Identifiers. Both internal and external.
	 * 
	 */
	@Test
	public void testIdentifiers(){
		Token<NanoTokens> tok;
		try {
			final NanoLexer lex=
			        new NanoLexer(_identifierTestScript.getPath(),NanoTokens.EOF);
			try {
				while ((tok=lex.nextTok()).getType() != NanoTokens.EOF) {
				    System.out.println(tok.getType() + ": " + tok.getContent());
				    assertEquals(tok.getType(), NanoTokens.IDENTIFIER);
				  }
			} catch (ParseException e) {
				System.out.println("identifierTest ParseException");
			}
		} catch (IOException e) {
			System.out.println("IdentifierTest: couldn't find file");
		}
		System.out.println("endTestIdentifiers=======================================");
	}
	
	@Test
	public void numberTests(){
		Token<NanoTokens> tok;
		try {
			final NanoLexer lex=
			        new NanoLexer(_numberTestScript.getPath(),NanoTokens.EOF);
			try {
				while ((tok=lex.nextTok()).getType() != NanoTokens.EOF) {
				    System.out.println(tok.getType() + ": " + tok.getContent());
                   assertEquals(tok.getType(), NanoTokens.NUMBER);
				  }
			} catch (ParseException e) {
				System.out.println("identifierTest ParseException");
			}
		} catch (IOException e) {
			System.out.println("IdentifierTest: couldn't find file");
		}
		System.out.println("endNUMBERtests=======================================");
	}
	@Test
	public void testPrefix(){
		Token<NanoTokens> tok;
		EnumSet<NanoTokens> _prefixOperators = EnumSet.of(NanoTokens.OP_NOT,
				NanoTokens.OP_MINUS, NanoTokens.OP_PLUS);
		try {
			final NanoLexer lex=
			        new NanoLexer(_prefixTestScript.getPath(),NanoTokens.EOF);
			try {
				while ((tok=lex.nextTok()).getType() != NanoTokens.EOF) {
				    System.out.println(tok.getType() + ": " + tok.getContent());
	            	   assertEquals(_prefixOperators.contains(tok.getType()), true);
				  }
			} catch (ParseException e) {
				System.out.println("identifierTest ParseException");
			}
		} catch (IOException e) {
			System.out.println("IdentifierTest: couldn't find file");
		}
		System.out.println("endNUMBERtests=======================================");
	}

	/**
	 * runs through tests to check if an unknown token is found.
	 */
	@Test
	public void TestAllScripts(){
		Token<NanoTokens> tok;
		for(String s : _files){
		try {	
			NanoLexer lex= new NanoLexer(s,NanoTokens.EOF);
			try {
				while ((tok=lex.nextTok()).getType() != NanoTokens.EOF) {
					System.out.println(s);
				    System.out.println(tok.getType() + ": " + tok.getContent());
				    if(tok.getType() == NanoTokens.UNKNOWN){
				                 fail("");
				    }
				  }
			
			} catch (ParseException e) {
				System.out.println("ifTests ParseException");
			}
		
		} catch (IOException e) {
			System.out.println("ifTest: couldn't find file" + s);
		}
		}
		System.out.println("endIfTests=======================================");
	}
	
	//KEY CREATORS FOR INPUT FILES
	/**
	 * Creates a key to match to the Lexed file {@link #_tokenSetScript}
	 * @return an array of tokens for {@link #_tokenSetKey}
	 */
	public NanoTokens[] createTokenSetKey(){
		final   NanoTokens[] _key= {NanoTokens.IDENTIFIER, 
                NanoTokens.K_ACTION,
                NanoTokens.K_FINALIZER,
                NanoTokens.K_POLICY,
                NanoTokens.K_CONSTRUCTOR,
                NanoTokens.K_RETURN,
                NanoTokens.K_WHILE,
                NanoTokens.K_ELSE,
                NanoTokens.K_IF,
                NanoTokens.IDENTIFIER,
                NanoTokens.IDENTIFIER,
                NanoTokens.IDENTIFIER,
                NanoTokens.OP_EQ_ASSIGN,
                NanoTokens.OP_NOT,
                NanoTokens.OP_NEQ_COMPARE,
                NanoTokens.OP_EQ_COMPARE,
                NanoTokens.OP_LT,
                NanoTokens.OP_LTE,
                NanoTokens.OP_GT,
                NanoTokens.OP_GTE,
                NanoTokens.OP_OR,
                NanoTokens.OP_AND,
                NanoTokens.OP_DIV,
                NanoTokens.OP_TIMES,
                NanoTokens.OP_PLUS,
                NanoTokens.OP_MINUS,
                NanoTokens.SEP_SEMICOLON,
                NanoTokens.SEP_COMMA,
                NanoTokens.SEP_L_BRACKET,
                NanoTokens.SEP_R_BRACKET,
                NanoTokens.SEP_L_PAREN,
                NanoTokens.SEP_R_PAREN,
                NanoTokens.SEP_L_BRACE,
                NanoTokens.SEP_R_BRACE,
                NanoTokens.STRING,
                NanoTokens.NUMBER,
                NanoTokens.NUMBER,
                NanoTokens.OP_EQ_COMPARE,
                NanoTokens.NUMBER,
                NanoTokens.NUMBER,
                NanoTokens.BOOLEAN,
                NanoTokens.BOOLEAN,
                NanoTokens.OP_NOT,
                NanoTokens.BOOLEAN};
		return _key;
	}
}
