package com.client.GUI;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.client.Main;
import com.client.Graphics.GraphicsAbstract;

/**
 * The main GUI class.  Sets up a frame and adds the necessary things to it.
 * @author Ian
 */
public final class GUIMain extends Frame {
	/** Required for some reason. */
	private static final long serialVersionUID = -7699227314654882636L;
	/** The instance of this frame. */
	private static GUIMain instance = null;
	
	/** The requested dimensions of the frame. */
	public final static Dimension SCREEN_SIZE = new Dimension(800,600);

	/** Sets up the main GUI. */
	private GUIMain() {
		instance = this;
		
		//this.setUndecorated(true);
		//this.setOpacity(0.5f);
		
		this.setTitle("TrollQuest-CS351-Fall 2011");
		
		Point screen_center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		this.setLocation(new Point(screen_center.x-SCREEN_SIZE.width/2,screen_center.y-SCREEN_SIZE.height/2));
		//this.setResizable(false);
		this.setPreferredSize(SCREEN_SIZE);
		this.setMinimumSize(SCREEN_SIZE);
		this.setMaximumSize(SCREEN_SIZE);
		
		this.addWindowListener(new WindowAdapter() {
			@Override public void windowClosing(WindowEvent e) {
				instance.setVisible(false);
				instance.dispose();
				Main.quit();
				while (true); //Wait for the other thread to close process.
			}
		});
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GUIGameCanvas game_canvas = GUIGameCanvas.getInstance();
		GraphicsAbstract.setCanvas(game_canvas);
		GraphicsAbstract.startAnimationTimer();
		this.add(game_canvas);
		
		this.pack();
		
		this.setVisible(true); //Must come before setup of doublebuffered canvas
		//this.requestFocusInWindow();
		
		game_canvas.setup();
		game_canvas.requestFocus();
	}

	/**
	 * Returns this frame.  Using singleton pattern.
	 * @return the instance.
	 */
	public static GUIMain getInstance() {
		if (instance==null) instance = new GUIMain();
		return instance;
	}
	
	/**
	 * Returns whether there is an instance.
	 * @return whether there is an instance.
	 */
	public static boolean hasInstance() {
		return (instance!=null);
	}
}