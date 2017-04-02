package com.client.Graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.client.ResourceLoader;
import com.utilities.UtilityImage;

/**
 * Encapsulates an animation.
 * @author Ian
 */
public final class Animation {
	//TODO: maybe ArrayList of DrawableResource?
	private final ArrayList<BufferedImage> animation = new ArrayList<BufferedImage>();
	private int frame = 0;
	
	/** Width and height of each animation frame. */
	public final int width, height;
	/** How many frames there are. */
	public int numof_frames;
	/** The maximum number of times this animation can execute, or -1 for no limit. */
	private int max_executions;
	/** Whether to cycle the frames backward (i.e., for a six frame animation: loop(1,2,3,4,5,6,5,4,3,2)). */
	private boolean fr_cycle;
	
	/** HashMap of all loaded animations, for caching. */
	private final static HashMap<ParametersAnimation,Animation> loaded_animations = new HashMap<ParametersAnimation,Animation>();
	
	/**
	 * Constructor for the animation.
	 * @param paths a list of all the paths comprising the animation.
	 * @param scalar what to resize each image by.
	 */
	private Animation(ArrayList<String> paths, float scalar) {
		numof_frames = paths.size();
		
		BufferedImage new_frame = null;
		int i = 0;
		for (String path : paths) {
			new_frame = ResourceLoader.loadImageResource(path,scalar).getImage();
			animation.add(new_frame);
			++i;
		}
		width = new_frame.getWidth();
		height = new_frame.getHeight();
	}
	
	/**
	 * Call this to get a new animation.  Throws an exception if it can't.
	 * Won't remake an animation if it's already loaded; will just return
	 * a new instance of the same type.  This is absolutely KEY for
	 * efficiency.
	 * 
	 * @return the Animation.
	 * @throws IOException if something went wrong.  The exception's message
	 * says why.
	 */
	public final static synchronized Animation getInstance(ParametersAnimation params) throws IOException {
		//System.out.println("Checking parameters "+params);
		if (loaded_animations.containsKey(params)) {
			//System.out.println("DID Found!");
			return loaded_animations.get(params);
		}
		//System.out.println("Did NOT find!");
		
		File files[];
		files = new File(params.directory).listFiles();
		if (files==null) {
			throw new IOException("Error: The directory \""+params.directory+"\" is not a directory!");
		}
		
		Arrays.sort(files);
		
		ArrayList<String> paths = new ArrayList<String>();
		
		String regex = params.directory + params.regex;
		regex = regex.replace("\\","\\\\");
		Pattern p = Pattern.compile(regex);
		for (File f : files) {
			//System.out.println(p.toString()+" versus "+f.toString());
			Matcher matcher = p.matcher(f.toString());
			if (matcher.lookingAt()) {
				//System.out.println("Yay!  The file \""+f.toString()+"\" matches!");
				paths.add(f.toString());
			}
		}
		//System.out.println();
		
		if (paths.isEmpty()) {
			throw new IOException("Error: The directory \""+params.directory+"\" contains no usable files!");
		}
		
		Animation result = new Animation(paths,params.parameters_image.scalar);
		result.max_executions = params.max_executions;
		result.fr_cycle = params.fr_cycle;
		loaded_animations.put(params,result);
		
		return result;
	}

	/** Increments the current frame. */
	private final synchronized void incrementFrame() {
		if (numof_frames==1) return;
		++frame;
		if (fr_cycle) {
			if (frame==numof_frames*2-2) {
				if (max_executions==1) --frame;
				else frame = 0;
			}
		} else {
			if (frame==numof_frames) {
				if (max_executions==1) --frame;
				else frame = 0;
			}
		}
	}
	/** Increments all frames of all animations. */
	public final static synchronized void incrementAllAnimations() {
		for (Animation anim : loaded_animations.values()) anim.incrementFrame();
	}
	
	/**
	 * Gets the current frame of the animation as a BufferedImage.  Some trickiness with
	 * fr_cycle, but can be shown correct by the Python script:
	 * 
	 * def test(numof_frames):
	 *     for frame in xrange(2*numof_frames-2):
	 *         if frame>numof_frames-2: print frame, 2*numof_frames-2-frame
	 *         else: print frame, frame
	 *         
	 * @return the animation frame.
	 */
	public final synchronized BufferedImage getCurrentFrame() {
		if (fr_cycle) {
			if (frame>numof_frames-2) return animation.get(2*numof_frames-2-frame);
			return animation.get(frame);
		}
		return animation.get(frame);
	}
	
	/**
	 * Copies the last frame of the animation and then adds it to the end.
	 * 
	 * Note that this will not create a timer, even though there will certainly be more
	 * than one frame now.  This is so that the Animation can be treated as a collection
	 * of images.
	 */
	public final synchronized void cloneLastFrame() {
		BufferedImage new_frame = UtilityImage.copy( animation.get(animation.size()-1) );
		animation.add(new_frame);
		++numof_frames;
	}
	
	//TODO: maybe return as DrawableResource?
	/**
	 * Returns a frame of the animation as a BufferedImage.
	 * @param index the index of the frame.
	 * @return the frame.
	 */
	public final synchronized BufferedImage getFrame(int index) {
		return animation.get(index);
	}
}