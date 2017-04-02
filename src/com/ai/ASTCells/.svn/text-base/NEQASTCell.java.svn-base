package com.ai.ASTCells;

import com.ai.VM;
import com.ai.reference.ExecutionException;
import com.ai.reference.TypeException;

/**
 * uses "==" operator to compare input of various {@link ASTCell}s
 * <p><b>Operations only operate on all ASTCell types</b></p>
 * <p>Uses overridden equals method in {@link AbstractASTCell}</p>
 * 
 * @author Martin Tice
 * 
 */
public class NEQASTCell extends AbstractASTCell {
	/**{@link ASTCell} to be compared with {@link #_rhs}*/
	private ASTCell _lhs;
	/**{@link ASTCell} to be compared with {@link #_lhs}*/
	private ASTCell _rhs;
	/**Boolean result of comparison*/
	private boolean _val;
	
	/**
	 * sets {@link #_lhs} and {@link #_rhs}
	 * @param lhs set to {@link #_lhs}
	 * @param rhs set to {@link #_rhs}
	 */
	public NEQASTCell(ASTCell lhs, ASTCell rhs){
		if(DEBUG){System.out.println("CreatingEQ with: "+lhs.print()+" "+rhs.print());}
		_lhs = lhs;
		_rhs = rhs;
	}

	/**
	 * returns the result of the "!=" comparison of two input cells.
	 * @return {@link #_val}
	 */
	public boolean boolVal() throws TypeException {
		return _val;
	}


	/**
	 * Uses the overridden equals() method in {@link AbstractASTCell} to compare two cells.
	 * sets {@link #_val} to opposite of result.S
	 * 
	 */
	public ASTCell execute(VM c) throws ExecutionException {
		if(DEBUG){System.out.println("NEQ.execute : "+_lhs.print()+" "+_rhs.print());}
		
		
		_val = !_lhs.execute(c).equals(_rhs.execute(c));
		if(DEBUG){System.out.println("NEQASTCell execute produce _val = "+ _val);}
		return BooleanASTCell.getValue(_val);
	}

	/**
	 * @return the string "eq" to signify this cell type, as well as the value of {@link #_val}.
	 */
	public String print() {
		return "eq: ("+_lhs.print()+"=="+_rhs.print()+") is: "+_val;
	}

}
