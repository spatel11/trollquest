package com.ai.ASTCells;

import com.ai.VM;
import com.ai.reference.ExecutionException;
import com.ai.reference.TypeException;

/**
 * Used to hold the information of a Creature definition.
 * Each definition has a <code>Statement</code> that defines a 
 * String, Policy, and Finalizer.
 * @author Martin Tice
 *
 */
public class CreatureASTCell extends AbstractASTCell{

	/** String for first Cell*/
	private String _name;
	/** {@link ASTCell} for {@link #_stringLiteral} */
	private ASTCell _statement1;
	/** {@link ASTCell} for {@link #_policy} */
	private ASTCell _policy;
	/** {@link ASTCell} for {@link #_finalizer} */
	private ASTCell _finalizer;
	
	
	/**
	 * Constructor. Sest all private variables to the parameters passed in.
	 * @param stringLiteral {@link #_name}
	 * @param statement1 {@link #_statement1}
	 * @param policyStatement {@link #_policy}
	 * @param finalizerStatement {@link #_finalizer}
	 */
	public CreatureASTCell(String stringLiteral,ASTCell statement1,
            ASTCell policyStatement, ASTCell finalizerStatement){
		if(DEBUG){System.out.println("creatureConstructor "+ stringLiteral);}
		_name = stringLiteral.replaceAll("\"", "");
		_statement1 = statement1;
		_policy = policyStatement;
		_finalizer = finalizerStatement;
		
		
	}
	/**
	 * Right now set to {@link #_stringLiteral}. Possibly meaning name.
	 */
	public String stringVal() throws TypeException {
		return _name;
	}

	
	/**
	 * Unknown how to execute at moment. Possibly adding information to gameState.
	 */
	public ASTCell execute(VM c) throws ExecutionException {
		if(DEBUG){System.out.println("CreatureASTExecute: "+ _name);}
		try {
			c.setInternal(_name, new StringASTCell(_name));
			double hp = _statement1.execute(c).numVal();
			String policy = _policy.execute(c).stringVal();
			String finalizer = _finalizer.execute(c).stringVal();
			if(DEBUG){System.out.println("CreatureAST VALUES : "+ hp +","+policy+","+finalizer);}
			
			c.setInternal(_name+"Hp", new NumberASTCell(_statement1.execute(c).numVal()));
			c.setInternal(_name+"Spell", new StringASTCell(_policy.execute(c).stringVal()));
			c.setInternal(_name+"Die", new StringASTCell(_finalizer.execute(c).stringVal()));
		} catch (TypeException e) {
			System.out.println("CreatureASTCell.execute had type exception");
			e.printStackTrace();
		}
		
		return this;
	}
	
	@Override
	public String print() {
		
		return "CreatureCell: "+ _name;
	}

}
