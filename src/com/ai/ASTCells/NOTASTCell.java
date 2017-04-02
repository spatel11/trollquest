package com.ai.ASTCells;

import com.ai.VM;
import com.ai.reference.ExecutionException;
import com.ai.reference.TypeException;

public class NOTASTCell extends AbstractASTCell {
	private ASTCell _rhs;
	private boolean _val;
	private boolean _even;
	
	public NOTASTCell(boolean even, ASTCell rhs){
		if(DEBUG){System.out.println("NOTASTCell constructor _even : "+even+" rhs: "+ rhs.print());}
		_rhs = rhs;
		_even = even;
	}

	public boolean boolVal() throws TypeException {
		return _val;
	}


	public ASTCell execute(VM c) throws ExecutionException {
		
		try {
			if(!_even){
				if(DEBUG){System.out.println("NOTASTCell.executeOddNot "+ !_rhs.execute(c).boolVal());}
				_val = !_rhs.execute(c).boolVal();
				return BooleanASTCell.getValue(_val);
			}else{
				_val = _rhs.execute(c).boolVal();
				return BooleanASTCell.getValue(_val);
			}
		} catch (TypeException e) {
			System.out.println("NOTASTCell.execute threw type exception");
			throw new ExecutionException();
		}
	}

	public String print() {
		return ""+_val;
	}

}
