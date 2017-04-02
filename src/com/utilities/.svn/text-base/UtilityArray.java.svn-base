package com.utilities;

/**
 * Encapsulates a bunch of array helper functions.
 * @author Ian
 */
public abstract class UtilityArray {
	/**
	 * Counts the number of non-null elements in the given array.
	 * @param array the array.
	 * @return the number of elements.
	 */
	public final static int countNonNull(Object[] array) {
		int count = 0;
		for (Object o : array) { if (o!=null) ++count; }
		return count;
	}
	
	/**
	 * Gets the index of the first null index.
	 * @param array the array to search in.
	 * @return the index, or the array's length if one does not exist.
	 */
	public final static int getFirstNullIndex(Object[] array) {
		return getFirstNullIndex(array,0);
	}
	/**
	 * Gets the index of the first null index.
	 * @param array the array to search in.
	 * @param start_index the index to start searching at.
	 * @return the index, or the array's length if one does not exist.
	 */
	public final static int getFirstNullIndex(Object[] array, int start_index) {
		int index = start_index;
		for (Object o : array) {
			if (o!=null) ++index;
			else break;
		}
		return index;
	}
	/**
	 * Swaps the elements at the given indices in the given array.
	 * @param array the array.
	 * @param index1 the first index.
	 * @param index2 the second index.
	 */
	public final static void swapElements(Object[] array, int index1, int index2) {
		Object temp = array[index1];
		array[index1] = array[index2];
		array[index2] = temp;
	}

	/**
	 * Returns the index of an element in an array, or -1 if it can't be found.
	 * @param array the array to search in.
	 * @param obj the object to look for.
	 * @return the index, or -1.
	 */
	public final static int indexOf(Object[] array, Object obj) {
		int i = 0; for (Object o : array) {
			if (o==obj) break;
			++i;
		}
		if (i==array.length) return -1;
		return i;
	}
	/**
	 * Reverses the elements of an array.
	 * @param array the array.
	 */
	public final static void reverse(Object[] array) {
		for (int i=0;i<array.length/2;++i) {
			Object temp = array[array.length-i-1];
			array[array.length-i-1] = array[i];
			array[i] = temp;
		}
	}
}