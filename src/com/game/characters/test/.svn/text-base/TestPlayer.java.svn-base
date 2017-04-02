package com.game.characters.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.game.characters.GameCharacter.Gender;
import com.game.characters.Creature.Stat;
import com.game.characters.Fighter;
import com.game.characters.Mage;
import com.game.characters.Player;
import com.game.characters.Thief;
import com.game.items.IllegalInventoryException;
import com.game.items.Swag;
import com.game.items.armor.Armor;
import com.game.items.armor.PlateMail;
import com.game.items.weapon.RustyDagger;
import com.game.items.weapon.Weapon;

/**
 * Tests the creations of playable characters and their default starting
 * conditions
 * 
 * @author TBworkstation
 * 
 */
public class TestPlayer {

  /** */
  @Before
  public void setUp() {
    fighter= new Fighter("The Thing", Gender.IT);
    thief= new Thief("John Smith", Gender.MALE);
    mage= new Mage("Jane Smith", Gender.FEMALE);
  }

  /** */
  @After
  public void tearDown() {
    mage= null;
    fighter= null;
    thief= null;
  }

  /**
   * Tests the creation of a {@link Mage} class and all the default requirements
   * by polling stats and its default weapons
   */
  @Test
  public void testMagePlayerCreation() {
    // Testing the intital startup stats for a mage
    assertEquals(1, mage.getBaseStat(Stat.LEVEL));
    assertEquals(8, mage.getBaseStat(Stat.HEALTHPOINTS));
    assertEquals(20, mage.getBaseStat(Stat.MAGICPOINTS));
    assertEquals(5, mage.getBaseStat(Stat.STRENGTH), 5);
    assertEquals(8, mage.getBaseStat(Stat.SPEED), 8);
    assertEquals(20, mage.getBaseStat(Stat.INTELLIGENCE), 20);
    assertEquals(1000 * Math.pow(1.5, mage.getBaseStat(Stat.LEVEL) + 1), mage.getBaseStat(Stat.EXPERIENCE), 0);
    assertEquals(0, mage.getInventory().getMoolah());
    assertEquals(Gender.FEMALE, mage.gender);

    assertEquals(10, mage.getCurrentWeapon().ENCUMBERANCE);
    assertEquals(10, mage.getCurrStat(Stat.ENCUMBERANCE));
  }

  /**
   * Tests the creation of a {@link Thief} class and all the default
   * requirements by polling stats and its default weapons
   */
  @Test
  public void testThiefPlayerCreation() {
    // Testing the intital startup stats for a thief
    assertEquals(1, thief.getBaseStat(Stat.LEVEL));
    assertEquals(15, thief.getBaseStat(Stat.HEALTHPOINTS));
    assertEquals(10, thief.getBaseStat(Stat.MAGICPOINTS));
    assertEquals(5, thief.getBaseStat(Stat.STRENGTH), 5);
    assertEquals(15, thief.getBaseStat(Stat.SPEED), 8);
    assertEquals(8, thief.getBaseStat(Stat.INTELLIGENCE), 20);
    assertEquals((int) (1000 * Math.pow(1.45, thief.getBaseStat(Stat.LEVEL) + 1)), thief.getBaseStat(Stat.EXPERIENCE));
    assertEquals(0, thief.getInventory().getMoolah());
    assertEquals(Gender.MALE, thief.gender);

    assertEquals(35, thief.getCurrentWeapon().ENCUMBERANCE);
    assertEquals(35, thief.getCurrStat(Stat.ENCUMBERANCE));
  }

  /**
   * Tests the creation of a {@link Fighter} class and all the default
   * requirements by polling stats and its default weapons
   */
  @Test
  public void testFighterPlayerCreation() {
    assertEquals(1, fighter.getBaseStat(Stat.LEVEL));
    assertEquals(20, fighter.getBaseStat(Stat.HEALTHPOINTS));
    assertEquals(0, fighter.getBaseStat(Stat.MAGICPOINTS));
    assertEquals(20, fighter.getBaseStat(Stat.STRENGTH), 5);
    assertEquals(10, fighter.getBaseStat(Stat.SPEED), 8);
    assertEquals(5, fighter.getBaseStat(Stat.INTELLIGENCE), 20);
    assertEquals((int) (1000 * Math.pow(1.55, fighter.getBaseStat(Stat.LEVEL) + 1)), fighter.getBaseStat(Stat.EXPERIENCE));
    assertEquals(0, fighter.getInventory().getMoolah());
    assertEquals(Gender.IT, fighter.gender);

    assertEquals(20, fighter.getCurrentWeapon().ENCUMBERANCE);
    assertEquals(20, fighter.getCurrStat(Stat.ENCUMBERANCE));
  }

  /**
   * Tests the level up functionality of the {@link Mage} player by polling its
   * stats after each level up for 50 level ups and ensures the stats increments
   * by the proper amounts
   */
  @Test
  public void testMageLevelUp() {
    int currLevel, currHealth, currMagic, currStr, currSpeed, currIntel;
    for (int i= 0; i < 50; i++) {
      currLevel= mage.getBaseStat(Stat.LEVEL);
      currHealth= mage.getBaseStat(Stat.HEALTHPOINTS);
      currMagic= mage.getBaseStat(Stat.MAGICPOINTS);
      currStr= mage.getBaseStat(Stat.STRENGTH);
      currSpeed= mage.getBaseStat(Stat.SPEED);
      currIntel= mage.getBaseStat(Stat.INTELLIGENCE);

      mage.levelUp();

      assertEquals(currLevel + 1, mage.getBaseStat(Stat.LEVEL));
      assertEquals(8, mage.getBaseStat(Stat.HEALTHPOINTS) - currHealth, 2);
      assertEquals(12.5, mage.getBaseStat(Stat.MAGICPOINTS) - currMagic, 2.5);
      assertEquals(2, mage.getBaseStat(Stat.STRENGTH) - currStr, 1);
      assertEquals(4, mage.getBaseStat(Stat.SPEED) - currSpeed, 2);
      assertEquals(5.5, mage.getBaseStat(Stat.INTELLIGENCE) - currIntel, 2.5);
      assertEquals((int) (1000 * Math.pow(1.5, mage.getBaseStat(Stat.LEVEL) + 1)), mage.getBaseStat(Stat.EXPERIENCE));
    }
  }

  /**
   * Tests the level up functionality of the {@link Thief} player by polling its
   * stats after each level up for 50 level ups and ensures the stats increments
   * by the proper amounts
   */
  @Test
  public void testThiefLevelUp() {
    int currLevel, currHealth, currMagic, currStr, currSpeed, currIntel;
    for (int i= 0; i < 50; i++) {
      currLevel= thief.getBaseStat(Stat.LEVEL);
      currHealth= thief.getBaseStat(Stat.HEALTHPOINTS);
      currMagic= thief.getBaseStat(Stat.MAGICPOINTS);
      currStr= thief.getBaseStat(Stat.STRENGTH);
      currSpeed= thief.getBaseStat(Stat.SPEED);
      currIntel= thief.getBaseStat(Stat.INTELLIGENCE);

      thief.levelUp();

      assertEquals(currLevel + 1, thief.getBaseStat(Stat.LEVEL));
      assertEquals(10, thief.getBaseStat(Stat.HEALTHPOINTS) - currHealth, 2);
      assertEquals(7.5, thief.getBaseStat(Stat.MAGICPOINTS) - currMagic, 2.5);
      assertEquals(3.5, thief.getBaseStat(Stat.STRENGTH) - currStr, 1.5);
      assertEquals(5.5, thief.getBaseStat(Stat.SPEED) - currSpeed, 2.5);
      assertEquals(3.5, thief.getBaseStat(Stat.INTELLIGENCE) - currIntel, 1.5);
      assertEquals((int) (1000 * Math.pow(1.45, thief.getBaseStat(Stat.LEVEL) + 1)), thief.getBaseStat(Stat.EXPERIENCE));
    }
  }

  /**
   * Tests the level up functionality of the {@link Fighter} player by polling
   * its stats after each level up for 50 level ups and ensures the stats
   * increments by the proper amounts
   */
  @Test
  public void testFighterLevelUp() {
    int currLevel, currHealth, currMagic, currStr, currSpeed, currIntel;
    for (int i= 0; i < 50; i++) {
      currLevel= fighter.getBaseStat(Stat.LEVEL);
      currHealth= fighter.getBaseStat(Stat.HEALTHPOINTS);
      currMagic= fighter.getBaseStat(Stat.MAGICPOINTS);
      currStr= fighter.getBaseStat(Stat.STRENGTH);
      currSpeed= fighter.getBaseStat(Stat.SPEED);
      currIntel= fighter.getBaseStat(Stat.INTELLIGENCE);

      fighter.levelUp();

      assertEquals(currLevel + 1, fighter.getBaseStat(Stat.LEVEL));
      assertEquals(12.5, fighter.getBaseStat(Stat.HEALTHPOINTS) - currHealth, 2.5);
      assertEquals(3, fighter.getBaseStat(Stat.MAGICPOINTS) - currMagic, 2);
      assertEquals(5.5, fighter.getBaseStat(Stat.STRENGTH) - currStr, 2.5);
      assertEquals(3.5, fighter.getBaseStat(Stat.SPEED) - currSpeed, 1.5);
      assertEquals(2, fighter.getBaseStat(Stat.INTELLIGENCE) - currIntel, 1);
      assertEquals((int) (1000 * Math.pow(1.55, fighter.getBaseStat(Stat.LEVEL) + 1)), fighter.getBaseStat(Stat.EXPERIENCE));
    }
  }

  /**
   * Tests basic inventory actions then ensures that the player's inventory
   * is consistent via the getter methods provided
   */
  @Test
  public void testInventory() {
    final int TEST_LIMIT= 10;
    int baseOffset= mage.getCurrStat(Stat.ENCUMBERANCE);

    for (int i= 0; i < TEST_LIMIT; i++) {
      Swag uranium= new Swag("uranium" + i, 100000, 50);
      try {
        mage.getInventory().add(uranium);
        assertEquals(baseOffset + uranium.ENCUMBERANCE, mage.getCurrStat(Stat.ENCUMBERANCE));

        mage.getInventory().remove(uranium);
        assertEquals(baseOffset, mage.getCurrStat(Stat.ENCUMBERANCE));
      }
      catch (IllegalInventoryException e) {
        fail(" Should be able to remove that dangerous uranium");
      }
    }
  }

  /**
   * Makes sure the player's inventory doesn't get corrupted when performing
   * actions that should return errors. Does this through checking if the proper
   * exceptions get thrown
   */
  @Test
  public void testBadInventoryActions() {
    Swag illegalSwag= new Swag("illegal drugs", 0, 0);
    // if its not in inventory we should get an exception here
    try {
      mage.getInventory().remove(illegalSwag);
      fail("item should not be in inventory");
    }
    catch (IllegalInventoryException e) {
    }

    // add it to inventory so we can remove it once, then test again
    try {
      mage.getInventory().add(illegalSwag);
      mage.getInventory().remove(illegalSwag);
    }
    catch (IllegalInventoryException e) {
      fail("should be able to remove it this time");
    }

    try {
      mage.getInventory().remove(illegalSwag);
      fail("should not be in inventory this time");
    }
    catch (IllegalInventoryException e) {
    }

    // testing for bad operations with moolah
    mage.getInventory().makeDeposit(100000);
    assertEquals(100000, mage.getInventory().getMoolah());

    try {
      mage.getInventory().makeWithdrawal(100001);
      fail("trying to make a withdrawal larger than the amount in inventory");
    }
    catch (IllegalInventoryException e) {
    }

    try {
      mage.getInventory().makeWithdrawal(100000);
    }
    catch (IllegalInventoryException e) {
      fail();
    }
  }

  /**
   * Tests the functionality of equipping a weapon by equipping and unequipping
   * a weapon to ensure you can't unequip a null weapon.
   */
  @Test
  public void testEquippingWeapon() {
    Weapon excalibur= new RustyDagger();

    try {
      mage.getInventory().add(excalibur);
      mage.equipWeapon(excalibur);
    }
    catch (IllegalInventoryException e) {
      fail();
    }

    try {
      mage.unequipWeapon();
      mage.getInventory().remove(excalibur);
    }
    catch (IllegalInventoryException e) {
      fail(e.getMessage());
    }

    try {
      mage.equipWeapon(excalibur);
      fail();
    }
    catch (IllegalInventoryException e) {
    }
  }

  /**
   * Tests the validity of equipping and unequipping armors in a similar manner
   * as equipping weapons
   */
  @Test
  public void testEquippingArmor() {
    Armor plateMail= new PlateMail();

    try {
      mage.getInventory().add(plateMail);
    }
    catch (IllegalInventoryException e1) {
      fail(); // must be able to add the armor
    }

    try {
      mage.equipArmor(plateMail);

    }
    catch (IllegalInventoryException e) {
      fail();
    }

    assertEquals(plateMail.getArmorVal(), mage.getArmor().getArmorVal());
    assertFalse(mage.getInventory().getSwagList().contains(plateMail));

    try {
      mage.unequipArmor();
    }
    catch (IllegalInventoryException e) {
      fail();
      // should be able to unequip the armor
    }

    assertTrue(mage.getCurrStat(Stat.DEFENSEVALUE) == 0);
    assertTrue(mage.getInventory().getSwagList().contains(plateMail));
  }

  /**
   * Tests the debuffStat() method to make sure stats do not ever drop below 0.
   */
  @Test
  public void testDebuffStats() {
    for (Stat stat : Stat.values()) {
      thief.changeStat(stat, -100);
      mage.changeStat(stat, -100);
      if (stat != Stat.ENCUMBERANCE) {
        assertEquals(0, mage.getCurrStat(stat));
        assertEquals(0, thief.getCurrStat(stat));
      }
    }
  }

  /**
   * Tests the player's ability to recover correctly using the predefined
   * recovery functions by decreasing the player's hit points and calling the
   * recovery functions
   */
  @Test
  public void testRecovery() {
    int origMP= mage.getCurrStat(Stat.MAGICPOINTS);
    int origHP= mage.getCurrStat(Stat.HEALTHPOINTS);
    int level= mage.getCurrStat(Stat.LEVEL);

    mage.changeStat(Stat.MAGICPOINTS, -10);
    mage.changeStat(Stat.HEALTHPOINTS, -7);

    assertEquals(origHP - 7, mage.getCurrStat(Stat.HEALTHPOINTS));
    assertEquals(origMP - 10, mage.getCurrStat(Stat.MAGICPOINTS));

    mage.recovery();

    assertEquals(origMP - 10 + 3 * level, mage.getCurrStat(Stat.MAGICPOINTS));
    assertEquals(origHP - 7 + 1 * level, mage.getCurrStat(Stat.HEALTHPOINTS));

    mage.recovery();

    assertEquals(origMP - 10 + 3 * level * 2, mage.getCurrStat(Stat.MAGICPOINTS));
    assertEquals(origHP - 7 + 1 * level * 2, mage.getCurrStat(Stat.HEALTHPOINTS));

    mage.recovery();
    mage.recovery();

    assertEquals(origMP, mage.getCurrStat(Stat.MAGICPOINTS));

    for (int i= 0; i < 5; i++) {
      mage.recovery();
    }

    assertEquals(origMP, mage.getCurrStat(Stat.MAGICPOINTS));
    assertEquals(origHP, mage.getCurrStat(Stat.HEALTHPOINTS));
  }

  /**
   * Tests to make sure two {@link Mage}s in a similar space do not interfere
   * with each other's stats and inventory
   */
  @Test
  public void testMultiples() {
    Player mage2= new Mage("Secondus Magus", Gender.MALE);

    mage2.levelUp();

    assertFalse(mage2.name.equalsIgnoreCase(mage.name));
    assertFalse(mage2.getBaseStat(Stat.HEALTHPOINTS) == mage.getBaseStat(Stat.HEALTHPOINTS));
    assertFalse(mage2.getBaseStat(Stat.MAGICPOINTS) == mage.getBaseStat(Stat.MAGICPOINTS));
    assertFalse(mage2.getBaseStat(Stat.STRENGTH) == mage.getBaseStat(Stat.STRENGTH));
    assertFalse(mage2.getBaseStat(Stat.SPEED) == mage.getBaseStat(Stat.SPEED));
    assertFalse(mage2.getBaseStat(Stat.INTELLIGENCE) == mage.getBaseStat(Stat.INTELLIGENCE));
    assertFalse(mage2.getBaseStat(Stat.LEVEL) == mage.getBaseStat(Stat.LEVEL));
  }

  /* ========================== PRIVATE PARTS =============================== */
  private Player mage;
  private Player thief;
  private Player fighter;
}
