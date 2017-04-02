package com.game.environment;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import com.game.characters.GameCharacter;
import com.game.environment.tiles.Tile;
import com.game.environment.tiles.terrain.TileDesert;
import com.game.environment.tiles.terrain.TileForest;
import com.game.environment.tiles.terrain.TileMountain;
import com.game.environment.tiles.terrain.TilePlain;
import com.game.environment.tiles.terrain.TileSwamp;
import com.game.environment.tiles.terrain.TilePortal;
import com.game.environment.tiles.terrain.TileWasteland;
import com.game.environment.tiles.terrain.TileWater;

/**
 * @author TBworkstation
 * @author Ian
 * 
 *         A representation of a map in JWorld
 */
public class GameMap implements Serializable {
  /** GameMap's serial ID */
  private static final long serialVersionUID= -1035877872024938701L;

  /**
   * Creates a new game map full of random tiles that is 50 by 50 tiles TODO:
   * TEMPORARY CONSTRUCTOR!
   */
  public GameMap(String name, int size_x, int size_y) {
    this.name= name;

    map= new Tile[size_y][size_x];

    Random rand= new Random();
    for (int i= 0; i < size_y; i++) {
      for (int j= 0; j < size_x; j++) {
        Tile new_tile= null;
        switch (rand.nextInt(8)) {
          case 0:
            new_tile= new TileDesert(i, j);
            break;
          case 1:
            new_tile= new TileForest(i, j);
            break;
          case 2:
            new_tile= new TileMountain(i, j);
            break;
          case 3:
            new_tile= new TilePlain(i, j);
            break;
          case 4:
            new_tile= new TileSwamp(i, j);
            break;
          case 5:
            new_tile= new TileWasteland(i, j);
            break;
          case 6:
            new_tile= new TilePortal(i, j, this.name);
            break;
          default:
            new_tile= new TileWater(i, j);
            break;
        }
        // new_tile = (rand.nextBoolean()) ? new TileDesert(i,j) : new
        // TileWater(i,j);
        // new_tile = new TileDesert(i,j);
        // if (rand.nextInt(50)==0) new_tile = new TilePortal(i,j);
        setTile(i, j, new_tile);
      }
    }
    spawnLocX= spawnLocY= 0;
  }

  /**
   * Creates a new {@link GameMap} by reading a file from disk. Each terrain
   * {@link Tile} will have a distinct letter representing that tile
   * 
   * The file format will be as follows: The first line will be two integers
   * designating the spawn location of all characters separated by commas. After
   * that, each line will have the same width, and a single character will
   * represent the tile type(tile characters below). If a portal tile is
   * encountered, the next string (separated by a comma) will be read as the
   * target destination for the portal.
   * 
   * Tile Character Representations: D = {@link TileDesert} F =
   * {@link TileForest} M = {@link TileMountain} P = {@link TilePlain} S =
   * {@link TileSwamp} O = {@link TilePortal} W = {@link TileWasteland} A =
   * {@link TileWater}
   * 
   * @param f
   *          the file the map will be read from. This will also turn into the
   *          {@link GameMap}'s name;
   */
  public GameMap(File f) {
    this.name= f.getName();

    String row= "";
    BufferedReader reader= null;
    try {
      reader= new BufferedReader(new FileReader(f));
      row= reader.readLine(); // name of the map, which is the same as
      // the
      // filename
      row= reader.readLine(); // this is the map's size;
      int width= Integer.parseInt(row.substring(0, row.indexOf(',')));
      int height= Integer.parseInt(row.substring(row.indexOf(',') + 1, row
          .length()));
      map= new Tile[height][width];

      row= reader.readLine(); // the default spawn location

    }
    catch (IOException e) {
    }

    spawnLocY= Integer.parseInt(row.substring(0, row.indexOf(',')));
    spawnLocX= Integer.parseInt(row.substring(row.indexOf(',') + 1, row
        .length()));

    int x= 0;
    int y= 0;
    try {
      if (reader != null) {
        while (null != (row= reader.readLine())) {
          for (x= 0; x < row.length(); x++) {
            char c= row.charAt(x);
            switch (c) {
              case 'D':
                setTile(y, x, new TileDesert(x, y));
                break;
              case 'F':
                setTile(y, x, new TileForest(x, y));
                break;
              case 'M':
                setTile(y, x, new TileMountain(x, y));
                break;
              case 'P':
                setTile(y, x, new TilePlain(x, y));
                break;
              case 'S':
                setTile(y, x, new TileSwamp(x, y));
                break;
              case 'O':
                String portalDest= row.substring(x + 1, row.indexOf(',', x));
                if (row.indexOf(',') == row.length()) {
                  row= row.substring(0, x + 1);
                }
                else {
                  row= row.substring(0, x + 1)
                      + row.substring(row.indexOf(',') + 1);
                }
                setTile(y, x, new TilePortal(x, y, portalDest));
                break;
              case 'W':
                setTile(y, x, new TileWasteland(x, y));
                break;
              default:
                setTile(y, x, new TileWater(x, y));
                break;
            }
          }
          y++;
        }
      }
    }
    catch (IOException e) {
      System.out.println("Error Constructing map");
    }

    if (reader != null) {
      try {
        reader.close();
      }
      catch (IOException e) {
      }
    }
  }

  /**
   * Returns the map width as an int
   * 
   * @return the integer value of the map width
   */
  public int getMapWidth() {
    return map[0].length;
  }

  /**
   * Returns the map heigh as an int
   * 
   * @return the integer value of the map heigh
   */
  public int getMapHeight() {
    return map.length;
  }

  /**
   * Returns the closest position to the spawn location of the map that is not
   * occupied by a {@link GameCharacter}
   * 
   * @return A {@link Point} location of the closest available cell in the map
   *         to the map's spawn location
   */
  public Point getSpawnLoc() {
    BFSIterator iter= getBFSIterator(map[spawnLocX][spawnLocY], map.length);

    while (iter.hasNext()) {
      Tile t= iter.next().getNode();
      if (t.getOccupant() == null) {
        return new Point(t.getPosition().x, t.getPosition().y);
      }
    }

    return null;
  }

  /**
   * Sets a tile. Links the tile to the other tiles around it. Does no bounds
   * checking.
   * 
   * @param i
   *          the x coordinate.
   * @param j
   *          the y coordinate.
   * @param new_tile
   *          the new tile.
   */
  public void setTile(int i, int j, Tile new_tile) {
    map[i][j]= new_tile;
    for (int i2= i - 1; i2 <= i + 1; ++i2) {
      for (int j2= j - 1; j2 <= j + 1; ++j2) {
        if (i2 < 0) continue;
        if (j2 < 0) continue;
        if (i2 >= map.length) continue;
        if (j2 >= map[0].length) continue;
        if (i == i2 && j == j2) continue;
        // if (Math.abs(i-i2)+Math.abs(j-j2)==2) continue;
        Tile.linkTiles(map[i][j], map[i2][j2]);
      }
    }
  }

  /**
   * Gets a tile. Does no bounds checking.
   * 
   * @param x
   *          the rel_x coordinate.
   * @param y
   *          the rel_y coordinate.
   * @return the tile.
   */
  public Tile getTile(int x, int y) {
    return map[y][x];
  }

  /**
   * Returns the 2D Tile array that represents a map.
   * 
   * @return This game map's physical map for rendering purposes
   */
  public Tile[][] getMap() {
    return map;
  }

  /**
   * Returns the name of the map in string format
   * 
   * @return the name of the map
   */
  public String getName() {
    return name;
  }

  /**
   * Returns a new BFS iterator with the set starting position and the depth you
   * want to go to.
   * 
   * @param start
   *          the starting tile
   * @param depthLimit
   *          the farthest the iterator will go before returning null
   * @return a factory created {@link BFSIterator}
   */
  public BFSIterator getBFSIterator(Tile start, int depthLimit) {
    if (start == null || start.getPosition().x < 0 || start.getPosition().y < 0
        || start.getPosition().x >= getMapWidth()
        || start.getPosition().y >= getMapHeight()) throw new IllegalArgumentException(
        "Error with the start tile");
    else return new BFSIterator(start, depthLimit);
  }

  @Override
  public String toString() {
    String s= name + "\n" + map[0].length + "," + map.length + "\n" + spawnLocX
        + "," + spawnLocY + "\n";
    System.out.println(s);
    for (int y= 0; y < map.length; y++) {
      for (int x= 0; x < map[0].length; x++) {
        s+= map[y][x].toString();
      }
      s+= "\n";
    }

    s= s.substring(0, s.length() - 1);

    return s;
  }

  /* ========================== PRIVATE PARTS =========================== */

  private Tile[][] map;
  private String name;
  private int spawnLocX;
  private int spawnLocY;

  private void writeObject(ObjectOutputStream out) throws IOException {
    out.writeObject(this.toString());
  }

  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
    String map= (String) in.readObject();
    rebuildMap(map);
  }

  /**
   * Creates a new {@link GameMap} by reading a String parameter. Each terrain
   * {@link Tile} will have a distinct letter representing that tile
   * 
   * The String format will be as follows: The first line has to be the name of
   * the map followed by a newline (\n). The second line will be two integers
   * designating the spawn location of all characters separated by a comma.
   * After that, each line will have the same width, and a single character will
   * represent the tile type(tile characters below). If a portal tile is
   * encountered, the next string (delimited by a comma) will be read as the
   * target destination for the portal.
   * 
   * Tile Character Representations: D = {@link TileDesert}
   * 
   * F = {@link TileForest}
   * 
   * M = {@link TileMountain}
   * 
   * P = {@link TilePlain}
   * 
   * S = {@link TileSwamp}
   * 
   * O = {@link TilePortal}
   * 
   * W = {@link TileWasteland}
   * 
   * A = {@link TileWater}
   * 
   * @param mapString
   *          the string identical to the original file's contents.
   */
  private void rebuildMap(String mapString) {
    String[] rows= mapString.split("\n|\r");

    this.name= rows[0];

    int width= Integer.parseInt(rows[1].substring(0, rows[1].indexOf(',')));
    int height= Integer.parseInt(rows[1].substring(rows[1].indexOf(',') + 1,
        rows[1].length()));

    map= new Tile[height][width];

    spawnLocX= Integer.parseInt(rows[2].substring(0, rows[2].indexOf(',')));
    spawnLocY= Integer.parseInt(rows[2].substring(rows[2].indexOf(',') + 1,
        rows[2].length()));

    for (int y= 3; y < rows.length; y++) {
      for (int x= 0; x < rows[y].length(); x++) {
        char c= rows[y].charAt(x);
        switch (c) {
          case 'D':
            setTile(y - 3, x, new TileDesert(x, y - 3));
            break;
          case 'F':
            setTile(y - 3, x, new TileForest(x, y - 3));
            break;
          case 'M':
            setTile(y - 3, x, new TileMountain(x, y - 3));
            break;
          case 'P':
            setTile(y - 3, x, new TilePlain(x, y - 3));
            break;
          case 'S':
            setTile(y - 3, x, new TileSwamp(x, y - 3));
            break;
          case 'O':
            String portalDest= rows[y].substring(rows[y].indexOf(c) + 1,
                rows[y].indexOf(',', rows[y].indexOf(c)));
            rows[y]= rows[y].substring(0, x + 1)
                + rows[y].substring(rows[y].indexOf(',') + 1);
            setTile(y - 3, x, new TilePortal(x, y - 3, portalDest));
            break;
          case 'W':
            setTile(y - 3, x, new TileWasteland(x, y - 3));
            break;
          default:
            setTile(y - 3, x, new TileWater(x, y - 3));
            break;
        }
      }
    }
  }

  /**
   * A new BFS iterator for the tile map built to iterate over the map's
   * structure in breadth fist order and only to a certain depth
   * 
   * @author TBworkstation
   * 
   */
  public class BFSIterator implements Iterator<SearchState<Tile>> {

    private final ArrayDeque<SearchState<Tile>> _open;
    private final Set<Tile> _closed;
    private final int depthLimit;

    private BFSIterator(Tile start, int depthLimit) {
      _open= new ArrayDeque<SearchState<Tile>>();
      _open.add(new SearchState<Tile>(0, start, null));
      _closed= new HashSet<Tile>();
      // _closed.add(start);
      this.depthLimit= depthLimit;
    }

    @Override
    public boolean hasNext() {
      if (_open.isEmpty()) return false;
      else if (_open.peek().getDepth() > depthLimit) return false;
      else return true;
      // return !_open.isEmpty();
    }

    @Override
    public SearchState<Tile> next() {
      SearchState<Tile> state= null;
      while (!_open.isEmpty()) {
        state= _open.removeFirst();
        if (!_closed.contains(state.getNode())) {
          _closed.add(state.getNode());
          for (Tile node : getNeighbors(state.getNode())) {
            SearchState<Tile> neighborState= new SearchState<Tile>(state
                .getDepth() + 1, node, state);
            _open.addLast(neighborState);
          }
          return state;
        }
      }
      return state;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException("Map cannot be changed");
    }

  }

  // returns all tiles that are a neighbor of the tile parameter
  private Set<Tile> getNeighbors(Tile t) {

    Set<Tile> set= new HashSet<Tile>();

    Point p= t.getPosition();

    for (int x= p.x - 1; x <= p.x + 1; x++) {
      for (int y= p.y - 1; y <= p.y + 1; y++) {
        if (x == p.x && y == p.y) continue;
        if (x < 0 || y < 0) continue;
        if (x >= this.getMapWidth() || y >= this.getMapHeight()) continue;
        set.add(map[y][x]);
      }
    }

    return set;
  }

  /* =======================USEFUL PUBLIC FIELDS ========================== */

  /** The directory to the test maps */
  public static final String TEST_MAP_DIRECTORY= new File(System
      .getProperty("user.dir")
      + File.separatorChar
      + "data"
      + File.separatorChar
      + "testData"
      + File.separatorChar + "testMaps").getAbsolutePath()
      + File.separatorChar;

  /** the directory to our predefined game maps */
  public static final String MAP_FILES_DIRECTORY= new File(System
      .getProperty("user.dir")
      + File.separatorChar + "mapFiles").getAbsolutePath()
      + File.separatorChar;

}
