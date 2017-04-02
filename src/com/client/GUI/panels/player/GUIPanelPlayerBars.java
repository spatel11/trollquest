package com.client.GUI.panels.player;

import java.awt.Color;

import com.client.Main;
import com.client.ResourceLoader;
import com.client.GUI.panels.GUIPanel;
import com.client.Graphics.DrawableResource;
import com.client.Graphics.GraphicsAbstract;
import com.game.characters.Creature.Stat;
import com.game.characters.Player;
import com.utilities.CubicInterpolator;

/**
 * Statistics panel.
 * @author Ian
 */
public final class GUIPanelPlayerBars extends GUIPanel {
	/** The instance. */
	private final static GUIPanelPlayerBars instance = new GUIPanelPlayerBars();
	
	/** Resource bar empty image. */
	private static DrawableResource resource_bar_empty;
	/** Resource bar full health image. */
	private static DrawableResource resource_bar_fullh;
	/** Resource bar full mana image. */
	private static DrawableResource resource_bar_fullm;
	/** Resource bar full XP image. */
	private static DrawableResource resource_bar_fullx;
	/** Resource bar heart image. */
	private static DrawableResource resource_heart;
	
	/** Variables to describe the beginning HP, Mana, and XP amounts. */
	private static double amt_hp_begin=1.0, amt_mn_begin=1.0, amt_xp_begin=0.0;
	/** Variables to describe the target HP, Mana, and XP amounts. */
	private static double amt_hp_target=1.0, amt_mn_target=1.0, amt_xp_target=0.0;
	/** Cubic interpolators for the HP bar. */
	private static CubicInterpolator interpolate_hp = new CubicInterpolator(1.0,1.0);
	/** Cubic interpolators for the MN bar. */
	private static CubicInterpolator interpolate_mn = new CubicInterpolator(1.0,1.0);
	/** Cubic interpolators for the XP bar. */
	private static CubicInterpolator interpolate_xp = new CubicInterpolator(0.0,0.0);
	/** Time for interpolators. */
	private final static double[] interpolate_time = {1.0,1.0,1.0};
	
	/** Constructor. */
	private GUIPanelPlayerBars() {
		resource_bar_empty = ResourceLoader.loadImageResource(ResourceLoader.DIRECTORY_IMAGES_GUI+      "bar_empty.png");
		resource_bar_fullh = ResourceLoader.loadImageResource(ResourceLoader.DIRECTORY_IMAGES_GUI+"bar_full_health.png");
		resource_bar_fullm = ResourceLoader.loadImageResource(ResourceLoader.DIRECTORY_IMAGES_GUI+  "bar_full_mana.png");
		resource_bar_fullx = ResourceLoader.loadImageResource(ResourceLoader.DIRECTORY_IMAGES_GUI+    "bar_full_xp.png");
		resource_heart     = ResourceLoader.loadImageResource(ResourceLoader.DIRECTORY_IMAGES_GUI+    "heart_small.png");
	}
	/** Returns an instance of the statistics panel.  Using singleton pattern. */
	public final static GUIPanelPlayerBars getInstance() {
		return instance;
	}
	
	/** Updates the amount of the bars. */
	public final static synchronized void update() {
		for (int i=0;i<interpolate_time.length;++i) {
			interpolate_time[i] += 0.05;
			if (interpolate_time[i]>1.0) {
				interpolate_time[i] = 1.0;
			}
		}
	}
	/**
	 * Changes the cubic equation, causing the HP bar to update.  If it's already moving, will have
	 * discontinuous second derivative.  Chances are, though, if this is happening, you're getting
	 * the crap beaten out of you, and aren't going to notice.
	 */
	private final static synchronized void changeTargetHP(double amt_hp_target) {
		interpolate_hp = new CubicInterpolator(amt_hp_begin,amt_hp_target);
		GUIPanelPlayerBars.amt_hp_target = amt_hp_target;
		interpolate_time[0] = 0.0;
	}
	/**
	 * Changes the cubic equation, causing the MN bar to update.  If it's already moving, will have
	 * discontinuous second derivative.  Chances are, though, if this is happening, you're getting
	 * the crap beaten out of you, and aren't going to notice.
	 */
	private final static synchronized void changeTargetMN(double amt_mn_target) {
		interpolate_mn = new CubicInterpolator(amt_mn_begin,amt_mn_target);
		GUIPanelPlayerBars.amt_mn_target = amt_mn_target;
		interpolate_time[1] = 0.0;
	}
	/**
	 * Changes the cubic equation, causing the XP bar to update.  If it's already moving, will have
	 * discontinuous second derivative.  Chances are, though, if this is happening, you're getting
	 * the crap beaten out of you, and aren't going to notice.
	 */
	private final static synchronized void changeTargetXP(double amt_xp_target) {
		interpolate_xp = new CubicInterpolator(amt_xp_begin,amt_xp_target);
		GUIPanelPlayerBars.amt_xp_target = amt_xp_target;
		interpolate_time[2] = 0.0;
	}
	
	/** {@inheritDoc} */
	@Override public final synchronized void draw() {
		Player self = Main.getSelf();
		if (self==null) return;
		synchronized(self) {
			double amt_hp = self.getCurrStatPart(Stat.HEALTHPOINTS);
			//System.out.println("============="+amt_hp+"   "+amt_hp_begin+"   "+interpolate_time[0]+"=================");
			double amt_mn = self.getCurrStatPart(Stat.MAGICPOINTS);
			double amt_xp = self.getCurrStatPart(Stat.EXPERIENCE);
			if (amt_hp_target!=amt_hp) changeTargetHP(amt_hp);
			if (amt_mn_target!=amt_mn) changeTargetMN(amt_mn);
			if (amt_xp_target!=amt_xp) changeTargetXP(amt_xp);
			amt_hp_begin = interpolate_hp.sample(interpolate_time[0]);
			amt_mn_begin = interpolate_mn.sample(interpolate_time[1]);
			amt_xp_begin = interpolate_xp.sample(interpolate_time[2]);
			
			GraphicsAbstract.setFont(GraphicsAbstract.font10);
			GraphicsAbstract.setColor(Color.WHITE);
			
			drawHP(amt_hp_begin);
			drawMP(amt_mn_begin);
			drawXP(amt_xp_begin);
		}
	}
	
	/** Draws the HP bar and icon(s). */
	private final synchronized void drawHP(double hp_amt) {
		GraphicsAbstract.drawResource(resource_bar_empty,
				0,0,-1,-1,
				5,GraphicsAbstract.getHeight()-26,-1,-1);
		GraphicsAbstract.drawResource(resource_bar_fullh,
				0,0,(float)hp_amt,1.0f,
				5,GraphicsAbstract.getHeight()-26,(float)hp_amt,1.0f);
		GraphicsAbstract.drawResource(resource_heart,
				0,0,-1,-1,
				190,GraphicsAbstract.getHeight()-29,-1,-1);
		
		//Font and color were set above in the .draw() method, which calls this.
		GraphicsAbstract.drawText("HP: "+Math.round(100.0*hp_amt)+"%",60,GraphicsAbstract.getHeight()-20);
	}
	/** Draws the MP bar and icon(s). */
	private final synchronized void drawMP(double mn_amt) {
		GraphicsAbstract.drawResource(resource_bar_empty,
				0,0,-1,-1,
				5,GraphicsAbstract.getHeight()-51,-1,-1);
		GraphicsAbstract.drawResource(resource_bar_fullm,
				0,0,(float)mn_amt,1.0f,
				5,GraphicsAbstract.getHeight()-51,(float)mn_amt,1.0f);
		//Font and color were set above in the .draw() method, which calls this.
		GraphicsAbstract.drawText("Mana: "+Math.round(100.0*mn_amt)+"%",60,GraphicsAbstract.getHeight()-46);
	}
	/** Draws the XP bar and icon(s). */
	private final synchronized void drawXP(double xp_amt) {
		GraphicsAbstract.drawResource(resource_bar_empty,
				0,0,-1,-1,
				5,GraphicsAbstract.getHeight()-76,-1,-1);
		GraphicsAbstract.drawResource(resource_bar_fullx,
				0,0,(float)xp_amt,1.0f,
				5,GraphicsAbstract.getHeight()-76,(float)xp_amt,1.0f);
		//Font and color were set above in the .draw() method, which calls this.
		GraphicsAbstract.drawText("Experience: "+Math.round(100.0*xp_amt)+"%",60,GraphicsAbstract.getHeight()-71);
	}
}