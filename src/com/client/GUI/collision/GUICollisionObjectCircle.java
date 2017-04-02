package com.client.GUI.collision;

import java.awt.Point;

import com.client.GUI.panels.GUIPanelMovable;
import com.client.Graphics.GraphicsAbstract;
import com.utilities.UtilityMath;

/**
 * Concrete collision object.  Encapsulates a circle.
 * @author Ian
 */
public final class GUICollisionObjectCircle extends GUICollisionObject {
	/** Radius of the collision object. */
	private final double radius;
	
	/**
	 * Constructor.
	 * @param parent the parent frame (used two superclasses up for positioning).
	 * @param radius the radius of the circle.
	 */
	public GUICollisionObjectCircle(GUIPanelMovable parent, double radius) {
		super(parent);
		this.radius = radius;
	}
	
	/**
	 * Collides the circle against a point.  See superclass.
	 * @param location the location to test.
	 */
	@Override public final boolean collide(Point location) {
		if (!enabled) return false;
		Point pos_abs = getPositionAbs();
		Point delta = new Point(
				location.x - pos_abs.x,
				location.y - pos_abs.y
		);
		double length = UtilityMath.getLength(delta);
		return length <= radius;
	}
	
	/**
	 * Draws the object.  See superclass.
	 */
	@Override public void draw() {
		if (!GUICollisionObject.DRAW) return;
		Point pos_abs = getPositionAbs();
		GraphicsAbstract.setColor(getDrawColor());
		GraphicsAbstract.drawOval(
				UtilityMath.rndint(pos_abs.x-radius),
				GraphicsAbstract.getHeight()-UtilityMath.rndint(pos_abs.y+radius),
				UtilityMath.rndint(radius*2),
				UtilityMath.rndint(radius*2)
		);
	}
};