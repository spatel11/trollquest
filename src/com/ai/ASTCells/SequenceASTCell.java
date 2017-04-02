package com.ai.ASTCells;

import java.util.ArrayList;

import com.ai.VM;
import com.ai.reference.ExecutionException;
import com.ai.reference.TypeException;

/**
 * The type of {@link ASTCell} that holds the Master parsed Abstract Syntax tree.
 * Holds any type of Cell that needs to sequentially execute its components.
 * 
 * At this point, see no need to define any of the *Val() methods. May change in future.
 * 
 * @author Martin Tice
 *
 */
public class SequenceASTCell extends AbstractASTCell {

	/**first cell in sequence*/
	private ASTCell _lhs;
	/**next cell in sequence*/
	private ASTCell _next;
	/**sequence of ASTCells to execute*/
	private ArrayList<ASTCell> _sequence;
	
	/**
	 * 
	 * @param lhs first statement in sequence
	 * @param next subsequent statement in sequence
	 */
	public SequenceASTCell(ASTCell root){
		//if(DEBUG){System.out.println("SEQUENCEASTCell constructor lhs: "+ lhs.print()+" , "+next.print());}
	
		_sequence = new ArrayList<ASTCell>();
		_sequence.add(root);
	}

	public void add(ASTCell next){
		_sequence.add(next);
	}

	/**
	 * executes {@link #_lhs}, followed by all {@link ASTCell}s inside {@link #_next}
	 */
	public ASTCell execute(VM c) throws ExecutionException{
		//if(DEBUG){System.out.println("SEQUENCEAST.execute lhs:"+_lhs.print()+" ,rhs:"+_next.print());}
		for(ASTCell cell : _sequence){
			if(DEBUG){System.out.println("++++++++++++++++++++++++++++++++++++++++++++");}
			cell.execute(c);
		}
		return NilASTCell.getNil();
			
	}
	
	public ArrayList<ASTCell> getAction(VM c){
		if(DEBUG){System.out.println("Getting action parameters");}
		return _sequence;
	}
	
	public String print(){
		return "{Sequence}";
	}
}
