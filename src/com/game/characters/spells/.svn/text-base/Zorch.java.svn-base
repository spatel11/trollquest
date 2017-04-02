/**
 * 
 */
package com.game.characters.spells;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
import com.server.command.RespawnCommand;

/**
 * @author TBworkstation
 * 
 */
public class Zorch extends AbstractSpell {

  /**
   * 
   */
  private static final long serialVersionUID= 662409529398660586L;

  /**
   * 
   */
  public Zorch() {
    super(LEVEL_REQ, SPELL_REQ, MP_COST, DIFFICULTY, RANGE);
  }

  @Override
  public AbstractSpell newInstance() {
    return new Zorch();
  }

  @Override
  public ComparableCommand cast(State state, String casterName, int targetX, int targetY, int damage) {
    Creature caster= null;
    caster= (Creature) state.getCharacter(casterName);

    GameMap map= state.getGameMap(caster.getMap());

    Tile target= map.getTile(targetX, targetY);

    int radius= (int) Math.floor(caster.getCurrStat(Stat.LEVEL) / 4);
    BFSIterator iter= map.getBFSIterator(target, radius);

    while (iter.hasNext()) {
      SearchState<Tile> node= iter.next();
      target= node.getNode();

      if (target.getOccupant() == null) continue;
      else if (!(target.getOccupant() instanceof Creature)) {
        continue;
      }
      Creature victim= (Creature) target.getOccupant();

      if (victim == caster) continue;

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
    return null;
  }

  @Override
  public int setDamage(State state, String casterName) {
    Creature caster= (Creature) state.getCharacter(casterName);

    Random rand= new Random(System.currentTimeMillis());

    return caster.getCurrStat(Stat.LEVEL) * (rand.nextInt(DAMAGE_RANGE) + 1)
        * caster.getCurrStat(Stat.INTELLIGENCE);

  }

  @Override
  public String toString() {
    return "Zorch";
  }

  /* ========================== PRIVATE PARTS ============================= */

  private static final int LEVEL_REQ= 5;
  private static final List<AbstractSpell> SPELL_REQ= buildReq();

  private static final int MP_COST= 20;
  private static final int DIFFICULTY= 50;
  private static final int DAMAGE_RANGE= 5;
  private static final int RANGE= -1;

  private static List<AbstractSpell> buildReq() {
    List<AbstractSpell> temp= new ArrayList<AbstractSpell>();

    temp.add(new Zot());

    return temp;
  }
}
