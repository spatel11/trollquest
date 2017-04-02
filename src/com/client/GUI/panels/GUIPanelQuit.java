package com.client.GUI.panels;

import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;

import com.client.Main;
import com.client.ResourceLoader;
import com.client.Graphics.DrawableResource;
import com.client.Graphics.GraphicsAbstract;
import com.utilities.Function;
import com.utilities.UtilityMath;

/**
 * Encapsulates drawing a quit panel.
 * @author Ian
 */
public class GUIPanelQuit extends GUIPanelMovable {
	/** The instance. */
	private final static GUIPanelQuit instance = new GUIPanelQuit();
	/** The images for the panel and for the chain. */
	private static DrawableResource panel_quit, panel_chain;
	
	/** Part how much the panel is exposed. */
	private double part_exposed = 0.0;
	/** Whether the panel is in motion. */
	private Boolean moving = false;
	/** Timer to help with animation. */
	private Timer timer;
	
	/** Buttons.  Quit and cancel. */
	private final GUIButton button_quit,button_cancel;
	
	/** Constructor. */
	private GUIPanelQuit() {
		super(null);
		
		panel_quit = ResourceLoader.loadImageResource(ResourceLoader.DIRECTORY_IMAGES_GUI+"quit.png");
		panel_chain = ResourceLoader.loadImageResource(ResourceLoader.DIRECTORY_IMAGES_GUI+"quitchain.png");
		
		button_quit    = new GUIButton(this,"RAGE QUIT");
		button_cancel  = new GUIButton(this,   "Cancel");
		
		button_quit  .setFunctionPressed(new Function() { @Override public void execute(Object... arguments) {  Main.quit(); } });
		button_cancel.setFunctionPressed(new Function() { @Override public void execute(Object... arguments) { closePanel(); } });
		
		children.put(  "quit",  button_quit);
		children.put("cancel",button_cancel);
	}
	/** Returns an instance of the quit panel.  Using singleton pattern. */
	public static GUIPanelQuit getInstance() {
		return instance;
	}
	
	/** Prepares the panel to move down. */
	private void setupMoveDown() {
		synchronized(moving) {
			if (moving) return;
			moving = true;
			
			part_exposed = 0.0;
			timer = new Timer();
			timer.scheduleAtFixedRate( new TimerTask() { @Override public void run() { moveDown(); } },10,10 );
		}
	}
	/** Prepares the panel to move up. */
	private void setupMoveUp() {
		synchronized(moving) {
			if (moving) return;
			moving = true;
			
			part_exposed = 1.0;
			timer = new Timer();
			timer.scheduleAtFixedRate( new TimerTask() { @Override public void run() { moveUp(); } },10,10 );
		}
	}
	/** Moves the buttons. */
	private void moveButtons() {
		int y = getExposedY() + 10;
		button_quit.setPositionRel(
				GraphicsAbstract.getWidth()/2 - 80,
				y
		);
		button_cancel.setPositionRel(
				GraphicsAbstract.getWidth()/2 + 20,
				y
		);
	}
	/** Moves everything down. */
	private void moveDown() {
		part_exposed += 0.03;
		moveButtons();
		if (part_exposed>=1.0) {
			timer.cancel();
			synchronized(moving) {
				moving = false;
			}
		}
	}
	/** Moves everything up. */
	private void moveUp() {
		part_exposed -= 0.03;
		moveButtons();
		if (part_exposed<=0.0) {
			timer.cancel();
			synchronized(moving) {
				moving = false;
			}
			super.closePanel();
		}
	}
	
	/** {@inheritDoc} */
	@Override public void openPanel() {
		super.openPanel();
		setupMoveDown();
	}
	/** {@inheritDoc} */
	@Override public void closePanel() {
		setupMoveUp();
	}
	
	/** Gets the exposed y coordinate. */
	private int getExposedY() {
		int y_start = GraphicsAbstract.getHeight();
		int y_end = GraphicsAbstract.getHeight()/2-panel_quit.height/2;
		int y = UtilityMath.rndint(  y_start + part_exposed*(y_end-y_start)  );
		return y;
	}
	/** {@inheritDoc} */
	@Override public void draw() {
		int y = getExposedY();
		
		int center = GraphicsAbstract.getWidth()/2;
		
		GraphicsAbstract.drawResource(panel_quit, 0,0,-1,-1, center-panel_quit.width/2,y,-1,-1);
		
		GraphicsAbstract.setFont(GraphicsAbstract.font10);
		GraphicsAbstract.setColor(new Color(255,255,255));
		GraphicsAbstract.drawText("YOU!  So ya wanna quit, huh?", center-panel_quit.width/2+10,y+45);
		
		y += panel_quit.height;
		while (y<GraphicsAbstract.getHeight()) {
			GraphicsAbstract.drawResource(panel_chain, 0,0,-1,-1, center-panel_chain.width/2+1,y,-1,-1);
			y += panel_chain.height;
		}
	}
}