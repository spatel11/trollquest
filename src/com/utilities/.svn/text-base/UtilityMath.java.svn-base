package com.utilities;

import java.awt.Point;

/**
 * Encapsulates a bunch of math helper functions.
 * @author Ian
 */
public abstract class UtilityMath {
	/**
	 * Rounds a number to an integer.
	 * @param num the number.
	 * @return the integer.
	 */
	public final static int rndint(float num) {
		return Math.round(num);
	}
	/**
	 * Rounds a number to an integer.
	 * @param num the number.
	 * @return the integer.
	 */
	public final static int rndint(double num) {
		return (int)(Math.round(num));
	}
	
	/**
	 * Clamps a number to a range.
	 * @param <T> the type of the number.
	 * @param number the number.
	 * @param low the lower bound, inclusive.
	 * @param high the upper bound, inclusive.
	 * @return the clamped number.
	 */
	public final static <T extends Comparable<T>> T clamp(T number, T low, T high) {
		//return Math.max(Math.min(number,high),low);
		if (number.compareTo( low)<0) return low;
		if (number.compareTo(high)>0) return high;
		return number;
	}
	
	/**
	 * Gets the delta between two Point objects, as a Point.
	 * @param p1 the first point.
	 * @param p2 the second point.
	 * @return the delta.
	 */
	public final static Point getDelta(Point p1, Point p2) {
		return new Point(p1.x-p2.x, p1.y-p2.y);
	}
	/**
	 * Gets the sum of two Point objects, as a Point.
	 * @param p1 the first point.
	 * @param p2 the second point.
	 * @return their sum.
	 */
	public final static Point getSum(Point p1, Point p2) {
		return new Point(p1.x+p2.x, p1.y+p2.y);
	}
	/**
	 * Interprets the Point argument as a vector, and returns that vector's L2 norm.
	 * @param p the point.
	 * @return its length.
	 */
	public final static double getLength(Point p) {
		return Math.sqrt(p.x*p.x+p.y*p.y);
	}
	
	/**
	 * Does a CORRECT modulus operation.
	 * @param a the first number.
	 * @param b the second number.
	 * @return the result
	 */
	public final static int mod(int a, int b) {
		a %= b;
		if (a<0) a += b;
		return a;
	}
}