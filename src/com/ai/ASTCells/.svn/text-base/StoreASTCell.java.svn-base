package com.ai.ASTCells;

import com.ai.VM;
import com.ai.reference.ExecutionException;
import com.ai.reference.Token;
import com.ai.reference.TypeException;

/**
 * This is the {@link ASTCell} responsible for creating an internal variable.
 * <p><b> {@link #_lhs} must only be of type {@link InternalSymbolASTCell} </b></p>
 * <p>Creates(if new) and stores the value of a variable inside {@link VM#_internals}</p>
 * <p><b>CANNOT ASSIGN ANY VALUE TO an{@link ExternalSymbolASTCell}</b></p>
 * uses operator "=" to assign input cells
 * 
 * @author Martin Tice
 *
 */
public class StoreASTCell extends AbstractASTCell {
	//private static final boolean DEBUG = true;
	/** variable to assign a value to*/
	private ASTCell _lhs;
	/**data to assign to variable*/
	private ASTCell _rhs;
	
	
    /**
     * Initializes variables.
     * @param lhs {@link InternalSymbolASTCell} to represent the variable name
     * @param rhs the data to assign the variable in the form of an {@link ASTCell}
     */
	public StoreASTCell(ASTCell lhs, ASTCell rhs){
		_lhs = lhs;
		_rhs = rhs;
	}


	/**
	 * Uses the passed in {@link VM} object to hold the variable and its data.
	 * If the variable is already in existance inside the VM, its value is replaced.
	 */
	public ASTCell execute(VM c) throws ExecutionException {
		if(DEBUG){System.out.println("StoreCellExecute lhs:  "+_lhs.print()+" rhs: "+ _rhs.print());}
		try {
			if(_lhs instanceof ExternalSymbolASTCell){
				System.out.println("externals cannot be assigned");
				throw new ExecutionException("cannont assign values to externals");}
			if(_rhs instanceof ExternalSymbolASTCell){
			if(DEBUG){System.out.println("STOREEXTERNALrhs "+_rhs.print());}//+"="+_rhs.execute(c).print());}	
				c.setInternal(_lhs.stringVal(), c.getExternal(_rhs.stringVal()));
			}
			if(c.containsVar(_rhs)){
				if(DEBUG){System.out.println("StoreASTCell found existence of variable : "+_lhs.stringVal());}
				c.setInternal(_lhs.stringVal(), c.getCell(_rhs.stringVal()));
				}
			else{c.setInternal(_lhs.stringVal(), _rhs.execute(c));}
		} catch (TypeException e) {
			System.out.println("StoreASTCell threw type exception");
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * Not finalized. 
	 * Should return the result of the boolean value of the data {@link ASTCell}
	 */
    public boolean boolVal() throws TypeException{
    	if(DEBUG){System.out.println("SToreASTCell.boolval");}
    	return _rhs.boolVal();
    }
    /**
	 * Not finalized. 
	 * Should return the result of the number value of the data {@link ASTCell}
	 */
	public Double numVal() throws TypeException {
		return _rhs.numVal();
	}
	/**
	 * Not finalized. 
	 * Should return the result of the String value of the data {@link ASTCell}
	 */
	public String stringVal() throws TypeException {
		return _rhs.stringVal();
	}
	/**
	 * Not finalized. 
	 * calls print on {@link #_lhs} and {@link #_rhs}.
	 */
	public String print(){
		return _lhs.print()+"="+_rhs.print();
	}

}
