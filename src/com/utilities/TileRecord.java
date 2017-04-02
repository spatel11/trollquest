package com.utilities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

import com.game.environment.tiles.Tile;
import com.game.environment.tiles.TileConnection;

/**
 * Encapsulates a tile record, for Dijkstra's Algorithm, mostly.
 * @author Ian
 */
public final class TileRecord implements Comparable<TileRecord>, Serializable {
	/** Required for some reason. */
	private static final long serialVersionUID = 2574806231916485344L;

	/** The tile corresponding to this tile record. */
	public final Tile tile;
	
	/** The distance this tile is, Dijkstra's-Algorithm-wise. */
	private int distance = Integer.MAX_VALUE;
	/** Parent of the tile record. */
	private TileRecord parent = null;
	
	/** Connections to this tile record. */
	public final Set<TileConnection> connections = new HashSet<TileConnection>();
	
	/** Constructor. */
	public TileRecord(Tile t) {
		tile = t;
	}
	
	/**
	 * Set the distance to this tile record, Dijkstra's-Algorithm-wise.
	 * @param new_distance the new distance.
	 * @param containing the priority queue that contains the tile record.  Needed
	 * because Java is retarded and its priority queue implementation is broken.  The
	 * hackish workaround it to remove the tile record and then reinsert it.
	 */
	public void setDistance(int new_distance, PriorityQueue<TileRecord> containing) {
		if (containing==null) {
			distance = new_distance;
		} else {
			containing.remove(this);
			distance = new_distance;
			containing.offer(this);
		}
	}
	/** Returns the distance to this tile record, Dijkstra's-Algorithm-wise. */
	public int getDistance() {
		return distance;
	}
	/**
	 * Sets the parent of this tile record, Dijkstra's-Algorithm-wise.
	 * @param new_parent the parent of the tile record.
	 */
	public void setParent(TileRecord new_parent) {
		parent = new_parent;
	}
	/**
	 * Returns the parent.
	 * @return the parent.
	 */
	public TileRecord getParent() {
		return parent;
	}
	
	/**
	 * Gets the connection between two tile records.
	 * @param tr1 the first tile record.
	 * @param tr2 the second tile record.
	 * @return the connection.
	 */
	public final static TileConnection getConnection(TileRecord tr1, TileRecord tr2) {
		//TODO: O(8).  Bleh.
		for (TileConnection tc : tr1.connections) {
			if (tc.getDual(tr1)==tr2) {
				return tc;
			}
		}
		throw new IllegalArgumentException("Tiles represented by these TileRecords are not adjacent!");
	}
	
	/** {@inheritDoc} */
	@Override public int compareTo(TileRecord obj) {
		return distance - obj.distance;
	}
}