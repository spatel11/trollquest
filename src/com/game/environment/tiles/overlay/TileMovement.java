package com.game.environment.tiles.overlay;

import java.io.File;

import com.game.environment.tiles.Tile;

/**
 * A movement glyph.
 * 
 * @author Ian
 */
public class TileMovement extends TileOverlay {
	private static final long serialVersionUID = 2337905974548793913L;

	/**
	 * creates a new movement tile
	 * 
	 * @param x
	 *            the x location in the standard Cartesian plane
	 * @param y
	 *            the y location in the standard Cartesian plane
	 */
	public TileMovement(int x, int y) {
		super(x, y);
	}

	/** Returns the name of this tile's resource. */
	@Override
	public final String getFileName() {
		return "move[0-3].png";
	}

	/**
	 * Returns the subdirectory of ResourceLoader.DIRECTORY_IMAGES_TILES that
	 * that file is in.
	 */
	@Override
	protected final String getFileSubdirectory() {
		return "map_symbols" + File.separator + "move" + File.separator;
	}

	@Override
	public int getMovementCost() {
		return -1;
	}

	@Override
	public int[] getImageOffset() {
		return Tile.IMAGE_OFFSET_MOVE_ATTACK_CAST;
	}
}