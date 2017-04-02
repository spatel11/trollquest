/**
 * 
 */
package com.game.characters.spells;

import java.util.ArrayList;
import java.util.List;

import com.game.characters.Creature;
import com.server.State;
import com.server.command.ComparableCommand;
import com.server.command.DescheduleFlyCommand;
import com.server.command.MoveCommand;

/**
 * @author TBworkstation
 * 
 */
public class Fly extends AbstractSpell {

  /**
   * 
   */
  private static final long serialVersionUID= -810840080918677201L;

  /**
   * Creates a new Fly Spell for a {@link SpellInventory}. This sets a flag for
   * all {@link MoveCommand}s to set their prep time to 10 for this
   * {@link Creature} who cast the spell
   */
  public Fly() {
    super(LEVEL_REQ, SPELL_REQ, MP_COST, DIFFICULTY, RANGE);
  }

  @Override
  public AbstractSpell newInstance() {
    return new Fly();
  }

  @Override
  public ComparableCommand cast(State state, String casterName, int targetX, int targetY, int damage) {
    Creature caster= null;
    ComparableCommand cc;
    caster= (Creature) state.getCharacter(casterName);

    caster.setIsFlying(true);

    cc= new DescheduleFlyCommand(caster.name);
    return cc;
  }

  @Override
  public int setDamage(State state, String casterName) {
    return -1;
  }

  @Override
  public String toString() {
    return "Fly";
  }

  /* ========================== PRIVATE PARTS ============================= */

  private static final int LEVEL_REQ= 8;
  private static final List<AbstractSpell> SPELL_REQ= buildReq();
  private static int MP_COST= 15;
  private static int DIFFICULTY= 40;
  private static int RANGE= -1;

  private static List<AbstractSpell> buildReq() {
    List<AbstractSpell> temp= new ArrayList<AbstractSpell>();

    temp.add(new Open());

    return temp;
  }
}
