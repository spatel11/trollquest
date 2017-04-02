/**
 * 
 */
package com.game.characters.content.npcs;

import java.io.File;

import com.game.characters.NPC;
import com.game.items.IllegalInventoryException;
import com.game.items.Loot;
import com.game.items.Swag;
import com.game.items.SwagFactory;
import com.game.items.armor.ChainMailBikini;
import com.game.items.armor.PlateMail;
import com.game.items.armor.ScaleMail;
import com.game.items.armor.SnailMail;
import com.game.items.armor.WetTowel;
import com.game.items.weapon.BearClaws;
import com.game.items.weapon.Excalibur;
import com.game.items.weapon.LeadToothbrush;
import com.game.items.weapon.Mace;
import com.game.items.weapon.RolledUpSock;
import com.game.items.weapon.RustyDagger;
import com.game.items.weapon.ShortSword;
import com.game.quest.QuestEvent;
import com.game.quest.QuestEvent.QuestAction;

/**
 * @author TBworkstation
 * 
 */
public class Merchant extends NPC {

  /**
   * Creates a new merchant {@link NPC} with the standard merchant inventory
   * 
   * @param name
   *          the name of the Merchant
   */
  public Merchant(String name) {
    super(name, Gender.MALE, ANIM_DIR, generateMerchantInventory(),
        QUEST_EVENT_NULL);
  }

  /**
   * Merchant purchases the {@link Swag} from the player and returns the price
   * 
   * @param s
   *          the {@link Swag} the merchant purchases
   * @return the swag's value / 2, truncated as an integer
   */
  public int purchaseSwag(Swag s) {
    return s.VALUE / 2;
  }

  /**
   * Uses the {@link SwagFactory} to create a new instance of the {@link Swag}
   * item that will be sold by the merchant. Does no checking if the merchant
   * has the item in the inventory
   * 
   * @param s
   *          The {@link Swag} the merchant is selling
   * @return a new instance of the {@link Swag} the merchant is selling
   */
  public Swag sellSwag(Swag s) {
    return SwagFactory.getNewInstance(s);

  }

  @Override
  public String getScript() {
    return NPC.scriptFile + "merchant.nano";
  }

  /* ===================== private parts ================= */


  private static final long serialVersionUID= 4708811747120583631L;

  private static final QuestEvent QUEST_EVENT_NULL= new QuestEvent("none",
      QuestAction.ACQUIRE, "", null); //
  private static final String ANIM_DIR= "merchant" + File.separatorChar;

  private static Loot generateMerchantInventory() {
    Loot temp= new Loot(0);

    try {
      temp.add(new BearClaws());
      temp.add(new Excalibur());
      temp.add(new LeadToothbrush());
      temp.add(new Mace());
      temp.add(new RolledUpSock());
      temp.add(new RustyDagger());
      temp.add(new ShortSword());
      temp.add(new ChainMailBikini());
      temp.add(new PlateMail());
      temp.add(new ScaleMail());
      temp.add(new SnailMail());
      temp.add(new WetTowel());
    }
    catch (IllegalInventoryException e) {
      // should never get here in a new loot container
    }

    return temp;
  }

}
