package com.game.environment.tiles.overlay;

import java.io.File;

import com.client.Graphics.Animation;
import com.client.Graphics.DrawableResource;
import com.game.environment.tiles.Tile;

/**
 * An arrow overlay pointing in the direction of LEFT-DOWN
 * 
 * @author ian
 * 
 */
public class TileArrowLD extends TileArrow {
	private static final long serialVersionUID = -7087030884928850392L;

	private static Animation images = null;

	/**
	 * creates a new LEFT-DOWN tile
	 * 
	 * @param x
	 *            the x location in the standard Cartesian plane
	 * @param y
	 *            the y location in the standard Cartesian plane
	 */
	public TileArrowLD(int x, int y) {
		super(x, y);
		if (images == null)
			images = getColorImages();
	}

	/** Returns the name of this tile's resource. */
	@Override
	public final String getFileName() {
		return "tile_arrow_ld.png";
	}

	/**
	 * Returns the subdirectory of ResourceLoader.DIRECTORY_IMAGES_TILES that
	 * that file is in.
	 */
	@Override
	protected final String getFileSubdirectory() {
		return "map_symbols" + File.separator + "arrows" + File.separator;
	}

	@Override
	public int getMovementCost() {
		return -1;
	}

	@Override
	public int[] getImageOffset() {
		return Tile.IMAGE_OFFSET_TYPICAL;
	}

	@Override
	public DrawableResource getResource() {
		return super.getResource(images);
	}
}