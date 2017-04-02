package com.client.Graphics;

import java.awt.image.BufferedImage;

/**
 * Concrete class of DrawableResource.
 * @author Ian
 */
public class DrawableResourceAnimation extends DrawableResource {
	private final Animation src;
	
	/**
	 * Sets up a new DrawableResource with an animation.
	 * @param src
	 */
	public DrawableResourceAnimation(Animation src) {
		super(src.width,src.height);
		
		this.src = src;
	}
	
	/**
	 * Returns a resource view as an image.
	 * @return an image.
	 */
	@Override public BufferedImage getImage() {
		return src.getCurrentFrame();
	}
	/**
	 * Returns the animation.
	 * @return the animation.
	 */
	public Animation getAnimation() {
		return src;
	}
}
