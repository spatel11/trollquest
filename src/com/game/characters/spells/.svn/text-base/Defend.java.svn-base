/**
 * 
 */
package com.game.characters.spells;

import java.util.ArrayList;

import com.game.characters.Creature;
import com.game.characters.Creature.Stat;
import com.server.State;
import com.server.command.ComparableCommand;
import com.server.command.DebuffCommand;

/**
 * @author TBworkstation
 * 
 */
public class Defend extends AbstractSpell {

  /**
   * Creates a defend spell that doubles the player's base defense value
   */
  public Defend() {
    super(0, new ArrayList<AbstractSpell>(), 0, 0, 1);

  }

  @Override
  public ComparableCommand cast(State state, String casterName, int targetX, int targetY, int damage) {
    Creature caster= null;
    int defense, duration;
    caster= (Creature) state.getCharacter(casterName);

    defense= caster.getCurrStat(Stat.DEFENSEVALUE);
    caster.changeStat(Stat.DEFENSEVALUE, defense);

    // scaled since game time is in milliseconds
    duration= 2 * caster.getCurrStat(Stat.SPEED) * 1000;
    return new DebuffCommand(casterName, Stat.DEFENSEVALUE, defense, duration);
  }

  @Override
  public int setDamage(State state, String casterName) {
    return -1;
  }

  @Override
  public AbstractSpell newInstance() {
    return new Defend();
  }

  @Override
  public String toString() {
    return "Defend";
  }

  private static final long serialVersionUID= -3332882939895193548L;

}
