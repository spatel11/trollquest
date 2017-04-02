package com.client.GUI.panels;

import java.awt.Point;

import com.utilities.UtilityMath;

/**
 * Encapsulates any movable panel (i.e., any panel that is, in principle at least,
 * movable).  This implies that all instances will have a relative position to a
 * containing frame, and an absolute position that can be calculated recursively
 * from these relative positions.
 * 
 * @author Ian
 */
public abstract class GUIPanelMovable extends GUIPanel {
	/** The parent of this panel.  Used for calculating proper offsets. */
	private final GUIPanelMovable parent;
	/** The relative rel_x location of the panel. */
	protected int rel_x = 0;
	/** The rel_y location of the panel. */
	protected int rel_y = 0;
	
	/**
	 * Constructor.  Takes the parent (can be null), which is needed for positioning information.
	 * @param parent
	 */
	protected GUIPanelMovable(GUIPanelMovable parent) {
		this.parent = parent;
	}
	
	/**
	 * Sets the relative position of the panel.
	 * @param rel_x
	 * @param rel_y
	 */
	public void setPositionRel(int rel_x, int rel_y) {
		this.rel_x = rel_x;
		this.rel_y = rel_y;
	}
	
	/** Returns the relative position of the panel. */
	public final Point getPositionRel() {
		return new Point(rel_x,rel_y);
	}
	/**
	 * Returns the absolute position of the panel.  Calculates
	 * this value recursively.
	 */
	public final Point getPositionAbs() {
		if (parent==null) return getPositionRel();
		return UtilityMath.getSum(new Point(rel_x,rel_y),parent.getPositionAbs());
	}
}
