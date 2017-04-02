/**
 * @author Stephen Patel (spatel11@unm.edu)
 */
package com.server.command;

import com.server.factory.classes.Profile;

/**
 * Used to send a {@link Profile} to it's user.
 * 
 * @author Stephen Patel
 * 
 */
public class SendProfileCommand extends ComparableCommand {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -4463617736460083605L;
	/**
	 * The Profile to send.
	 */
	private final Profile profile;

	/**
	 * Creates a SendProfileCommand with the input Profile.
	 * 
	 * @param profile
	 *            the Profile to send.
	 */
	public SendProfileCommand(Profile profile) {
		super(profile.getUserName(), 0, 0, 0, true);
		this.profile = profile;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SendProfileCommand [");
		if (profile != null) {
			builder.append("profile=");
			builder.append(profile);
			builder.append(", ");
		}
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
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ComparableCommand execute() {
		return null;
	}

	/**
	 * Gets the profile contained in this command.
	 * 
	 * @return The contained profile.
	 */
	public Profile getProfile() {
		return profile;
	}
}
