package com.client.Graphics;

/**
 * Class that encapsulates parameters of an animation.  Used
 * to help Animation cache stuff, which is critical to
 * performance.
 * @author Ian
 */
public class ParametersAnimation {
	/** Directory the animation's frames reside in. */
	public final String directory;
	/** Regex specifying what the file name(s) can be. */
	public final String regex;
	/** Maximum number of executions of the animation. */
	public final int max_executions;
	/** Whether the animation should cycle the frames. */
	public final boolean fr_cycle;
	/** The image parameters */
	public final ParametersImage parameters_image;
	
	/**
	 * Constructor.  Arguments correspond directly to the final fields.
	 * @param directory the directory to load the animation's images from.
	 * @param regex the regex that finds the image paths.  The animations will
	 * be loaded in a sorted order (so probably the same order they appear in
	 * your OS's filesystem.
	 * @param scalar what to resize the animation to.
	 * @param max_executions the maximum number of executions a given instance
	 * of this animation is allowed to run for.
	 * @param fr_cycle whether to double the animation's length by adding the
	 * frames in reverse order after in forward order.
	 */
	public ParametersAnimation(String directory, String regex, float scalar, int max_executions, boolean fr_cycle) {
		this.directory = directory;
		this.regex = regex;
		this.max_executions = max_executions;
		this.fr_cycle = fr_cycle;
		parameters_image = new ParametersImage(directory,scalar);
	}
	
	/** Needed so that this class can be used in HashMap.  Uses prime numbers. */
	@Override public int hashCode() {
		return 
			directory.hashCode()+
			regex.hashCode()+
			467*parameters_image.hashCode()+
			739*max_executions+
			997*(fr_cycle?0:1);
	}
	/**
	 * Tests if objects are equal.
	 * @param obj the object to test against.
	 */
	@Override public boolean equals(Object obj) {
		if (this==obj) return true;
		if (!(obj instanceof ParametersAnimation)) return false;
		
		ParametersAnimation ap = (ParametersAnimation)(obj);
		if (!       directory.equals(       ap.directory)) return false;
		if (!           regex.equals(           ap.regex)) return false;
		if (!parameters_image.equals(ap.parameters_image)) return false;
		if (  max_executions!=  ap.max_executions) return false;
		if (        fr_cycle!=        ap.fr_cycle) return false;
		return true;
	}
	
	/** Prints the contents. */
	@Override public String toString() {
		return "["+directory+","+regex+","+parameters_image+","+max_executions+","+fr_cycle+"]";
	}
}