package com.client.GUI.panels;

import java.awt.Color;
import java.awt.Point;

import com.client.Main;
import com.client.Graphics.GraphicsAbstract;
import com.game.characters.GameCharacter;
import com.game.characters.Player;
import com.game.characters.content.npcs.Merchant;
import com.game.environment.Directions;
import com.game.environment.GameMap;
import com.game.environment.tiles.Tile;

public final class GUIPanelInteractMerchant extends GUIPanel {
	/** The instance. */
	private final static GUIPanelInteractMerchant instance = new GUIPanelInteractMerchant();
	
	/** The merchant to which this panel refers. */
	private static Merchant merchant = null;
	
	/** Constructor. */
	private GUIPanelInteractMerchant() {}
	/** Returns an instance of the statistics panel.  Using singleton pattern. */
	public final static GUIPanelInteractMerchant getInstance() {
		return instance;
	}
	
	/** Updates the panel. */
	public final static void update() {
		Player self = Main.getSelf();
		if (self==null) return;
		Point pos = self.getPosition();
		
		GameMap map = Main.getCurrentMap();
		merchant = null;
		for (int y=pos.y-1;y<=pos.y+1;++y) {
			for (int x=pos.x-1;x<=pos.x+1;++x) {
				if (x<0) continue;
				if (y<0) continue;
				if (x>=map.getMapWidth()) continue;
				if (y>=map.getMapHeight()) continue;
				Tile t = map.getTile(x,y);
				GameCharacter gc = t.getOccupant();
				if (gc instanceof Merchant) {
					merchant = (Merchant)(gc);
					return;
				}
			}
		}
		//merchant is null.  This panel must be closed.
		if (instance.is_open) instance.closePanel();
	}
	
	/** Whether the merchant panel can be opened (can't if there's no merchant). */
	public final boolean canBeOpened() {
		return (merchant!=null);
	}
	
	public final void drawCharacter(GameCharacter character, Directions direction, Point pos, int rel_x, int rel_y) {
		Directions temp_direction = character.getDirection();
		character.setDirection(direction);
		GraphicsAbstract.drawCharacterAbsolute(
				character,
				(pos.x+rel_x),
				GraphicsAbstract.getHeight()-(pos.y+rel_y)
		);
		character.setDirection(temp_direction);
	}
	/** {@inheritDoc} */
	@Override public void draw() {
		int w = 500;
		int h = 300;
		Point pos = new Point(
				GraphicsAbstract.getWidth()/2-w/2,
				GraphicsAbstract.getHeight()/2-h/2
		);
		
		GraphicsAbstract.setColor(new Color(64,64,64,225));
		GraphicsAbstract.drawRectFill(
				pos.x,pos.y,
				w,h
		);
		
		Player self = Main.getSelf();
		if (self==null) return;
		
		drawCharacter(self,Directions.RD,pos,20,20);
		drawCharacter(merchant,Directions.LD,pos,w-20,20);
	}
}