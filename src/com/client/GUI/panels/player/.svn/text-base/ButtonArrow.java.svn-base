package com.client.GUI.panels.player;

import java.awt.Point;
import java.io.File;
import com.client.ResourceLoader;
import com.client.GUI.collision.GUICollisionObject;
import com.client.GUI.panels.GUIPanelMovable;
import com.client.Graphics.DrawableResource;
import com.client.Graphics.GraphicsAbstract;
import com.utilities.Function;

/**
 * Encapsulates an arrow button.
 * @author Ian
 */
public class ButtonArrow extends GUIPanelMovable {
	/** The type of arrow button. */
	public static enum ARROW_TYPE {
		UP("up"),DOWN("down");
		/**
		 * Constructor.
		 * @param name the path of the button.
		 */
		private ARROW_TYPE(String name) {
			resource = ResourceLoader.loadImageResource(
					ResourceLoader.DIRECTORY_IMAGES_GUI+File.separatorChar+"createchar"+File.separatorChar+name+".png"
			);
			resource_hover = ResourceLoader.loadImageResource(
					ResourceLoader.DIRECTORY_IMAGES_GUI+File.separatorChar+"createchar"+File.separatorChar+name+"_hover.png"
			);
		}
		/** The button's resource. */
		public final DrawableResource resource;
		/** The button's resource for hovering. */
		public final DrawableResource resource_hover;
	}
	
	/** Which type this button is. */
	private final ARROW_TYPE type;
	/** Whether the button is being hovered over. */
	private boolean hovering = false;
	
	/**
	 * Constructor.
	 * @param parent the parent object.
	 * @param type the type of button to make.
	 */
	protected ButtonArrow(GUIPanelMovable parent, ARROW_TYPE type) {
		super(parent);
		this.type = type;
	}
	
	/**
	 * Adds a collision object to the button.  Automatically adds calls backs to
	 * make hovering work properly.
	 * @param co the collision object to add.
	 */
	public void addCollisionObject(GUICollisionObject co) {
		co.setFunctionEnter(new Function() { @Override public void execute(Object... arguments) {
			hovering = true;
		} });
		co.setFunctionExit(new Function() { @Override public void execute(Object... arguments) {
			hovering = false;
		} });
		objects.add(co);
	}

	/** {@inheritDoc} */
	@Override public void draw() {
		Point pos = getPositionAbs();
		if (hovering) {
			GraphicsAbstract.drawResource(type.resource_hover, 0,0,-1,-1, pos.x,pos.y,-1,-1);
		} else {
			GraphicsAbstract.drawResource(type.      resource, 0,0,-1,-1, pos.x,pos.y,-1,-1);
		}
	}
}