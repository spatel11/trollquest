package com.ai;

import com.ai.reference.HasPattern;
import com.ai.reference.Token;

/**
 * This is a simple class for building concrete tokens
 * @author Martin Tice
 *
 * @param <T> is the type of token. In all Nano project, this type is a specific index in {@link NanoTokens}
 */
public class ConcreteToken<T extends Enum<T> & HasPattern> implements Token<T> {
	/** The actual content that matches a token type*/
    private String _content;
    /**the specific type of {@link NanoTokens}*/
    private T _type;
    
//CONSTRUCTOR=================================================================
    /**
     * Constructor wraps getter methods of both type and content to a given type and content of a token
     * 
     * @param t specific {@link NanoTokens} type
     * @param content actual String content of token.
     */
	public ConcreteToken(T t, String content){
	          	_type = t;
	          	_content = content;
	}//END CONSTRUCTOR========================================================
	/**
	 * @return String the content that the token returns
	 */
	public String getContent() {
		
		return _content;
	}

	/**
	 * @return T the type of the token
	 */
	public T getType() {
		return _type;
	}
	

}
