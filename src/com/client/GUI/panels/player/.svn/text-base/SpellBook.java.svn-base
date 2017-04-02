package com.client.GUI.panels.player;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Point;

import com.client.Main;
import com.client.ResourceLoader;
import com.client.Graphics.DrawableResource;
import com.client.Graphics.GraphicsAbstract;
import com.game.characters.spells.AbstractSpell;
import com.server.command.CastCommand;
import com.utilities.CubicInterpolator;
import com.utilities.UtilityMath;

/**
 * Encapsulates a spell book.
 * @author Ian
 */
public class SpellBook {
	/** The speed with which the spellbooks move in and out. */
	private final static double selection_speed = 0.1;
	
	/** The resource for the book. */
	private final static DrawableResource resource_book;
	
	/** The part the book is out. */
	private double animation_sampler = 0.0;
	/** Whether the book represents an active spell. */
	public boolean active = false;
	/** The interpolator for the position. */
	private final CubicInterpolator interpolator = new CubicInterpolator(0.0,1.0);
	/** The width of the descriptor text. */
	private int width = -1;
	/** The string to draw. */
	private final String text;
	
	/**
	 * The number of this book on the player's knowledge stack.  Must be unique.  TODO: maybe do that
	 * with a factory method.
	 */
	private int number;
	/** The spell.  Should be unique to make sense. */
	public final AbstractSpell spell;
	
	/** Static initializer.  Loads the book resource. */
	static {
		resource_book = ResourceLoader.loadImageResource(ResourceLoader.DIRECTORY_IMAGES_GUI+
				"mageandthaumaturgistshandbook.png",0.7f);
	}
	/**
	 * Constructor.
	 * @param number the id of the new spellbook.
	 * @param spell the spell.
	 */
	public SpellBook(int number, AbstractSpell spell) {
		this.number = number;
		this.spell = spell;
		this.text = spell.toString();//+" ("+number+")";
	}
	
	/** Updates the spellbook.  Serves to move it in or out. */
	public void update() {
		if (active) animation_sampler += selection_speed;
		else        animation_sampler -= selection_speed;
		animation_sampler = UtilityMath.clamp(animation_sampler,0.0,1.0);
	}
	
	/** Selects the spellbook. */
	public void select() {
		active = true;
	}
	/** Deselects the spellbook. */
	public void deselect() {
		active = false;
	}
	
	/** Gets a new command associated with this spell. */
	public CastCommand getCommandSpell(Point target_location) {
		return new CastCommand(
				Main.getState(),
				Main.getSelfName(),
				target_location.x,target_location.y,
				spell
		);
	}

	/** Sets the width and font of the text. */
	private final void setWidthFont() {
		GraphicsAbstract.setFont(GraphicsAbstract.font10);
		if (width==-1) {
			FontMetrics metrics = GraphicsAbstract.getFontMetrics(GraphicsAbstract.font10);
			width = metrics.stringWidth(text);
		}
	}
	/** Draws the text at the top of the spells bar.  Only draws if the spellbook is active. */
	public final void drawTextAtTop(int max_books) {
		setWidthFont();
		if (active) {
			GraphicsAbstract.setColor(new Color(255,255,255,255));
			drawTextBox(max_books,GraphicsAbstract.getWidth()-width-70,GraphicsAbstract.getHeight()-20,1.0f);
			//drawTextBox(max_books,GraphicsAbstract.getWidth()-width-10,GraphicsAbstract.getHeight()-260,1.0f);
		}
	}
	/** Draws the text in a box. */
	public final void drawTextBox(int max_books, int x, int y, float alpha) {
		Color text_c = GraphicsAbstract.getColor();
		
		GraphicsAbstract.setColor(new Color( 32,32,32,UtilityMath.rndint(255.0*alpha)));
		GraphicsAbstract.drawRectFill(x-3,GraphicsAbstract.getHeight()-(y+9),width+6,14);
		
		GraphicsAbstract.setColor(text_c);
		GraphicsAbstract.drawText(text,x,y);
		
	}
	/** Draws the text adjacent to the spell book it corresponds to.  Always draws. */
	public final void drawTextAdjacent(int max_books) {
		setWidthFont();
		float alpha;
		if (active) {
			GraphicsAbstract.setColor(new Color(255,255,255,255));
			alpha = 0.8f;
		} else {
			GraphicsAbstract.setColor(new Color(255,255,255,128));
			alpha = 0.5f;
		}
		drawTextBox(max_books,getX(max_books)-width-10,getY(max_books)+30,alpha);
	}
	/** Returns the x coordinate used to draw the book. */
	private final int getX(int max_books) {
		int x = GraphicsAbstract. getWidth() - 60;
		x -= UtilityMath.rndint(10.0*interpolator.sample(animation_sampler));
		return x;
	}
	/** Returns the y coordinate used to draw the book. */
	private final int getY(int max_books) {
		int y = GraphicsAbstract.getHeight() - 50 + 15*(number-max_books);
		y -= UtilityMath.rndint( 6.0*interpolator.sample(animation_sampler));
		return y;
	}
	/**
	 * Draws the spellbook.
	 * @param max_books the number of books on the player's knowledge stack.  Necessary
	 * so that the book may be drawn in the right place.  The book must be drawn from
	 * bottom to top to get the ordering right, but the books are added top to bottom.
	 */
	public void draw(int max_books) {
		GraphicsAbstract.drawResource(resource_book,
				0,0,-1,-1,
				getX(max_books),getY(max_books),-1,-1);
		
		//drawTextAtTop(max_books);
		drawTextAdjacent(max_books);
	}
}