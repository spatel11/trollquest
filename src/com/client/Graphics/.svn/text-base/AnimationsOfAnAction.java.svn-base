package com.client.Graphics;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.game.environment.Directions;

/**
 * Stores the "same" animation from different directions (i.e., stores
 * multiple animations of a single action, from different angles).
 * @author Ian
 */
public class AnimationsOfAnAction {
	private Map    <Directions,DrawableResource> _animations = 
		new HashMap<Directions,DrawableResource>();
	
	/**
	 * Constructor.
	 * @param directory_action the directory where the animations' frames can be found.
	 * @param scalar the constant to resize all frames by.
	 * @param max_executions the maximum number of times to draw the animations.
	 * @param fr_cycle whether to cycle the animations forward and backward.
	 */
	public AnimationsOfAnAction(String directory_action, float scalar, int max_executions, boolean fr_cycle) {
		for (Directions d : Directions.class.getEnumConstants()) {
			try {
				Animation ad = Animation.getInstance(new ParametersAnimation(directory_action,d.regex,scalar,max_executions,fr_cycle));
				_animations.put(d,new DrawableResourceAnimation(ad));
			} catch (IOException e) {
				//ResourceLoader.showError(e.getMessage());
			}
		}
	}
	
	/**
	 * Gets a resource representing the animation.
	 * @param direction from this direction.
	 * @return the animation, as a DrawableResource.
	 */
	public DrawableResource getResource(Directions direction) {
		return _animations.get(direction);
	}
	
	/**
	 * Whether there are no actions stored.
	 * @return if so.
	 */
	public boolean isEmpty() {
		return _animations.isEmpty();
	}
}