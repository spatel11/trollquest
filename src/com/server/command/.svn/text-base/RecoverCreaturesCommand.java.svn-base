/**
 * @author Stephen Patel (spatel11@unm.edu)
 */
package com.server.command;

import com.game.characters.Creature;

/**
 * Calls the recovery method of every living {@link Creature} in the game.
 * 
 * @author Stephen Patel
 * 
 */
public class RecoverCreaturesCommand extends ComparableCommand {

	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = 2787157402674403130L;

	/**
	 * Creates a new RecoverCreaturesCommand.
	 */
	public RecoverCreaturesCommand() {
		super(null, 0, 0, 0, false);
	}

	/**
	 * The RecoverCreaturesCommand is always valid.
	 */
	@Override
	public boolean isValid() {
		return true;
	}

	/**
	 * Iterates over every living {@link Creature} and calls their recovery
	 * method.
	 */
	@Override
	public ComparableCommand execute() {
		synchronized (state) {
			for (Creature c : state.getLivingCreatures()) {
				c.recovery();
			}
		}
		return null;
	}
}
