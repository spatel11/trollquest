package com.game.characters;

import java.io.File;

import com.game.items.IllegalInventoryException;
import com.game.items.Loot;
import com.game.items.Swag;
import com.game.quest.QuestEvent;

/**
 * @author TBworkstation
 */
public abstract class NPC extends GameCharacter implements Scriptable {

  /** NPC serial ID */
  private static final long serialVersionUID = -7080933708475703109L;

  /**
   * Creates a new NPC with a set Name, gender, animation directory, and default
   * inventory
   * 
   * @param name
   *          the name of the NPC
   * @param gender
   *          the {@link Gender} of the NPC
   * @param anim_directory
   *          the directory path of the NPC
   * @param inventory
   *          the default inventory of the NPC
   */
  public NPC(String name, Gender gender, String anim_directory, Loot inventory,
      QuestEvent qe) {
    super(name, gender, anim_directory, qe);

    for (Swag s : inventory.getSwagList()) {
      try {
        this.getInventory().add(s);
      }
      catch (IllegalInventoryException e) {
        // TODO proper catch block
      }
    }

    this.getInventory().makeDeposit(inventory.getMoolah());
  }

  @Override
  public String getScript() {
    return scriptFile + name + ".nano";
  }

  /* ============================= PRIVATE PARTS ============================ */

  protected static final String scriptFile= "data" + File.separatorChar
      + "NanoScripts" + File.separatorChar + "gameScripts" + File.separatorChar;
}
