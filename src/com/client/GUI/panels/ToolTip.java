package com.client.GUI.panels;

import java.awt.Color;
import java.awt.FontMetrics;
import java.util.HashSet;

import com.client.Graphics.GraphicsAbstract;
import com.utilities.UtilityMath;

public final class ToolTip {
	/** Set of all tooltips. */
	private final static HashSet<ToolTip> tooltips = new HashSet<ToolTip>();
	
	/** The text to display on the tooltip. */
	private String text = "<Empty Tooltip>";
	/** The transparency of the tooltip. */
	private double alpha = 0.0;

	/** The tooltip constructor. */
	private ToolTip() {}
	/** Makes a new tooltip, initially inactive. */
	public final static ToolTip makeToolTip() {
		ToolTip new_tooltip = new ToolTip();
		tooltips.add(new_tooltip);
		return new_tooltip;
	}

	/** Sets the text on the tooltip. */
	public final void setText(String text) {
		this.text = text;
	}

	/** Shows the tooltip. */
	public final void show() {
		alpha = 0.8;
	}
	
	/** Updates all tooltips.  Argument is the time passed in tmilliseconds. */
	public final static void updateAll(int dt) {
		for (ToolTip tt : tooltips) {
			tt.update(dt);
		}
	}
	/** Updates the tooltip.  Argument is the time passed in tmilliseconds. */
	public final void update(int dt) {
		//Want it to start off decreasing slowly and then increase from there.
		//Try:
		//f'(x) = 1.0 - (a*x)^(1/n)
		//f (x) = x   -   (  n*x*((a*x)^(1/n)) / (n+1)  )
		//Ack.  Try:
		//f(x) = 1 - (a*x)^(1/n)
		//f'(x) = -( ((a*x)^(1/n)) / (n*x) )
		double a = 0.000002*dt;
		double n = 2.0;
		alpha -= Math.pow(a*alpha,1.0/n) / (n*alpha);
		if (alpha<0.0) alpha = 0.0;
	}

	/** Draws the tooltip. */
	public final void draw(int x, int y) {
		GraphicsAbstract.setFont(GraphicsAbstract.font10);
		FontMetrics metrics = GraphicsAbstract.getFontMetrics(GraphicsAbstract.font10);
		
		int w = metrics.stringWidth(text);
		int h = metrics.getHeight();
		int padding = 4;
		int transparency = UtilityMath.rndint(255.0*alpha);
		
		GraphicsAbstract.setColor(new Color(255,255,128,transparency));
		GraphicsAbstract.drawRectFill(x,y,w+padding,h+padding);
		
		GraphicsAbstract.setColor(new Color(0,0,0,transparency));
		GraphicsAbstract.drawText(text,x+padding/2,GraphicsAbstract.getHeight()-y-h+4-padding/2);
	}
}