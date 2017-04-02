package com.ai.ASTCells;

import com.ai.NanoTokens;
import com.ai.VM;
import com.ai.reference.ExecutionException;
import com.ai.reference.Token;
import com.ai.reference.TypeException;

/**
 * Used to represent a Prefix value of a NUMBER cell. if the prefix value is a '!' character,
 * the {@link NOTASTCell} is used.
 * <p><b>Only cells with a non-null number value may use this cell.</b></p>
 * Returns the positive or negative value of a given number.
 * @author Martin Tice
 *
 */
public class PrefixASTCell extends AbstractASTCell {

	/** either a '+' or a '-'*/
	private Token<NanoTokens> _prefix;
	/**the result of a unaryExpression that has a non-null number value*/
	private ASTCell _unaryExpression;
	/**the result of {@link #_prefix}  on {@link #_unaryExpression}*/
	private Double _val;
	
	/**
	 * takes in a '+' or a '-' prefix, followed by an {@link ASTCell} with a valid non-null number value
	 * @param prefix '+' or '-'
	 * @param unaryExpression {@link ASTCell} must contain a non-null number value.
	 */
	public PrefixASTCell(Token<NanoTokens> prefix, ASTCell unaryExpression){
		_prefix = prefix;
		_unaryExpression = unaryExpression;
	}
	

	/**
	 * the result of the prefix expression on the given number value of {@link #_unaryExpression}
	 */
	@Override
	public Double numVal() throws TypeException {
		return _val;
	}

	/**
	 * @return ""+ {@link #_val}
	 */
	public String print() {
		 return ""+_val;
	}

	/**
	 * changes the number value of {@link #_unaryExpression} according to {@link #_prefix}
	 * and returns the {@link NumberASTCell} representing the result.
	 */
	public ASTCell execute(VM c) throws ExecutionException {
    
		try {
			if(_prefix.getType() == NanoTokens.OP_MINUS){
			_val = -(_unaryExpression.execute(c).numVal());
			System.out.println("prefixASTCell produced negative number : "+ _val);
			}
			else{_val = _unaryExpression.execute(c).numVal();}
			return new NumberASTCell(_val);
		} catch (TypeException e) {
			throw new ExecutionException("PrefixASTCell.execute with + or - prefix, a number must follow");
		}
	}

}
