package com.client.GUI;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;

import javax.swing.Timer;

import com.client.MainHandlerInput;
import com.client.Graphics.GraphicsAbstract;

/**
 * Canvas where the game is rendered.  Is doublebuffered on
 * a hardware context.
 * 
 * Events are passed off to Main to be handled.  See .setup().
 * 
 * @author Ian
 */
public class GUIGameCanvas extends Canvas {
	/** Required for some reason. */
	private static final long serialVersionUID = 8026927513461346462L;
	/** The instance. */
	private static GUIGameCanvas instance = null;
	/** Time for redrawing. */
	private final Timer timer;
	/** Buffer strategy. */
	private BufferStrategy strategy;
	
	/** Static initalizer.  Perhaps, strictly speaking, not necessary. */
	static {
		//System.setProperty("sun.java2d.trace", "timestamp,log,count");
		System.setProperty("sun.java2d.transaccel", "True");
		System.setProperty("sun.java2d.opengl", "True");
	}
	/** Constructor. */
	private GUIGameCanvas() {
		this.setMinimumSize(GUIMain.SCREEN_SIZE);
		
		this.setIgnoreRepaint(true);
		
		timer = new Timer(1000/GraphicsAbstract.TARGET_FRAMERATE,new ActionListener() {
			@Override public void actionPerformed(ActionEvent ae) {
				render();
			}
		});
	}
	/**
	 * Sets up the canvas.  MUST be called AFTER the canvas has been added to
	 * something, because of technical limitations with the doublebuffered context.
	 */
	public void setup() {
		this.createBufferStrategy(2);
		strategy = this.getBufferStrategy();
		
		this.addMouseMotionListener(new MouseMotionListener() {
			@Override public void mouseDragged(MouseEvent me) {}
			@Override public void mouseMoved(MouseEvent me) { MainHandlerInput.getInstance(); MainHandlerInput.mouseMove(me); }
		});
		this.addMouseListener(new MouseListener() {
			@Override public void mouseEntered(MouseEvent me) {}
			@Override public void mouseExited(MouseEvent me) {}
			
			@Override public void mouseClicked(MouseEvent me) {}
			
			@Override public void mousePressed(MouseEvent me) { MainHandlerInput.getInstance(); MainHandlerInput.mouseClick(me); }
			@Override public void mouseReleased(MouseEvent me) {}
		});
		this.addKeyListener(new KeyListener() {
			@Override public void keyPressed(KeyEvent ke) { MainHandlerInput.keyPressed(ke); }
			@Override public void keyReleased(KeyEvent ke) { MainHandlerInput.keyReleased(ke); }
			@Override public void keyTyped(KeyEvent ke) {}
		});
		
		timer.start();
	}
	/**
	 * Returns the instance of the canvas.  Using singleton pattern.
	 * @return the instance.
	 */
	public final static GUIGameCanvas getInstance() {
		if (instance==null) instance = new GUIGameCanvas();
		return instance;
	}
	
	/** Renders the game. */
	private void render() {
		Graphics2D g = (Graphics2D)(strategy.getDrawGraphics());
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON); //http://mindprod.com/jgloss/antialiasing.html
		
		//Insets inset = getBorder().getBorderInsets(this);
		//g.drawImage(img,inset.left,inset.top,d.width,d.height,null);
		
		g.setColor(new Color(0,0,0));
		g.fillRect(0,0,GraphicsAbstract.getWidth(),GraphicsAbstract.getHeight());
		
		GraphicsAbstract.draw(g);
		
		g.dispose();
		strategy.show();
		Toolkit.getDefaultToolkit().sync();
    }
}
