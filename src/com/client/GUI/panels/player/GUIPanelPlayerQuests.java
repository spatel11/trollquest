package com.client.GUI.panels.player;

import java.awt.Color;
import java.awt.Point;

import com.client.Main;
import com.client.ResourceLoader;
import com.client.GUI.panels.GUIPanel;
import com.client.Graphics.DrawableResource;
import com.client.Graphics.GraphicsAbstract;
import com.game.characters.Player;
import com.game.quest.Quest;
import com.game.quest.QuestEvent;

/**
 * Encapsulates the panel that contains all quests.
 * @author Ian
 */
public final class GUIPanelPlayerQuests extends GUIPanel {
	/** The instance. */
	private final static GUIPanelPlayerQuests instance = new GUIPanelPlayerQuests();
	
	/** Resource bar empty image. */
	private static DrawableResource resource_scroll;
	
	/** Constructor. */
	private GUIPanelPlayerQuests() {
		resource_scroll = ResourceLoader.loadImageResource(ResourceLoader.DIRECTORY_IMAGES_GUI+"scroll.png");
	}
	/** Returns an instance of the statistics panel.  Using singleton pattern. */
	public final static GUIPanelPlayerQuests getInstance() {
		return instance;
	}
	
	/** Draws a line of text. */
	private final void drawLine(String text, Point pos, int indent) {
		GraphicsAbstract.drawText(text,pos.x+indent,pos.y);
		pos.y -= 11;
	}
	/** {@inheritDoc} */
	@Override public final void draw() {
		Point pos = new Point(
				GraphicsAbstract.getWidth()/2-resource_scroll.width/2,
				GraphicsAbstract.getHeight()/2-resource_scroll.height/2
		);
		GraphicsAbstract.drawResource(resource_scroll,
				0,0,-1,-1,
				pos.x,pos.y,
				-1,-1
		);
		
		Player self = Main.getSelf();
		if (self==null) return;
		
		pos.x +=  70;
		pos.y += 400;
		
		GraphicsAbstract.setFont(GraphicsAbstract.font10);
		GraphicsAbstract.setColor(Color.BLACK);
		
		Quest[] quests = { self.getPlayersQuest() };
		for (Quest quest : quests) {
			drawLine("\""+quest.getQuestName()+"\":",pos,0);
			boolean completed = true;
			for (QuestEvent qe : quest.getAllSteps()) {
				//System.err.println(quest.getCurrentStepIndex()+ "=================================");
				drawLine("-"+qe.toString(),pos,15);
				if (qe.equals(quest.getCurrentStep())) {
					GraphicsAbstract.setColor(new Color(0,0,0,64));
					completed = false;
				}
				if (completed) {
					GraphicsAbstract.drawRectFill(pos.x+12,GraphicsAbstract.getHeight()-(pos.y+14),210,1);
				}
			}
		}
	}
}