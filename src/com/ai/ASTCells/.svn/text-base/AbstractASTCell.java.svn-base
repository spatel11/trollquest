package com.ai.ASTCells;

import com.ai.VM;
import com.ai.reference.ExecutionException;
import com.ai.reference.TypeException;

/**
 * Parent class of all concrete ASTCells. Handles all invalid *val() method calls.
 * If a given cell does not have a number, string, or boolean value, then they do not define
 * the respective method.
 * <p>
 * 
 * Also overrides the equals method in order to handle "==" comparisons
 * </p>
 * @author Martin Tice
 *
 */
public abstract class AbstractASTCell implements ASTCell {

	/**used to toggle print statements on and off*/
	protected static boolean DEBUG = false;

	/** If a cell has a boolean value, it defines its return, if not, this method throws a {@link TypeException}*/
	public Double numVal() throws TypeException {
		throw new TypeException("no number value found");
	}
	/** If a cell has a String value, it defines its return, if not, this method throws a {@link TypeException}*/
	public String stringVal() throws TypeException {
		throw new TypeException("No String Value found");
	}
	/** If a cell has a boolean value, it defines its return, if not, this method throws a {@link TypeException}*/
	public boolean boolVal() throws TypeException {
		throw new TypeException("Type of boolean not found.");
	}
    
	/**Used to print out the contents of a concrete cell. If a cell does not define a print method, this is called*/
	public String print() {
		return "no Print option available";
	}

	/**If a given cell does not define an execute method, this will throw an exception.*/
	public ASTCell execute(VM c) throws ExecutionException {
		throw new ExecutionException("ASTCell did not define an execute method");
	}
	/**if a given cell does not define this method, will throw exception*/
    public ASTCell lValExecute(VM c) throws ExecutionException{
    	throw new ExecutionException("ASTCell did not define a lValExecute() method");
    }
	/**
	 * Compares the class, number value, string value, and boolean value of the parameter cell
	 * and the calling cell.
	 * @return true if cells all share same values,
	 *         false if cells have different values.
	 */
	public boolean equals(Object o) {
		ASTCell _param = (ASTCell) o;
		if(DEBUG)System.out.println("equals->"+this.print()+"<->"+_param.print()+"<-");
		try {//try to match numVal()
				if((double)_param.numVal() == (double)this.numVal()){
					if(DEBUG)System.out.println("EQUALS numVal");
							return true;
					}
				return false;
		}catch(TypeException te){
				try{
					if(this.stringVal().equals(_param.stringVal())){
						if(DEBUG)System.out.println("EQUALS stringVal->"+_param.stringVal()+"<->"+this.stringVal()+"<-");
								return true;
						}
						return false;
					}catch(TypeException teex){
						try{
						if(_param.boolVal() == this.boolVal()){
							return true;
							}
						return false;
					}catch(TypeException boo){
						if(DEBUG)System.out.println("equals has failed you my child");
					}
	       }
		}
		return false;
	}
}
