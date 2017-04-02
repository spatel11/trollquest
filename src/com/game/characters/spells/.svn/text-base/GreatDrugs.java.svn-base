/**
 * 
 */
package com.game.characters.spells;

import java.util.ArrayList;
import java.util.List;

import com.game.characters.Creature;
import com.game.characters.Creature.Stat;
import com.game.environment.GameMap;
import com.game.environment.tiles.Tile;
import com.server.State;
import com.server.command.ComparableCommand;

/**
 * @author TBworkstation
 * 
 */
public class GreatDrugs extends AbstractSpell {

  /**
   * 
   */
  private static final long serialVersionUID= -328038644598524965L;

  /**
   * 
   */
  public GreatDrugs() {
    super(LEVEL_REQ, SPELL_REQ, MP_COST, DIFFICULTY, RANGE);
  }

  @Override
  public AbstractSpell newInstance() {
    return new GreatDrugs();
  }

  @Override
  public ComparableCommand cast(State state, String casterName, int targetX, int targetY, int damage) {
    Creature caster= null;
    caster= (Creature) state.getCharacter(casterName);

    GameMap map= state.getGameMap(caster.getMap());

    Tile target= map.getTile(targetX, targetY);

    if (target.getOccupant() == null) return null;
    Creature victim= (Creature) target.getOccupant();

    victim.changeStat(Stat.HEALTHPOINTS, damage);
    return null;
  }

  @Override
  public int setDamage(State state, String casterName) {
    Creature caster= (Creature) state.getCharacter(casterName);

    return (caster.getCurrStat(Stat.LEVEL) / 5)
        * caster.getCurrStat(Stat.INTELLIGENCE);

  }

  @Override
  public String toString() {
    return "Great Drugs";
  }

  /* ========================== PRIVATE PARTS ============================= */

  private static final int LEVEL_REQ= 4;
  private static final List<AbstractSpell> SPELL_REQ= buildReq();
  private static final int MP_COST= 20;
  private static final int DIFFICULTY= 40;
  private static final int RANGE= 8;

  private static List<AbstractSpell> buildReq() {
    List<AbstractSpell> temp= new ArrayList<AbstractSpell>();

    temp.add(new GoodDrugs());

    return temp;
  }
}
