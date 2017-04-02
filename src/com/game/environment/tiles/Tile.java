package com.game.environment.tiles;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import com.client.ResourceLoader;
import com.client.Graphics.Animation;
import com.client.Graphics.ParametersAnimation;
import com.client.Graphics.DrawableResource;
import com.client.Graphics.DrawableResourceAnimation;
import com.game.characters.Creature;
import com.game.characters.GameCharacter;
import com.game.environment.Directions;
import com.game.environment.tiles.overlay.TileArrowLD;
import com.game.environment.tiles.overlay.TileArrowLU;
import com.game.environment.tiles.overlay.TileArrowL_;
import com.game.environment.tiles.overlay.TileArrowRD;
import com.game.environment.tiles.overlay.TileArrowRU;
import com.game.environment.tiles.overlay.TileArrowR_;
import com.game.environment.tiles.overlay.TileArrow_D;
import com.game.environment.tiles.overlay.TileArrow_U;
import com.game.environment.tiles.overlay.TileAttack;
import com.game.environment.tiles.overlay.TileCast;
import com.game.environment.tiles.overlay.TileMovement;
import com.game.environment.tiles.overlay.TileOverlay;
import com.game.items.IllegalInventoryException;
import com.game.items.Loot;
import com.utilities.UtilityMath;
import com.utilities.TileRecord;

/**
 * @author TBworkstation
 */
public abstract class Tile implements Serializable {
	private static final long serialVersionUID = -6337478863080586834L;

	  /**
   * Creates a new tile with the specified rel_x and rel_y coordinates in a
   * standard Cartesian plane coordinate system
   * 
   * @param x
   *          the rel_x coordinate
   * @param y
   *          the rel_y coordinate
   */
	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns a loot object that was on the cell currently or null if there was
	 * no loot on the cell
	 * 
	 * @return Loot object that was on the cell or null if there was nothing
	 */
	public Loot pickupLoot() {
		Loot temp = loot;
		loot = null;
		return temp;
	}
	
	/** Returns whether the tile has a loot object on it. */
	public final boolean hasLoot() {
		return (loot!=null);
	}

	/**
	 * Puts the specified Loot on the cell. If there was loot there already, it
	 * merges the two Loot object together
	 * 
	 * @param l
	 *          the Loot that will be put on the cell
	 * @throws IllegalInventoryException
	 *           if the merging of inventories failed
	 */
	public void putLoot(Loot l) throws IllegalInventoryException {
		if (loot == null) {
			loot = l;
		}
		else {
			loot.merge(l);
		}
	}

	/**
	 * Returns a reference to the current occupant of the cell. Will return null
	 * if there is no occupant occupying the cell.
	 * 
	 * @return Creature reference that currently on the cell. Will return null if
	 *         no creature is occupying the cell
	 */
	public GameCharacter getOccupant() {
		return occupant;
	}
	/**
	 * Sets the occupant of the cell. If a creature is already there this method
	 * will throw an {@link IllegalArgumentException} as this method should not be
	 * called if there is an occupant already on the cell.
	 * 
	 * @param c
	 *          the creature that will be put on the cell
	 * @throws IllegalArgumentException
	 *           if there is an occupant on the cell already
	 */
	public void setOccupant(GameCharacter c) throws TileException {
		if (occupant!=null) {
			throw new TileException("GameCharacter already on that tile");
		}
		else {
			c.setPosition(new Point(this.x, this.y));
			occupant = c;
		}
	}
	/**
	 * Polls the {@link Creature} occupant on the cell. Then sets the occupant to
	 * null indicating there is no occupant on the cell
	 * 
	 * @return {@link Creature} that occupied the cell up until this method was
	 *         called
	 */
	public GameCharacter removeOccupant() {
		GameCharacter temp = occupant;
		occupant = null;
		return temp;
	}

	protected abstract String getFileName();
	protected abstract String getFileSubdirectory();

	/**
	 * Returns a {@link DrawableResource} used to draw the tile on the screen
	 * 
	 * @return a {@link DrawableResource} that represents the tile
	 */
	public DrawableResource getResource() {
		Animation result = null;
		String dir = ResourceLoader.DIRECTORY_IMAGES_TILES+getFileSubdirectory();
		try {
			result = Animation.getInstance(
					new ParametersAnimation(
							dir,
							getFileName(),
							1.0f,-1,false
					)
			);
		} catch (IOException e) {
			ResourceLoader.showError(
					"Could not find the image(s):\n"+
					"\t\""+getFileName()+"\"\n"+
					"\n"+
					"In directory:\n"+
					"\t\""+dir+"\"!"
			);
		}
		return new DrawableResourceAnimation(result);
	}

	/**
	 * Returns the position of tile in its relative x, y coordinate
	 * 
	 * @return a point position encapsulating the x, y coordinate of the tile in
	 * the standard Cartesian plane
	 */
	public Point getPosition() {
		return new Point(x, y);
	}

	  /**
   * Returns the {@link Directions} of an adjacent tile as the enum itself
   * 
   * @param delta
   *          the relative location of the adjacent target tile in respect to
   *          the source tile
   * @return a {@link Directions} that represents in what direction the
   *         destination target tile is
   */
	public static Directions getDirection(Point delta) {
		for (Directions d : Directions.class.getEnumConstants()) {
			if (d.map_delta.equals(delta)) { return d; }
		}
		return null;
	}
	
	/**
	 * Returns the Manhattan Distance between two tiles
	 * 
	 * @param t1 the source tile
	 * @param t2 the destination tile
	 * @return an Point with the differences between the x coordinates and the 
	 * differences between the y coordinates
	 */
	public static Point getDelta(Tile t1, Tile t2) {
		Point p1 = t1.getPosition();
		Point p2 = t2.getPosition();
		return UtilityMath.getDelta(p1,p2);
	}
	
	/**
	 * Returns the average cost to move from one tile to the other
	 * @param t1 the source tile
	 * @param t2 the destination tile
	 * @return a double value representing the average between the two tile 
	 * connections
	 */
	public static double getCost(Tile t1, Tile t2) {
		return TileRecord.getConnection(t1.tile_record,t2.tile_record).cost;
	}
	
	/**
	 * Creates a {@link TileRecord} between the two tiles, effectively 'linking'
	 * them together for traversal through Dijkstra's algorithm
	 * 
	 * @param t1 the source tile
	 * @param t2 the target tile
	 */
	public static void linkTiles(Tile t1, Tile t2) {
		if (t1==null) return;
		if (t2==null) return;
		TileConnection new_connection = new TileConnection(t1,t2);
		t1.tile_record.connections.add(new_connection);
		t2.tile_record.connections.add(new_connection);
	}

	/**
	 * Returns the integer movement cost for the specific cell type
	 * @return integer movement cost for the cell type
	 */
	public abstract int getMovementCost();

	/**
	 * Returns an integer array image offset, with two values {x, y} offset values
	 * @return an integer array consisting of the x, y values of the image offsets
	 */
	public int[] getImageOffset() {
		return Tile.IMAGE_OFFSET_TYPICAL;
	}

	// public abstract int[] statModifyers();

	/* ============================ PRIVATE PARTS =========================== */
	private GameCharacter    occupant;
	private Loot             loot;
	protected int            x;
	protected int            y;

	//For images
	protected static int[] IMAGE_OFFSET_TYPICAL = {-32,-15};
	protected static int[] IMAGE_OFFSET_LR = {-53,-15};
	protected static int[] IMAGE_OFFSET_UD = {-32,-26};
	protected static int[] IMAGE_OFFSET_MOVE_ATTACK_CAST = {-32,-32};

	//For Dijkstra's algorithm.
	/** A tile record for traversal for Dijkstra's alrogithm */
	public final TileRecord tile_record = new TileRecord(this);

	//For overlay
	/** A left-down arrow overlay */
	public final static TileOverlay tile_arrow_ld = new TileArrowLD(0,0);
	/** a left-up arrow overlay */
	public final static TileOverlay tile_arrow_lu = new TileArrowLU(0,0);
	/** a right-down arrow overlay */
	public final static TileOverlay tile_arrow_rd = new TileArrowRD(0,0);
	/** a right-up arrow overlay */
	public final static TileOverlay tile_arrow_ru = new TileArrowRU(0,0);

	/** a left arrow overlay */
	public final static TileOverlay tile_arrow_l_ = new TileArrowL_(0,0);
	/** a right arrow overlay */
	public final static TileOverlay tile_arrow_r_ = new TileArrowR_(0,0);
	/** a down arrow overlay */
	public final static TileOverlay tile_arrow__d = new TileArrow_D(0,0);
	/** an up arrow overlay */
	public final static TileOverlay tile_arrow__u = new TileArrow_U(0,0);
	
	/** a movement overlay */
	public final static TileOverlay tile_movement = new TileMovement(0,0);
	/** a movement overlay */
	public final static TileOverlay tile_attack = new TileAttack(0,0);
	/** a movement overlay */
	public final static TileOverlay tile_cast = new TileCast(0,0);
	
	//Misc.
	/** A loot resource. */
	public final static DrawableResource resource_loot = ResourceLoader.loadImageResource(
			ResourceLoader.DIRECTORY_IMAGES_TILES+"map_symbols"+File.separatorChar+"chest"+File.separatorChar+"ld0000.png",
			0.5f
	);
}
