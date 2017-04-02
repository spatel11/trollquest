package com.client.Graphics;

import java.awt.image.BufferedImage;

/**
 * Encapsulates a resource.  See concrete classes DrawableResourceImage and
 * DrawableResourceAnimation.  This encapsulation is done mainly to abstract
 * away graphics specifics; theoretically the concrete graphics classes
 * might not need to know about what exactly it is they're getting.
 * 
 * @author Ian
 */
public abstract class DrawableResource {
	/** Width and height. */
	public final int width, height;
	
	/**
	 * Initializes the resource encapsulator with width and height.
	 * @param width
	 * @param height
	 */
	protected DrawableResource(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Returns a resource view as an image.
	 * @return
	 */
	public abstract BufferedImage getImage();
}