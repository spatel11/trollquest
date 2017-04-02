/**
 * 
 */
package com.game.characters.spells;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
public class GoodDrugs extends AbstractSpell {

  /**
   * 
   */
  private static final long serialVersionUID= -4091917394202268319L;

  /**
   * 
   */
  public GoodDrugs() {
    super(LEVEL_REQ, SPELL_REQ, MP_COST, DIFFICULTY, RANGE);
  }

  @Override
  public AbstractSpell newInstance() {
    return new GoodDrugs();
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

    Random rand= new Random(System.currentTimeMillis());

    return caster.getCurrStat(Stat.LEVEL) * (rand.nextInt(DAMAGE_RANGE) + 1);

  }

  @Override
  public String toString() {
    return "Good Drugs";
  }

  /* ========================== PRIVATE PARTS ============================= */

  private static final int LEVEL_REQ= 1;
  private static final List<AbstractSpell> SPELL_REQ= new ArrayList<AbstractSpell>();
  private static final int MP_COST= 8;
  private static final int DIFFICULTY= 25;
  private static final int DAMAGE_RANGE= 10;
  private static final int RANGE= 5;
}
