package com.game.characters;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.client.ResourceLoader;
import com.client.Dialog.JWorldDialogError;
import com.client.Graphics.AnimationsOfAnAction;
import com.client.Graphics.GraphicsAbstract;
import com.game.characters.GameCharacter.Gender;
import com.game.environment.Directions;

/**
 * Enumeration of all the character types. Used for selecting a character. Also
 * might be useful if you need a list of all the various types of characters.
 * 
 * Feel free to add on to this as concrete classes become available without
 * asking me. Additions INCLUDE monsters and NPCs; simply set the "playable"
 * flag in the constructor to false to indicate that the player may not choose
 * to be one.
 * 
 * @author Ian
 */
public enum GameCharacterTypes {
	FIGHTER(true, "Fighter", Fighter.class),
	MAGE   (true,    "Mage",    Mage.class),
	THIEF  (true,   "Thief",   Thief.class);

	/** Constructor for the enum type. */
	private GameCharacterTypes(boolean playable, String directory_which_character, Class<? extends Player> clazz) {
		this.playable = playable;
		representation = new AnimationsOfAnAction(
				ResourceLoader.DIRECTORY_IMAGES_CHARACTERS
						+ directory_which_character + File.separatorChar
						+ "stand" + File.separatorChar, 0.5f, -1, true);
		this.clazz = clazz;
	}

	/** Draws a representation of the character. */
	public final void drawRepresentation(int x, int y) {
		GraphicsAbstract.drawResource(representation.getResource(Directions.R_), 0,0,1.0f,1.0f, x,y,1.0f,1.0f);
	}

	/** Whether the character type is actually playable. */
	public final boolean playable;
	/** The animation representing the character. */
	private final AnimationsOfAnAction representation;
	/** The class of the Player for this enum constant. */
	private final Class<? extends Player> clazz;

	/**
	 * Creates a new Player with the specified Name and gender. This Players
	 * class will be whatever it's enum constant is.
	 * 
	 * @param name
	 *            the Name of the Player to create.
	 * @param g
	 *            the Gender of the Player to create.
	 * @return a new Player with the specified name and gender, of this class.
	 */
	public Player instantiate(String name, Gender g) {
		Player p = null;
		Constructor<? extends Player> con = null;
		try {
			con = clazz.getConstructor(String.class, Gender.class);
			p = con.newInstance(name, g);
		} catch (NoSuchMethodException e) {
			new JWorldDialogError(e.toString(), "Player Creation Error!", null);
		} catch (SecurityException e) {
			new JWorldDialogError(e.toString(), "Player Creation Error!", null);
		} catch (InstantiationException e) {
			new JWorldDialogError(e.toString(), "Player Creation Error!", null);
		} catch (IllegalAccessException e) {
			new JWorldDialogError(e.toString(), "Player Creation Error!", null);
		} catch (IllegalArgumentException e) {
			new JWorldDialogError(e.toString(), "Player Creation Error!", null);
		} catch (InvocationTargetException e) {
			new JWorldDialogError(e.toString(), "Player Creation Error!", null);
		}
		return p;
	}
}