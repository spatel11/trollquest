/**
 * 
 */
package com.server.command;

import java.awt.Point;

import com.game.characters.Creature;
import com.game.characters.Creature.Stat;
import com.game.characters.spells.AbstractSpell;
import com.game.characters.spells.Open;
import com.game.environment.GameMap;
import com.game.environment.tiles.Tile;
import com.game.environment.tiles.terrain.TilePortal;
import com.server.State;

/**
 * @author TBworkstation
 * 
 */
public class CastCommand extends ComparableCommand {

  /**
   * Creates a new Cast command for an arbitrary spell
   * 
   * @param stateRef
   *          a reference to the current state, so damage can be set
   * @param charName
   *          the caster of the spell
   * @param targetX
   *          the x location of the target of the spell
   * @param targetY
   *          the y location of the target of the spell
   * @param spell
   *          the spell that is scheduled to cast
   */
  public CastCommand(State stateRef, String charName, int targetX, int targetY,
      AbstractSpell spell) {
    super(charName, 0, 0, 0, false);

    _spell= spell;
    _targetX= targetX;
    _targetY= targetY;
    _casterName= charName;
    damage= _spell.setDamage(stateRef, charName);
  }

  @Override
  public boolean isValid() {
    Creature caster= (Creature) state.getCharacter(_casterName);

    int deltaX= (caster.getPosition().x - _targetX);
    int deltaY= (caster.getPosition().y - _targetY);

    int distance= (int) Math.sqrt(deltaX * deltaX + deltaY * deltaY);

    if (_spell.getRange() > 0 && _spell.getRange() < distance) {

      System.err.println("SpellRange: " + _spell.getRange() + "\nTargetRange: "
          + distance);
      return false;
    }

    if (_spell.getMPCost() > caster.getCurrStat(Stat.MAGICPOINTS)) {
      System.err.println("Not enough magic points");
      return false;
    }

    if (_spell instanceof Open) {
      GameMap map= state.getGameMap(caster.getMap());
      Point position= caster.getPosition();

      Tile portalTile= map.getTile(position.x, position.y);
      if (!(portalTile instanceof TilePortal)) {
        return false;
      }

    }

    return true;
  }

  @Override
  public ComparableCommand execute() {
    Creature caster= (Creature) state.getCharacter(_casterName);
    caster.changeStat(Stat.MAGICPOINTS, -_spell.getMPCost());
    ComparableCommand ret= _spell.cast(state, _casterName, _targetX, _targetY,
        damage);
    if (ret instanceof RespawnCommand) {
      userName= null;
    }
    return ret;
  }

  @Override
  public void setState(State state) {
    super.setState(state);

    // _spell.setDamage(this.state, this.charName);

    this.prepTime= Math.max(0, _spell.getDifficulty()
        - ((Creature) state.getCharacter(charName))
            .getCurrStat(Stat.INTELLIGENCE));
    this.executeTime= 0;
    this.resetTime= Math.max(0, _spell.getDifficulty()
        - 2
        * ((Creature) state.getCharacter(charName))
            .getCurrStat(Stat.INTELLIGENCE));
  }

  /* ====================== private parts ======================== */

  private final AbstractSpell _spell;
  private final int _targetX, _targetY;
  private final String _casterName;
  private final int damage;

  private static final long serialVersionUID= -6732240915477694903L;

}
