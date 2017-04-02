package com.ai.ASTCells;

import com.ai.VM;
import com.ai.reference.ExecutionException;
import com.ai.reference.TypeException;

/**
 * uses "+" or "-" operators to manipulate input cells
 * 
 * @author Martin Tice
 * 
 */
public class addASTCell extends AbstractASTCell {

	private ASTCell _lhs;
	private ASTCell _rhs;
	private double _numVal;

	public addASTCell(ASTCell lhs, ASTCell rhs) {
		_lhs = lhs;
		_rhs = rhs;
	}

	public ASTCell getLeft() {
		return _lhs;
	}

	public ASTCell getRight() {
		return _rhs;
	}

	@Override
	public ASTCell execute(VM c) throws ExecutionException {
		System.out.println("addASTCell lhs: "+_lhs.print()+" rhs: "+ _rhs.print());
		try {
			_numVal = getLeft().execute(c).numVal()
			+ getRight().execute(c).numVal();
			
			return new NumberASTCell(_numVal);
		} catch (TypeException e) {
			System.out.println("AddASTCell type misMatch");
			e.printStackTrace();
			throw new ExecutionException();
		}
	}

	@Override
	public Double numVal() throws TypeException {
		System.out.println("addASTCell numVal called "+ _numVal);
		// TODO Auto-generated method stub
		return _numVal;
	}

	@Override
	public String print() {
		 //_lhs.print()+" + "+ _rhs.print();
		return  ""+_numVal;
	}

}
