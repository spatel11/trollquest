/**
 * 
 */
package com.game.characters;

import java.awt.Point;
import java.io.File;
import java.util.Random;

import com.game.items.IllegalInventoryException;
import com.game.items.Loot;
import com.game.items.Swag;
import com.game.items.SwagFactory;
import com.game.items.weapon.Weapon;
import com.game.quest.QuestEvent;

/**
 * @author TBworkstation
 * 
 */
public abstract class Monster extends Creature implements Scriptable {

  /** Monster serial ID */
  private static final long serialVersionUID= -6675323262673862231L;

  /**
   * @param name
   */
  public Monster(String name, Gender gender, String anim_directory, int baseHP,
      int baseMP, int baseSPD, int baseSTR, int baseINT, int baseXP,
      int hpMult, int mpMult, Weapon baseWeap, QuestEvent e, int hpRange,
      int mpRange, int speedRange, int strRange, int intRange) {
    super(name, gender, anim_directory, baseHP, baseMP, baseSPD, baseSTR,
        baseINT, baseXP, hpMult, mpMult, baseWeap, e);

    HP_RANGE= hpRange;
    MP_RANGE= mpRange;
    SPD_RANGE= speedRange;
    STR_RANGE= strRange;
    INT_RANGE= intRange;
    XP= baseXP;
    rand= new Random(System.currentTimeMillis());

    defaultSpawnLoc= new Point(-1, -1);
  }

  @Override
  public int getRespawnTime() {
    return MONSTER_RESPAWN_TIME;
  }

  @Override
  public Loot dropInventory() {
    Loot loot= new Loot(this.getInventory().getMoolah());
    Loot monsterLoot= this.getInventory();

    for (Swag swag : monsterLoot.getSwagList()) {
      try {
        loot.add(SwagFactory.getNewInstance(swag));
      }
      catch (IllegalInventoryException e) {
        return loot; // return what we can
      }
    }

    return loot;
  }

  @Override
  public String getScript() {
    String[] fileName= name.split("[0-9]+");
    return gameScriptDir + fileName[0] + ".nano";
  }

  /**
   * Sets the monster's default spawn location in a map. Once this is set, it
   * cannot be changed, and any subsequent calls to this method will be ignored
   * 
   * @param p
   *          the point the monster will be respawned at by default
   */
  public void anchorSpawnLoc(Point p) {
    if (defaultSpawnLoc.x == -1 && defaultSpawnLoc.y == -1) {
      defaultSpawnLoc= p;
    }
  }

  /**
   * Gets the spawn location
   * 
   * @return the point spawn location for a monster. this does not change after
   *         initialization
   */
  public Point getSpawnloc() {
    return defaultSpawnLoc;
  }

  /* ================================ PRIVATE PARTS ========================= */
  protected Point defaultSpawnLoc;

  private static final int MONSTER_RESPAWN_TIME= 60000;

  private static final String gameScriptDir= "data" + File.separatorChar
      + "NanoScripts" + File.separatorChar + "gameScripts" + File.separatorChar;

  private final int HP_RANGE, MP_RANGE, SPD_RANGE, STR_RANGE, INT_RANGE, XP;
  private final Random rand;

  @Override
  public int levelHP() {
    return 1 + rand.nextInt(HP_RANGE);
  }

  @Override
  public int levelMP() {
    return 1 + rand.nextInt(MP_RANGE);
  }

  @Override
  public int levelSPD() {
    return 1 + rand.nextInt(SPD_RANGE);
  }

  @Override
  public int levelSTR() {
    return 1 + rand.nextInt(STR_RANGE);
  }

  @Override
  public int levelINT() {
    return 1 + rand.nextInt(INT_RANGE);
  }

  @Override
  public int levelXP() {
    return XP;
  }

  @Override
  public int getSpellRate() {
    // make it so monsters dont learn new spells
    // as long as this is above level 20 we are fine.
    // ideally this should not be hard coded
    return 50;
  }
}
