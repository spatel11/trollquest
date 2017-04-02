package com.ai.ASTCells;

import com.ai.VM;
import com.ai.reference.ExecutionException;
import com.ai.reference.TypeException;

/**
 * uses "-" operator to manipulate input {@link NumberASTCell}s
 * <p><b>Operations only operate on cells with a non-null Number value.</b></p>
 * <p>Only has value of type "number"</p>
 * 
 * @author Martin Tice
 * 
 */
public class MinusASTCell extends AbstractASTCell {

	/**left hand side of add expression*/
	private ASTCell _lhs;
	/**right hand side of add expression*/
	private ASTCell _rhs;
	/**result of subtracting{@link #_rhs} from {@link #_lhs} */
	private double _numVal;
	
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
	 * Both input cells must have a non-null number value.
	 * @param lhs {@link ASTCell} representing left hand side of subtract expression.
	 * @param rhs {@link ASTCell} representing right hand side of subtract expression.
	 */
	public MinusASTCell(ASTCell lhs, ASTCell rhs){
		_lhs = lhs;
		_rhs = rhs;
	}

	/**
	 * Subtracts the number value of {@link #_rhs} from {@link #_lhs}
     * @return the {@link NumberASTCell} result of subtraction.
	 */
	public ASTCell execute(VM c) throws ExecutionException {
		if(DEBUG){System.out.println("MinusASTCell lhs: "+_lhs.print()+" rhs: "+ _rhs.print());}
		try {
			_numVal = getLeft().execute(c).numVal()
			- getRight().execute(c).numVal();
			
			return new NumberASTCell(_numVal);
		} catch (TypeException e) {
			System.out.println("MinusASTCell type misMatch");
			e.printStackTrace();
			throw new ExecutionException("MinusASTCell.execute found Type misMatch");
		}
	}

	@Override
	public Double numVal() throws TypeException {
		System.out.println("MinusASTCell numVal called "+ _numVal);
		return _numVal;
	}

	@Override
	public String print() {
		// TODO Auto-generated method stub
		return ""+_numVal;
	}


}
