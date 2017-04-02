package com.ai.ASTCells;

import com.ai.VM;
import com.ai.reference.ExecutionException;
import com.ai.reference.TypeException;

public class OrASTCell extends AbstractASTCell {

	private ASTCell _lhs;
	private ASTCell _rhs;
	private boolean _val;
	
	
//GETTERS AND SETTERS
	public ASTCell getLeft(){
		return _lhs;
	}
	public ASTCell getRight(){
		return _rhs;
	}//END GETTERS AND SETTERS
	

	public OrASTCell(ASTCell lhs, ASTCell rhs){
		_lhs = lhs;
		_rhs = rhs;
	}


	@Override
	public boolean boolVal() throws TypeException {
		if(DEBUG){System.out.println("AndASTCell.boolval");}
		return _val;
	}

	public ASTCell execute(VM c) throws ExecutionException{
		if(DEBUG){System.out.println("ANDASTCell execute lhs: "+_lhs.print()+" rhs: "+ _rhs.print());}

		try {
			_lhs.execute(c);
			_rhs.execute(c);
			 _val = getLeft().execute(c).boolVal() || getRight().execute(c).boolVal();
			return  BooleanASTCell.getValue(_val);
		} catch (TypeException e) {
			System.out.println("AndASTCell.execute() type mismatch");
			e.printStackTrace();
			throw new ExecutionException();
		}
	}
	@Override
	public String print() {
		return "1"+_val;
	}

}
