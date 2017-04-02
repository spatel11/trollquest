package com.game.characters.spells;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.client.Message;
import com.game.characters.GameCharacter;
import com.game.environment.GameMap;
import com.game.environment.tiles.Tile;
import com.game.environment.tiles.TileException;
import com.game.environment.tiles.terrain.TilePortal;
import com.server.State;
import com.server.command.ComparableCommand;

/**
 * @author TBworkstation
 * 
 */
public class Open extends AbstractSpell {

  /**
   * 
   */
  private static final long serialVersionUID= -5548523498503785477L;

  /**
   * 
   */
  public Open() {
    super(LEVEL_REQ, SPELL_REQ, MP_COST, DIFFICULTY, RANGE);
  }

  @Override
  public AbstractSpell newInstance() {
    return new Open();
  }

  @Override
  public ComparableCommand cast(State state, String casterName, int targetX, int targetY, int damage) {
    GameCharacter player= state.getCharacter(casterName);
    GameMap map= state.getGameMap(player.getMap());
    TilePortal temp= (TilePortal) map.getTile(player.getPosition().x, player
        .getPosition().y);
    temp.removeOccupant();

    GameMap targetMap= state.getGameMap(temp.getDestination());
    Point target= targetMap.getSpawnLoc();
    Tile destination= targetMap.getTile(target.x, target.y);
    player.setMap(targetMap.getName());
    try {
      destination.setOccupant(player);
    }
    catch (TileException e) {
      e.printStackTrace(); // TODO handle properly
    }
    state.addCharacter(player);
    Message msg= new Message("SERVER", casterName + " has entered "
        + player.getMap() + " at location " + player.getPosition().x + ", "
        + player.getPosition().y);
    msg.setComplete();
    state.addMessage(msg);
    return null;
  }

  @Override
  public int setDamage(State state, String casterName) {
    return -1;
  }

  @Override
  public String toString() {
    return "Open";
  }

  /* ============================ PRIVATE PARTS ============================= */

  private static final int LEVEL_REQ= 3;
  private static final List<AbstractSpell> SPELL_REQ= new ArrayList<AbstractSpell>();

  private static int MP_COST= 10;
  private static int DIFFICULTY= 20;
  private static int RANGE= -1;
}
