package com.utilities;

/**
 * A mutable integer class.
 * @author Ian
 */
public class MutableInteger {
	/** Value of the mutable integer. */
	protected int _number;
	
	/**
	 * Constructor.
	 * @param number
	 */
	public MutableInteger(int number) {
		set(_number);
	}
	/**
	 * Constructor.
	 * @param number
	 */
	public MutableInteger(float number) {
		set(_number);
	}
	
	/**
	 * Sets the integer.  
	 * @param number
	 */
	public void set(int number) {
		_number = number;
	}
	/**
	 * Sets the integer.  Takes a floating point number and rounds it
	 * to the nearest integer, then stores that.
	 * @param number
	 */
	public void set(float number) {
		_number = Math.round(number);
	}
	/**
	 * Returns the value of the integer.
	 * @return the value.
	 */
	public int get() {
		return _number;
	}
	
	/**
	 * Increments the integer.
	 */
	public void increment() {
		++_number;
	}
	/**
	 * Decrements the integer.
	 */
	public void decrement() {
		--_number;
	}
}
