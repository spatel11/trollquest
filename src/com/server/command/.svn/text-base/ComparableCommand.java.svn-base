/**
 * @author Stephen Patel (spatel11@unm.edu)
 */
package com.server.command;

import java.io.Serializable;

import com.server.State;

/**
 * This class is intended to be extended into every {@link ComparableCommand}
 * that can be scheduled. It implements the Comparable interface so that a
 * {@link PriorityBlockingQueue} may order the commands correctly.
 * ComparableCommands are compared by their prep times. This class is intended
 * to be sent across an {@link ObjectOutputStream} to the server, and then sent
 * back to all the clients upon successful execution. Each subclass MUST create
 * a new generated svUID. The setState method should be called before executing,
 * on both the client and the server side. The isValid method should be called
 * ONLY on the server side.
 * 
 * @author Stephen Patel
 * 
 */
public abstract class ComparableCommand implements
		Comparable<ComparableCommand>, Serializable {

	/**
	 * Generated UID for this class.
	 */
	private static final long serialVersionUID = -3198952868252439809L;
	/**
	 * The time it takes to prepare this {@link ComparableCommand}.
	 */
	protected int prepTime;
	/**
	 * The time it takes to execute this {@link ComparableCommand}.
	 */
	protected int executeTime;
	/**
	 * The time it takes to reset this {@link ComparableCommand}.
	 */
	protected int resetTime;
	/**
	 * The {@link State} this {@link ComparableCommand} uses to get it's
	 * resources.
	 */
	protected transient State state;
	/**
	 * The name of the GameCharacter using this command.
	 */
	protected final String charName;
	/**
	 * Indicates whether the command was successful upon return to the client
	 */
	private boolean commandSucceeded;
	/**
	 * The name of a user. Can be null.
	 */
	protected transient String userName;

	/**
	 * Constructs a new {@link ComparableCommand} with the input prep, execute,
	 * and reset times. Meant to be called by subclasses in addition to their
	 * own specific constructors.
	 * 
	 * @param charName
	 *            the name of the GameCharacter using this command.
	 * @param prepTime
	 *            the preparation time for this {@link ComparableCommand}.
	 * @param executeTime
	 *            the execution time for this {@link ComparableCommand}.
	 * @param resetTime
	 *            the time before this {@link ComparableCommand} can be executed
	 *            again.
	 * @param success
	 *            whether this command succeeded or not.
	 */
	protected ComparableCommand(String charName, int prepTime, int executeTime,
			int resetTime, boolean success) {
		this.prepTime = prepTime;
		this.executeTime = executeTime;
		this.resetTime = resetTime;
		this.charName = charName;
		this.commandSucceeded = success;
	}

	/**
	 * Get the name of the {@link GameCharacter} using this
	 * {@link ComparableCommand}.
	 * 
	 * @return
	 */
	public String getCharName() {
		return charName;
	}

	/**
	 * Determines whether this {@link ComparableCommand} is valid.
	 * 
	 * @return true if it is, false otherwise.
	 */
	public abstract boolean isValid();

	/**
	 * Set's the transient {@link State} reference this
	 * {@link ComparableCommand}. This is essentially a mini constructor. It
	 * MUST be executed before this Commands methods can be used.
	 * 
	 * @param state
	 *            the state this command will act on.
	 */
	public void setState(State state) {
		this.state = state;
	}

	/**
	 * Gets the prepTime for this {@link ComparableCommand}.
	 * 
	 * @return the prepTime
	 */
	public int getPrepTime() {
		return prepTime;
	}

	/**
	 * Updates the prepTime and executeTime of this {@link ComparableCommand} to
	 * correspond to the current time.
	 * 
	 * @param curTime
	 *            the current time.
	 */
	public void updateTime(int curTime) {
		this.prepTime += curTime;
	}

	/**
	 * Gets the executeTime for this {@link ComparableCommand}
	 * 
	 * @return the executeTime
	 */
	public int getExecuteTime() {
		return executeTime;
	}

	/**
	 * Get's the resetTime for this {@link CompparableCommand}
	 * 
	 * @return the resetTime
	 */
	public int getResetTime() {
		return resetTime;
	}

	/**
	 * Returns whether or not the command succeeded on the server
	 * 
	 * @return true if the command was executed, false otherwise
	 */
	public boolean getSuccess() {
		return commandSucceeded;
	}

	/**
	 * Sets whether or not the command succeeded when it executed
	 * 
	 * @param b
	 *            a boolean indicating if the command succeeded or not
	 */
	public void setSuccess(boolean b) {
		commandSucceeded = b;
	}

	/**
	 * Get's the name of the user who sent this command. Null if this is a
	 * monster command.
	 * 
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Set's the name of the user who sent this command. Should be called by the
	 * ServerListener that receives this command before it's scheduled.
	 * 
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Checks to see if two commands are equal. They are if the charNames are
	 * equal.
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (!o.getClass().isInstance(this))
			return false;
		ComparableCommand c = (ComparableCommand) o;
		if (!charName.equals(c.charName))
			return false;
		return true;
	}

	/**
	 * Compares this {@link ComparableCommand} with another. Returns -1 if this
	 * {@link ComparableCommand}s preptime is less than the input
	 * {@link ComparableCommand}s, 1 if it is greater, 0 if it is equal.
	 * 
	 * @return int representing the order of this {@link ComparableCommand} and
	 *         the input.
	 */
	@Override
	public int compareTo(ComparableCommand o) {
		if (prepTime < o.prepTime)
			return -1;
		if (prepTime > o.prepTime)
			return 1;
		return 0;
	}

	/**
	 * Executes this {@link ComparableCommand}. Returns a descheduling
	 * {@link ComparableCommand} if necessary, null otherwise.
	 * 
	 * @return a descheduling {@link ComparableCommand} or null.
	 */
	public abstract ComparableCommand execute();
}
