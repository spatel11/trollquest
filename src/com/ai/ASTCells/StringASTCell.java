package com.ai.ASTCells;

import com.ai.VM;
import com.ai.reference.TypeException;

/**
 * A cell that holds a String value only.
 * @author Martin Tice
 *
 */
public class StringASTCell extends AbstractASTCell{

	/**The string the cell holds.*/
	private String _value;
	
	/**
	 * Creates a new instance with a String for the new instance to hold.
	 * @param s
	 */
	public StringASTCell(String s){
		_value = s;
	}
	/**
	 * Returns itself.
	 */
	public ASTCell execute(VM c) {
           return this;
	}
	/**
	 * @return {@link #_value} held in the specific instance.
	 */
	public String stringVal() throws TypeException {
		return _value;
	}
	
	/**
	 * @return {@link #_value} held in the specific instance.
	 */
    public String print(){
    	return _value;
    }
	

}
