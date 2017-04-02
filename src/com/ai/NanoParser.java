package com.ai;

import java.io.IOException;
import java.util.EnumSet;


import com.ai.ASTCells.ARefASTCell;
import com.ai.ASTCells.ASTCell;
import com.ai.ASTCells.ActionASTCell;
import com.ai.ASTCells.AndASTCell;
import com.ai.ASTCells.BooleanASTCell;
import com.ai.ASTCells.CreatureASTCell;
import com.ai.ASTCells.DivASTCell;
import com.ai.ASTCells.EQASTCell;
import com.ai.ASTCells.ExternalARefASTCell;
import com.ai.ASTCells.ExternalSymbolASTCell;
import com.ai.ASTCells.GTASTCell;
import com.ai.ASTCells.GTEASTCell;
import com.ai.ASTCells.IFASTCell;
import com.ai.ASTCells.InternalSymbolASTCell;
import com.ai.ASTCells.LTASTCell;
import com.ai.ASTCells.LTEASTCell;
import com.ai.ASTCells.MinusASTCell;
import com.ai.ASTCells.MultASTCell;
import com.ai.ASTCells.NEQASTCell;
import com.ai.ASTCells.NOTASTCell;
import com.ai.ASTCells.NilASTCell;
import com.ai.ASTCells.NumberASTCell;
import com.ai.ASTCells.OrASTCell;
import com.ai.ASTCells.PlusASTCell;
import com.ai.ASTCells.PrefixASTCell;
import com.ai.ASTCells.SequenceASTCell;
import com.ai.ASTCells.StoreASTCell;
import com.ai.ASTCells.StringASTCell;
import com.ai.ASTCells.WhileASTCell;
import com.ai.reference.ParseException;
import com.ai.reference.Token;

/**
 * <p><b>Concrete {@link Parser} object used to parse a NanoC file into an AbstractSyntax tree.</b></p>
 * <p>Creates an Abstract Syntax tree by means of Recursive Descent Parsing with each node on tree
 * represented as an {@link ASTCell} object</p>
 * <p>Comprised of individual parse statements that each represent a specific non-terminal or terminal inside the NanoC grammer</p>
 * <p>There is also a helper method that creates a given {@link ASTCell} from a specific operator. {@link #infixOpSel(NanoTokens, ASTCell, ASTCell)}</p>
 * @author Martin Tice
 * 
 */
public class NanoParser extends AbstractNanoParser<ASTCell, NanoTokens> {

	/**used to toggle debug print statements on and off*/
	private boolean _DEBUG = false;
	/**final result of the parsed File. Of type {@link SequenceASTCell}*/
	private SequenceASTCell _result;
	/**used to hold a creture definition, as defined in the NanoC grammer*/
	private ASTCell _creatureDef;
	/**used to hold an action definition, as defined in the NanoC grammer*/
	//private ASTCell _actionCell;

	// GETTERS AND SETTERS
	/**
	 * Gets final {@link ASTCell} result from the parsing of a NanoC file.
	 * 
	 * @return {@link #_result} ASTCell of parsed file. 
	 */
	public ASTCell getResult() {
		return _result;
	}// END OF GETTERS AND SETTERS

// CONSTRUCTOR        ===========================================CONSTRUCTOR
	/**
	 * Takes in a file and gets it ready to be parsed into an Abstract syntax tree.
	 * @param filename
	 *            is the NanoC script to be parsed
	 * @throws IOException
	 *             if the filename is invalid
	 */
	public NanoParser(String filename) throws IOException {
		super(filename, NanoTokens.EOF);
	}// END CONSTRUCTOR

	/**
	 * First/only method needed by client after initialization.
	 * Parses a file into an {@link ASTCell}. begins by creating a random {@link NumberASTCell}(CAN CHANGE IN FUTURE).
	 * <p>gets the next token in a file, decides what non-terminal it represents the start of, and 
	 *    delegates further parsing to a specific parse method</p>
	 * <p>Continues This process until the end of file is reached.</p>
	 */
	public void parse() {
		ASTCell lhs = NilASTCell.getNil();
		_result = new SequenceASTCell(lhs);
		try {
			while (!expect(NanoTokens.EOF)) {
				if (expect(NanoTokens.K_CONSTRUCTOR)) {
					_result.add(parseCreatureDef());
				} else {
					_result.add(parseStatement());
				}
				if(_DEBUG){System.out.println("should go here before error");}
			}
		} catch (ParseException pe) {
			System.out.println("NanoParser.parse ParseException");
		}

	}

// ============================================NON-TERMINAL PARSERS
	
	/**
	 * Parses a <code>CreatureDef</code> non-terminal defined by: 
	 * <code>
	 *  K_CONSTRUCTOR String Statement Statement Statement K_FINALIZER
	 * </code>
	 * 
	 * 
	 * @return a {@link CreatureASTCell}
	 */
	public ASTCell parseCreatureDef() throws ParseException {
		if (_DEBUG) {
			System.out
					.println("*********************************parseCreatureDef()");
		}
		consume(NanoTokens.K_CONSTRUCTOR);
		final String stringLiteral = consume(NanoTokens.STRING).getContent();
		final ASTCell statement1 = parseStatement();
		consume(NanoTokens.K_POLICY);
		final ASTCell policyStatement = parseStatement();
		consume(NanoTokens.K_FINALIZER);
		final ASTCell finalizerStatement = parseStatement();
		return new CreatureASTCell(stringLiteral, statement1,
		                            policyStatement, finalizerStatement);
	}

	/**
	 * parses a <code>Statement</code> non-terminal defined by: <code>Block
	 *  | K_IF ParExpression Statement (K_ELSE Statement)?
	 *  | K_WHILE ParExpression Statement
	 *  | K_RETURN Action SEP_SEMICOLON
	 *  | Expression SEP_SEMICOLON
	 * </code>
	 * 
	 * @returns an {@link IfASTCell}, {@link WhileASTCell}, {@link ReturnASTCell}, or any
	 *              any number of concrete ASTCells representing expressions.
	 */
	public ASTCell parseStatement() throws ParseException {
		if (_DEBUG) {System.out.println("*********************************parseStatement()");}
		// --case block
		if (expect(NanoTokens.SEP_L_BRACE))
			return parseBlock();

		// ---case IF STATEMENT
		if (expect(NanoTokens.K_IF)) {
			consume();
			final ASTCell cond = parseParExpression();
			final ASTCell thenClause = parseStatement();

			ASTCell elseClause = null;
			if (expect(NanoTokens.K_ELSE)) {
				consume();
				elseClause = parseStatement();
			} else {
				elseClause = NilASTCell.getNil();
			}

			return new IFASTCell(cond, thenClause, elseClause);
		}

		// ---case WHILE STATEMENT
		if (expect(NanoTokens.K_WHILE)) {
			consume();
			final ASTCell cond = parseParExpression();
			final ASTCell clause = parseStatement();
			return new WhileASTCell(cond, clause);
		}

		// --case K_RETURN <ACTION> SEP_SEMICOLON
		if (expect(NanoTokens.K_RETURN)) {
			consume();
			final ASTCell action = parseAction();
			consume();
			return action;
		}

		if (expect(NanoTokens.SEP_SEMICOLON)) {
			consume();
			return NilASTCell.getNil();
		}
		// Any thing left must be an expression. Let parseExpr handle checking
		// all of its cases.
		final ASTCell result = parseExpression();
		consume(NanoTokens.SEP_SEMICOLON);
		return result;
	}

	/**
	 * parses a <code>Block</code> non-terminal defined by: <code>
	 *  SEP_L_BRACE Statement Statement* SEP_R_BRACE
	 *  </code>
	 * 
	 * @return a <code>Block</code> non-terminal in form of a
	 *         {@link SequenceASTCell}
	 */
	public ASTCell parseBlock() throws ParseException {
		if (_DEBUG) {
			System.out.println("*********************************parseBlock()");
		}
		consume(NanoTokens.SEP_L_BRACE);
		SequenceASTCell lhs = new SequenceASTCell(parseStatement());
		if (_DEBUG) {
			System.out.println("***********************BlockParsedStatement");
		}
		while (!expect(NanoTokens.SEP_R_BRACE)) {
			lhs.add(parseStatement());
		}
		consume(NanoTokens.SEP_R_BRACE);
		return lhs;
	}

	/**
	 * parses an <code>Expression</code> non-terminal defined by: <code>
	 * LogicalExpression (OP_EQ_ASSIGN Expression)?
	 * </code>
	 * 
	 * @return any number of concrete {@link ASTCell} nodes representing an expression.
	 */
	public ASTCell parseExpression() throws ParseException {
		if (_DEBUG) {
			System.out.println("*************************parseExpression()");
		}
		final ASTCell lhs = parseLogicalExpression();
		if (_DEBUG) {System.out.println("***********************ExpressionParsedLogical");}
		
		if(_DEBUG)System.out.println("logical produced : "+lhs.print());
		if (expect(NanoTokens.OP_EQ_ASSIGN)) {
			if (_DEBUG) {System.out.println("***********************ExpressionParsedAssign");}
			
			consume();
			final ASTCell rhs = parseExpression();
			if (_DEBUG) {System.out.println("ExpressionASSIGNED rhs: "+rhs.print()+" to lhs: "+lhs.print());}
			return new StoreASTCell(lhs, rhs);
		}
		return lhs;
	}

	/**
	 * Parses a <code>LogicalExpression</code> non-terminal defined by: <code>
	 * EqualityExpression (LogicalOperator EqualityExpression)*
	 * </code>
	 * 
	 * @return a concrete {@link ASTCell}
	 */
	public ASTCell parseLogicalExpression() throws ParseException {
		if (_DEBUG) {System.out.println("*********************************parseLogicalExpression()");}
		ASTCell lhs = parseEqualityExpression();
		if (_DEBUG) {
			System.out.println("***********************LogicalParsedEquality");
			System.out.println("parseLogicalExpression.parseEquality produced : "+ lhs.print());
		}
		while (expect(_logicalOperators)) {
		if(_DEBUG)System.out.println("-------------------Logical FOUND LogicOperator");
			Token<NanoTokens> op = consume();
			lhs = infixOpSel(op.getType(), lhs, parseEqualityExpression());
		}
		return lhs;
	}

	/**
	 * parses an <code>EqualityExpression</code> non-terminal defined by: <code>
	 * RelationalExpression (EqualityOperator RelationalExpression)*
	 * </code>
	 * 
	 * @return a concrete {@link ASTCell}
	 */
	public ASTCell parseEqualityExpression() throws ParseException {
		if (_DEBUG) {System.out.println("***************************parseEqualityExpression()");}
		
		ASTCell lhs = parseRelationalExpression();
		if (_DEBUG) {System.out.println("***********************EqualityParsedRelational");}
		
		while (expect(_equalityOperators)) {
			if (_DEBUG) {System.out.println("***********************EqualityFoundEqualityOperator");}
			
			Token<NanoTokens> rel = consume();
			lhs = infixOpSel(rel.getType(), lhs, parseRelationalExpression());
		}
		return lhs;
	}

	/**
	 * parses an <code>RelationalExpression</code> non-terminal defined by:
	 * <code>
	 * AdditiveExpression (RelationalOperator AdditiveExpression)*
	 * </code>
	 * 
	 * @return a concrete {@link ASTCell}
	 */
	public ASTCell parseRelationalExpression() throws ParseException {
		if (_DEBUG) {
			System.out
					.println("*********************************parseRelationalExpression()");
		}
		ASTCell lhs = parseAdditiveExpression();
		if (_DEBUG) {
			System.out
					.println("**********************RelationalParsedAdditive");
		}
		while (expect(_relationalOperators)) {
			Token<NanoTokens> add = consume();
			lhs = infixOpSel(add.getType(), lhs, parseAdditiveExpression());
		}

		return lhs;
	}

	/**
	 * parses an <code>AdditiveExpression</code> non-terminal defined by: <code>
	 * MultiplicativeExpression (AdditionOperator MultiplicativeExpression)*
	 * </code>
	 * 
	 * @return a concrete {@link ASTCell}
	 */
	public ASTCell parseAdditiveExpression() throws ParseException {
		if (_DEBUG) {
			System.out
					.println("*********************************parseAdditiveExpression()");
		}
		ASTCell lhs = parseMultiplicativeExpression();
		if (_DEBUG) {
			System.out.println("**********************AdditiveParsedMultiplicative");}
		while (expect(_additionOperators)) {
			Token<NanoTokens> add = consume();
			lhs = infixOpSel(add.getType(), lhs,
					parseMultiplicativeExpression());
		}
		return lhs;
	}

	/**
	 * parses an <code>MultiplicativeExpression</code> non-terminal defined by:
	 * <code>
	 * UnaryExpression (MultiplicationOperator UnaryExpression)*
	 * </code>
	 * 
	 * @return a concrete {@link ASTCell}
	 */
	public ASTCell parseMultiplicativeExpression() throws ParseException {
		if (_DEBUG) {
			System.out
					.println("*********************************parseMultiplicativeExpression()");
		}
		ASTCell lhs = parseUnaryExpression();
		if (_DEBUG) {
			System.out.println("********************MultiplicativeParsedUnary");
		}
		while (expect(_multiplicationOperators)) {
			Token<NanoTokens> un = consume();
			lhs = infixOpSel(un.getType(), lhs, parseUnaryExpression());
		}
		return lhs;
	}

	/**
	 * parses an <code>UnaryExpression</code> non-terminal defined by: <code>
	 * PrefixOperator UnaryExpression
	 *   | ParExpression
	 *   | Literal
	 *   | Identifier
	 * </code>
	 * 
	 * @return a concrete {@link ASTCell}
	 */
	public ASTCell parseUnaryExpression() throws ParseException {
		if (_DEBUG) {
			System.out
					.println("************************parseUnaryExpression()");
		}
		ASTCell lhs;
		if (expect(_prefixOperators)) {
			boolean evenNumberOfNotOperators = false;
			if (_DEBUG) {System.out.println("parseUnaryFoundPrefix");}
			Token<NanoTokens> pre = consume();
				if(pre.getType() == NanoTokens.OP_NOT){
					if (_DEBUG) {System.out.println("parseUnarycreatingNotASTCell");}
					while (expect(NanoTokens.OP_NOT)) {
						System.out.println("CONSUMEEXTRANOT");
						evenNumberOfNotOperators = !evenNumberOfNotOperators;
						consume();
						}
					return lhs = new NOTASTCell(evenNumberOfNotOperators, parseUnaryExpression());
				}
				else{return new PrefixASTCell(pre, parseUnaryExpression());}
		}
		if (expect(NanoTokens.SEP_L_PAREN)) {
			return parseParExpression();
		}
		if (expect(_literals)) {
			if (_DEBUG) {System.out.println("*****************unaryParsedLiteral");}
			Token<NanoTokens> lit = consume();
			if (lit.getType() == NanoTokens.BOOLEAN) {
				return BooleanASTCell.getValue(Boolean.valueOf(lit
						.getContent()));
			} else if (lit.getType() == NanoTokens.NUMBER) {
				return new NumberASTCell(Double.parseDouble(lit.getContent()));
			}
			return new StringASTCell(lit.getContent());
		}
		return parseIdentifier();
	}

	/**
	 * parses an <code>Identifier</code> non-terminal defined by: <code>
	 * Identifier ARef*
	 * </code>
	 * 
	 * @return a concrete {@link ASTCell} that either holds an identifier or an ARef.
	 */
	public ASTCell parseIdentifier() throws ParseException {
		if (_DEBUG) {System.out.println("*********************************parseIdentifier()");}
		ASTCell lhs;
		Token<NanoTokens> id = consume(NanoTokens.IDENTIFIER);

		if (id.getContent().indexOf(".") > 0) {
			if (_DEBUG) {System.out.println("*****************IdentifierFoundExternal");}
			lhs = new ExternalSymbolASTCell(id.getContent());
			if (expect(NanoTokens.SEP_L_BRACKET)) {
				consume();
				while (!expect(NanoTokens.SEP_R_BRACKET)) {
					lhs = new ExternalARefASTCell(lhs, parseExpression());
				}
				consume(NanoTokens.SEP_R_BRACKET);
			}
		}else{
			 if (_DEBUG) {System.out.println("*****************IdentifierFoundInternal");}
			lhs = new InternalSymbolASTCell(id.getContent());
			if (expect(NanoTokens.SEP_L_BRACKET)) {
				consume();
				while (!expect(NanoTokens.SEP_R_BRACKET)) {
					lhs = new ARefASTCell(lhs, parseExpression());
				}
				consume(NanoTokens.SEP_R_BRACKET);
			}
		}
		return lhs;
	}

	/**
	 * parses a <code>ParExpression</code> non-terminal defined by: <code>
	 * SEP_L_PAREN Expression SEP_R_PAREN
	 * </code>
	 * 
	 * @return a concrete {@link ASTCell}
	 */
	public ASTCell parseParExpression() throws ParseException {
		if (_DEBUG) {
			System.out
					.println("*********************************parseParExpression()");
		}
		consume(NanoTokens.SEP_L_PAREN);
		final ASTCell _exp = parseExpression();
		consume(NanoTokens.SEP_R_PAREN);
		return _exp;
	}

	/**
	 * Returns an Action in the form of {@link ActionASTCell}.
	 * @return an {@link ActionASTCell}
	 */
	public ASTCell parseAction() throws ParseException {
		if (_DEBUG) {System.out.println("********************parseAction()");}
		consume(NanoTokens.K_ACTION);
		consume(NanoTokens.SEP_L_PAREN);
		SequenceASTCell lhs = new SequenceASTCell(parseExpression());
		if (_DEBUG) {System.out.println("********************ActionParsedExpression");}
		while (expect(NanoTokens.SEP_COMMA)) {
			consume(NanoTokens.SEP_COMMA);
			lhs.add(parseExpression());
		}
		consume(NanoTokens.SEP_R_PAREN);
		return new ActionASTCell(lhs);
	}

	/**
	 * Takes a {@NanoTokens} object and two {@link ASTCell} objects and returns
	 * the correct concrete {@link ASTCell} type by means of switch statement.
	 * @param op the operator that acts on each side of expression
	 * @param lhs the left side of the operation
	 * @param rhs the right side of the operation
	 * @return the specific {@link ASTCell}
	 */
	private ASTCell infixOpSel(NanoTokens op, ASTCell lhs, ASTCell rhs) {

		switch (op) {
		case OP_PLUS:
			return new PlusASTCell(lhs,rhs);
		case OP_MINUS:
			return new MinusASTCell(lhs,rhs);
		case OP_TIMES:
			return new MultASTCell(lhs,rhs);
		case OP_DIV:
			return new DivASTCell(lhs,rhs);
		case OP_AND:
			return new AndASTCell(lhs,rhs);
		case OP_OR:
			return new OrASTCell(lhs,rhs);
		case OP_GTE:
			return new GTEASTCell(lhs,rhs);
		case OP_GT:
			return new GTASTCell(lhs,rhs);
		case OP_LTE:
			return new LTEASTCell(lhs,rhs);
		case OP_LT:
			return new LTASTCell(lhs,rhs);
		case OP_EQ_COMPARE:
			return new EQASTCell(lhs,rhs);
		case OP_NEQ_COMPARE:
			return new NEQASTCell(lhs,rhs);
		}

		return rhs;
	}

	// helper sets of tokens.. defines all non-terminals that are made up of
	// only terminals
	EnumSet<NanoTokens> _tokens = EnumSet.allOf(NanoTokens.class);
	EnumSet<NanoTokens> _literals = EnumSet.of(NanoTokens.BOOLEAN,
			NanoTokens.NUMBER, NanoTokens.STRING);
	EnumSet<NanoTokens> _logicalOperators = EnumSet.of(NanoTokens.OP_AND,
			NanoTokens.OP_OR);
	EnumSet<NanoTokens> _equalityOperators = EnumSet.of(
			NanoTokens.OP_EQ_COMPARE, NanoTokens.OP_NEQ_COMPARE);
	EnumSet<NanoTokens> _relationalOperators = EnumSet.of(NanoTokens.OP_LT,
			NanoTokens.OP_LTE, NanoTokens.OP_GT, NanoTokens.OP_GTE);
	EnumSet<NanoTokens> _additionOperators = EnumSet.of(NanoTokens.OP_PLUS,
			NanoTokens.OP_MINUS);
	EnumSet<NanoTokens> _multiplicationOperators = EnumSet.of(
			NanoTokens.OP_TIMES, NanoTokens.OP_DIV);
	EnumSet<NanoTokens> _prefixOperators = EnumSet.of(NanoTokens.OP_NOT,
			NanoTokens.OP_MINUS, NanoTokens.OP_PLUS);
}
