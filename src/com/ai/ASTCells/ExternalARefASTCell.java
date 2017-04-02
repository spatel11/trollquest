package com.ai.ASTCells;

import com.ai.VM;
import com.ai.reference.ExecutionException;
import com.ai.reference.TypeException;

/**
 * Used to represent and access data from an outside source. All in the form of
 * letters'.'lettersAndNumbers'['Expression']'
 * <p>Will access this information in the VM differently than the {@link InternalSymbolASTCell}
 * by accessing a different data type, as well as different manipulation levels of access. </p>
 * 
 * <p>At this point the idea is to make all data of this type read-only</p>
 * @author Martin Tice
 *
 */
public class ExternalARefASTCell extends AbstractASTCell {
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
	public ExternalARefASTCell(ASTCell head, ASTCell index){
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
	 * Accesses Game State data
	 */
	public ASTCell execute(VM c) throws ExecutionException {
		
		return null;
	}
	
	/**
	 * @return the ASTCell representing just the name of the cell.
	 */
     public ASTCell lValExecute(VM c) throws ExecutionException{
    	 return _head;
     }

}
