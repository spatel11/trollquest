package com.ai.ASTCells;

import com.ai.VM;
import com.ai.reference.ExecutionException;
import com.ai.reference.TypeException;

/**
 * Used to keep track of variables created during execution of a root syntax tree.
 * <p>When a variable is created, it is set inside {@link VM#_internals} </p>
 * <p>Internal symbols are differentiated from External symbols by the existence of a '.' character.</p>
 * <p>Internal symbols must be of <code>Identifier</code> type.</p>
 * @author Martin Tice
 *
 */
public class InternalSymbolASTCell extends AbstractASTCell {

	/**The name of the variable*/
	private String _name;
	
	/** sets {@link #_name}*/
	public InternalSymbolASTCell(String s){
		_name = s;
	}
	/**
	 * AT this point set to {@link #_name}. In future will be set to whatever
	 * value its data represents.
	 */
	public String stringVal() throws TypeException {
		return _name;
	}

	/**
	 * returns the {@link ASTCell} associated with the given name.
	 */
	public ASTCell execute(VM c) throws ExecutionException {
		return c.getCell(_name);
	}

	/**
	 * @return name of variable.
	 */
	public String print() {
		// TODO Auto-generated method stub
		return "sym: "+_name;
	}

}
