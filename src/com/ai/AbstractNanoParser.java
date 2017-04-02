package com.ai;

import java.io.File;
import java.io.IOException;
import java.util.EnumSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ai.reference.HasPattern;
import com.ai.reference.LexicalAnalyzer;
import com.ai.reference.Parser;
import com.ai.reference.Token;

//import unm.cs351.f11.tdrl.p1.HasPattern;
//import unm.cs351.f11.tdrl.p1.LexicalAnalyzer;
import com.ai.reference.ParseException;

/**
 * Abstract Parser for NanoC language. Parent class of {@link NanoParser}.
 * Used to handle all helper methods for concrete parser. Begins by taking in
 * a filename and creating a {@link LexicalAnalyzer} object to analyze file. It then gets first {@link Token<T>}
 * from file. This is used as a buffer token in case any of the {@link #expect(Enum)} or {@link #consume()} methods 
 * returns a false or an error. 
 * @author Martin Tice
 *
 * @param <S> data type to hold parsed input. If parent of {@link NanoParser}, S = ASTCell
 * @param <T> type of input token. If parent of {@link NanoParser}, T = {@link NanoTokens}
 */
public class AbstractNanoParser<S, T extends Enum<T> & HasPattern> implements Parser<S, T>  {
    /**used to prompt print statements when debugging*/
	protected boolean DEBUG = false;
	/**Input file*/
	private File _in;
	/**Used to return, token by token, the data inside {@link #_in} file.*/
	private LexicalAnalyzer<NanoTokens> _tokenStream;
	/**currently analysed token*/
	protected Token<T> _currTok;
	/**buffer token in the case it is unwanted*/
	private Token<T> _buffTok;
	/**token to signal end of file*/
	private T _eof;
	
//================================================================CONSTRUCTOR
	/***
	 * Constructor creates a new {@link LexicalAnalyzer} of type {@link NanoLexer} from file passed in as parameter.
	 * Initializes {@link #_currTok} as first token in input file. Sets {@link #_eof} equal to the {@param eofToken}.
	 * 
	 * @author Martin Tice
	 * 
	 * @param String file to be parsed
	 * @param Token<T> to signal end of file
	 * @throws IOException if error in finding and processing file
	 */
	public AbstractNanoParser(String filename, T eofToken) throws IOException{
		try{
			_in = new File(filename);
		}catch(NullPointerException npe){
			System.out.println("AbsractNanoParser input file is invalid");
		}
		_tokenStream = new NanoLexer(_in.getPath(), eofToken);
		try {
			_currTok =  (Token<T>) _tokenStream.nextTok();
		} catch (ParseException e) {
			System.out.println("ABParser constructor fail: empty tokenStream");
		}
		_eof = eofToken;
	}
//=====END CONSTRUCTOR

	
    /**
     * Consumes next token in {@link #_in} file. Throws {@link ParseException} if next token is {@link #_eof}.
     * Sets {@link #_currTok} to {@link #_buffTok}, calls {@link NanoLexer#nextTok()} and sets output to {@link #_currTok}.
     * @return {@link #_buffTok}. Which was set to {@link #_currTok}, or the next unprocessed token.
     */
	public Token<T> consume() throws ParseException {
		if(_currTok.getType() == _eof){throw new ParseException();}
		if(DEBUG){System.out.println("abNanoParser.consume() got: "+_currTok.getType()+" "+_currTok.getContent());}
		_buffTok = _currTok;
		_currTok = (Token<T>) _tokenStream.nextTok();
		return _buffTok;
	}

    /**
     * <p><b>Consumes token only if token matches given input token.</b></p>
     * First checks {@link #_currTok} vs. {@link Token<T>} expected . If not equal, returns {@link ParseException}. If equal, carries on with consume process:<p>
     * Consumes next token in {@link #_in} file. Throws {@link ParseException} if next token is {@link #_eof}.
     * Sets {@link #_currTok} to {@link #_buffTok}, calls {@link NanoLexer#nextTok()} and sets output to {@link #_currTok}.</p>
     * @param T expected is the expected {@link Token<T>} to match.
     * @return {@link #_buffTok}. Which was set to {@link #_currTok}, or the next unprocessed token.
     */
	public Token<T> consume(T expected) throws ParseException {
		if(_currTok.getType() != expected){
			if(DEBUG)System.out.println("AbParser.consume(T expected): " +
					"token does not match expected. curr= "+ _currTok.getType()+", expected="+ expected);
			throw new ParseException();
		}
		if(DEBUG)System.out.println("abNanoParser.Consume(expected): "+ expected+
				" , content is: {"+_currTok.getContent()+"}");
		_buffTok = _currTok;
		_currTok = (Token<T>) _tokenStream.nextTok();
		return _buffTok;
	}

	 /**
	 *<p><b>Consumes token only if {@link #_currTok} matches one on given EnumSet<T></b></p>
     * First checks {@link #_currTok} vs. {@link Token<T>} expected . If not equal, returns {@link ParseException}. If equal, carries on with consume process:<p>
     * Consumes next token in {@link #_in} file. Throws {@link ParseException} if next token is {@link #_eof}.
     * Sets {@link #_currTok} to {@link #_buffTok}, calls {@link NanoLexer#nextTok()} and sets output to {@link #_currTok}.</p>
     * @param Token<T> expected is the expected {@link Token<T>} to match.
     * @return {@link #_buffTok}. Which was set to {@link #_currTok}, or the next unprocessed token.
     */
	public Token<T> consume(EnumSet<T> expectedClass) throws ParseException {
		for(T type: expectedClass){
			if(_currTok.getType() == type){
				_buffTok = _currTok;
				_currTok = (Token<T>) _tokenStream.nextTok();
				return _buffTok;
			}
		}
		System.out.println("attempt to consume unexpected token");
		throw new ParseException();
	}

	/**
	 * <p><b>Consumes token only if {@link #_currToken} matches given {@link Token<T>} and {@link Matcher}<T></b></p>
     * First checks {@link #_currTok} vs. {@link Token<T>} expected and {@link Matcher} ePat . If not equal, returns {@link ParseException}. If equal, carries on with consume process:<p>
     * Consumes next token in {@link #_in} file. Throws {@link ParseException} if next token is {@link #_eof}.
     * Sets {@link #_currTok} to {@link #_buffTok}, calls {@link NanoLexer#nextTok()} and sets output to {@link #_currTok}.</p>
     * @param T expected is the expected {@link Token<T>} to match.
     * @return {@link #_buffTok}. Which was set to {@link #_currTok}, or the next unprocessed token.
	 */
	public Token<T> consume(T expected, Matcher ePat) throws ParseException {
		ePat.reset(_currTok.getContent());
		Pattern p =  Pattern.compile(_currTok.getType().getPattern());
		Matcher m = (p.matcher(_currTok.getContent()));
		if(ePat.find() && m.find()){
		}else{
			
			throw new ParseException();
		}
		
		if(_currTok.getType() == expected && m.group() == ePat.group()){
			//System.out.println("--------------consumeTM success!!");
			_buffTok = _currTok;
			_currTok = (Token<T>) _tokenStream.nextTok();
			return _buffTok;
		}
		return _buffTok;
	}


    /**
     * Consumes all tokens in {@link #_tokenStream} until the token matches given  {@link Token<T>}.
     * 
     */
	public void consumeUntil(T until) throws ParseException {
		 while (_currTok.getType()!= until) {
		     if(_currTok.getType() == NanoTokens.EOF){
		    	 throw new ParseException();
		     }
		     _currTok = (Token<T>) _tokenStream.nextTok();
		    }
		
	}

	/**
	 * Consumes all tokens until a token matches {@link Token<T>} until and {@link Matcher} untilPat.
	 * 
	 */
	public void consumeUntil(T until, Matcher untilPat) throws ParseException {
		untilPat.reset(_currTok.getContent());
		Pattern pActual =  Pattern.compile(_currTok.getType().getPattern());
		Matcher mActual = (pActual.matcher(_currTok.getContent()));
		while (_currTok.getType()!= until && !untilPat.find()) {
		     if(_currTok.getType() == NanoTokens.EOF){
		    	 throw new ParseException();
		     }
		     _currTok = (Token<T>) _tokenStream.nextTok();
		     untilPat.reset(_currTok.getContent());
		    }
	}

    /**
     * <p><b>Used to check next token before consuming.</b></p>
     * Checks {@link #_currTok} vs {@link Token<T>} expected. If the same, return true,
     * else return false.
     * 
     * @return true if expected token matches {@link #_currTok}
     * @return false if expected token does not match {@link #_currTok}
     */
	public boolean expect(T expected) {
	    if(_currTok.getType() == expected){
		return true;
	    }
	    return false;
	}

	/**
	 * <p><b>Used to check next token before consuming.</b></p>
	 * checks {@link #_currTok} vs. any of the tokens in {@link EnumSet<T>} expected.
	 * If a match is found, return true, else return false.
	 */
	public boolean expect(EnumSet<T> expected) {
		for(T type: expected){
			//System.out.println(type);
			if(_currTok.getType() == type){
				//System.out.println("found------"+ type);
				return true;
			}
		}
	return false;
	}

	/**
     * <p><b>Used to check next token before consuming.</b></p>
     * Checks {@link #_currTok} vs {@link Token<T>} expected and {@link Matcher} ePat. If the same, return true,
     * else return false.
     * 
     * @return true if expected token matches {@link #_currTok}
     * @return false if expected token does not match {@link #_currTok}
     */
	public boolean expect(T expected, Matcher ePat) {
		ePat.reset(_currTok.getContent());
		if(_currTok.getType() == expected && ePat.find() ){
			return true;
		}
		return false;
	}

	/**
	 * Used to signal beginning of Parsing process. To be handled by the {@link NanoParser} class
	 * to whom this class is a parent.
	 */
	public void parse(S arg0) throws ParseException {
		// implementation handled by child
		
	}

	/**
	 * Performs any operation needed to end parse process. Not yet used in {@link NanoParser} child.
	 */
	public void shutDown() throws IOException {
		//not yet used in NanoParser.
		
	}


}
