package com.ai.ASTCells;


import com.ai.VM;
import com.ai.reference.ExecutionException;
import com.ai.reference.TypeException;

/**
 * Used to represent an Array reference. Takes in an internal or external symbol{@link ASTCell},
 * along with some form of expression Cell to represent the index of the given symbol.
 * <p><b>{@link #_exp} must be either an internal or external symbol cell </b></p>
 * <p><b>{@link #_index} must have a non-null number value </b></p>
 * <p>Distinguishes between internal and external inputs to reference in {@link VM} data types.</p> 
 * @author Martin Tice
 *
 */
public class ARefASTCell extends AbstractASTCell{
    /**Name of array variable*/
	private final ASTCell _head;
	/**Index of array variable*/
	private final ASTCell _index;
	/**string with head and index to represent name*/
	private String _name;
	
	/**
	 * Constructor. Takes in an array variable name and an Index to reference.
	 * @param head variable name. Either internal or external.
	 * @param index index. must have a non-null number value.
	 */
	public ARefASTCell(ASTCell head, ASTCell index){
		this._head = head;
		this._index = index;
		try {
			this._name = _head.stringVal()+"["+Double.toString(_index.numVal())+"]";
			if(DEBUG){System.out.println("ARef created name : "+ this._name);}
		} catch (TypeException e) {
			System.out.println("ARef failed to create a name with input");
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the name of this cell. 
	 * @return {@link #_name}
	 */
	public String stringVal() throws TypeException {
		
		return this._name;
	}

	/**
	 * Calls print() at {@link #_exp}[{@link #_index}]
	 */
	public String print() {
		return "aRef : "+_name;
	}

	/**
	 * @return the ASTCell at {@link #_exp}[{@link #_index}]
	 */
	public ASTCell execute(VM c) throws ExecutionException {
		
		return new InternalSymbolASTCell(_name).execute(c);
	}
	
	/**
	 * @return the ASTCell representing just the name of the cell.
	 */
     public ASTCell lValExecute(VM c) throws ExecutionException{
    	 return _head;
     }
}
