package com.client.GUI.panels;

import com.client.ResourceLoader;
import com.client.GUI.collision.GUICollisionObject;
import com.client.GUI.collision.GUICollisionObjectCircle;
import com.client.Graphics.DrawableResource;
import com.client.Graphics.GraphicsAbstract;
import com.utilities.Function;

/**
 * Class that encapsulates drawing the control bar.
 * @author Ian
 */
public class GUIPanelControlBar extends GUIPanelMovable { //Not really movable, but in principle so.
	/** The instance. */
	private final static GUIPanelControlBar instance = new GUIPanelControlBar();
	/** The resource of the control bar image. */
	private static DrawableResource resource_bar;
	
	/** Constructor. */
	private GUIPanelControlBar() {
		super(null);
		
		resource_bar = ResourceLoader.loadImageResource(ResourceLoader.DIRECTORY_IMAGES_GUI+"controls.png");
		
		GUICollisionObject collide_talk = new GUICollisionObjectCircle(this, 13.0);
		collide_talk.setPositionRel(27,22);
		collide_talk.setFunctionClick(
				new Function() { @Override public void execute(Object... arguments) { GUIPanels.togglePanelMessage(); } }
		);
		objects.add(collide_talk);
		
		GUICollisionObject collide_inventory = new GUICollisionObjectCircle(this, 13.0);
		collide_inventory.setPositionRel(64,22);
		collide_inventory.setFunctionClick(
				new Function() { @Override public void execute(Object... arguments) { GUIPanels.togglePanelInventory(); } }
		);
		objects.add(collide_inventory);
		
		GUICollisionObject collide_quests = new GUICollisionObjectCircle(this, 13.0);
		collide_quests.setPositionRel(100,22);
		collide_quests.setFunctionClick(
				new Function() { @Override public void execute(Object... arguments) { GUIPanels.togglePanelQuests(); } }
		);
		objects.add(collide_quests);
	}
	/** Returns the instance of the control bar.  Using singleton pattern. */
	public static GUIPanelControlBar getInstance() {
		return instance;
	}
	
	/** {@inheritDoc} */
	@Override public void draw() {
		GraphicsAbstract.drawResource(resource_bar,
				0,0,-1,-1,
				0,0,-1,-1);
	}
}