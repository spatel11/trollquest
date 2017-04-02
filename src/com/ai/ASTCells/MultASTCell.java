package com.ai.ASTCells;

import com.ai.VM;
import com.ai.reference.ExecutionException;
import com.ai.reference.TypeException;

public class MultASTCell extends AbstractASTCell {

	private ASTCell _lhs;
	private ASTCell _rhs;
	private double _numVal;

	public MultASTCell(ASTCell lhs, ASTCell rhs) {
		_lhs = lhs;
		_rhs = rhs;
	}
//GETTERS AND SETTERS
	public ASTCell getLeft() {
		return _lhs;
	}

	public ASTCell getRight() {
		return _rhs;
	}// END GETTERS AND SETTERS

	@Override
	public ASTCell execute(VM c) throws ExecutionException {
		if(DEBUG){System.out.println("MultASTCell lhs: "+_lhs.print()+" rhs: "+ _rhs.print());}
		try {
			_numVal = getLeft().execute(c).numVal()
			* getRight().execute(c).numVal();
			
			return new NumberASTCell(_numVal);
		} catch (TypeException e) {
			System.out.println("MultASTCell type misMatch");
			e.printStackTrace();
			throw new ExecutionException();
		}
	}

	@Override
	public Double numVal() throws TypeException {
		System.out.println("MultASTCell numVal called "+ _numVal);
		return _numVal;
	}

	@Override
	public String print() {
		 //_lhs.print()+" + "+ _rhs.print();
		return  ""+_numVal;
	}

}
