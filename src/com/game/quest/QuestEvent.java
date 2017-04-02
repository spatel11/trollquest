package com.game.quest;

import java.io.Serializable;

import com.game.characters.Player;
import com.game.items.Swag;

/**
 * A {@link QuestEvent} that can be compared to other {@link QuestEvent}s to
 * determine if the correct action was taken
 * 
 * @author tberge01
 * 
 */
public class QuestEvent implements Serializable {

  /**
   * Creates a new quest Event with the character's name and the action took
   * place
   * 
   * @param characterName
   *          the target character's name the action was applied to REQUIRED.
   *          NPC and monster names should be exact, and {@link Player}s should
   *          simply be "player" so the characterName will apply to all clients
   *          connected to the server
   * @param qa
   *          the {@link QuestAction} that was performed REQUIRED
   * @param questDialog
   *          a String, if any, offering any sort of help or text cues for the
   *          quest
   * @param questSwag
   *          a {@link Swag} if any, the {@link QuestAction} requires
   */
  public QuestEvent(String characterName, QuestAction qa, String questDialog,
      Swag questSwag) {
    this.characterName= characterName;
    this.questDialog= questDialog;
    this.questAction= qa;
    this.questSwag= questSwag;
  }

  /**
   * Returns the quest dialog if it has one, may return null
   * 
   * @return the quest dialog associated with the quest
   */
  public String getQuestDialog() {
    return questDialog;
  }

  /**
   * Returns the {@link QuestAction} required to activate this
   * {@link QuestEvent}
   * 
   * @return a {@link QuestAction} associated with this {@link QuestEvent}
   */
  public QuestAction getQuestAction() {
    return questAction;
  }

  /**
   * Returns the character's name associated with the {@link QuestEvent} and
   * that the {@link QuestAction} is applied to
   * 
   * @return the character's name the {@link QuestAction} is applied to
   */
  public String getCharacterName() {
    return characterName;
  }

  /**
   * Returns the required {@link Swag} associated with this {@link QuestEvent}
   * 
   * @return the required {@link Swag} associated with this {@link QuestEvent}
   */
  public Swag getQuestSwag() {
    return questSwag;
  }
  
  @Override
  public String toString() {
  	String s = "";
  	
  	s+= "You must " + questAction.toString().toLowerCase();
  	
  	if (questDialog != null) {
  		s+= " to " + characterName;
  	}
  	else if (questSwag != null) {
  		s+= " " + questSwag.NAME.toLowerCase();
  	}
  	else {
  		s+= " an enemy " + characterName;
  	}
  	
  	return s;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) return false;
    if (!(o instanceof QuestEvent)) return false;

    QuestEvent e= (QuestEvent) o;

    if (!e.getCharacterName().equalsIgnoreCase(characterName)) return false;
    if (e.getQuestAction() != questAction) return false;
    if (e.getQuestDialog() != null) {
      if (!e.getQuestDialog().equals(questDialog)) return false;
    }
    if (e.getQuestSwag() != null) {
      if (e.getQuestSwag().equals(questSwag)) return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hash= 0;

    hash+= characterName.hashCode();
    hash+= questAction.hashCode();

    if (questDialog != null) hash+= questDialog.hashCode();
    if (questSwag != null) hash+= questSwag.hashCode();

    return hash;
  }

  /* =========================== QuestActions ============================= */

  /**
   * The types of actions that can initiate {@link QuestEvent}
   */
  public enum QuestAction {
    /** if a creature dies */
    KILL,
    /** if a creature talks */
    TALK,
    /** if a creature acquires a particular swag */
    ACQUIRE;
  }

  /* =========================== private parts ============================ */
  private final String      characterName;
  private final String      questDialog;
  private final QuestAction questAction;
  private final Swag        questSwag;

  private static final long serialVersionUID = 2159877048065922820L;
}
