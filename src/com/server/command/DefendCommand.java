package com.server.command;

import java.util.concurrent.PriorityBlockingQueue;

import com.game.characters.Creature;
import com.game.characters.Creature.Stat;
import com.server.State;

/**
 * Allows a character to defend itself in combat... or if he wants to feel more
 * secure about him/her self
 * 
 * @author taylor
 * 
 */
public class DefendCommand extends ComparableCommand {

	/** DefendCommand's serial ID */
	private static final long serialVersionUID = 3523573447517840372L;

	/**
	 * Creates a new Defend command that can be scheduled in the server's
	 * {@link PriorityBlockingQueue}
	 * 
	 * @param creature
	 *            the {@link Creature} calling this defend command
	 */
	public DefendCommand(String creature) {
		super(creature, 0, 0, 0, false);

		this.creature = creature;
	}

	@Override
	public ComparableCommand execute() {

		final int buff = ((Creature) state.getCharacter(creature))
				.getBaseStat(Stat.DEFENSEVALUE);

		if (((Creature) state.getCharacter(creature))
				.getCurrStat(Stat.HEALTHPOINTS) > 0) {
			((Creature) state.getCharacter(creature)).changeStat(
					Stat.DEFENSEVALUE, buff);
		}

		// returns an anonymous inverse of the defend command.
		// TODO check if storing 'buff' works!
		return new ComparableCommand(null,
				((Creature) state.getCharacter(creature))
						.getCurrStat(Stat.SPEED) * 2, 0, 0, false) {

			/** De-execute command for a DefendCommand */
			private static final long serialVersionUID = 5937492773692428465L;

			@Override
			public boolean isValid() {
				if (((Creature) state.getCharacter(creature))
						.getCurrStat(Stat.HEALTHPOINTS) <= 0) {
					return false;
				}

				return true;
			}

			@Override
			public ComparableCommand execute() {

				if (((Creature) state.getCharacter(creature))
						.getCurrStat(Stat.HEALTHPOINTS) > 0) {
					((Creature) state.getCharacter(creature)).changeStat(
							Stat.DEFENSEVALUE, -buff);
				}
				return null;
			}

			@Override
			public boolean equals(Object o) {
				return super.equals(o);
			}
		};
	}

	@Override
	public boolean isValid() {
		if (((Creature) state.getCharacter(creature))
				.getCurrStat(Stat.HEALTHPOINTS) <= 0) {
			return false;
		}
		return true;
	}

	@Override
	public void setState(State state) {
		super.setState(state);
		this.executeTime = ((Creature) state.getCharacter(creature))
				.getCurrStat(Stat.SPEED) * 2;

	}

	/* ============================PRIVATE PARTS ============================ */
	private final String creature;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.server.command.ComparableCommand#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		return super.equals(o);
	}
}
