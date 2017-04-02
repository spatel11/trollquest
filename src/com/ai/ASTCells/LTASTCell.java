package com.ai.ASTCells;

import com.ai.VM;
import com.ai.reference.ExecutionException;
import com.ai.reference.TypeException;

/**
 * uses "<" operator to compare input of {@link NumberASTCell}s
 * <p><b>Operations only on ASTCells with a non-null number type</b></p>
 * 
 * @return {@link BooleanASTCell} representing the outcome of comparison
 * 
 * @author Martin Tice
 * 
 */
public class LTASTCell extends AbstractASTCell {
	
	/**left hand side of comparison*/
	private ASTCell _lhs;
	/**right hand side of comparison*/
	private ASTCell _rhs;
	/**result of {@link #_lhs} < {@link #_rhs} */
	private boolean _val;
	
	/**
	 * Initialized left and right hand side
	 * @param lhs set to {@link #_lhs}
	 * @param rhs set to {@link #_rhs}
	 */
	public LTASTCell(ASTCell lhs, ASTCell rhs){
		_lhs = lhs;
		_rhs = rhs;
	}
	
	/**
     * @return {@link #_val} 
     */
	public boolean boolVal() throws TypeException {
		return _val;
	}

	/**
	 * Sets {@link #_val} to the result of {@link #_lhs} < {@link #_rhs}
	 */
	public ASTCell execute(VM c) throws ExecutionException {
		try {
			if(DEBUG){System.out.println("LTASTCELL.execute() lhs.numVal: "+ _lhs.execute(c).numVal()+" < "+_rhs.execute(c).numVal());}
			_val = _lhs.execute(c).numVal() < _rhs.execute(c).numVal();
		} catch (TypeException e) {
			throw new ExecutionException("LTASTCELL.execute() failed to get numberValues for both sides");
		}
		return BooleanASTCell.getValue(_val);
	}


	/**
	 * @return ""+{@link #_val}
	 */
	public String print() {
		
		return ""+_val;
	}


}
