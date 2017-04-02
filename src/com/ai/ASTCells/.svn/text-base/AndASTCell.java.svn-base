package com.ai.ASTCells;

import com.ai.VM;
import com.ai.reference.ExecutionException;
import com.ai.reference.TypeException;

/**
 * Uses "&&" operator to manipulate {@link BooleanASTCell}s that have a valid boolean value.
 * <p><b>Operations only operate on cells with a valid boolean value.</b></p>
 * <p>This cell only has value of type "boolean"</p>
 * 
 * @author Martin Tice
 * 
 */
public class AndASTCell extends AbstractASTCell {

	/**left hand {@link BooleanASTCell}*/
	private ASTCell _lhs;
	/**right hand {@link BooleanASTCell}*/
	private ASTCell _rhs;
	/**result of {@link #_lhs} && {@link #_rhs} */
	private boolean _val;
	
	
//GETTERS AND SETTERS
	/** @return {@link #_lhs}*/
	public ASTCell getLeft(){
		return _lhs;
	}
	/**@return {@link #_rhs}*/
	public ASTCell getRight(){
		return _rhs;
	}//END GETTERS AND SETTERS
	

	/**
	 *
	 * Performs logical "&&" operation on two {@link BooleanASTCell}s.
	 * Both input cells must have a non-null number value.
	 * @param lhs {@link ASTCell} representing left hand side of add expression.
	 * @param rhs {@link ASTCell} representing right hand side of add expression.
	 *
	 * @param lhs {@link BooleanASTCell}
	 * @param rhs {@link BooleanASTCell}
	 */
	public AndASTCell(ASTCell lhs, ASTCell rhs){
		_lhs = lhs;
		_rhs = rhs;
	}


	/**
	 * Returns the value set in execution equal to {@link #_lhs} && {@link #_rhs}
	 *@return {@link #_val}
	 */
	public boolean boolVal() throws TypeException {
		if(DEBUG){System.out.println("AndASTCell.boolval");}
		return _val;
	}

	/**
	 * Calculates {@link #_lhs} && {@link #_rhs}, stores its value in {@link VM#_internals}, and sets the result in {@link #_val}
	 * 
	 */
	public ASTCell execute(VM c) throws ExecutionException{
		if(DEBUG){System.out.println("ANDASTCell execute lhs: "+_lhs.print()+" rhs: "+ _rhs.print());}

		try {
			_lhs.execute(c);
			_rhs.execute(c);
			 _val = getLeft().execute(c).boolVal() && getRight().execute(c).boolVal();
			return  BooleanASTCell.getValue(_val);
		} catch (TypeException e) {
			System.out.println("AndASTCell.execute() type mismatch");
			e.printStackTrace();
			throw new ExecutionException();
		}
	}
	/**
	 * returns "And" to signify cell type, and {@link #_val} to reveal boolean value.
	 * @return "And"+ {@link #_val} to signify the cell type and the boolean value
	 */
	public String print() {
		return "And"+_val;
	}

}
