/**
 * @author Stephen Patel (spatel11@unm.edu)
 */
package com.server.command;

import javax.swing.JOptionPane;

import com.game.characters.Player;
import com.server.interfaces.Server;

/**
 * Requests {@link Player} information from the User. This data is collected
 * through {@link JOptionPane}s, and is sent in a {@link PlayerDataCommand} to
 * the {@link Server}. If there exists a Player with the specified name, then
 * the {@link Server} will send another one of these commands. Otherwise, the
 * server will send the PlayerDataCommand to all the other Clients.
 * 
 * @author Stephen Patel
 * 
 */
public class RequestPlayerDataCommand extends ComparableCommand {

	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = 6722563652747065341L;

	/**
	 * Creates a new {@link RequestPlayerDataCommand}.
	 */
	public RequestPlayerDataCommand() {
		super(null, 0, 0, 0, true);
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
//		String title = "Choose your Meme";
//
//		Object[] classPossibilities = { "Mage", "Thief", "Fighter" };
//		Object[] genderPossibilities = { "Male", "Female", "It" };
//		Gender g = null;
//		String name = JOptionPane.showInputDialog(null, "Le character name:",
//				title, JOptionPane.QUESTION_MESSAGE);
//		String gender = (String) JOptionPane.showInputDialog(null,
//				"Le character gender:", title, JOptionPane.PLAIN_MESSAGE, null,
//				genderPossibilities, genderPossibilities[0]);
//		if (gender.equals("Male"))
//			g = Gender.MALE;
//		else if (gender.equals("Female"))
//			g = Gender.FEMALE;
//		else
//			g = Gender.IT;
//		String clas = (String) JOptionPane.showInputDialog(null,
//				"Le character class:", title, JOptionPane.PLAIN_MESSAGE, null,
//				classPossibilities, classPossibilities[0]);
//		int c = 0;
//		if (clas.equals("Mage"))
//			c = 0;
//		else if (clas.equals("Thief"))
//			c = 1;
//		else
//			c = 2;
//		return new PlayerDataCommand(name, null, g, c);
		return null;
	}
}
