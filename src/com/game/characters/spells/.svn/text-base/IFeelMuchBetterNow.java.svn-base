/**
 * 
 */
package com.game.characters.spells;

import java.util.ArrayList;
import java.util.List;

import com.game.characters.Creature;
import com.game.characters.Creature.Stat;
import com.game.environment.GameMap;
import com.game.environment.tiles.Tile;
import com.server.State;
import com.server.command.ComparableCommand;

/**
 * @author TBworkstation
 * 
 */
public class IFeelMuchBetterNow extends AbstractSpell {

	/**
   * 
   */
	private static final long serialVersionUID = 7177624767797470610L;

	/**
   * 
   */
	public IFeelMuchBetterNow() {
		super(LEVEL_REQ, SPELL_REQ, MP_COST, DIFFICULTY, RANGE);
	}

	@Override
	public AbstractSpell newInstance() {
		return new IFeelMuchBetterNow();
	}

	@Override
	public ComparableCommand cast(State state, String casterName, int targetX,
 int targetY, int damage) {
		Creature caster = null;
		caster = (Creature) state.getCharacter(casterName);

		GameMap map = state.getGameMap(caster.getMap());

		Tile target = map.getTile(targetX, targetY);

		if (target.getOccupant() == null)
			return null;
		Creature victim = (Creature) target.getOccupant();

		int hpDiff = victim.getBaseStat(Stat.HEALTHPOINTS)
				- victim.getCurrStat(Stat.HEALTHPOINTS);

		victim.changeStat(Stat.HEALTHPOINTS, hpDiff);
		return null;
	}

	@Override
  public int setDamage(State state, String casterName) {
    return -1;
	}

	@Override
	public String toString() {
		return "I Feel Much Better Now";
	}

	/* ========================== PRIVATE PARTS ============================= */

	private static final int LEVEL_REQ = 12;
	private static final List<AbstractSpell> SPELL_REQ = buildReq();
	private static int MP_COST = 30;
	private static int DIFFICULTY = 80;
	private static int RANGE = 8;

	private static List<AbstractSpell> buildReq() {
		List<AbstractSpell> temp = new ArrayList<AbstractSpell>();

		temp.add(new GreatDrugs());

		return temp;
	}
}
