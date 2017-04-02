/**
 * 
 */
package com.game.characters.spells;

import java.util.ArrayList;
import java.util.List;

import com.game.characters.Creature;
import com.game.characters.Player;
import com.game.characters.Creature.Stat;
import com.game.environment.Directions;
import com.game.environment.GameMap;
import com.game.environment.tiles.Tile;
import com.game.quest.Quest;
import com.game.quest.QuestEvent;
import com.server.State;
import com.server.command.ComparableCommand;
import com.server.command.DieCommand;
import com.server.command.RescheduledDummySpell;
import com.server.command.RespawnCommand;

/**
 * @author TBworkstation
 * 
 */
public class FlameThrower extends AbstractSpell {

  /**
   * 
   */
  private static final long serialVersionUID= -4930216012579302395L;

  /**
   * Creates a new flamethrower {@link AbstractSpell}
   */
  public FlameThrower() {
    super(LEVEL_REQ, SPELL_REQ, MP_COST, DIFFICULTY, RANGE);
  }

  @Override
  public AbstractSpell newInstance() {
    return new FlameThrower();
  }

  /**
   * Returns a new instance of a {@link FlameThrower} DURING INITIALIZATION
   * ONLY!!!
   * 
   * @return a new flamethrower {@link AbstractSpell} for another
   *         {@link Creature}
   */
  public static AbstractSpell getNewInstance() {
    return new FlameThrower();
  }

  @Override
  public ComparableCommand cast(State state, String casterName, int targetX, int targetY, int damage) {
    Creature caster= null;
    caster= (Creature) state.getCharacter(casterName);

    Directions dir= caster.getDirection();

    GameMap map= state.getGameMap(caster.getMap());
    Tile start= map.getTile(caster.getPosition().x, caster.getPosition().y);

    for (int i= 0; i < RANGE; i++) {
      int newx= start.getPosition().x + dir.map_delta.x;
      int newy= start.getPosition().y + dir.map_delta.y;
      start= map.getTile(newx, newy);

      if (start.getOccupant() == null) continue;
      else if (start.getOccupant() instanceof Creature) {
        Creature victim= (Creature) start.getOccupant();

        victim.changeStat(Stat.HEALTHPOINTS, -damage);

        if (victim.getCurrStat(Stat.HEALTHPOINTS) <= 0) {
          if (state.getCharacter(casterName) instanceof Player) {
            Player player= (Player) caster;
            Quest playerQuest= player.getPlayersQuest();
            QuestEvent qe= victim.getQuestEvent();
            playerQuest.checkTop(qe);
          }

          ComparableCommand com;
          com= new DieCommand(caster.name, victim.name);
          com.setState(state);
          com.execute();
          return new RespawnCommand(victim);
        }
      }
    }
    return new RescheduledDummySpell(casterName, this, caster
        .getCurrStat(Stat.INTELLIGENCE), caster.getPosition().x, caster
        .getPosition().y, targetX, targetY, damage);
  }

  @Override
  public int setDamage(State state, String casterName) {
    return 2;
  }

  @Override
  public String toString() {
    return "Flame Thrower";
  }

  /* ========================== PRIVATE PARTS ============================= */

  private static final int LEVEL_REQ= 10;
  private static final List<AbstractSpell> SPELL_REQ= new ArrayList<AbstractSpell>();

  private static int MP_COST= 2;
  private static int DIFFICULTY= 30;
  private static int RANGE= 5;
}
