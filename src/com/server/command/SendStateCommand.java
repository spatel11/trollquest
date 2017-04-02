/**
 * @author Stephen Patel (spatel11@unm.edu)
 */
package com.server.command;

import com.server.State;

/**
 * @author Stephen Patel
 *
 */
public class SendStateCommand extends ComparableCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6981661077758465772L;
	private final State newState;

	/**
	 * @param prepTime
	 * @param executeTime
	 * @param resetTime
	 */
	public SendStateCommand(State s) {
		super(null, 0, 0, 0, true);
		this.newState = s;
		this.setSuccess(true);
	}

	/* (non-Javadoc)
	 * @see com.server.command.ComparableCommand#isValid()
	 */
	@Override
	public boolean isValid() {
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SendStateCommand [");
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

	/* (non-Javadoc)
	 * @see com.server.command.ComparableCommand#execute()
	 */
	@Override
	public ComparableCommand execute() {
		synchronized(state){
			State.copy(state, newState);
		}
		return null;
	}

	@Override
	public boolean equals(Object o) {
		return super.equals(o);
	}
}
