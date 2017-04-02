package com.game.environment;

import java.awt.Point;

import com.client.Graphics.Animation;
import com.game.characters.GameCharacter;

/**
 * Enumerates the directions each {@link GameCharacter} can face
 * @author ian
 *
 */
public enum Directions {
	/** Left */
	L_("l_[0-9]+.png", "left", new Point(-1, 1), new Point(-64,  0)),
	/** right */
	R_("r_[0-9]+.png", "right", new Point( 1,-1), new Point( 64,  0)),
	/** Up */
	_U("_u[0-9]+.png", "up", new Point( 1, 1), new Point(  0, 31)),
	/** Down */
	_D("_d[0-9]+.png", "down", new Point(-1,-1), new Point(  0,-31)),
	/** LeftUp */
	LU("lu[0-9]+.png", "left-up", new Point( 0, 1), new Point(-32, 15)),
	/** LeftDown  */
	LD("ld[0-9]+.png", "left-down", new Point(-1, 0), new Point(-32,-15)),
	/** RightUp */
	RU("ru[0-9]+.png", "right-up", new Point( 1, 0), new Point( 32, 15)),
	/** RightDown */
	RD("rd[0-9]+.png", "right-down", new Point( 0,-1), new Point( 32,-15));
	
	private Directions(String regex, String name, Point map_delta, Point subpos_delta) {
		this.regex = regex;
		this.name = name;
		this.map_delta = map_delta;
		this.subpos_delta = subpos_delta;
	}
	@Override public final String toString() {
		return name;
	}
	
	/** The regex for the {@link Animation}'s direction prefix */
	public final String regex;
	/** The string representation of the direction. */
	public final String name;
	/** The new tile's map location change */
	public final Point map_delta;
	/** The characters sub-position change */
	public final Point subpos_delta;
}
