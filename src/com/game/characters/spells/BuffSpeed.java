/**
 * 
 */
package com.game.characters.spells;

import java.util.ArrayList;

import com.game.characters.Creature;
import com.game.characters.Creature.Stat;
import com.game.environment.GameMap;
import com.game.environment.tiles.Tile;
import com.server.State;
import com.server.command.ComparableCommand;
import com.server.command.DebuffCommand;

/**
 * @author TBworkstation
 * 
 */
public class BuffSpeed extends AbstractSpell {

  /**
   * Creates a new buff spell that will buff a {@link Creature}'s speed by 1/4
   * of the caster's intelligence
   */
  public BuffSpeed() {
    super(LEVEL_REQ, requirements, MP_COST, DIFFICULTY, RANGE);
  }

  @Override
  public ComparableCommand cast(State state, String casterName, int targetX, int targetY, int damage) {
    Creature caster= null;
    caster= (Creature) state.getCharacter(casterName);

    GameMap map= state.getGameMap(caster.getMap());
    Tile targetTile= map.getTile(targetX, targetY);

    int buffAmount= caster.getCurrStat(Stat.INTELLIGENCE) / 4;

    if (targetTile.getOccupant() instanceof Creature) {
      Creature target= (Creature) targetTile.getOccupant();

      target.changeStat(Stat.SPEED, buffAmount);

      int duration= 3 * caster.getCurrStat(Stat.INTELLIGENCE) * 1000;

      return new DebuffCommand(target.name, Stat.SPEED, buffAmount, duration);
    }
    return null;
  }

  @Override
  public int setDamage(State state, String casterName) {
    return -1;

  }

  @Override
  public AbstractSpell newInstance() {
    return new BuffSpeed();
  }

  @Override
  public String toString() {
    return "Buff Speed";
  }

  /* ================= private parts ====================== */

  private static final int LEVEL_REQ= 2;
  private static final int MP_COST= 10;
  private static final int DIFFICULTY= 30;
  private static final int RANGE= 4;

  private static final ArrayList<AbstractSpell> requirements= new ArrayList<AbstractSpell>();

  private static final long serialVersionUID= 8269330891827167665L;

}
