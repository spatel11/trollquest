/**
 * 
 */
package com.server.command;

import com.game.characters.Creature;
import com.game.characters.Creature.Stat;

/**
 * @author TBworkstation
 * 
 */
public class DebuffCommand extends ComparableCommand {

  /**
   * "Debuffs" the character's specified {@link Stat} by the indicated amount.
   * Causes a reduction by the specified amount, so the integer should be
   * positive.
   * 
   * @param charName
   *          the character's name to debuff
   * @param stat
   *          the {@link Stat} that will be debuffed
   * @param debuff
   *          the value to debuff the indicated stat by
   * @param duration
   *          essentially the prep time of the {@link DebuffCommand}. This will
   *          schedule this buff to last the indicated amount of time in game
   *          ticks
   */
  public DebuffCommand(String charName, Stat stat, int debuff, int duration) {
    super(charName, duration, 0, 0, false);

    this.debuff= debuff;
    this.stat= stat;
  }

  @Override
  public boolean isValid() {
    return true;
  }

  @Override
  public ComparableCommand execute() {
    synchronized (state) {
      Creature caster= (Creature) state.getCharacter(this.charName);

      if (stat == Stat.HEALTHPOINTS || stat == Stat.MAGICPOINTS) {
        caster.changeBaseHPorMP(stat, -debuff);
      }
      else {
        caster.changeStat(stat, -debuff);
      }
    }
    return null;
  }

  private final int debuff;
  private final Stat stat;

  private static final long serialVersionUID= 7692401681961733310L;

}
