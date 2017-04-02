/**
 * @author Stephen Patel (spatel11@unm.edu)
 */
package com.server.command;

import java.awt.Point;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

import com.client.Message;
import com.client.Graphics.AnimationsOfACharacter.ActionType;
import com.game.characters.Creature;
import com.game.characters.Creature.Stat;
import com.game.characters.Monster;
import com.game.characters.Player;
import com.game.environment.GameMap;
import com.game.items.Loot;
import com.game.quest.Quest;
import com.game.quest.QuestEvent;

/**
 * Used to kill a Creature. This Creature still exists in the game, his action
 * state is set to die, and he is not allowed to do anything until respawn.
 * 
 * @author Stephen Patel
 * 
 */
public class DieCommand extends ComparableCommand {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -80612556505869310L;
	/**
	 * The name of the killer.
	 */
	private final String killerName;

	/**
	 * Creates a new DieCommand that kills the dyer by the killers hand.
	 * 
	 * @param killer
	 *            The Creature that landed the killing stroke.
	 * @param dyer
	 *            the Creature that died.
	 */
	public DieCommand(String killer, String dyer) {
		super(dyer, 0, 0, 0, false);
		this.killerName = killer;
	}

	/**
	 * This Command is valid if the creature does not exist in the game already,
	 * and it's health is 0 or less.
	 */
	@Override
	public boolean isValid() {
		Creature c = (Creature) state.getCharacter(charName);
		String userName = state.getUserName(charName);
		if (userName == null) {
			if (c == null)
				return false;
		} else {
			if (c.getCurrStat(Stat.HEALTHPOINTS) > 0)
				return false;
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DieCommand [");
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

	/**
	 * Kills a creature, drops that creatures loot, distributes xp, handles
	 * quest events, and if it's a player dying, clears it's queue.
	 */
	@Override
	public ComparableCommand execute() {
		synchronized (state) {
			System.err.println(killerName + " killed " + charName);
			Creature victim = (Creature) state.getCharacter(charName);
			Creature killer = (Creature) state.getCharacter(killerName);
			Loot reward = victim.dropInventory();
			GameMap map = state.getGameMap(victim.getMap());
			ComparableCommand com = new DropCommand(state, victim.name, reward);
			com.execute();

			// checking quest log
			if (killer instanceof Player) {
				Quest pcQuest = ((Player) killer).getPlayersQuest();
				QuestEvent qe = victim.getQuestEvent();
				if (pcQuest != null) {
					pcQuest.checkTop(qe);
				}
			}

			if (killer instanceof Player && killer != victim) {
				// begin distribution of XP. @author Taylor
				Set<Creature> attackerSet = victim.getAttackerSet();
				int distributedXP;
				// just a little insurance we don't divide by 0
				if (attackerSet.size() == 0) {
					// though if this gets called we do have a bug....
					distributedXP = victim.getBaseStat(Stat.EXPERIENCE);
					killer.changeStat(Stat.EXPERIENCE, distributedXP);

					ComparableCommand levelCom = new LevelPlayerCommand(
							killerName);
					levelCom.setState(this.state);
					if (levelCom.isValid()) {
						levelCom.execute();
					}
				} else {
					distributedXP = victim.getBaseStat(Stat.EXPERIENCE)
							/ attackerSet.size();
					// distribute the xp
					for (Creature attacker : attackerSet) {
						attacker.changeStat(Stat.EXPERIENCE, distributedXP);

						ComparableCommand levelCom = new LevelPlayerCommand(
								attacker.name);
						levelCom.setState(this.state);
						if (levelCom.isValid()) {
							levelCom.execute();
						}
					}
				}
				attackerSet.clear();
			} else if (killer instanceof Monster) {
				// begin distribution of XP. @author Taylor
				Set<Creature> attackerSet = victim.getAttackerSet();
				int distributedXP;
				// just a little insurance we don't divide by 0
				if (attackerSet.size() == 0) {
					// though if this gets called we do have a bug....
					distributedXP = victim.getBaseStat(Stat.EXPERIENCE) / 1000; // to
																				// make
					// the game fair
					killer.changeStat(Stat.EXPERIENCE, distributedXP);
				} else {
					distributedXP = (victim.getBaseStat(Stat.EXPERIENCE) / attackerSet
							.size()) / 1000;
					// distribute the xp
					for (Creature attacker : attackerSet) {
						attacker.changeStat(Stat.EXPERIENCE, distributedXP);
					}
				}
				attackerSet.clear();
			}

			Message msg = new Message("SERVER", charName
					+ " has been defeated!");
			msg.setComplete();
			state.addMessage(msg);
			Point loc = victim.getPosition();
			if (state.getUserName(charName) != null) {
				BlockingQueue<ComparableCommand> schedule = state
						.getSchedule(state.getUserName(charName));
				schedule.clear();
			}
			map.getTile(loc.x, loc.y).removeOccupant();
			killer.setSubX(0.0);
			killer.setSubY(0.0);
			killer.setSubXvel(0.0);
			killer.setSubYvel(0.0);
			killer.setActionState(ActionType.STAND);
			victim.setSubX(0.0);
			victim.setSubY(0.0);
			victim.setSubXvel(0.0);
			victim.setSubYvel(0.0);
			victim.setActionState(ActionType.DIE);
			state.removeCharacter(victim.name);
			state.addCharacter(victim);
		}
		return null;
	}
}
