package com.ai.ASTCells;

import com.ai.VM;
import com.ai.reference.ExecutionException;
import com.ai.reference.TypeException;

public class EqualASTCell extends AbstractASTCell {

	private ASTCell _lhs;
	private ASTCell _rhs;
	
	private ASTCell getLeft(){
		return _lhs;
	}
	private ASTCell getRight(){
		return _rhs;
	}
	public EqualASTCell(ASTCell lhs, ASTCell rhs){
		_lhs = lhs;
		_rhs = rhs;
	}
	
	@Override
	public ASTCell execute(VM c) throws ExecutionException{
		
		//TODO apply to bool and numbers
		try {
			return  BooleanASTCell.getValue(getLeft().execute(c).numVal() == getRight().execute(c).numVal());
		} catch (TypeException e) {
			System.out.println("AndASTCell.execute() type mismatch");
			e.printStackTrace();
			throw new ExecutionException();
		}
        
	}

}
