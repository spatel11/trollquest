package com.ai.ASTCells;

import com.ai.VM;
import com.ai.reference.ExecutionException;
import com.ai.reference.TypeException;

/**
 * uses "/" operator to manipulate input {@link NumberASTCell}s
 * <p><b>Operations only operate on cells with a non-null Number value.</b></p>
 * <p>Only has value of type "number"</p>
 * 
 * @author Martin Tice
 * 
 */
public class DivASTCell extends AbstractASTCell {

	/**left hand side of add expression*/
	private ASTCell _lhs;
	/**right hand side of add expression*/
	private ASTCell _rhs;
	/**result of adding {@link #_lhs} and {@link #_rhs} */
	private double _numVal;

	/**
	 * Divides number values of two {@link ASTCell}.
	 * Both input cells must have a non-null number value.
	 * @param lhs {@link ASTCell} represents the dividend of division expression.
	 * @param rhs {@link ASTCell} represents the divisor of division expression.
	 */
	public DivASTCell(ASTCell lhs, ASTCell rhs) {
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
	 * divides the number value of {@link #_lhs} by the {@link #_rhs}. Saves that in the {@link #_numVal}
	 * 
	 */
	public ASTCell execute(VM c) throws ExecutionException {
		if(DEBUG){System.out.println("DivASTCell lhs: "+_lhs.print()+" rhs: "+ _rhs.print());}
		try {
			_numVal = getLeft().execute(c).numVal()
			/ getRight().execute(c).numVal();
			
			return new NumberASTCell(_numVal);
		} catch (TypeException e) {
			System.out.println("DivASTCell type misMatch");
			e.printStackTrace();
			throw new ExecutionException();
		}
	}

	@Override
	public Double numVal() throws TypeException {
		System.out.println("DivASTCell numVal called "+ _numVal);
		return _numVal;
	}

	@Override
	public String print() {
		 //_lhs.print()+" + "+ _rhs.print();
		return  ""+_numVal;
	}


}
