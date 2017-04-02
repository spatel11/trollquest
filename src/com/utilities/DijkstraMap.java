package com.utilities;

import java.util.LinkedList;
import java.util.PriorityQueue;

import com.game.environment.GameMap;
import com.game.environment.tiles.Tile;
import com.game.environment.tiles.TileConnection;

/**
 * Class to encapsulate Dijkstra's algorithm on a map.
 * @author Ian
 */
public final class DijkstraMap {
	
	private final PriorityQueue<TileRecord> pq = new PriorityQueue<TileRecord>();
	
	/**
	 * Solves Disjkstra's algorithm.  Iff (there's no move_path) or (if the from and to
	 * nodes are the same), the result will be empty.  Else, it will contain a path.
	 * @param map the map to find the move_path on.
	 * @param from the tile to start from.
	 * @param to the tile to end on.
	 */
	public void solve(GameMap map, Tile from, Tile to, LinkedList<Tile> result) {
		pq.clear();
		result.clear();
		for (Tile[] row : map.getMap()) {
			for (Tile t : row) {
				t.tile_record.setDistance(Integer.MAX_VALUE,null);
				t.tile_record.setParent(null);
				pq.add(t.tile_record);
			}
		}
		
		from.tile_record.setDistance(0,pq);
		
		while (!pq.isEmpty()) {
			TileRecord tr = pq.remove();
			if (tr.getDistance()==Integer.MAX_VALUE) break;
			if (tr.tile==to) break;
			
			for (TileConnection connection : tr.connections) {
				int test_distance = tr.getDistance() + connection.cost;
				
				TileRecord connected_to = connection.getDual(tr);
				if (connected_to.tile.getOccupant()!=null) test_distance += 999999;
				if (test_distance<connected_to.getDistance()) {
					connected_to.setDistance(test_distance,pq);
					connected_to.setParent(tr);
				}
			}
		}
		
		if (to.tile_record.getParent()==null) return;
		Tile t = to;
		do {
			result.addFirst(t);
			t = t.tile_record.getParent().tile;
		} while (t.tile_record.getParent()!=null);
		result.addFirst(from);
	}
}