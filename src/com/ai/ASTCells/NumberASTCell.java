package com.ai.ASTCells;

import com.ai.VM;
import com.ai.reference.ExecutionException;
import com.ai.reference.TypeException;

/**
 * ASTCell type that holds a double value.
 * @author Martin Tice
 *
 */
public class NumberASTCell extends AbstractASTCell {

	/**double value this cell is a wrapper for*/
	private double _val;

	/**Takes in a double as input, and sets it to {@link #_val} */
	public NumberASTCell(double val) {
		_val = val;
	}

	/**
	 * @return {@link #_val}
	 */
	public Double numVal() throws TypeException {
		return _val;
	}

	/**
	 * @return an instance of itself
	 */
	public ASTCell execute(VM c) throws ExecutionException {
		return this;
	}
	/**
	 * @return a string containing only {@link @_val}
	 */
	public String print(){
		return ""+_val;
	}
	

}
