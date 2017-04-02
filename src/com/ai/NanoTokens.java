package com.ai;

import com.ai.reference.HasPattern;


/**
 * Token set for the NanoC scripting Language. Each index of this enum
 * represents a specific terminal of the NanoC grammer.
 * There is a string for each enum type that can be retrieved by the {@link #getPattern()} method.
 * 
 * @author Martin Tice
 * 
 */
public enum NanoTokens implements HasPattern {
	//BOO("true|false"),
	WHITESPACE("\\s"),
	NEWLINE("\\n"),
	NUMBER("[0-9]+[.][0-9]*(?:([eE][-+]?[0-9]+)?)?|[0-9]+(?:([eE][-+]?[0-9]+)?)?"),
	SEP_L_BRACE("\\{"),
	SEP_R_BRACE("\\}"),
	SEP_L_PAREN("\\("),
	SEP_R_PAREN("\\)"),
	SEP_L_BRACKET("\\["),
	SEP_R_BRACKET("\\]"),
	SEP_COMMA(","),
	SEP_SEMICOLON(";"),
	OP_MINUS("-"),
	OP_PLUS("\\+"),
	OP_TIMES("\\*"),
	OP_DIV("/"),
	OP_AND("&&"),
	EOF("\\z"),
	OP_OR("\\|\\|"),
	OP_GTE(">="),
	BOOLEAN("true|false"),
	OP_GT(">"),
	OP_LTE("<="),
	OP_LT("<"),
	OP_EQ_COMPARE("=="),
	OP_NEQ_COMPARE("!="),
	OP_NOT("!"),
	OP_EQ_ASSIGN("="),
	K_IF("if"),
	K_ELSE("else"),
	K_WHILE("while"),
	K_RETURN("return"),
	K_CONSTRUCTOR("constructor"),
	K_POLICY("policy"),
	K_FINALIZER("finalizer"),
	K_ACTION("action"),
	IDENTIFIER("[_$a-zA-Z][_$0-9a-zA-Z.]*"),
	STRING("\"[^\"]*\""),
	UNKNOWN(".*");

    /**
     * returns the string associated with each enum type.
     */
	public String getPattern() {
		return _pattern;
	}

	/**
	 * @param  regular expression to represent each enum type. 
	 */
	private NanoTokens(String pat) {
		assert pat != null;
		_pattern = pat;
	}

	/** string representing the regular expression of each element */
	private final String _pattern;

}
