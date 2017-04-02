package com.client.GUI.panels;

import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.client.GUI.GUIMain;
import com.client.GUI.collision.GUICollisionObject;
import com.client.Graphics.GraphicsAbstract;

/**
 * Defines a panel that the user can interact with in the game.
 * @author Ian
 */
public abstract class GUIPanel implements ComponentListener {
	/** Collidable objects on the panel */
	protected final Set<GUICollisionObject> objects = new HashSet<GUICollisionObject>();
	/** Child panels */
	protected final Map<String,GUIPanel> children = new HashMap<String,GUIPanel>();
	/** Whether this panel is open. */
	protected boolean is_open = false;
	
	/** Makes the panel. */
	protected GUIPanel() {
		GUIMain.getInstance().addComponentListener(this);
	}
	
	/**
	 * Processes a key command.
	 * @param ke the key event.
	 * @return whether the key could have been processed
	 * (in abstract class, it couldn't have been, so false).
	 */
	public boolean processKey(KeyEvent ke) { return false; }
	/**
	 * Processes a mouse command.
	 * @param me the event.
	 */
	public final boolean processMouseClick(MouseEvent me) {
		for (GUIPanel child : children.values()) child.processMouseClick(me);
		
		Point mpos = GraphicsAbstract.getMousePosition();
		if (mpos==null) return false;
		for (GUICollisionObject co : objects) {
			if (co.collide(mpos)) {
				co.executeFunctionClick();
				return true;
			}
		}
		return false;
	}
	/**
	 * Processes a mouse command.
	 * @param me the event.
	 */
	public final void processMouseMove(MouseEvent me) {
		for (GUIPanel child : children.values()) child.processMouseMove(me);
		
		Point mpos = GraphicsAbstract.getMousePosition();
		if (mpos==null) return;
		for (GUICollisionObject co : objects) {
			if (co.collide(mpos)) co.executeFunctionMoveEnter();
			else co.executeFunctionMoveExit();
		}
	}
	
	/** {@inheritDoc} */
	@Override public void componentHidden(ComponentEvent ce) {}
	/** {@inheritDoc} */
	@Override public void componentMoved(ComponentEvent ce) {}
	/** {@inheritDoc} */
	@Override public void componentResized(ComponentEvent ce) {}
	/** {@inheritDoc} */
	@Override public void componentShown(ComponentEvent ce) {}
	
	/** Draws the panel. */
	public abstract void draw();
	/** Draws the panel and children. */
	public void drawRecursive() {
		draw();
		drawCollisionObjects();
		
		for (GUIPanel child : children.values()) {
			child.drawRecursive();
		}
	}
	/** Draws the collision objects */
	public void drawCollisionObjects() {
		for (GUICollisionObject co : objects) {
			co.draw();
		}
	}
	
	/**
	 * Called when the panel opens.
	 * 
	 * Subclasses MUST call super.openPanel(), at some point!
	 */
	public void  openPanel() { is_open = true; }
	/**
	 * Called when the panel closes.  It must be safe to call this method repeatedly.
	 * Default implementation simply notifies the GUIPanels that it can be closed
	 * immediately.  Some other panels might take longer to close.
	 * 
	 * Subclasses MUST call super.closePanel(), at some point!
	 */
	public void closePanel() { is_open = false; GUIPanels.observerUpdate(); }
}