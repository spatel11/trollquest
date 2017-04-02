package com.ai.ASTCells;

import java.util.ArrayList;

import com.ai.VM;
import com.ai.reference.ExecutionException;
import com.ai.reference.TypeException;

/**
 * Defines an <code>Action</code> nanoC non-terminal.
 * <p>Requirements:</p>
 * Each action is constructed with a SequenceASTCell consisting of only two String fields,
 * which represent a command and a target respectively. These will be assigned to {@link #_target}, and {@link #_command}.
 * 
 * <p>
 * The {@link #execute(VM)} calls {@link VM#parseAction(String, String)} and sends in
 * the {@link #_command} and {@link #_target} fields.
 * </p>
 * @author Martin Tice
 *
 */
public class ActionASTCell extends AbstractASTCell {

	/** of type {@link SequenceASTCell} in case several actions are needed*/
	private SequenceASTCell _actionStatements;
	/**the target of the action cell*/
	private String _target;
	/**the decided action*/
	private String _command;
	/**an {@link ArrayList} of {@link ASTCell}s that make up {@link ComparableCommand} parameters*/
	private ArrayList<ASTCell> _parameters;
	
	/**
	 * Takes a {@link SequenceASTCell} that MUST have only two {@link StringASTCell}s.
	 * The first {@link StringASTCell} MUST specify a command, and the second MUST specify a target.
	 * 
	 * @param actions a {@SequenceASTCell} containg a command and a target string.
	 */
	public ActionASTCell(SequenceASTCell actions){
		_actionStatements = actions;
	}

	/**
	 * prints "ACTION==" + {@link #_actionStatements#print()}
	 */
	@Override
	public String print() {
		return "ACTION=="+_actionStatements.print();
	}

	/**
	 * Sets {@link #_target} and {@link #_command} from the string values inside {@link #_actionStatements}.
	 * Calls {@link VM#parseAction(String, String)} with {@link #_command} and {@link #_target} respectively.
	 * 
	 */
	@Override
	public ASTCell execute(VM c) throws ExecutionException {
		if(DEBUG){System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAActionASTCell.execute() : "+ _actionStatements.print());}
		_parameters = _actionStatements.getAction(c);
		try {
			if(DEBUG)System.out.println("action return size"+_parameters.size());
			if(DEBUG)System.out.println(_parameters.get(0).execute(c).print());
			_command = _parameters.get(0).execute(c).stringVal().replaceAll("\"", "");
			_target = _parameters.get(1).execute(c).stringVal().replaceAll("\"", "");
			
			if(DEBUG){System.out.println("Action result: "+_target+", "+_command);}
			c.parseAction(_command, _target);
			
			
		} catch (TypeException e) {
			System.out.println("ActionASTCell.execute failed to retrieve all command parameters");
			e.printStackTrace();
		}
		return NilASTCell.getNil();
	}


}
