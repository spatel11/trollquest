package com.ai.ASTCells;

import com.ai.VM;
import com.ai.reference.ExecutionException;
import com.ai.reference.TypeException;

/**
 * <p>Node in the {@link #AST} structure representing a while statement.</p>
 * <p>Holds a statement ASTCell for the condition to enter a block of code.</p>
 * <p>Holds a block ASTCell to execute if  the clause condition was met.</p>

 * 
 * @author Martin Tice
 * 
 */
public class WhileASTCell extends AbstractASTCell{
	
	/**condition to enter {@link #_clause}*/
	private ASTCell _cond;
	/**block of code to execue while {@link #_cond} is true*/
	private ASTCell _clause;
	
	
//------------------------------------------------------------------CONSTRUCTOR
	/**
	 * 
	 * @param whileCond condition to enter whileClause
	 * @param whileClause block of code to run while whileCond is true
	 */
	public WhileASTCell(ASTCell whileCond, ASTCell whileClause){
		_cond = whileCond;
		_clause = whileClause;
	}//END CONSTRUCTOR

	/**
	 * prints the result of the conditional clause at beginning.
	 */
	public String print() {
		return _cond.print();
	}


	/**
	 * Executes the contents of {@link #_clause} if {@link #_cond} returns true.
	 * 
	 */
	public ASTCell execute(VM c) throws ExecutionException {
		try {
			//boolean b = _cond.execute(c).boolVal();
			if(DEBUG){System.out.println("WHILECELL.execute: "+_cond.print());}
			while(_cond.execute(c).boolVal()){
				if(DEBUG){System.out.println("WHILECELL.conditionHolds: "+_cond.print());}
				_clause.execute(c);
			}
			return NilASTCell.getNil();
		} catch (TypeException e) {
			throw new ExecutionException("WHILECELL.execute threw type exception");
		}
	}

}
