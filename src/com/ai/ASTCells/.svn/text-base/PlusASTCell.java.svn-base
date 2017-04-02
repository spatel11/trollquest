package com.ai.ASTCells;

import com.ai.VM;
import com.ai.reference.ExecutionException;
import com.ai.reference.TypeException;

/**
 * uses "+" operator to manipulate input {@link NumberASTCell}s
 * <p><b>Operations only operate on cells with a non-null Number value.</b></p>
 * <p>Only has value of type "number"</p>
 * 
 * @author Martin Tice
 * 
 */
public class PlusASTCell extends AbstractASTCell {
	/**left hand side of add expression*/
	private ASTCell _lhs;
	/**right hand side of add expression*/
	private ASTCell _rhs;
	/**result of adding {@link #_lhs} and {@link #_rhs} */
	private double _numVal;

	/**
	 * Both input cells must have a non-null number value.
	 * @param lhs {@link ASTCell} representing left hand side of add expression.
	 * @param rhs {@link ASTCell} representing right hand side of add expression.
	 */
	public PlusASTCell(ASTCell lhs, ASTCell rhs) {
		_lhs = lhs;
		_rhs = rhs;
		
	}
//GETTERS AND SETTERS
	/**@return {@link #_lhs} */
	public ASTCell getLeft() {
		return _lhs;
	}
	 /** @return {@link #_rhs} */
	public ASTCell getRight() {
		return _rhs;
	}// END GETTERS AND SETTERS

	/**
	 * Adds the number value of {@link #_lhs} and {@link #_rhs}. 
     * Saves that in the {@link #_numVal}.
	 * @return the {@link NumberASTCell} of the result.
	 */
	public ASTCell execute(VM c) throws ExecutionException {
		if(DEBUG){System.out.println("PlusASTCell lhs: "+_lhs.print()+" rhs: "+ _rhs.print());}
		try {
			_numVal = getLeft().execute(c).numVal()
			+ getRight().execute(c).numVal();
			
			return new NumberASTCell(_numVal);
		} catch (TypeException e) {
			System.out.println("PlusASTCell type misMatch");
			e.printStackTrace();
			throw new ExecutionException("PlusASTCell.execute found type misMatch");
		}
	}

	@Override
	public Double numVal() throws TypeException {
		if(DEBUG){System.out.println("PlusASTCell numVal called "+ _numVal);}
		return _numVal;
	}

	@Override
	public String print() {
		 //_lhs.print()+" + "+ _rhs.print();
		return  ""+_numVal;
	}

}
