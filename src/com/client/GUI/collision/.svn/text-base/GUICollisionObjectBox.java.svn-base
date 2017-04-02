package com.client.GUI.collision;

import java.awt.Point;

import com.client.GUI.panels.GUIPanelMovable;
import com.client.Graphics.GraphicsAbstract;

/**
 * Concrete collision object.  Encapsulates a box.
 * @author Ian
 */
public final class GUICollisionObjectBox extends GUICollisionObject {
	/** The width and height of the box. */
	public final int w,h;
	
	/**
	 * Constructor.
	 * @param parent the parent frame (used two superclasses up for positioning).
	 * @param w the box's width.
	 * @param h the box's height.
	 */
	public GUICollisionObjectBox(GUIPanelMovable parent, int w, int h) {
		super(parent);
		this.w=w; this.h=h;
	}
	
	/**
	 * Collides the box against a point.  See superclass.
	 * @param location the location to test.
	 */
	@Override public boolean collide(Point location) {
		if (!enabled) return false;
		Point pos_abs = getPositionAbs();
		if (location.x<pos_abs.x  ) return false;
		if (location.y<pos_abs.y  ) return false;
		if (location.x>pos_abs.x+w) return false;
		if (location.y>pos_abs.y+h) return false;
		return true;
	}
	
	/** {@inheritDoc} */
	@Override public void draw() {
		if (!GUICollisionObject.DRAW) return;
		Point pos_abs = getPositionAbs();
		GraphicsAbstract.setColor(getDrawColor());
		GraphicsAbstract.drawRect(
				pos_abs.x,
				GraphicsAbstract.getHeight()-(pos_abs.y+h+1),
				w,
				h
		);
	}
};