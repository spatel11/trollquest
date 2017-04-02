package com.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.io.Serializable;
import java.util.Calendar;

import com.client.Dialog.JWorldDialogError;
import com.client.Graphics.GraphicsAbstract;

/**
 * Encapsulates a message.
 * 
 * @author Ian
 */
public final class Message implements Serializable {
	/** Required for some reason. */
	private static final long serialVersionUID = 7152973712711080686L;
	/** The color of a non-finalized message. */
	private final Color COLOR_TYPING = new Color(255,128,0);
	/** The color of a finalized message. */
	private final Color COLOR_FINISHED = new Color(255,255,50);

	/** The color of this message.  Initially not final. */
	private Color color = copyColor(COLOR_TYPING);
	/** The sections this message is broken into, potentially with different fonts. */
	private final Section sections[] = new Section[3];
	/** Author of the message. */
	private String author;

	/**
	 * Encapsulates drawing a section of the text.  This allows different
	 * fonts to be used for various sections.  In particular, the timestamp
	 * must use a fixed width font, but fixed width fonts look kinda weird
	 * on normal text.  Hence, a message is broken down into sections and
	 * each section uses different fonts.
	 * 
	 * The draw method draws the section from a static variable x, and
	 * then adds the amount rendered to x.  Thus, all sections are drawn in
	 * the right places.  .resetDraw() should be called to reset x before
	 * drawing all the sections.
	 * 
	 * @author Ian
	 */
	private final static class Section {
		/** The text to be drawn. */
		public final String text;
		private final Font font;
		private static int x;
		/** Constructor.  Takes the text to draw and the font to use to draw it. */
		public Section(String text, Font font) {
			this.text = text;
			this.font = font;
			resetDraw();
		}
		/** Resets drawing. */
		public final static void resetDraw() {
			x = 0;
		}
		/** Draws the message. */
		public void draw(int y) {
			GraphicsAbstract.setFont(font);
			GraphicsAbstract.drawText(text, 160+x,y );

			FontMetrics metrics = GraphicsAbstract.getFontMetrics(font);
			x += metrics.stringWidth(text);
		}
	}

	/** Initializes the message with an author. */
	private final void init(String author) {
		sections[0] = new Section("[",GraphicsAbstract.font10);
		updateTime();
		this.author = author;
	}

	/** Constructor.  Takes the author. */
	public Message(String author) {
		init(author);
	}
	/** Constructor.  Takes the author and message to display. */
	public Message(String author, String message) {
		init(author);
		setMessage(message);
	}

	/**
	 * Updates the timestamp associated with this message.  For example,
	 * the message in development must have the current timestamp
	 * dynamically updated.
	 */
	public final void updateTime() {
		String time_stamp = String.format(
				"%02d:%02d:%02d",
				Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
				Calendar.getInstance().get(Calendar.MINUTE),
				Calendar.getInstance().get(Calendar.SECOND)
		);
		sections[1] = new Section(time_stamp,GraphicsAbstract.font10fw);
	}
	/** Sets the message.  Necessary if the first constructor is used. */
	public final void setMessage(String message) {
		sections[2] = new Section("] "+author+": "+message,GraphicsAbstract.font10);
	}
	/** Returns a message being used.  A hackish solution, but simple. */
	public final String getMessage() {
		//TODO: caching would make this much faster.  Also would improve method
		//in GUIPanelMessage significantly.
		String result = "";
		for (Section s : sections) result += s.text;
		return result;
	}
	/** Returns the author. */
	public String getAuthor() {
		return author;
	}

	/** Copies a color to a new color.  Necessary because Java is retarded. */
	private final static Color copyColor(Color c) {
		return new Color(c.getRed(),c.getGreen(),c.getBlue(),c.getAlpha());
	}
	/**
	 * Marks this message as complete.  This has the effect of changing the drawn
	 * color.
	 */
	public final void setComplete() {
		color = copyColor(COLOR_FINISHED);
	}
	/**
	 * Sets the alpha value of this message.  This is called to make older messages
	 * more faint.
	 */
	public final void setAlpha(int alpha) {
		color = new Color(color.getRed(),color.getGreen(),color.getBlue(),alpha);
	}

	/** Draws this message by drawing all sections contained therein. */
	public final void draw(int y) {
		GraphicsAbstract.setColor(color);

		Section.resetDraw();

		for (Section s : sections) {
			if (s==null) new JWorldDialogError(".setMessage() was not called on a message!","Internal Error",null);
			s.draw(y);
		}
	}
}