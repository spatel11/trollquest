package com.server.command;

import java.awt.Point;
import java.util.Random;

import com.client.Graphics.AnimationsOfACharacter.ActionType;
import com.game.characters.Creature;
import com.game.characters.GameCharacter;
import com.game.characters.Creature.Stat;
import com.game.environment.GameMap;
import com.server.State;

/**
 * An attack command used to physically attack another creature with a weapon
 * This should only affect creatures, not NPC's
 * 
 * @author tberge01
 * 
 */
public class AttackCommand extends ComparableCommand {

  /** AttackCommand serial ID */
  private static final long serialVersionUID= -7172582886687831447L;

  /**
   * Creates a schedule-able attack command
   * 
   * @param gs
   *          the current game's {@link State}
   * @param gameMap
   *          the current {@link GameMap} the {@link Creature} is on
   * @param creature
   *          the {@link Creature} executing the command
   * @param target
   *          a {@link Point} target on the map
   */
  public AttackCommand(State s, String attacker, int xTarget, int yTarget) {
    super(attacker, 0, 0, 0, false);
    this.x= xTarget;
    this.y= yTarget;
    this.state= s;
    synchronized (state) {
      Creature attackerCreature= (Creature) state.getCharacter(attacker);
      Creature targetCreature= (Creature) state.getGameMap(
          attackerCreature.getMap()).getTile(xTarget, yTarget).getOccupant();

      hitPercent= playerHitPercent= playerDmg= -1;
      if (targetCreature != null) {
        // debugging purposes
        System.err.println(attackerCreature.getCurrStat(Stat.STRENGTH) + 5
            * attackerCreature.getCurrStat(Stat.LEVEL)
            - targetCreature.getCurrStat(Stat.DEFENSEVALUE));

        hitPercent= Math.max(0.0, Math.min(1.0, ((attackerCreature
            .getCurrStat(Stat.STRENGTH)
            + 5 * attackerCreature.getCurrStat(Stat.LEVEL) - targetCreature
            .getCurrStat(Stat.DEFENSEVALUE)) / 100.0)));
        playerHitPercent= r.nextDouble();
        if (attackerCreature.getWeapon() != null) {
          playerDmg= (int) Math.floor(attackerCreature.getWeapon().getDamage()
              + attackerCreature.getCurrStat(Stat.STRENGTH)
              * attackerCreature.getWeapon().getStrMod());
        }
        else {
          playerDmg= (int) Math.floor(attackerCreature
              .getCurrStat(Stat.STRENGTH));
        }
      }
    }
  }

  @Override
  public ComparableCommand execute() {
    ComparableCommand respawn= null;
    ComparableCommand com= null;
    synchronized (state) {
      GameCharacter character= state.getCharacter(charName);

      String map= state.getCharacter(charName).getMap();
      Creature targetCreature= (Creature) state.getGameMap(map).getTile(x, y)
          .getOccupant();
      if (targetCreature == null) return null;
      if (playerHitPercent < hitPercent) {
        targetCreature.changeStat(Stat.HEALTHPOINTS, -playerDmg);

        if (targetCreature.getCurrStat(Stat.HEALTHPOINTS) <= 0) {
          com= new DieCommand(charName, targetCreature.name);
          com.setState(state);
          com.execute();
          respawn= new RespawnCommand(targetCreature);
          userName= null;
        }
      }
      character.setSubX(0.0);
      character.setSubY(0.0);
      character.setSubXvel(0.0);
      character.setSubYvel(0.0);
      character.setActionState(ActionType.STAND);
    }
    return respawn;
  }

  @Override
  public boolean isValid() {
    synchronized (state) {
      if (hitPercent == -1 || playerHitPercent == -1 || playerDmg == -1)
        return false;
      if (state.getCharacter(charName) == null) return false;
      String map= state.getCharacter(charName).getMap();
      if (state.getGameMap(map).getTile(x, y).getOccupant() == null) {
        return false;

      }
      else if (!(state.getGameMap(map).getTile(x, y).getOccupant() instanceof Creature)) {
        return false;
      }
      if (((Creature) state.getCharacter(charName))
          .getCurrStat(Stat.HEALTHPOINTS) == 0) {
        return false;
      }
      if (Math.abs(state.getCharacter(charName).getPosition().x - x) > 1
          || Math.abs(state.getCharacter(charName).getPosition().y - y) > 1) {
        return false;
      }
    }
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.server.command.ComparableCommand#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object o) {
    if (!super.equals(o)) return false;

    if (o == null) return false;
    if (!(o instanceof AttackCommand)) return false;
    AttackCommand a= (AttackCommand) o;
    if (!a.charName.equals(charName)) return false;
    if (a.x != x || a.y != y) return false;
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder= new StringBuilder();
    builder.append("AttackCommand [x=");
    builder.append(x);
    builder.append(", y=");
    builder.append(y);
    builder.append(", hitPercent=");
    builder.append(hitPercent);
    builder.append(", playerHitPercent=");
    builder.append(playerHitPercent);
    builder.append(", playerDmg=");
    builder.append(playerDmg);
    builder.append(", ");
    if (charName != null) {
      builder.append("charName=");
      builder.append(charName);
      builder.append(", ");
    }
    if (getClass() != null) {
      builder.append("getClass()=");
      builder.append(getClass());
    }
    builder.append("]");
    return builder.toString();
  }

  @Override
  public void setState(State state) {
    super.setState(state);
    synchronized (state) {
      this.prepTime= Math.max(5, ((Creature) state.getCharacter(charName))
          .getWeapon().ENCUMBERANCE
          - ((Creature) state.getCharacter(charName)).getCurrStat(Stat.SPEED));
      this.executeTime= 0;
      this.resetTime= 0;

      r= new Random(System.currentTimeMillis());
    }
  }

  /* =========================PRIVATE PARTS ============================ */

  private final int x, y;
  private transient Random r= new Random(System.currentTimeMillis());

  private double hitPercent, playerHitPercent;
  private int playerDmg;
}
