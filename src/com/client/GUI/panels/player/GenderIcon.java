package com.client.GUI.panels.player;

import java.awt.Point;
import java.io.File;

import com.client.ResourceLoader;
import com.client.GUI.collision.GUICollisionObject;
import com.client.GUI.panels.GUIPanelMovable;
import com.client.Graphics.DrawableResource;
import com.client.Graphics.GraphicsAbstract;
import com.game.characters.GameCharacter.Gender;
import com.utilities.Function;

/**
 * Encapsulates a gender icon.
 * @author Ian
 */
public class GenderIcon extends GUIPanelMovable {
	/** The image of the icon. */
	private DrawableResource gender_icon;
	/** The hovering image of the icon. */
	private final static DrawableResource resource_square_hover = ResourceLoader.loadImageResource(
			ResourceLoader.DIRECTORY_IMAGES_GUI+"square_hover.png",1.6875f
	);
	/** The selected image of the icon. */
	private final static DrawableResource resource_square_select = ResourceLoader.loadImageResource(
			ResourceLoader.DIRECTORY_IMAGES_GUI+"square_select.png",1.6875f
	);
	/** Whether we're hovering or selected. */
	private boolean hovering=false, selected=false;
	/** The gender this GenderIcon represents. */
	public final Gender gender;
	
	/**
	 * Constructor.
	 * @param parent the parent frame.
	 * @param filename the filename of the icon.
	 * @param scalar the scalar to resize the icon to.
	 */
	public GenderIcon(GUIPanelPlayerCreate parent, Gender gender, String filename, float scalar) {
		super(parent);
		
		this.gender = gender;
		
		gender_icon = ResourceLoader.loadImageResource(
				ResourceLoader.DIRECTORY_IMAGES_GUI+File.separatorChar+"createchar"+File.separatorChar+
				filename+".png",scalar
		);
	}
	
	/**
	 * Adds a collision object.  Automatically adds calls backs to
	 * make hovering work properly.
	 * @param co
	 */
	public void addCollisionObject(GUICollisionObject co) {
		co.setFunctionEnter(
				new Function() { @Override public void execute(Object... arguments) { setHovering(true); } }
		);
		co.setFunctionExit(
				new Function() { @Override public void execute(Object... arguments) { setHovering(false); } }
		);
		objects.add(co);
	}
	
	/**
	 * Sets whether the icon is being hovered over.
	 * @param hovering whether it is.
	 */
	public void setHovering(boolean hovering) { this.hovering = hovering; }
	/**
	 * Sets whether the icon is selected.
	 * @param selected whether it is.
	 */
	public void setSelected(boolean selected) { this.selected = selected; }
	/** Gets whether the icon is selected. */
	public boolean getSelected() { return this.selected; }

	/**
	 * Draws a resource.  Still a working method.
	 * @param g
	 * @param dr
	 */
	private void drawResource(DrawableResource dr) {
		Point pos = getPositionAbs();
		GraphicsAbstract.drawResource(dr, 0,0,-1,-1, pos.x,pos.y,-1,-1);
	}
	/** {@inheritDoc} */
	@Override public void draw() {
		Point pos = getPositionAbs();
		GraphicsAbstract.drawResource(gender_icon, 0,0,-1,-1, pos.x,pos.y,-1,-1);
		
		if      (selected) drawResource(resource_square_select);
		else if (hovering) drawResource( resource_square_hover);
	}
}