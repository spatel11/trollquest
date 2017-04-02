package com.client.Graphics;

/**
 * Class that encapsulates parameters of a font.  Used
 * to cache fonts, which is critical to performance.
 * @author Ian
 */
public class ParametersFont {
	/** The location of the font. */
	public final String name;
	/**Size of the font. */
	public final int size;
	
	/**
	 * Constructor.  Arguments correspond directly to the final fields.
	 * @param path
	 * @param size
	 */
	public ParametersFont(String path, int size) {
		this.name = path;
		this.size = size;
	}
	
	/** Needed so that this class can be used in HashMap.  Uses prime numbers. */
	@Override public int hashCode() {
		return 
			13*name.hashCode()+
			337*size;
	}
	/** Tests if objects are equal. */
	@Override public boolean equals(Object obj) {
		if (this==obj) return true;
		if (!(obj instanceof ParametersFont)) return false;
		
		ParametersFont ap = (ParametersFont)(obj);
		if (!name.equals(ap.name)) return false;
		if (size!=ap.size) return false;
		return true;
	}
	
	/** Prints the contents. */
	@Override public String toString() {
		return "(\""+name+"\","+size+")";
	}
}