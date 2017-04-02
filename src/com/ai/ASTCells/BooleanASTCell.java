package com.ai.ASTCells;

import com.ai.VM;
import com.ai.reference.ExecutionException;
import com.ai.reference.TypeException;

/**
 * A Singleton Class that Holds one instance of a FALSE {@link #BooleanASTCell(boolean)}
 * and one instance of a TRUE{@link #BooleanASTCell(boolean)}. A static {@link #getValue(boolean)}
 * method is called and returns the one and only instance of the above cells.
 * <p>Only has a value of type boolean</p>
 * 
 * @author Martin Tice
 *
 */
public class BooleanASTCell extends AbstractASTCell {

	private final static BooleanASTCell _false = new BooleanASTCell(false);
	private final static BooleanASTCell _true = new BooleanASTCell(true);
	private final boolean _data;
	//TODO singleton makes constructor private
	private BooleanASTCell(boolean b){
		
		_data = b;
	}
	//TODO print() method prints either true or false
	
	public static BooleanASTCell getTrue(){return _true;}
	public static BooleanASTCell getFalse(){return _false;}
	public static BooleanASTCell getValue(boolean value){
		if(DEBUG){System.out.println("boolean.getvalue ====== "+value);}
		if(value){return getTrue();}
		else{return getFalse();}
	}
	
	
	public boolean boolVal(){
		return _data;
	}
	
	public String print(){
		return ""+_data;
	}
	
    /**
     * returns an {@link ASTCell} 
     */
	public ASTCell execute(VM c) throws ExecutionException {
		if(DEBUG){System.out.println("BooleanASTCell execute");}
		return getValue(_data);
	}

}
