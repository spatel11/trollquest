package com.server.command;

import com.game.characters.Creature;
import com.game.characters.Creature.Stat;
import com.game.characters.spells.Open;
import com.server.State;

/**
 * Levels a player across the server/client connection by setting the level up
 * values only once on the server side.
 * 
 * @author TBworkstation
 * 
 */
public class LevelPlayerCommand extends ComparableCommand {


  /**
   * Creates a new command to level the indicated {@link Creature} by one
   * amount. This is done through the {@link Creature} class using the level*()
   * methods.
   * 
   * @param charName
   *          the character's name to level
   */
	public LevelPlayerCommand(String charName) {
		super(charName, 0, 0, 0, false);
		upHP = upINT = upMP = upSPD = upSTR = upXP = 0;
	}

	@Override
	public boolean isValid() {
		Creature creature = (Creature) state.getCharacter(charName);
		int currXP = creature.getCurrStat(Stat.EXPERIENCE);
		int targetXP = creature.getBaseStat(Stat.EXPERIENCE);
		
		if (currXP < targetXP) {
			return false;
		}
		
		if (creature.getCurrStat(Stat.LEVEL) >= 20) {
			return false;
		}
		
		return true;
	}

	@Override
	public ComparableCommand execute() {
		Creature creature = (Creature) state.getCharacter(charName);

		creature.levelUp();
		creature.levelIncreaseHP(upHP);
		creature.levelIncreaseMP(upMP);
		creature.levelIncreaseSTR(upSTR);
		creature.levelIncreaseSPD(upSPD);
		creature.levelIncreaseINT(upINT);
		
		creature.levelSetXP(upXP);

		
    if (creature.getCurrStat(Stat.LEVEL) == 3) {
      creature.getSpellInventory().addSpell(new Open());
    }
		if (creature.getCurrStat(Stat.LEVEL)% creature.getSpellRate() == 0) {
			creature.getSpellInventory().addNextSpell(creature);
		}
		return null;
	}

	@Override
	public void setState(State state) {
		super.setState(state);
		
		Creature creature = (Creature) state.getCharacter(charName);
		
		upHP = creature.levelHP();
		upMP = creature.levelMP();
		upSTR = creature.levelSTR();
		upSPD = creature.levelSPD();
		upINT = creature.levelINT();
		upXP = creature.levelXP();
	}
	
	
	/* ================== private parts ==================== */
	
	private int upHP, upMP, upSTR, upSPD, upINT, upXP;
	

	private static final long serialVersionUID = 5056612890420680070L;

}
