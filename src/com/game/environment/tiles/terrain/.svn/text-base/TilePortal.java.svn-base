package com.game.environment.tiles.terrain;

import java.io.File;

import com.game.environment.tiles.Tile;

/**
 * @author TBWorkstation
 */
public class TilePortal extends Tile {
  private static final long serialVersionUID= -2091394923009498234L;

  /**
   * Creates a new portal tile with the specified position x, y in the Cartesian
   * plane coordinate system
   * 
   * @param x
   *          the x coordinate
   * @param y
   *          the y coordinate
   */
  public TilePortal(int x, int y, String portalDestination) {
    super(x, y);
    this.portalDestination= portalDestination;
  }

  /** Returns the name of this tile's resource. */
  @Override
  public final String getFileName() {
    return "tile[0-9]{2}.png";
  }

  /**
   * Returns the subdirectory of ResourceLoader.DIRECTORY_IMAGES_TILES that that
   * file is in.
   */
  @Override
  protected final String getFileSubdirectory() {
    return "portal" + File.separator;
  }

  @Override
  public int getMovementCost() {
    return MOVEMENT_COST;
  }

  /**
   * Returns the portal's destination map name
   * 
   * @return String representation of the destination map's name
   */
  public String getDestination() {
    return portalDestination;
  }

  @Override
  public String toString() {
    return "O" + portalDestination + ",";
  }

  /* ====================== PRIVATE MEMBERS ========================== */

  /**
   * Movement cost for the cell
   */
  private static final int MOVEMENT_COST= 500;

  /**
   * The portal's map destination
   */
  private final String portalDestination;
}
