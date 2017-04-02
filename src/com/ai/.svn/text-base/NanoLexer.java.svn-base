package com.ai;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ai.reference.HasPattern;
import com.ai.reference.LexicalAnalyzer;
import com.ai.reference.ParseException;
import com.ai.reference.Token;


/**
 * Concrete implementation of a {@link LexicalAnalyzer}. Used as a filter of a given
 * file, returning one {@link NanoTokens} at a time, in order read from file.
 * <p>Turns an input file into a CharSequence {@link #_csf} by means of {@link CharSeqFile} adapter class.</p>
 * <p>Converts {@NanoTokens} into a big {@link Pattern} object {@link NanoLexer#_superPattern}
 *  and uses {@link NanoLexer#nextTok()} to return individual tokens one by one</p> 
 * 
 * @author Martin Tice
 *
 * @param <T> type of signal token.
 */
public class NanoLexer<T extends Enum<T> & HasPattern> implements LexicalAnalyzer<T> {

	    /**created from {@link #_superPattern}*/
		private Matcher _matcher;
		/**Pattern objet created from all enum items in {@link NanoTokens}*/
		private Pattern _superPattern;
		/**created from input file for easer traversal*/
		private CharSequence _csf;
		/**signal of end of file*/
		private T _eofFlag;
		/**all tokens inside {@link NanoTokens}*/
		private T[] _enumValues;

		
//CONSTRUCTOR======================================================================
		/**
		 * constructor for a {@link  NanoLexer}
		 * @param raw is the data to lexically analyze
		 * @param eof token to signal the end of file
		 * @throws IOException 
		 */
		public NanoLexer(String rawFileName, T eof) throws IOException{
			if(rawFileName == null){throw new FileNotFoundException();}
			_csf = new CharSeqFile(rawFileName);
			_eofFlag = eof;
			_enumValues = (T[]) _eofFlag.getClass().getEnumConstants();
			_superPattern = makeSuperPattern(_enumValues);
			_matcher = _superPattern.matcher(_csf);
			
	    }//END CONSTRUCTOR===========================================================

		/**
		 * Heart of NanoLexer. Factory Method that returns an object of type {@link Token<T>} that represents
		 * the next token of file.
		 * This is used to return the next token in a given input.
		 * @returns the next token in the token stream
		 */
		public Token<T> nextTok() throws ParseException {
			if(_matcher.find()){
				for(T t: _enumValues){
					if(_matcher.group().matches(t.getPattern())){
						if(t == NanoTokens.WHITESPACE){nextTok();}
						else{
							return new ConcreteToken(t, _matcher.group());
							}
					}
				}
			}else{
				System.out.println("nextToken: failed to match token ");
				throw new ParseException();
			}
			return null;
		}
		
	//---------------------private helper method-----------------
		/**
	     * <p><b>converts an array of enum tokens to a {@link Pattern} object</b></p>
	     * @param enumValues is the string to 
	     * @return a huge {@link Pattern} that consists of all of the individual patterns
	     * of a given enum.  
	     */
		private Pattern makeSuperPattern(T[] enumValues) {
			StringBuilder superPattern = new StringBuilder("");
			for(T t: enumValues){
				superPattern.append("("+ t.getPattern()+")|");
			}
			return Pattern.compile(superPattern.toString());
		}
		

}
