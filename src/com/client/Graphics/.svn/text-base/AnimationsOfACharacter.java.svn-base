package com.client.Graphics;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.client.ResourceLoader;
import com.game.environment.Directions;

/**
 * Encapsulates all the animations that a character can do.  Stores a series of
 * AnimationsOfAnAction to do this.
 * @author Ian
 */
public class AnimationsOfACharacter {
	/** Map of all the possible animations that a character can do. */
	private Map    <ActionType,AnimationsOfAnAction> _animations = 
		new HashMap<ActionType,AnimationsOfAnAction>();
	
	/**
	 * Constructor.  
	 * @param directory_which_character the directory to look in for the
	 * animation directories for this character.
	 * @param scalar the scalar to resize everything by.
	 */
	public AnimationsOfACharacter(String directory_which_character, float scalar) {
		for (ActionType e : ActionType.class.getEnumConstants()) {
			AnimationsOfAnAction ad = new AnimationsOfAnAction(
					ResourceLoader.DIRECTORY_IMAGES_CHARACTERS+directory_which_character+e.directory_action,
					scalar,
					e.max_executions,
					e.fr_cycle);
			if (ad.isEmpty()) continue;
			
			_animations.put(e,ad);
		}
	}
	
	/**
	 * Gets a resource that may be drawn, corresponding to a particular action and
	 * direction.
	 * 
	 * @param type the type of animation to get.
	 * @param direction the direction of the animation to get.
	 * @return the animation, as a DrawableResource.
	 */
	public DrawableResource getResource(ActionType type, Directions direction) {
		if (!_animations.containsKey(type)) return null;
		return _animations.get(type).getResource(direction);
	}
	
	/**
	 * What a character can be doing.  These are the character's internal
	 * states, and might be useful for more than merely drawing.
	 * @author Ian
	 */
	public static enum ActionType {
		/** Self-evident. */
		ATTACK("attack"+File.separator,-1, true),
		   DIE(   "die"+File.separator, 1,false),
		 SHOOT( "shoot"+File.separator,-1, true),
		 STAND( "stand"+File.separator,-1, true),
		  WALK(  "walk"+File.separator,-1,false);
		
		private ActionType(String directory_action, int max_executions, boolean fr_cycle) {
			this.directory_action = directory_action;
			this.max_executions = max_executions;
			this.fr_cycle = fr_cycle;
		}
		/** The directory where the action may be found. */
		public final String directory_action;
		/** The maximum number of times this may execute. */
		public final int max_executions;
		/** Whether to cycle the animation forward and backward. */
		public final boolean fr_cycle;
	}
}