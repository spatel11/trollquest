package com.game.items;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.game.characters.GameCharacter;

/**
 * @author TBworkstation
 */
public final class Loot implements Serializable{

	/** Item's serial ID*/
	private static final long serialVersionUID = -3828339491225830033L;

	/**
	 * Creates a new loot object with the specified MOOLAH and list of SWAG items
	 * 
	 * @param moolah
	 *          the initial amount of MOOLAH in the loot bag
	 * @param items
	 *          the List of {@link Swag} in the loot bag
	 */
	public Loot(int moolah, List<Swag> items) {
		this.moolah= moolah;
    swags= new Swag[MAX_INVENTORY];
    items.toArray(swags);
    nonNullElements= items.size();
	}

  /**
   * Creates a Loot with no moolah, and the {@link Swag} specified in the param
   * as the sole element in the loot bag
   * 
   * @param swag
   *          the only {@link Swag} that will be in the loot bag when it is
   *          created
   */
  public Loot(Swag swag) {
    this.moolah= 0;
    swags= new Swag[MAX_INVENTORY];
    swags[0]= swag;
    nonNullElements= 1;
  }

  /**
   * Creates a Loot object with and empty Loot bag but a specified amount of
   * MOOLAH
   * 
   * @param moolah
   *          the amount of moolah the Loot bag will start with.
   */
	public Loot(int moolah) {
		this.moolah= moolah;
    swags= new Swag[MAX_INVENTORY];
    nonNullElements= 0;
	}

	  /**
   * Utility method that will merge two Loots together by adding the sum of
   * MOOLAH together, and combining both lists of {@link Swag}
   * 
   * **WARNING** THIS WILL REMOVE ALL ITEMS FROM THE PARAMETER'S INVENTORY! MAKE
   * SURE THIS IS CALLED IN THE RIGHT DIRECTION
   * 
   * @param lootBag
   *          the other Loot object whose contents will be copied
   * @throws IllegalInventoryException
   */
  public final void merge(Loot lootBag) throws IllegalInventoryException {
    if (lootBag.getNonNullElementCount() + nonNullElements > MAX_INVENTORY) {
      throw new IllegalInventoryException("Cannot concat inventory, it would result it too many items!");
    }
    for (int j= 0; j < MAX_INVENTORY; j++) {
      if (lootBag.getSwagAt(j) == null) continue;
      for (int i= 0; i < MAX_INVENTORY; i++) {
        if (swags[i] == null) {
          swags[i]= lootBag.remove(j);
          nonNullElements++;
          break;
        }
      }
    }
		moolah+= lootBag.moolah;
	}

	  /**
   * Returns a list of everything in the Loot item as an unmodifyable List.
   * 
   * @return an unmodifiable List of all {@link Swag} in the Loot
   */
	public List<Swag> getSwagList() {
    ArrayList<Swag> temp= new ArrayList<Swag>();
    for (int i= 0; i < MAX_INVENTORY; i++) {
      if (swags[i] == null) continue;
      else temp.add(swags[i]);
    }
    return Collections.unmodifiableList(temp);
	}

  /**
   * Adds a single {@link Swag} to the Loot object
   * 
   * @param swag
   *          the {@link Swag} that will be added to the list of Swags
   * @throws IllegalInventoryException
   *           if the inventory is full
   */
  public void add(Swag swag) throws IllegalInventoryException {
    if (swag == null) return;
    if (nonNullElements + 1 > MAX_INVENTORY) {
      throw new IllegalInventoryException("Not enough room in inventory");
    }
    for (int i= 0; i < MAX_INVENTORY; i++) {
      if (swags[i] == null) {
        swags[i]= swag;
        break;
      }
    }
    nonNullElements++;
	}

	/**
	 * Removes the specified {@link Swag} from the Loot container. If the
	 * {@link Swag} does not exist, it throws an IllegalInventoryException
	 * 
	 * @param swag
	 *          the {@link Swag} to be removed from the Loot container
	 * @return the {@link Swag} requested from the Loot container
	 * 
	 * @throws IllegalInventoryException
	 *           if the specified swag is not in the Loot object
	 */
	public Swag remove(Swag swag) throws IllegalInventoryException{
    for (int i= 0; i < MAX_INVENTORY; i++) {
      if (swags[i] == null) continue;
      if (swags[i].equals(swag)) {
        swags[i]= null;
        nonNullElements--;
        return swag;
      }
		}
    throw new IllegalInventoryException(swag.NAME + " is not in this inventory");
	}

  /**
   * Removes and returns a piece of {@link Swag} at a particular index
   * 
   * @param index
   *          The index the {@link Swag} is located at
   * @return the {@link Swag} at the specified index
   * @throws IllegalInventoryException
   *           if the specified index in null
   */
  public Swag remove(int index) throws IllegalInventoryException {
    if (swags[index] == null)
      throw new IllegalInventoryException("There is nothing there!");

    Swag temp= swags[index];
    swags[index]= null;
    nonNullElements--;
    return temp;
  }

  /**
   * Swaps two swag's locations in inventory
   * 
   * @param index1
   * @param index2
   */
 	public final void swap(int index1, int index2) {
    Swag s= swags[index1];
    swags[index1]= swags[index2];
    swags[index2]= s;
	}
	
	/**
	 * Makes a monetary deposit into the Loot container. Will throw an {@link IllegalArgumentException}
	 * if the amount specified is less than 0. 
	 * 
	 * @param amount the integer amount of moolah to be deposited
	 * @throws IllegalArgumentException if the amount is less than 0
	 */
	public final void makeDeposit(int amount) throws IllegalArgumentException {
		if (amount < 0) {
			throw new IllegalArgumentException("Amount cannot be negative");
		}
		else {
			moolah += amount;
		}
	}
	/**
	 * Withdrawals a specified amount from the Moolah in the Loot object. Will throw an
	 * {@link IllegalInventoryException} if there is not enough Moolah in the Loot and an
	 * {@link IllegalArgumentException} if the amount is < 0
	 * 
	 * @param d the amount to be withdrawn from the total moolah in the Loot
	 * @return the amount withdrawn. It will be exactly the same as the amount parameter
	 * @throws IllegalArgumentExcpetion if the amount < 0
	 * @throws IllegalInventoryException
	 */
	public final int makeWithdrawal(int d) throws IllegalInventoryException {
		if (d < 0) {
			throw new IllegalArgumentException("Amount cannot be negative");
		}
		else if (d > moolah) {
			throw new IllegalInventoryException("Cannot withdrawal " + d + " moolah from " + moolah);
		}
		else {
			moolah -= d;
			return d;
		}
	}

  /**
   * Returns the total number of moolah in an inventory
   * 
   * @return an integer representation of the total amount of moolah in an
   *         inventory
   */
	public final int getMoolah() {
		return moolah;
	}

  /**
   * Returns the {@link Swag} at a particular index, will return null if nothing
   * is there
   * 
   * @param index
   *          the the {@link Swag} is located at
   * @return the {@link Swag} located at the specified index. Will return null
   *         if nothing is there
   */
	public Swag getSwagAt(int index) {
    return swags[index];
	}

  /**
   * Calculates and returns the total encumberance of a loot object
   * 
   * @return an integer value of the total encumberance of a loot object
   */
	public final int getEncumberance() {
		int enc= 0;
		for (int i =0; i < MAX_INVENTORY; i++) {
			if (swags[i] == null) continue;
			else 
				enc += swags[i].ENCUMBERANCE;
		}
		return enc;
	}
	
  /**
   * Returns the total number of all {@link Swag} in the loot inventory.
   * 
   * @return the total number of non null {@link Swag} in the inventory
   */
  public int getNonNullElementCount() {
    return nonNullElements;
	}

  /* ========================== PUBLIC FIELS =============================== */
  /** Maximum number of swag's a {@link GameCharacter} can hold */
  public static final int MAX_INVENTORY = 48;

  /** Maximum number of swags in the x direction */
  public static final int MAX_SIZE_X    = 6;

  /** Maximum number of swags in the y direction */
  public static final int MAX_SIZE_Y    = 8;

  /* ======================== PRIVATE PARTS ================================ */
  private int             moolah;
  private Swag[]          swags;
  private int             nonNullElements;

}