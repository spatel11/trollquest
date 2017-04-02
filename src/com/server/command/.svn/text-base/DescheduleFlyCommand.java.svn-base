/**
 * 
 */
package com.server.command;

import com.game.characters.Creature;
import com.game.characters.Creature.Stat;
import com.server.State;

/**
 * @author TBworkstation
 *
 */
public class DescheduleFlyCommand extends ComparableCommand {

  /**
   * Creates an event that will remove the fly status from a player
   * 
   * @param charName
   *          the character to remove the fly status from
   */
  public DescheduleFlyCommand(String charName) {
    super(charName, 0, 0, 0, false);

    _caster= charName;
  }

  @Override
  public boolean isValid() {

    return true;
  }

  @Override
  public ComparableCommand execute() {
    Creature caster= (Creature) state.getCharacter(_caster);

    caster.setIsFlying(false);

    return null;
  }

  @Override
  public void setState(State state) {
    Creature caster= (Creature) state.getCharacter(_caster);

    this.prepTime= 30 * caster.getCurrStat(Stat.LEVEL)
        + caster.getCurrStat(Stat.INTELLIGENCE);
  }

  /* ================== private parts ============== */
  private final String _caster;

  private static final long serialVersionUID= 6306229549285507798L;

}
