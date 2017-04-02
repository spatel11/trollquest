/**
 * 
 */
package com.game.characters.content;

import java.util.ArrayList;
import java.util.List;

import com.game.characters.content.monsters.Bear;
import com.game.characters.content.monsters.Orc;
import com.game.characters.content.monsters.ProfessorLane;
import com.game.characters.content.npcs.Gerald;
import com.game.characters.content.npcs.Harold;
import com.game.characters.content.npcs.Helga;
import com.game.characters.content.npcs.Olga;
import com.game.characters.content.npcs.ProfessorCastellanos;
import com.game.items.IllegalInventoryException;
import com.game.items.Loot;
import com.game.items.weapon.PipeCleanerOfDoom;
import com.game.quest.Quest;
import com.game.quest.QuestEvent;

/**
 * @author TBworkstation
 *
 */
public abstract class QuestList {

  /**
   * Returns a copy of the main {@link Quest} for newly instantiated players.
   * 
   * @return the Main {@link Quest} for TrollQuest.
   */
  public static Quest getMainQuest() {
  	return GetMainQuest();
  }

  /* ======================== PRIVATE PARTS ================================ */

  private static Quest GetMainQuest() {
  	List<QuestEvent> steps = new ArrayList<QuestEvent>();
  	Loot loot = new Loot(1000000);
  	try {
	    loot.add(new PipeCleanerOfDoom());
    } catch (IllegalInventoryException e) {
	    // its a new loot container. we shouldn't get here
    }
  	
    steps.add(Harold.getHarold().getQuestEvent());
    steps.add(new Bear(1).getQuestEvent());
    steps.add(Gerald.getGerald().getQuestEvent());
    steps.add(Helga.getHelga().getQuestEvent());
    Orc o= new Orc(1); // temporary Orc
    steps.add(o.getQuestEvent());
    steps.add(o.getQuestEvent());
    steps.add(o.getQuestEvent());
    steps.add(o.getQuestEvent());
    steps.add(o.getQuestEvent());
    steps.add(Olga.getOlga().getQuestEvent());
    steps.add(ProfessorCastellanos.getProf().getQuestEvent());
    steps.add(ProfessorLane.getProfLane().getQuestEvent());
  	
  	return new Quest("Main Quest", steps, loot);
    // TODO FILL THIS OUT
  }
}
