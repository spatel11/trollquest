package com.utilities;

/**
 * Pretty cubic class for pretty cubic interpolation.
 * Uses some basic Calculus to derive a cubic fit
 * to interpolate between two points, with derivative
 * zero at each point.
 * 
 * @author Ian
 */
public class CubicInterpolator { //Not final.
	/** Cubic constants.  Turns out, c is always 0.0, so it is ignored. */
	private final double a,b,d;
	
	/**
	 * Constructor.
	 * @param start the starting value to interpolate.
	 * @param end the ending value to interpolate.
	 */
	public CubicInterpolator(double start, double end) {
		//Begin:
		//  f (x) =   a*x^3 +   b*x^2 + c*x + d
		//  f'(x) = 3*a*x^2 + 2*b*x   + c
		
		//Notice:
		//  f (0) = start;  =>  a*0^3 + b*0^2 + c*0 + d = start  =>  d = start
		//So:
		//  f (x) =   a*x^3 +   b*x^2 + c*x + start
		//  f'(x) = 3*a*x^2 + 2*b*x   + c

		//Notice:
		//  f'(0) = 0  =>  3*a*0^2 + 2*b*0 + c = 0;  c = 0
		//So:
		//  f (x) =   a*x^3 +   b*x^2 + start
		//  f'(x) = 3*a*x^2 + 2*b*x
		
		//Notice:
		//  f (1) = end  =>    a*1^3 +   b*1^2 + start = end  =>    a +   b + start = end  =>  a +   b +   start =    end
		//  f'(1) =   0  =>  3*a*1^2 + 2*b*1           =   0  =>  3*a + 2*b         =   0  =>  a       - 2*start = -2*end
		//  a = 2*start-2*end
		//  2*start - 2*end + b + start = end  =>  b = 3*end - 3*start
		//So:
		//  f(x) = (2*start-2*end)*x^3 + 3*(3*end-3*start)*x^2 + start
		
		//def get_f(start,end):
		//	a = str( 2.0*start-2.0*end )
		//	b = str( 3.0*end - 3.0*start )
		//	return a+"*x*x*x + "+b+"*x*x + "+str(start)
		
		a = 2.0*start - 2.0*end;
		b = 3.0*end - 3.0*start;
		d = start;
	}
	/**
	 * Samples the cubic at a given point.
	 * @param x the point.  Best to make this in [0.0,1.0].
	 * @return the sample.
	 */
	public final double sample(double x) {
		//System.out.println(a+"*x*x*x + "+b+"*x*x + "+d);
		return a*x*x*x + b*x*x + d;
	}
}