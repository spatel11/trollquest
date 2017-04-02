/**
 * 
 */
package com.server.command;

import com.client.Graphics.AnimationsOfACharacter.ActionType;
import com.game.characters.Creature;
import com.game.characters.Creature.Stat;
import com.game.characters.spells.AbstractSpell;
import com.game.characters.spells.ArmageddonAndThenSome;
import com.game.characters.spells.FlameThrower;
import com.server.GameScheduler;

/**
 * @author TBworkstation
 * 
 */
public class RescheduledDummySpell extends ComparableCommand {

  /**
   * Reschedules a continuous command to be executed again at the next available
   * game step. This is mainly used with the {@link FlameThrower}, and
   * {@link ArmageddonAndThenSome}
   * 
   * @param caster
   * @param originSpell
   * @param reschedulesLeft
   * @param casterOriginX
   * @param casterOriginY
   * @param origTargetX
   * @param origTargetY
   */
  public RescheduledDummySpell(String caster, AbstractSpell originSpell,
      int reschedulesLeft, int casterOriginX, int casterOriginY,
      int origTargetX, int origTargetY, int damage) {
    super(caster, 0, 0, 0, false);

    casterName= caster;
    this.casterOriginX= casterOriginX;
    this.casterOriginY= casterOriginY;

    targetOriginX= origTargetX;
    targetOriginY= origTargetY;

    _counter= reschedulesLeft;

    _spell= originSpell;

    this.damage= damage;

  }

  @Override
  public boolean isValid() {
    if (_counter <= 0) return false;

    Creature caster= (Creature) state.getCharacter(casterName);
    if (caster.getPosition().x != casterOriginX
        || caster.getPosition().y != casterOriginY) {
      return false;
    }

    if (caster.getCurrStat(Stat.MAGICPOINTS) < _spell.getMPCost()) {
      return false;
    }

    if (caster.getActionState() == ActionType.DIE) {
      return false;
    }

    return true;
  }

  @Override
  public ComparableCommand execute() {
    synchronized (state) {
      if (_counter > 0) {

        Creature caster= (Creature) state.getCharacter(casterName);

        caster.changeStat(Stat.MAGICPOINTS, -_spell.getMPCost());

        GameScheduler gs= state.getScheduler();
        gs.schedule(_spell.cast(state, casterName, targetOriginX,
            targetOriginY, damage));
      }
    }
    return new RescheduledDummySpell(casterName, _spell, _counter - 1,
        casterOriginX, casterOriginY, targetOriginX, targetOriginY, damage);
  }

  /* =============== private parts ======================= */

  private final AbstractSpell _spell;
  private final int _counter;
  private final int casterOriginX, casterOriginY, targetOriginX, targetOriginY;
  private final String casterName;
  private final int damage;

  private static final long serialVersionUID= 8459350546030521287L;

}
