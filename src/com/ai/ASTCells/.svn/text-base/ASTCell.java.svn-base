package com.ai.ASTCells;

import com.ai.VM;
import com.ai.reference.ExecutionException;
import com.ai.reference.TypeException;


/**
 * Represents a semantic meaning of a parsed NanoC syntax.
 * <p>Each concrete cell encapsulates the behavior of one type of java terminal</p>
 * <p>All of the *Val() methods will return a Double, String, or boolean depending on what the given cell has a value for.</p>
 * <p>If the given cell does not contain a value for the given *Val(), it will return null.</p>
 * @author Martin Tice
 *
 */
public interface ASTCell {
    /** If the given cell has a number value, it will be returned.
     *     If not, an exception will be thrown.*/
	public Double numVal() throws TypeException;
	/** If the given cell has a String value, it will be returned.
     *     If not, an exception will be thrown.*/
	public String stringVal() throws TypeException;
	/** If the given cell has a boolean value, it will be returned.
     *     If not, an exception will be thrown.*/
	public boolean boolVal() throws TypeException;
	/** A string that reveals what a given cell holds.*/
	public String print();
    /***
     * Every concrete object will have a unique instantiation of this method.
     * Used to encapsulate the meaning of a single java terminal or action.
     * @param c the object that holds the values for all internally created variables,
     *           Satisfies any requests to access any external data.
     * @return an ASTCell that holds the result of any execute() method.
     * @throws ExecutionException if errors occur during the execute process.
     */
	public ASTCell execute(VM c) throws ExecutionException;
}
