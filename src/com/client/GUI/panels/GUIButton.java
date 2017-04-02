package com.client.GUI.panels;

import java.awt.Color;
import java.awt.Point;

import com.client.ResourceLoader;
import com.client.GUI.collision.GUICollisionObjectBox;
import com.client.Graphics.DrawableResource;
import com.client.Graphics.GraphicsAbstract;
import com.utilities.Function;

/**
 * A button.
 * @author Ian
 */
public final class GUIButton extends GUIPanelMovable {
	/** Resource for the button. */
	private final static DrawableResource resource_button = ResourceLoader.loadImageResource(ResourceLoader.DIRECTORY_IMAGES_GUI+"button_blank.png",0.7f);
	/** The collision object. */
	private final GUICollisionObjectBox collide_button;
	
	/** The text to put on the button. */
	private final String text;
	/** Whether the button is active. */
	private boolean hidden = false;
	/** Whether the button is being hovered over. */
	private boolean hovering = false;
	
	/**
	 * Constructor.
	 * @param parent the parent frame (used for positioning).
	 * @param text the text to put on the button.
	 */
	public GUIButton(GUIPanelMovable parent, String text) {
		super(parent);
		collide_button = new GUICollisionObjectBox(parent, 61,18);
		collide_button.setFunctionEnter(new Function() { @Override public void execute(Object... arguments) { hovering =  true; } });
		collide_button. setFunctionExit(new Function() { @Override public void execute(Object... arguments) { hovering = false; } });
		objects.add(collide_button);
		this.text = text;
	}
	/**
	 * Sets the function to call when the button is clicked.
	 * @param function_click
	 */
	public final synchronized void setFunctionPressed(Function function_click) {
		collide_button.setFunctionClick(function_click);
	}
	
	/**
	 * Sets the relative position of this button.
	 */
	@Override public final synchronized void setPositionRel(int x, int y) {
		super.setPositionRel(x,y);
		collide_button.setPositionRel(x,y);
	}
	
	/**
	 * Draws the button.  See superclass.
	 */
	@Override public final synchronized void draw() {
		if (!hidden) {
			Point pos_abs = this.getPositionAbs();
			
			GraphicsAbstract.setFont(GraphicsAbstract.font10);
			if (hovering) {
				GraphicsAbstract.setColor(new Color(255,128,0));
			} else {
				GraphicsAbstract.setColor(new Color(255,255,50));
			}
			
			GraphicsAbstract.drawResource(resource_button, 0,0,-1,-1, pos_abs.x,pos_abs.y,-1,-1);
			int text_width = GraphicsAbstract.getFontMetrics(GraphicsAbstract.font10).stringWidth(text);
			//ga.drawText(g, text, pos_abs.x+18,pos_abs.y+7);
			GraphicsAbstract.drawText(text, pos_abs.x+resource_button.width/2-text_width/2,pos_abs.y+7);
		}
	}
	
	/** Hides the button (and disables clicking on it). */
	public final synchronized void hide() {
		hidden = true;
		collide_button.disable();
	}
	/** Shows the button (and enables clicking on it). */
	public final synchronized void show() {
		hidden = false;
		collide_button.enable();
	}
}