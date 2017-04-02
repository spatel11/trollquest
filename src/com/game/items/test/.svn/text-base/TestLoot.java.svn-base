/**
 * 
 */
package com.game.items.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.game.items.IllegalInventoryException;
import com.game.items.Loot;
import com.game.items.Swag;
import com.game.items.armor.PlateMail;
import com.game.items.weapon.Excalibur;
import com.game.items.weapon.PipeCleanerOfDoom;

/**
 * @author TBworkstation
 * 
 */
public class TestLoot {

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    moneyBag= new Loot(100);
    singleItem= new Loot(new Swag("uranium", 100000, 500));
    inventory= new Loot(new Excalibur());
    inventory.add(new Swag("uranium", 100000, 500));
    inventory.add(new PlateMail());
    inventory.makeDeposit(1234567); // 1,234,567 look i'm a millionaire!
  }

  /**
   * Tests the initialization of inventory by using all three constructors and
   * checking inventory contents using the getter methods in the {@link Loot}
   * class
   */
  @Test
  public void testInitialization() {
    assertEquals(100, moneyBag.getMoolah());
    assertEquals(0, moneyBag.getEncumberance());
    assertEquals(0, moneyBag.getNonNullElementCount());
    assertEquals(0, moneyBag.getSwagList().size());

    assertEquals(0, singleItem.getMoolah());
    assertEquals(500, singleItem.getEncumberance());
    assertEquals(1, singleItem.getNonNullElementCount());
    assertEquals(1, singleItem.getSwagList().size());

    assertEquals(1234567, inventory.getMoolah());
    assertEquals(500 + new PlateMail().ENCUMBERANCE, +new Excalibur().ENCUMBERANCE, inventory.getEncumberance());
    assertEquals(3, inventory.getNonNullElementCount());
    assertEquals(3, inventory.getSwagList().size());
  }

  /**
   * Tests the merging of two inventories by merging an empty inventory with 
   * just money into an inventory with just items. Checks the current inventory
   * to make sure there items were transferred correctly, then checks the old
   * inventory to make sure it has nothing in it.
   */
  @Test
  public void testMerge() {
    try {
      moneyBag.merge(singleItem);
    }
    catch (IllegalInventoryException e) {
      fail();
    }

    assertEquals(100, moneyBag.getMoolah());
    assertEquals(500, moneyBag.getEncumberance());
    assertEquals(1, moneyBag.getNonNullElementCount());
    assertEquals(1, moneyBag.getSwagList().size());
    assertEquals(0, singleItem.getMoolah());
    assertEquals(0, singleItem.getEncumberance());
    assertEquals(0, singleItem.getNonNullElementCount());
    assertEquals(0, singleItem.getSwagList().size());
  }

  /**
   * Makes sure we receive the correct exceptions when trying to merge to {@link Loot}
   * objects together if the size of the new {@link Loot} object is too large
   */
  @Test
  public void testTooLargeMerge() {
    Loot fullLoot= new Loot(0);
    for (int i= 0; i < Loot.MAX_INVENTORY; i++) {
      try {
        fullLoot.add(new PlateMail());
      }
      catch (IllegalInventoryException e) {
        fail("I really value plate mail ok? let me fill my inventory");
      }
    }

    // couple other easy tests here. (maintaining correct nonNullCount, etc)
    assertEquals(Loot.MAX_INVENTORY, fullLoot.getNonNullElementCount());
    assertEquals(Loot.MAX_INVENTORY * new PlateMail().ENCUMBERANCE, fullLoot.getEncumberance());
    assertEquals(0, fullLoot.getMoolah());
    assertEquals(Loot.MAX_INVENTORY, fullLoot.getSwagList().size());

    try {
      singleItem.merge(fullLoot);
      fail("uhm... I dont have that many hands.");
    }
    catch (IllegalInventoryException e) {
    }

    try {
      fullLoot.merge(singleItem);
      fail("again, not that many hands");
    }
    catch (IllegalInventoryException e) {
    }
  }

  /**
   * Test simple adding of {@link Swag} to a {@link Loot} item. Checks the
   * inventory's properties using the getter methods to ensure the item was 
   * properly added.
   */
  @Test
  public void testAddSwag() {
    try {
      singleItem.add(null);
    }
    catch (IllegalInventoryException e) {
      fail();
    }

    assertEquals(1, singleItem.getNonNullElementCount());
    assertEquals(1, singleItem.getSwagList().size());

    try {
      singleItem.add(new PlateMail());
    }
    catch (IllegalInventoryException e) {
      fail();
    }

    for (int i= 0; i < Loot.MAX_INVENTORY; i++) {
      try {
        moneyBag.add(new PlateMail());
      }
      catch (IllegalInventoryException e) {
        fail();
      }
    }

    try {
      moneyBag.add(new PlateMail());
      fail();
    }
    catch (IllegalInventoryException e) {
      assertEquals(Loot.MAX_INVENTORY, moneyBag.getNonNullElementCount());
      assertTrue(moneyBag.getSwagAt(0).equals(moneyBag.getSwagAt(1)));
    }
  }

  /**
   * Tests the correct removal of {@link Swag} items by removing the item then
   * checking the inventory's properties to ensure the item was removed properly
   */
  @Test
  public void testRemove() {
    Swag drugs= new Swag("drugs", 0, 0);

    try {
      moneyBag.add(drugs);
      singleItem.add(drugs);
      inventory.add(drugs);
    }
    catch (IllegalInventoryException e) {
      fail();
    }

    // keep in mind remove must mean the POINTER to the object is the same,
    // so these are compared by == not .equals(object o)
    try {
      Swag handOff= moneyBag.remove(drugs);
      assertTrue(handOff == drugs);
      assertEquals(0, moneyBag.getNonNullElementCount());

    }
    catch (IllegalInventoryException e) {
      fail();
    }

    try {
      // drugs should be in index 1, as add() adds the swag to the first
      // available null spot in the array
      Swag handOff= singleItem.remove(1);
      assertTrue(handOff == drugs);
      assertEquals(1, singleItem.getNonNullElementCount());
    }
    catch (IllegalInventoryException e) {
      fail();
    }

    Swag handOff= null;
    try {
      handOff= inventory.remove(drugs);
      assertTrue(handOff == drugs);
      assertEquals(3, inventory.getNonNullElementCount());

      // already removed. should not be in the list anymore
      inventory.remove(drugs);
      fail();
    }
    catch (IllegalInventoryException e) {
      if (handOff == null) {
        fail();
      }
    }
  }

  /**
   * Test swapping two item's locations by swapping two actual items and making
   * sure the references to the items were updated. Then checks swapping an item
   * with a null reference, which should also be ok. 
   */
  @Test
  public void testSwap() {
    Loot moneyBag2= new Loot(100);
    Swag plate= new PlateMail();
    Swag pipe= new PipeCleanerOfDoom();

    try {
      moneyBag2.add(plate);
      moneyBag.add(plate);

      moneyBag.add(pipe);
      moneyBag2.add(pipe);
    }
    catch (IllegalInventoryException e) {
      fail();
    }

    moneyBag2.swap(0, 1);
    assertTrue(moneyBag2.getSwagAt(1) == plate);
    assertTrue(moneyBag2.getSwagAt(0) == pipe);

    moneyBag.swap(0, Loot.MAX_INVENTORY - 1);
    moneyBag.swap(1, 5);
    assertTrue(moneyBag.getSwagAt(Loot.MAX_INVENTORY - 1) == plate);
    assertTrue(moneyBag.getSwagAt(5) == pipe);
  }

  /**
   * tests whether or not you can deposit a negative amount
   */
  @Test(expected= IllegalArgumentException.class)
  public void testNegativeDeposit() {
    inventory.makeDeposit(-1);
  }

  /**
   * tests whether or not you can withdrawal a negative amount
   */
  @Test(expected= IllegalArgumentException.class)
  public void testNegativeWithdrawal() {
    try {
      inventory.makeWithdrawal(-1);
    }
    catch (IllegalInventoryException e) {
      fail("wrong exception");
    }
  }

  /**
   * Tests the banking system of the {@link Loot} item and whether it correctly
   * manages depositing and withdrawing arbitrary amounts of money.
   */
  @Test
  public void testBankingSystem() {
    try {
      inventory.makeWithdrawal(0);
    }
    catch (IllegalInventoryException e) {
      fail();
    }
    assertEquals(1234567, inventory.getMoolah());

    try {
      inventory.makeWithdrawal(1234567);
    }
    catch (IllegalInventoryException e) {
      fail();
    }

    assertEquals(0, inventory.getMoolah());

    try {
      inventory.makeWithdrawal(1);
      fail();
    }
    catch (IllegalInventoryException e) {
    }

    for (int i= 0; i < 1000000; i++) {
      try {
        inventory.makeWithdrawal(0);
      }
      catch (IllegalInventoryException e) {
        fail();
      }
    }

  }

  /* ======================= private parts ================================= */
  private Loot moneyBag, inventory, singleItem;
}
