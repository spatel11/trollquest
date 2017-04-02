package com.ai.ASTCells;

import com.ai.VM;
import com.ai.reference.ExecutionException;
import com.ai.reference.TypeException;

/**
 * Used to represent and access data from an outside source. All in the form of
 * letters'.'lettersAndNumbers
 * <p>Will access this information in the VM differently than the {@link InternalSymbolASTCell}
 * by accessing a different data type, as well as different manipulation levels of access. </p>
 * 
 * <p>At this point the idea is to make all data of this type read-only</p>
 * @author Martin Tice
 *
 */
public class ExternalSymbolASTCell extends AbstractASTCell {

	/**The name of extribute trying to be accessed.*/
	private String _val;
	
	/**
	 * Must be an <code>Identifier</code> terminal with the occurance of '.' in the middle.
	 * There will be a finite number of allowable strings of this type. To be finalized 
	 * at a later date.
	 * @param s
	 */
	public ExternalSymbolASTCell(String s){
		_val = s;
	}

    /**if requested data is a number, this method will return it
     * **To be completed at later date**
     *  */
	public Double numVal() throws TypeException {
		// TODO Auto-generated method stub
		return null;
	}

	 /**if requested data is a String, this method will return it
     * **To be completed at later date**
     *  */
	public String stringVal() throws TypeException {
		return _val;
	}

	 /**if requested data is a boolean, this method will return it
     * **To be completed at later date**
     *  */
	public boolean boolVal() throws TypeException {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Exact instantiation to be completed at a later date.
	 */
	public ASTCell execute(VM c) throws ExecutionException {
		if(DEBUG)System.out.println("EXTERNALSYMBOLexecute _val: "+_val);
		return c.getExternal(_val);
	}
	/**
	 * AT this stage only prints out {@link #_val}
	 */
	public String print() {
		
		return "ext="+_val;
	}

}
