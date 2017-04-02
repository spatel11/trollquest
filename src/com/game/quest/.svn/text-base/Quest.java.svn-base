package com.game.quest;

import java.io.Serializable;
import java.util.List;
import java.util.Queue;

import com.game.items.Loot;
import com.game.items.Swag;

/**
 * Quests to complete for monetary rewards or item rewards
 * 
 * @author tberge01
 * 
 */
public class Quest implements Serializable {

	/**
	 * creates a new Quest with a {@link Queue} of {@link QuestEvent} steps that
	 * have to be completed, the name of the quest, and the loot reward.
	 */
	public Quest(String name, List<QuestEvent> steps, Loot reward) {
		questName = name;
		questSequence = steps;
		this.reward = reward;
		completed = false;
		INDEX = 0;
	}

	  /**
   * Returns a view of all the {@link QuestEvent}s left in a Quest
   * 
   * @return a {@link List} of all {@link QuestEvent}s that are in the quest
   */
	public List<QuestEvent> getAllSteps() {
		return questSequence;
	}

	/**
	 * Checks the top of the player's Quest with the QuestEvent passed in as a
	 * parameter
	 * 
	 * @param externalEvent
	 *            a {@link QuestEvent} that happened in the world that should be
	 *            checked against the players Quest object's sequence. If the
	 *            external {@link QuestEvent} is equivalent to the player's
	 *            questSequence's first element, the player's questSequence will
	 *            be updated with the next step of the Quest
	 */
	public void checkTop(QuestEvent externalEvent) {
		if (externalEvent == null) {
			return;
		}

		if (INDEX >= questSequence.size()) {
			return;
		}

		System.err.println("index " + INDEX + " ========================");
		System.err.println(questSequence.get(INDEX));
		if (!completed && externalEvent.equals(questSequence.get(INDEX))) {
			System.err.println("quest index  updated ========================");
			INDEX++;
		}

		if (questSequence.size() == INDEX) {
			completed = true;
		}
	}

	/**
	 * Returns the next {@link QuestEvent} that needs to be completed for the
	 * quest. Does not remove the event from the {@link Queue}
	 * 
	 * @return the next required {@link QuestEvent} that has to be completed
	 */
	public QuestEvent getCurrentStep() {
		if (INDEX >= questSequence.size()) {
			return null;
		} else {
			return questSequence.get(INDEX);
		}
	}
	
	  /**
   * Returns the current index of the {@link Quest}.
   * 
   * @return the integer index of the current step in the quest
   */
	public int getCurrentStepIndex() {
		return INDEX;
	}

	/**
	 * Returns the reward for completing the {@link Quest}
	 * 
	 * @return a {@link Loot} object containing some {@link Swag} and moolah
	 */
	public Loot getReward() {
		return reward;
	}

	/**
	 * Returns the string name of the quest
	 * 
	 * @return the string name of the quest
	 */
	public String getQuestName() {
		return questName;
	}

	/**
	 * Returns a boolean representing whether or not the {@link Quest} has been
	 * completed or not
	 * 
	 * @return a boolean representing whether the quest has been completed
	 */
	public Boolean isFinished() {
		return completed;
	}

	/* ===========================PRIVATE PARTS============================== */
	private final List<QuestEvent> questSequence;
	private final Loot reward;
	private final String questName;
	private int INDEX;

	private boolean completed;

	private static final long serialVersionUID = 3676185549698336904L;
}
