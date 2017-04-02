package com.client.Graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.client.Main;
import com.client.MainHandlerInput;
import com.client.ResourceLoader;
import com.client.Dialog.JWorldDialogError;
import com.client.GUI.GUIGameCanvas;
import com.client.GUI.panels.GUIPanelInteractMerchant;
import com.client.GUI.panels.GUIPanels;
import com.client.GUI.panels.ToolTip;
import com.client.GUI.panels.player.GUIPanelPlayerBars;
import com.client.GUI.panels.player.GUIPanelPlayerSpells;
import com.client.Graphics.AnimationsOfACharacter.ActionType;
import com.client.Main.MoveType;
import com.game.characters.Creature;
import com.game.characters.Creature.Stat;
import com.game.characters.GameCharacter;
import com.game.characters.Player;
import com.game.characters.spells.AbstractSpell;
import com.game.environment.Directions;
import com.game.environment.tiles.Tile;
import com.game.environment.tiles.overlay.TileHighlight;
import com.game.environment.tiles.overlay.TileOverlay;
import com.server.State;
import com.utilities.UtilityMath;
import com.utilities.MutableInteger;

/**
 * Abstract graphics class.  Handles the vague and graphics-backend-independent aspects of
 * rendering the image (e.g., pixel coordinate conversion, coordinating drawing, etc.).
 * @author Ian
 */
public abstract class GraphicsAbstract {
	/** The framerate we're going for. */
	public static final int TARGET_FRAMERATE = 30;
	
	/** The graphics to use. */
	private final static GRAPHICS_TYPE graphics_type = GRAPHICS_TYPE.CPU;
	/** The graphics types we could be using. */
	private static enum GRAPHICS_TYPE { CPU }
	
	/** For the singleton pattern. */
	protected static GraphicsAbstract instance;
	
	/** The canvas context to be drawing into. */
	protected static Canvas canvas_context;
	
	/** The graphics backend. */
	protected static Graphics graphics;
	
	/** Size 10 font. */
	public final static Font font10 = ResourceLoader.loadFont("Fontin-Regular.ttf",10);
	/** Size 12 font. */
	public final static Font font12 = ResourceLoader.loadFont("Fontin-Regular.ttf",12);
	/** Size "10" fixed-width font. */
	public final static Font font10fw = ResourceLoader.loadFont("LucidaBrightRegular.ttf",8);
	/** Size "12" fixed-width font. */
	public final static Font font12fw = ResourceLoader.loadFont("LucidaBrightRegular.ttf",10);
	
	/** The highlight tile. */
	public final static TileOverlay tile_highlight = new TileHighlight(0,0);
	
	/** Timer for animating moving the player between tiles. */
	private final static Timer move_anim_timer = new Timer();
	
	/** Starts up the special animations' timer. */
	public static void startAnimationTimer() {
		final int dt = 1000/TARGET_FRAMERATE;
		move_anim_timer.scheduleAtFixedRate(new TimerTask() {
			@Override public void run() {
				State state = Main.getState();
				synchronized(state) {
					for (GameCharacter character : state.getCharactersOnMap(Main.getCurrentMap().getName())) {
						if (character.getActionState()==ActionType.WALK) {
							//Velocity is in pixels per millisecond.
							character.setSubX(character.getSubX()+character.getSubXvel()*1000.0/TARGET_FRAMERATE);
							character.setSubY(character.getSubY()+character.getSubYvel()*1000.0/TARGET_FRAMERATE);
							
							//character.setSubX(  UtilityMath.clamp( character.getSubX(), -64.0, 64.0 )  );
							//character.setSubY(  UtilityMath.clamp( character.getSubY(), -31.0, 31.0 )  );
							
							Point delta = character.getDirection().subpos_delta;
							double x = Math.abs(delta.x);
							double y = Math.abs(delta.y);
							character.setSubX(  UtilityMath.clamp( character.getSubX(), -x,x )  );
							character.setSubY(  UtilityMath.clamp( character.getSubY(), -y,y )  );
						} else {
							//Cannot happen here, or else jittering will happen.  Happens instead when a move command executes.
							//self.setSubX(0.0);
							//self.setSubY(0.0);
						}
					}
				}
				GUIPanelPlayerSpells.update();
				GUIPanelPlayerBars.update();
				GUIPanelInteractMerchant.update();
				ToolTip.updateAll(dt);
				Animation.incrementAllAnimations();
			}
		}, dt, dt);
	}
	
	/** Sets the canvas of the graphics. */
	public final static void setCanvas(GUIGameCanvas canvas) {
		if (canvas==null) throw new IllegalArgumentException("Canvas may not be null!");
		canvas_context = canvas;
	}
	/** Sets the color of the graphics. */
	public final static void setColor(Color color) {
		graphics.setColor(color);
	}
	/** Gets the color of the graphics. */
	public final static Color getColor() {
		return graphics.getColor();
	}
	/** Sets the font of the graphics. */
	public final static void setFont(Font font) {
		graphics.setFont(font);
	}
	/** Returns the font metric of the graphics' currently selected font. */
	public final static FontMetrics getFontMetrics(Font font) {
		return graphics.getFontMetrics(font);
	}
	/**
	 * Draws a rectangle.
	 * @param x the starting x coordinate of the rectangle.
	 * @param y the starting y coordinate of the rectangle.
	 * @param w the width of the rectangle.
	 * @param h the height of the rectangle.
	 */
	public final static void drawRect(int x, int y, int w, int h) {
		graphics.drawRect(x,y,w,h);
	}
	/**
	 * Draws a filled rectangle.  Coordinates are in typical 2D screenspace (i.e., y-axis reversed).
	 * @param x the starting x coordinate of the rectangle.
	 * @param y the starting y coordinate of the rectangle.
	 * @param w the width of the rectangle.
	 * @param h the height of the rectangle.
	 */
	public final static void drawRectFill(int x, int y, int w, int h) {
		graphics.fillRect(x,y,w,h);
	}
	/**
	 * Draws an oval inscribed in the given rectangle.
	 * @param x the starting x coordinate of the rectangle.
	 * @param y the starting y coordinate of the rectangle.
	 * @param w the width of the rectangle.
	 * @param h the height of the rectangle.
	 */
	public final static void drawOval(int x, int y, int w, int h) {
		graphics.drawOval(x, y, w, h);
	}
	
	/**
	 * Draws a DrawableResource.
	 * @param resource the resource to draw.
	 * @param x1 source left
	 * @param y1 source bottom
	 * @param w1 source width.
	 * @param h1 source height.
	 * @param x2 destination left
	 * @param y2 destination bottom
	 * @param w2 destination width.
	 * @param h2 destination height.
	 */
	public static void drawResource(  DrawableResource resource,  int x1, int y1, int w1, int h1,  int x2, int y2, int w2, int h2  ) {
		switch (graphics_type) {
			case CPU:
				GraphicsCPU.drawResource(resource, x1,y1,w1,h1, x2,y2,w2,h2); break;
			default:
				new JWorldDialogError("Unrecognized graphics type "+graphics_type,"Graphics Error",null); break;
		}
	}
	/**
	 * Draws a DrawableResource.
	 * @param resource the resource to draw.
	 * @param x1 source left
	 * @param y1 source bottom
	 * @param w1 source width, as a percent of the source image's total width.
	 * @param h1 source height, as a percent of the source image's total height.
	 * @param x2 destination left
	 * @param y2 destination bottom
	 * @param w2 destination width, as a percent of the source image's total width.
	 * @param h2 destination height, as a percent of the source image's total height.
	 */
	public final static void drawResource(  DrawableResource resource,  int x1, int y1, float w1, float h1,  int x2, int y2, float w2, float h2  ) {
		int w1i = UtilityMath.rndint(w1*resource. width);
		int h1i = UtilityMath.rndint(h1*resource.height);
		int w2i = UtilityMath.rndint(w2*resource. width);
		int h2i = UtilityMath.rndint(h2*resource.height);
		switch (graphics_type) {
			case CPU:
				GraphicsCPU.drawResource(resource, x1,y1,w1i,h1i, x2,y2,w2i,h2i); break;
			default:
				new JWorldDialogError("Unrecognized graphics type "+graphics_type,"Graphics Error",null); break;
		}
	}
	/**
	 * Draws text.
	 * @param text the text to draw.
	 * @param x left.
	 * @param y bottom.
	 */
	public static void drawText(String text, int x, int y) {
		graphics.drawString(
				text,
				x,GraphicsAbstract.canvas_context.getHeight()-y
		);
	}
	
	/** Returns the actual realized width of the graphics context. */
	public final static int getWidth() {
		return canvas_context.getWidth();
	}
	/** Returns the actual realized height of the graphics context. */
	public final static int getHeight() {
		return canvas_context.getHeight();
	}
	
	/**
	 * Returns the mouse position as a point in proper coordinates (i.e.,
	 * (0,0) is bottom left of screen).
	 */
	public final static Point getMousePosition() {
		Point mpos = canvas_context.getMousePosition();
		if (mpos==null) return null;
		mpos.x = mpos.x;
		mpos.y = (getHeight()-mpos.y);
		return mpos;
	}
	/**
	 * Converts the mouse position to map coordinates.
	 * @param tile_x the result x coordinate.
	 * @param tile_y the result y coordinate.
	 * @return true if successful.
	 */
	public final static boolean convertMouseToMap(MutableInteger tile_x, MutableInteger tile_y) {
		Point mpos = getMousePosition();
		if (mpos==null) return false;
		convertPixelToMap(mpos.x,mpos.y,tile_x,tile_y);
		return true;
	}
	/**
	 * Converts a pixel in proper coordinates ((0,0) is bottom-left) into
	 * map coordinates.
	 * @param pixel_x the pixel's x.
	 * @param pixel_y the pixel's y.
	 * @param tile_x the result x coordinate.
	 * @param tile_y the result y coordinate.
	 */
	public final static void convertPixelToMap(int pixel_x, int pixel_y, MutableInteger tile_x, MutableInteger tile_y) {
		Player self = Main.getSelf();
		if (self!=null) {
			pixel_x += UtilityMath.rndint( self.getSubX() );
			pixel_y += UtilityMath.rndint( self.getSubY() );
		}
		
		//pixel_y += 16;
		pixel_x -= canvas_context. getWidth()/2;
		pixel_y -= canvas_context.getHeight()/2;
		
		//32*rel_x - 32*rel_y = pixel_x
		//16*rel_x + 16*rel_y = pixel_y => 32*rel_x + 32*rel_y = 2*pixel_y
		//64*rel_x = pixel_x + 2*pixel_y
		//rel_x = (pixel_x + 2*pixel_y) / 64
		//rel_y = (32*rel_x - pixel_x) / 32 = rel_x - pixel_x/32
		tile_x.set( (pixel_x+2*pixel_y) / 64.0f );
		tile_y.set( (2*pixel_y-pixel_x) / 64.0f );
		
		//System.out.println(pixel_x+" "+pixel_y+" => "+tile_x.get()+" "+tile_y.get());
		
		Point pos;
		if (self==null) pos = new Point(Main.getCurrentMap().getSpawnLoc());
		else pos = self.getPosition();
		tile_x.set(tile_x.get()+pos.x);
		tile_y.set(tile_y.get()+pos.y);
		
		tile_x.set( UtilityMath.clamp(tile_x.get(),0,Main.getCurrentMap() .getMapWidth()-1) );
		tile_y.set( UtilityMath.clamp(tile_y.get(),0,Main.getCurrentMap().getMapHeight()-1) );
	}
	/**
	 * Converts map coordinates to a pixel in proper coordinates ((0,0) is bottom-left).
	 * @param tile_x the x coordinate.
	 * @param tile_y the y coordinate.
	 * @param pixel_x the result pixel's x.
	 * @param pixel_y the result pixel's y.
	 */
	protected final static void convertMapToPixel(int tile_x, int tile_y, MutableInteger pixel_x, MutableInteger pixel_y) {
		Point pos;
		Player self = Main.getSelf();
		if (self==null) pos = new Point(Main.getCurrentMap().getSpawnLoc());
		else pos = self.getPosition();
		tile_x -= pos.x;
		tile_y -= pos.y;
		//if ((tile_x==0)&&(tile_y==0)) { pixel_x.set(Integer.MAX_VALUE); return; }
		pixel_x.set( 32*tile_x - 32*tile_y + canvas_context. getWidth()/2 );
		pixel_y.set( 16*tile_x + 16*tile_y + canvas_context.getHeight()/2 );
		if (self!=null) {
			pixel_x.set( pixel_x.get()-UtilityMath.rndint( self.getSubX() ) );
			pixel_y.set( pixel_y.get()-UtilityMath.rndint( self.getSubY() ) );
		}
	}
	
	/** Draws the map. */
	private final static void drawMap() {
		Tile[][] map = Main.getCurrentMap().getMap();
		
		for (Tile[] row : map) {
			for (Tile t : row) {
				//drawTile(new TileDesert(0,0));
				drawTile(t);
				/*if (!t.hasLoot()) {
					drawTile(t,ResourceLoader.loadImageResource(
							ResourceLoader.DIRECTORY_IMAGES_TILES+"/map_symbols/tile_blank.png"
					)); 
				} else {
					drawTile(t,ResourceLoader.loadImageResource(
							ResourceLoader.DIRECTORY_IMAGES_TILES+"/map_symbols/tile_occupied.png"
					));
				}*/
			}
		}
	}
	/** Draws the current path. */
	private final static void drawPath() {
		if (GUIPanels.hasActivePanel()) return;
		
		Player self = Main.getSelf();
		
		if (self==null) return;
		synchronized(self) {
			if (self.getActionState()==ActionType.STAND) {
				if (!MainHandlerInput.move_path.isEmpty()||Main.move_type!=MoveType.MOVE) {
					//!tile_highlight.getPosition().equals(Main.self.getPosition())
					drawTile(tile_highlight);
				}
			}
		}
		
		Tile previous = null;
		MainHandlerInput.getInstance();
		synchronized(MainHandlerInput.move_path) {
			MainHandlerInput.getInstance();
			for (Tile t : MainHandlerInput.move_path) {
				if (previous!=null) {
					TileOverlay to_draw = null;
					Point delta = Tile.getDelta(t,previous);
					Directions direction = Tile.getDirection(delta);
					switch (direction) {
						case LD: to_draw = Tile.tile_arrow_ld; break;
						case LU: to_draw = Tile.tile_arrow_lu; break;
						case RD: to_draw = Tile.tile_arrow_rd; break;
						case RU: to_draw = Tile.tile_arrow_ru; break;
						case L_: to_draw = Tile.tile_arrow_l_; break;
						case R_: to_draw = Tile.tile_arrow_r_; break;
						case _U: to_draw = Tile.tile_arrow__u; break;
						case _D: to_draw = Tile.tile_arrow__d; break;
						//default: throw new Exception(""); //TODO
					}
					to_draw.setPosition(previous.getPosition());
					drawTile(to_draw);
				}
				previous = t;
			}
		}
		
		synchronized(self) { synchronized(Main.move_type) {
			if (self.getActionState()!=ActionType.STAND) {
				if (previous!=null) {
					TileOverlay to_draw = null;
					switch(Main.move_type) {
						case MOVE: to_draw = Tile.tile_movement; break;
						case MOVE_TO_ATTACK: to_draw = Tile.tile_attack; break;
						case MOVE_TO_CAST: to_draw = Tile.tile_cast; break;
						default: new JWorldDialogError("Cannot represent the move type "+Main.move_type,"Fatal Error",null); break;
					}
					to_draw.setPosition(previous.getPosition());
					drawTile(to_draw);
				}
			}
		}}
	}
	/** Draws a tile using its DrawableResource. */
	private final static void drawTile(Tile tile) {
		drawTile(tile,null);
	}
	/** Draws a tile using the given DrawableResource. */
	private final static void drawTile(Tile tile, DrawableResource resource) {
		Point position = tile.getPosition();
		int[] offset = tile.getImageOffset();
		MutableInteger pixel_x = new MutableInteger(0), pixel_y = new MutableInteger(0);
		convertMapToPixel(position.x,position.y,pixel_x,pixel_y);
		if (resource==null) {
			drawResource(
					tile.getResource(),
					0,0,-1,-1,
					pixel_x.get()+offset[0],pixel_y.get()+offset[1],-1,-1
			);
		} else {
			drawResource(
					resource,
					0,0,-1,-1,
					pixel_x.get()+offset[0],pixel_y.get()+offset[1],-1,-1
			);
		}
		if (tile.hasLoot()) {
			//System.err.println("Tile " + tile.getPosition().x + "," + tile.getPosition().y + " has loot");
			drawResource(
					Tile.resource_loot,
					0,0,-1,-1,
					pixel_x.get()-16,pixel_y.get()-20,-1,-1
			);
		}
	}
	
	/** Draws a text bar for a character. */
	private final static void drawCharacterBarText(String text, int pixel_x, int pixel_y) {
		setFont(font10);
		int w = getFontMetrics(font10).stringWidth(text);
		
		setColor(new Color(64,64,64,192));
		drawRectFill(pixel_x-10,getHeight()-(pixel_y+45),w+2,10);
		
		setColor(Color.WHITE);
		drawText(text,pixel_x-10+1,pixel_y+37);
	}
	/**
	 * Draws a creature's bar.
	 * @param pixel_x the x pixel coordinate of the creature.
	 * @param pixel_y the y pixel coordinate of the creature.
	 * @param number the number of the bar (used to offset pixel_x).
	 * @param part the amount of the bar to display.
	 * @param filled whether the bar should be filled or not.
	 */
	private final static void drawCharacterBar(int pixel_x, int pixel_y, int number, double part, boolean filled) {
		int w = 4;
		int x = pixel_x + 20 + (w+1)*number;
		int h = UtilityMath.rndint(30.0*part);
		int y = getHeight() - (pixel_y+30-(30-h));
		if (filled) drawRectFill(x,y,w,h);
		else drawRect(x,y,w-1,h); //width minus one because of stupid Java's stupid rasterization rules.
	}
	/** Draws a character's health, mana, and XP bars. */
	private final static void drawCreatureBars(Creature creature, int pixel_x, int pixel_y) {
		setColor(new Color(64,64,64,192));
		drawCharacterBar(pixel_x,pixel_y, 0, 1.0,  true);
		drawCharacterBar(pixel_x,pixel_y, 1, 1.0,  true);
		drawCharacterBar(pixel_x,pixel_y, 2, 1.0,  true);
		
		setColor(new Color(0,192,0));
		drawCharacterBar(pixel_x,pixel_y, 0, creature.getCurrStatPart(Stat.HEALTHPOINTS),  true);
		setColor(new Color(0,0,192));
		drawCharacterBar(pixel_x,pixel_y, 1, creature.getCurrStatPart(Stat.MAGICPOINTS),  true);
		setColor(new Color(135,45,129));
		drawCharacterBar(pixel_x,pixel_y, 2, creature.getCurrStatPart(Stat.EXPERIENCE),  true);
		
		setColor(new Color(32,32,32));
		drawCharacterBar(pixel_x,pixel_y, 0, 1.0, false);
		drawCharacterBar(pixel_x,pixel_y, 1, 1.0, false);
		drawCharacterBar(pixel_x,pixel_y, 2, 1.0, false);
	}
	/**
	 * Draws a character at an absolute location and with the resource
	 * corresponding to its internal state.
	 * @param character the character.
	 * @param pixel_x the absolute x coordinate.
	 * @param pixel_y the absolute y coordinate.
	 */
	public final static void drawCharacterAbsolute(GameCharacter character, int pixel_x, int pixel_y) {
		DrawableResource anim = character.getResource();
		if (anim==null) {
			String err = "";
			err += "Could not find a player resource, because it was not be loaded, because it did not exist!\n";
			err += "Ideally, the player would have been facing "+character.getDirection()+", but the requisite files.\n";
			err += "were not present."+character;
			new JWorldDialogError(err,"Fatal Error",null);
		}
		drawResource(anim,
				0,0,-1,-1,
				pixel_x-anim. width/2   +UtilityMath.rndint( character.getSubX() ),
				pixel_y-anim.height/2+12+UtilityMath.rndint( character.getSubY() ),-1,-1);
	}
	/**
	 * Draws a character at the correct location and with the resource
	 * corresponding to its internal state.
	 * @param character the character.
	 */
	private final static void drawCharacter(GameCharacter character) {
		MutableInteger pixel_x=new MutableInteger(0), pixel_y=new MutableInteger(0);
		Point pos = character.getPosition(); convertMapToPixel(pos.x,pos.y,pixel_x,pixel_y);
		drawCharacterAbsolute(character,pixel_x.get(),pixel_y.get());
	}
	/** Draws a character's name. */
	private final static void drawCharacterName(GameCharacter character) {
		MutableInteger pixel_x=new MutableInteger(0), pixel_y=new MutableInteger(0);
		Point pos = character.getPosition(); convertMapToPixel(pos.x,pos.y,pixel_x,pixel_y);
		
		pixel_x.set(UtilityMath.rndint( pixel_x.get() + character.getSubX() ));
		pixel_y.set(UtilityMath.rndint( pixel_y.get() + character.getSubY() ));
		
		drawCharacterBarText(character.name,pixel_x.get(),pixel_y.get());
	}
	/** Draws a creature's stats. */
	private final static void drawCreatureStats(GameCharacter character) {
		if (!(character instanceof Creature)) return;
		Creature creature = (Creature)(character);
		
		MutableInteger pixel_x=new MutableInteger(0), pixel_y=new MutableInteger(0);
		Point pos = creature.getPosition(); convertMapToPixel(pos.x,pos.y,pixel_x,pixel_y);
		
		pixel_x.set(UtilityMath.rndint( pixel_x.get() + creature.getSubX() ));
		pixel_y.set(UtilityMath.rndint( pixel_y.get() + creature.getSubY() ));
		
		drawCreatureBars(creature,pixel_x.get(),pixel_y.get());
		
		pixel_x.set(UtilityMath.rndint( pixel_x.get() -  5 ));
		pixel_y.set(UtilityMath.rndint( pixel_y.get() - 60 ));
		
		drawCharacterBarText("Level: "+creature.getCurrStat(Stat.LEVEL),pixel_x.get(),pixel_y.get());
	}
	
	/** Draws all spells. */
	private final static void drawSpells() {
		//Call .drawSpell()
	}
	/** Draws a spell. */
	@SuppressWarnings("unused") private final static void drawSpell(AbstractSpell spell) {
		//spell.
	}
	
	/** Draws the GUI. */
	private final static void drawPanels() {
		GUIPanels.draw();
	}
	
	/** Main drawing method.  Called by the canvas when it's time to repaint. */
	public final static void draw(Graphics g) {
		graphics = g;
		
		//drawTile(new TileDesert(0,0));
		
		State state = Main.getState();
		synchronized(state) {
			drawMap();
		}
		drawPath(); //beware of deadlock
		synchronized(state) {
			List<GameCharacter> chars = state.getCharactersOnMap(Main.getCurrentMap().getName());
			for (GameCharacter gc : chars) drawCharacter(gc);
			for (GameCharacter gc : chars) drawCreatureStats(gc);
			for (GameCharacter gc : chars) drawCharacterName(gc);
			drawSpells();
		}
		
		drawPanels();
	}
}