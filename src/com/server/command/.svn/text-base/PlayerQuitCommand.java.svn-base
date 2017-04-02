/**
 * @author Stephen Patel (spatel11@unm.edu)
 */
package com.server.command;

/**
 * @author Stephen Patel
 * 
 */
public class PlayerQuitCommand extends ComparableCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8446150267823696041L;

	/**
	 * @param charName
	 * @param prepTime
	 * @param executeTime
	 * @param resetTime
	 * @param success
	 */
	public PlayerQuitCommand(String charName, String userName) {
		super(charName, 0, 0, 0, true);
		this.userName = userName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.server.command.ComparableCommand#isValid()
	 */
	@Override
	public boolean isValid() {
		if (state == null)
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PlayerQuitCommand [");
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.server.command.ComparableCommand#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		return super.equals(o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.server.command.ComparableCommand#execute()
	 */
	@Override
	public ComparableCommand execute() {
		synchronized (state) {
			if (userName != null) {
				state.removeClient(userName);
			}
			state.removeCharacter(charName);
		}
		return null;
	}
}
