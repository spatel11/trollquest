package com.server.command;

import com.client.Graphics.AnimationsOfACharacter.ActionType;
import com.game.characters.GameCharacter;
import com.game.environment.Directions;

/**
 * Encapsulates a command to change a character's state.
 * 
 * @author Ian
 */
public class CommandChangeState extends ComparableCommand {
	private static final long serialVersionUID = 5073724285853277286L;

	private final ActionType new_state;
	private final Directions new_direction;
	private final double vel_x, vel_y;

	/**
	 * Constructor. Takes the character to operate on and the requested new
	 * state.
	 * 
	 * @param character
	 * @param new_state
	 */
	public CommandChangeState(String char_name, ActionType new_state, Directions new_direction, double vel_x, double vel_y) {
		super(char_name, 0, 0, 0, false);
		this.new_state = new_state;
		this.new_direction = new_direction;
		this.vel_x = vel_x;
		this.vel_y = vel_y;
	}

	/**
	 * Execution command.
	 */
	@Override public ComparableCommand execute() {
		synchronized(state) {
			GameCharacter c = state.getCharacter(charName);
			c.setActionState(new_state);
			c.setDirection(new_direction);
			c.setSubX(0.0);
			c.setSubY(0.0);
			c.setSubXvel(vel_x);
			c.setSubYvel(vel_y);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CommandChangeState [");
		if (new_state != null) {
			builder.append("new_state=");
			builder.append(new_state);
			builder.append(", ");
		}
		if (new_direction != null) {
			builder.append("new_direction=");
			builder.append(new_direction);
			builder.append(", ");
		}
		builder.append("vel_x=");
		builder.append(vel_x);
		builder.append(", vel_y=");
		builder.append(vel_y);
		builder.append(", ");
		if (charName != null) {
			builder.append("charName=");
			builder.append(charName);
			builder.append(", ");
		}
		if (getClass() != null) {
			builder.append("getClass()=");
			builder.append(getClass());
		}
		builder.append("]");
		return builder.toString();
	}

	@Override public boolean isValid() {
		if(state.getCharacter(charName) == null)
			return false;
		return true;
	}

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
