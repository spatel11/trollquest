package com.client.GUI.panels.player;

import java.awt.Color;
import java.awt.Point;

import com.client.Main;
import com.client.GUI.panels.GUIPanel;
import com.client.Graphics.GraphicsAbstract;
import com.game.characters.Creature.Stat;
import com.game.characters.Player;
import com.game.environment.Directions;

public final class GUIPanelPlayerStats extends GUIPanel {
	/** The instance. */
	private final static GUIPanelPlayerStats instance = new GUIPanelPlayerStats();
	
	/** Constructor. */
	private GUIPanelPlayerStats() {}
	/** Returns an instance of the statistics panel.  Using singleton pattern. */
	public final static GUIPanelPlayerStats getInstance() {
		return instance;
	}
	
	/** Draws a line of text. */
	private final void drawLine(String text, Point pos, int indent) {
		GraphicsAbstract.drawText(text,pos.x+indent,pos.y);
		pos.y -= 11;
	}
	/** {@inheritDoc} */
	@Override public void draw() {
		GraphicsAbstract.setColor(new Color(64,64,64,225));
		
		Point pos = new Point(
				GraphicsAbstract.getWidth()/2-100,
				GraphicsAbstract.getHeight()/2-115
		);
		GraphicsAbstract.drawRectFill(
				pos.x,pos.y,
				200,230
		);
		
		Player self = Main.getSelf();
		if (self==null) return;
		
		GraphicsAbstract.setFont(GraphicsAbstract.font10);
		GraphicsAbstract.setColor(Color.WHITE);
		
		synchronized(self) {
			//Draw a representation of the player.
			//	Hacks to draw the player facing right down.
			Directions temp_direction = self.getDirection();
			self.setDirection(Directions.RD);
			GraphicsAbstract.drawCharacterAbsolute(
					self,
					(pos.x+20),
					GraphicsAbstract.getHeight()-(pos.y+20)
			);
			self.setDirection(temp_direction);
		
			//Draw vital statistics about the player.
			pos.x +=  20;
			pos.y += 255-75;
			
			drawLine("Name: \""+self.name+"\"",pos,0);
			drawLine("Gender: \""+self.gender.name().toLowerCase()+"\"",pos,0);
			drawLine("Currently Equipped:",pos,0);
			drawLine(  "Armor: \""+((self.getArmor() == null) ? self.getArmor() : self.getArmor().NAME)+"\"",pos,15);
			drawLine(  "Weapon: \""+((self.getWeapon() == null) ? self.getWeapon() : self.getWeapon().NAME)+"\"",pos,15);
			drawLine("Statistics: (Current/Base)",pos,0);
			drawLine(  "Health:                  "  +self.getCurrStat(Stat.HEALTHPOINTS)+" / "+self.getBaseStat(Stat.HEALTHPOINTS),pos,15);
			drawLine(  "Magic:                   "  +self.getCurrStat(Stat. MAGICPOINTS)+" / "+self.getBaseStat(Stat. MAGICPOINTS),pos,15);
			drawLine(  "Experience:            "    +self.getCurrStat(Stat.  EXPERIENCE)+" / "+self.getBaseStat(Stat.  EXPERIENCE),pos,15);
			drawLine(  "Level:                     "+self.getCurrStat(Stat.       LEVEL),pos,15);
			drawLine(  "Defense:                 "  +self.getCurrStat(Stat.DEFENSEVALUE)+" / "+self.getBaseStat(Stat.DEFENSEVALUE),pos,15);
			drawLine(  "Encumberance:      "        +self.getCurrStat(Stat.ENCUMBERANCE)+" / "+self.getBaseStat(Stat.ENCUMBERANCE),pos,15);
			drawLine(  "Speed:                    " +self.getCurrStat(Stat.       SPEED)+" / "+self.getBaseStat(Stat.       SPEED),pos,15);
			drawLine(  "Intelligence:           "   +self.getCurrStat(Stat.INTELLIGENCE)+" / "+self.getBaseStat(Stat.INTELLIGENCE),pos,15);
			drawLine(  "Strength:                "  +self.getCurrStat(Stat.    STRENGTH)+" / "+self.getBaseStat(Stat.    STRENGTH),pos,15);
			drawLine("Current Map: \""+self.getMap()+"\"",pos,0);
		}
	}
}