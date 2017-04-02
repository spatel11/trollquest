package com.ai.ASTCells;

import com.ai.VM;
import com.ai.reference.ExecutionException;
import com.ai.reference.TypeException;

/**
 * used to represent a null ASTCell with all null properties
 * Uses Singleton design pattern since all nil cells are equivilent. 
 * @author Martin Tice
 *
 */
public class NilASTCell extends AbstractASTCell {
	
	private static final NilASTCell _nil= new NilASTCell();
	
	
	private NilASTCell(){
		
	}
    
    public static ASTCell getNil(){
    	return _nil;
    }
    
    public String print(){
    	return"nilCell";
    }
	public ASTCell execute(VM c) throws ExecutionException{
		//TODO
		return this;
	}

}
