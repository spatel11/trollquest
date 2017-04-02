/**
 * 
 */
package com.game.environment.tiles.overlay;

import java.io.File;

/**
 * A tile for highlights.
 * 
 * @author Ian
 */
public class TileHighlight extends TileOverlay {
	private static final long serialVersionUID = -5989409292069955137L;

	/**
	 * Creates a new TileHighlight tile with the specified position rel_x,rel_y
	 * in the cartesian plane coordinate system
	 * 
	 * @param x the relative x coordinate
	 * @param y the relative y coordinate
	 */
	public TileHighlight(int x, int y) {
		super(x, y);
	}

	@Override
	public int getMovementCost() {
		return MOVEMENT_COST;
	}

	/** Returns the name of this tile's resource. */
	@Override
	public final String getFileName() {
		return "tile_highlight.png";
	}

	/**
	 * Returns the subdirectory of ResourceLoader.DIRECTORY_IMAGES_TILES that
	 * that file is in.
	 */
	@Override
	protected final String getFileSubdirectory() {
		return "map_symbols" + File.separator;
	}

	/* ====================== PRIVATE MEMBERS ========================== */

	/**
	 * Movement cost for the cell
	 */
	private static final int MOVEMENT_COST = -1;
}
