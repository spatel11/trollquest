package com.ai.ASTCells;

import com.ai.VM;
import com.ai.reference.ExecutionException;
import com.ai.reference.TypeException;

/**
 * Node in the {@link #AST} structure representing an if statement.
 * The only trinary node in the Syntax tree structure.
 * Holds a statement ASTCell for the condition to enter the thenClause,
 * a block ASTCell to execute if  the clause condition was met.
 * and a block to execute if there is an else statement. If there is no else statement,
 * This block contains the {@link NilASTCell}
 * 
 * @author Martin Tice
 * 
 */
public class IFASTCell extends AbstractASTCell {

	/** represents the if condition to enter {@link #_then} */
	private ASTCell _if;
	/** represents a block to execute if {@link #_if} was satisfied */
	private ASTCell _then;
	/** represents an optional else block to execute */
	private ASTCell _else;

	// -------------------------------------------------------CONSTRUCTOR
	/**
	 * 
	 * @param ifCond
	 *            condition to enter the thenClause
	 * @param thenClause
	 *            block to execute if condition met
	 * @param elseClause
	 *            block to execute if ifCond not met
	 */
	public IFASTCell(ASTCell ifCond, ASTCell thenClause, ASTCell elseClause) {
		if(DEBUG){System.out.println("IFASTCell constructor if: "+ifCond.print()+" then: "+thenClause.print()+" else: "+elseClause.print());}
		_if = ifCond;
		_then = thenClause;
		_else = elseClause;

	}// end CONSTRUCTOR

	
	/**
	 * prints the result of the conditional clause at beginning.
	 */
	public String print() {
		return "{ifAST= "+_if.print()+","+_then.print()+" ,"+_else.print()+"}";
	}

	/**
	 * Executes any statement inside {@link #_then} and {@link #_else}.
	 */
	public ASTCell execute(VM c) throws ExecutionException {
		
		//if(DEBUG){System.out.println("IFCELL.execute() if="+_if.execute(c).print()+"then:"+_then.execute(c).print()+ "else: "+ _else.print());}
		try {
			if(_if.execute(c).boolVal()){
				if(DEBUG){System.out.println("IFCell.execute() condition true.. returning _then.execute()");}
				return _then.execute(c);
			}else{ return _else.execute(c);}
		} catch (TypeException e) {
			throw new ExecutionException("IFASTCell.execute() threw Type exception");
		}
	}

}
