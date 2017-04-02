/**
 * 
 */
package com.game.environment.tiles.terrain;

import java.io.File;

import com.game.environment.tiles.Tile;

/**
 * @author TBworkstation
 */
public class TileForest extends Tile {
  private static final long serialVersionUID= -4938884121765132708L;

  /**
   * Creates a new TileForest tile with the specified position x, y in the
   * cartesian plane coordinate system
   * 
   * @param x
   *          the x coordinate
   * @param y
   *          the y coordinate
   */
  public TileForest(int x, int y) {
    super(x, y);
  }

  /** Returns the name of this tile's resource. */
  @Override
  public final String getFileName() {
    return "tile[0-9].png";
  }

  /**
   * Returns the subdirectory of ResourceLoader.DIRECTORY_IMAGES_TILES that that
   * file is in.
   */
  @Override
  protected final String getFileSubdirectory() {
    return "forest" + File.separator;
  }

  @Override
  public int getMovementCost() {
    return MOVEMENT_COST;
  }

  @Override
  public String toString() {
    return "F";
  }

  /* ====================== PRIVATE MEMBERS ========================== */

  /**
   * Movement cost for the cell
   */
  private static final int MOVEMENT_COST= 1500;
}
