/**
 * @author Stephen Patel (spatel11@unm.edu)
 */
package com.server.command;

import com.server.factory.classes.Profile;
import com.server.interfaces.Listener;

/**
 * Used to send a username to the server.
 * 
 * @author Stephen Patel
 * 
 */
public class UserDataCommand extends ComparableCommand {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 7048880169446458579L;

	/**
	 * Creates a new UserDataCommand to send the input username.
	 * 
	 * @param charName
	 */
	public UserDataCommand(String userName) {
		super(userName, 0, 0, 0, true);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserDataCommand [");
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValid() {
		return !state.containsUser(charName);
	}

	/**
	 * Sends the {@link Profile} with the input username to the client.
	 */
	@Override
	public ComparableCommand execute() {
		Profile p = state.getProfile(charName);
		Listener l = state.getClient(charName);
		l.getSender().addToCommandQueue(new SendStateCommand(state));
		l.getSender().addToCommandQueue(new SendProfileCommand(p));
		return null;
	}
}
