/**
 * @author Stephen Patel (spatel11@unm.edu)
 */
package com.server.command;

import javax.swing.JOptionPane;

/**
 * Used to signal to the Clients that the Game has ended. On execution, this
 * command generates a popup message stating that the game is over, and then
 * terminates the client.
 * 
 * @author Stephen Patel
 * 
 */
public class GameOverCommand extends ComparableCommand {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -6535670911427953352L;

	/**
	 * Creates a new GameOverComand. This command has no name prep time, execute
	 * time, or reset time.
	 */
	public GameOverCommand() {
		super(null, 0, 0, 0, true);
	}

	/**
	 * The GameOverCommand is always valid, because it can only be generated
	 * when the game is over.
	 */
	@Override
	public boolean isValid() {
		return true;
	}

	/**
	 * Generates a popup message to inform the user that the game is over, then
	 * calls System.exit(0).
	 */
	@Override
	public ComparableCommand execute() {
		JOptionPane.showMessageDialog(null, "Game Over", "The Game Has Ended.",
				JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);
		return null;
	}
}
