/**
 * 
 */
package com.game.characters.spells;

import java.util.ArrayList;
import java.util.List;

import com.game.characters.Creature;
import com.game.characters.Player;
import com.game.characters.Creature.Stat;
import com.game.environment.GameMap;
import com.game.environment.SearchState;
import com.game.environment.GameMap.BFSIterator;
import com.game.environment.tiles.Tile;
import com.game.quest.Quest;
import com.game.quest.QuestEvent;
import com.server.GameScheduler;
import com.server.State;
import com.server.command.ComparableCommand;
import com.server.command.DieCommand;
import com.server.command.RescheduledDummySpell;
import com.server.command.RespawnCommand;

/**
 * @author TBworkstation
 * 
 */
public class ArmageddonAndThenSome extends AbstractSpell {

  /**
   * 
   */
  private static final long serialVersionUID= 3726786288187342141L;

  /**
   * 
   */
  public ArmageddonAndThenSome() {
    super(LEVEL_REQ, SPELL_REQ, MP_COST, DIFFICULTY, RANGE);
  }

  @Override
  public AbstractSpell newInstance() {
    return new ArmageddonAndThenSome();
  }

  @Override
  public ComparableCommand cast(State state, String casterName, int targetX, int targetY, int damage) {
    Creature caster= null;
    caster= (Creature) state.getCharacter(casterName);

    GameMap map= state.getGameMap(caster.getMap());
    Tile center= map.getTile(targetX, targetY);

    BFSIterator iter= map.getBFSIterator(center, RANGE);

    while (iter.hasNext()) {
      SearchState<Tile> node= iter.next();
      Tile target= node.getNode();

      if (target.getOccupant() == null) continue;
      if (target.getOccupant() == caster) continue;
      else if (target.getOccupant() instanceof Creature) {
        Creature victim= (Creature) target.getOccupant();
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

          com= new RespawnCommand(victim);
          GameScheduler gs= state.getScheduler();
          gs.schedule(com);
        }
      }
    }
    return new RescheduledDummySpell(casterName, this, caster
        .getCurrStat(Stat.INTELLIGENCE), caster.getPosition().x, caster
        .getPosition().y, targetX, targetY, DAMAGE);

  }

  @Override
  public int setDamage(State state, String casterName) {
    return DAMAGE;

  }

  @Override
  public String toString() {
    return "Armageddon and then Some";
  }

  /* ========================== PRIVATE PARTS ============================= */

  private static final int LEVEL_REQ= 18;
  private static final List<AbstractSpell> SPELL_REQ= buildReq();

  private static final int MP_COST= 3;
  private static int DIFFICULTY= 100;
  private static int DAMAGE= 5; // scaled down like Professor Lane said we
  // should
  private static final int RANGE= 8;;

  private static List<AbstractSpell> buildReq() {
    List<AbstractSpell> temp= new ArrayList<AbstractSpell>();

    temp.add(new Zorch());
    temp.add(new FlameThrower());

    return temp;
  }

}
