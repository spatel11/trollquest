package com.client.Graphics;

import java.awt.image.BufferedImage;

/**
 * Concrete class of DrawableResource.  
 * @author Ian
 */
public class DrawableResourceImage extends DrawableResource {
	private final BufferedImage src;
	
	/**
	 * Sets up a new DrawableResource with an image.
	 * @param src
	 */
	public DrawableResourceImage(BufferedImage src) {
		super(src.getWidth(),src.getHeight());
		
		this.src = src;
	}
	/**
	 * Returns a resource view as an image.
	 * @return
	 */
	@Override public BufferedImage getImage() {
		return src;
	}
}
