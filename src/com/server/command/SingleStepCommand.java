/**
 * @author Stephen Patel (spatel11@unm.edu)
 */
package com.server.command;

/**
 * Performs a single execution of the main game loop.
 * 
 * @author Stephen Patel
 * 
 */
public class SingleStepCommand extends ComparableCommand {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 2477301291287737614L;

	/**
	 * Creates a new SingleStepCommand.
	 */
	public SingleStepCommand() {
		super(null, 0, 0, 0, true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof SingleStepCommand))
			return false;
		return true;
	}

	/**
	 * This command is always valid.
	 */
	@Override
	public boolean isValid() {
		return true;
	}

	/**
	 * Performs one iteration of the main game loop.
	 */
	@Override
	public ComparableCommand execute() {
		state.singleStep();
		return null;
	}
}
