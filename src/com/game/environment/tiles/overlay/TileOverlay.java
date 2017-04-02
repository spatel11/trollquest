/**
 * 
 */
package com.game.environment.tiles.overlay;

import java.awt.Point;

import com.game.environment.tiles.Tile;

/**
 * @author TBworkstation
 * 
 */
public abstract class TileOverlay extends Tile {

	/** TileOverlay serial ID */
	private static final long serialVersionUID = 19677398102433515L;

	/**
	 * Creates a new Tile Overlay with the specified position
	 * 
	 * @param x
	 *            the x location of the Tile Overlay
	 * @param y
	 *            the y location of the Tile Overlay
	 */
	public TileOverlay(int x, int y) {
		super(x, y);
	}

	/**
	 * sets the Overlay's position to the parameter's point
	 * 
	 * @param p
	 *            the new point of the overlay tile
	 */
	public void setPosition(Point p) {
		x = p.x;
		y = p.y;
	}

}
