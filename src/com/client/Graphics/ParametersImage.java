package com.client.Graphics;

import com.utilities.UtilityMath;

/**
 * Class that encapsulates parameters of an animation.  Used
 * to cache images, which is critical to performance.
 * @author Ian
 */
public class ParametersImage {
	/** The location of the image. */
	public final String path;
	/** Scalar to resize the image to on load. */
	public final float scalar;
	
	/**
	 * Constructor.  Arguments correspond directly to the final fields.
	 * @param path
	 * @param scalar
	 */
	public ParametersImage(String path, float scalar) {
		this.path = path;
		this.scalar = scalar;
	}
	
	/** Needed so that this class can be used in HashMap.  Uses prime numbers. */
	@Override public int hashCode() {
		return 
			13*path.hashCode()+
			UtilityMath.rndint(337.0*scalar);
	}
	/** Tests if objects are equal. */
	@Override public boolean equals(Object obj) {
		if (this==obj) return true;
		if (!(obj instanceof ParametersImage)) return false;
		
		ParametersImage ap = (ParametersImage)(obj);
		//System.out.println("Comparing "+this+" and "+ap);
		if (!path.equals(ap.path)) return false;
		if (scalar!=ap.scalar) return false;
		return true;
	}
	
	/** Prints the contents. */
	@Override public String toString() {
		return "(\""+path+"\","+scalar+")";
	}
}