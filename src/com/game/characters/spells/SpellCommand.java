/**
 * 
 */
package com.game.characters.spells;

import com.server.State;
import com.server.command.ComparableCommand;
import com.server.command.RespawnCommand;

/**
 * @author TBworkstation
 *
 */
public interface SpellCommand {

  /**
   * Casts the using the correct semantics with the given information.
   * 
   * @param state
   *          the current game state
   * @param casterName
   *          the caster's name
   * @param targetX
   *          the x Location of the target of the spell
   * @param targetY
   *          the y location of the target of the spell
   * 
   * @return A {@link RespawnCommand} if necessary
   * 
   */
  public ComparableCommand cast(State state, String casterName, int targetX, int targetY, int damage);

  /**
   * If the spell is a damaging spell this will set the damage of the spell upon
   * instantiation so the same damage gets distributed across the network
   * 
   * @param state
   * @param casterName
   * 
   * @return the damage the spell does or -1 if it doesnt do damage
   */
  public int setDamage(State state, String casterName);
}
