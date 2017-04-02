package com.client.GUI.collision;

import java.awt.Color;
import java.awt.Point;

import com.client.GUI.panels.GUIPanelMovable;
import com.utilities.Function;

/**
 * Abstract class defining a collidable object.  These are useful for the GUI.
 * GUI panels usually contain at least one of the concrete classes derived from
 * this, and user input is tested against them.
 * @author Ian
 */
public abstract class GUICollisionObject extends GUIPanelMovable {
	/** Whether to draw the collision object(s). */
	public final static boolean DRAW = false;
	
	/** Functions to call on various events. */
	private Function function_click=null, function_enter=null, function_exit=null;
	/** Whether the collision object is actually enabled. */
	protected boolean enabled = true;
	
	/** The constructor.  Takes the parent, which the superclass needs for positioning information. */
	protected GUICollisionObject(GUIPanelMovable parent) {
		super(parent);
	}
	
	/** Returns if the point is "inside" this collision object. */
	public abstract boolean collide(Point location);
	
	/**
	 * Sets the function to call whenever the mouse is clicked inside this collision object.
	 * @param function_click the function to use.
	 */
	public final void setFunctionClick(Function function_click) { this.function_click = function_click; }
	/**
	 * Sets the function to call whenever the mouse enters this collision object.
	 * @param function_enter
	 */
	public final void setFunctionEnter(Function function_enter) { this.function_enter = function_enter; }
	/**
	 * Sets the function to call whenever the mouse exits this collision object.
	 * @param function_exit
	 */
	public final void  setFunctionExit(Function  function_exit) { this. function_exit =  function_exit; }
	
	/** Executes the click function, if there is one. */
	public void     executeFunctionClick() { if (function_click!=null) function_click.execute(); }
	/** Executes the enter function, if there is one. */
	public void executeFunctionMoveEnter() { if (function_enter!=null) function_enter.execute(); }
	/** Executes the exit function, if there is one. */
	public void  executeFunctionMoveExit() { if ( function_exit!=null)  function_exit.execute(); }
	
	/** Enables the collision object.  Subclasses should not collide if the collision object is disabled. */
	public void  enable() { enabled =  true; }
	/** Disables the collision object.  Subclasses should not collide if the collision object is disabled. */
	public void disable() { enabled = false; }
	
	/** Returns the color that should be used to draw this object.  Green if enabled, red if not. */
	protected final Color getDrawColor() {
		if (enabled) return new Color(0,255,0);
		return new Color(255,0,0);
	}
};