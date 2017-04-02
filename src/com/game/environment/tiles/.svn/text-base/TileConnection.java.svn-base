package com.game.environment.tiles;

import java.awt.Point;
import java.io.Serializable;

import com.game.environment.Directions;
import com.utilities.TileRecord;

/**
 * Represents a linking between two adjacent tiles, really only useful for 
 * dijkstra's algorithm
 * 
 * @author ian
 *
 */
public class TileConnection implements Serializable {
	private static final long serialVersionUID = 2030344830984877124L;

	/** the cost it takes to move between two tiles */
	public final int cost;
	
	/** the direction from tile 1 to tile 2 */
	public final Directions direction12;
	/** the direction from tile 2 to tile 1 */
	public final Directions direction21;
	
	/** the source tile*/
	public final Tile tile_1;
	/** the destination tile */
	public final Tile tile_2;
	
	/**
	 * Creates a new connection between tile 1 and tile 2
	 * 
	 * @param t1 the source tile
	 * @param t2 the destination tile
	 */
	public TileConnection(Tile t1, Tile t2) {
		tile_1 = t1;
		tile_2 = t2;
		
		Point delta = Tile.getDelta(t1,t2);
		double avg_cost = (t1.getMovementCost()+t2.getMovementCost())/2.0;
		cost = (int)Math.round(avg_cost*Math.sqrt(delta.x*delta.x+delta.y*delta.y));
		
		Point delta12 = Tile.getDelta(t1,t2); boolean found_delta12 = false;
		Point delta21 = Tile.getDelta(t2,t1); boolean found_delta21 = false;
		if ((direction12=Tile.getDirection(delta12))!=null) { found_delta12 = true; }
		if ((direction21=Tile.getDirection(delta21))!=null) { found_delta21 = true; }
		if (!found_delta12||!found_delta21) {
			throw new IllegalArgumentException("Could not link non-adjacent tiles!");
		}
	}
	
	/**
	 * returns the tile record for the tile connected to the parameter
	 * 
	 * @param tr the current tile record you have
	 * @return a tile record of the tile connected to the parameter
	 */
	public TileRecord getDual(TileRecord tr) {
		if      (tr==tile_1.tile_record) return tile_2.tile_record;
		else if (tr==tile_2.tile_record) return tile_1.tile_record;
		else throw new IllegalArgumentException("The given tile record is not a member of this connection!");
	}
	
	/**
	 * Returns the {@link Directions} to the tile connected to the parameter
	 * @param from {@link Tile} source
	 * @return the direction to the tile connected to the parameter
	 */
	public Directions getDirection(Tile from) {
		if      (from==tile_1) return direction12;
		else if (from==tile_2) return direction21;
		throw new IllegalArgumentException("Tile not in connection!");
	}
}