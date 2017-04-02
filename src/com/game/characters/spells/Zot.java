package com.game.characters.spells;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.client.Graphics.AnimationsOfACharacter.ActionType;
import com.game.characters.Creature;
import com.game.characters.Creature.Stat;
import com.game.environment.GameMap;
import com.game.environment.tiles.Tile;
import com.server.State;
import com.server.command.ComparableCommand;
import com.server.command.DieCommand;
import com.server.command.RespawnCommand;

/**
 * A Zot {@link AbstractSpell} that records the information needed to "learn"
 * the spell
 * 
 * @author tberge01
 * 
 */
public class Zot extends AbstractSpell {

  /**
   * 
   */
  private static final long serialVersionUID= 8219323380236669830L;

  /**
   * Creates a new {@link AbstractSpell} with the name Zot that does a single
   * targeted attack
   */
  public Zot() {
    super(LEVEL_REQ, SPELL_REQ, MP_COST, DIFFICULTY, RANGE);
  }

  @Override
  public AbstractSpell newInstance() {
    return new Zot();
  }

  @Override
  public ComparableCommand cast(State state, String casterName, int targetX, int targetY, int damage) {
    ComparableCommand respawn= null;
    Creature caster= (Creature) state.getCharacter(casterName);

    GameMap map= state.getGameMap(caster.getMap());

    Tile target= map.getTile(targetX, targetY);

    if (target.getOccupant() == null) return null;
    Creature victim= (Creature) target.getOccupant();

    if (victim == caster) return null;

    victim.changeStat(Stat.HEALTHPOINTS, -damage);

    if (victim.getCurrStat(Stat.HEALTHPOINTS) <= 0) {
      ComparableCommand com;
      com= new DieCommand(caster.name, victim.name);
      com.setState(state);
      com.execute();
      respawn= new RespawnCommand(victim);
    }
    caster.setSubX(0.0);
    caster.setSubY(0.0);
    caster.setSubXvel(0.0);
    caster.setSubYvel(0.0);
    caster.setActionState(ActionType.STAND);
    return respawn;
  }

  @Override
  public int setDamage(State state, String casterName) {
    Creature caster= (Creature) state.getCharacter(casterName);

    Random rand= new Random(System.currentTimeMillis());

    return caster.getCurrStat(Stat.LEVEL) * (rand.nextInt(DAMAGE_RANGE) + 1);

  }

  @Override
  public String toString() {
    return "Zot";
  }

  /* ========================== PRIVATE PARTS ============================= */

  private static final int LEVEL_REQ= 1;
  private static final int MP_COST= 3;
  private static final int DIFFICULTY= 25;
  private static final int DAMAGE_RANGE= 5;
  private static final int RANGE= -1;
  private static final List<AbstractSpell> SPELL_REQ= new ArrayList<AbstractSpell>();
}
