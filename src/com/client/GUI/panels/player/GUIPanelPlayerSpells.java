package com.client.GUI.panels.player;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.client.Main;
import com.client.ResourceLoader;
import com.client.Dialog.JWorldDialogError;
import com.client.GUI.panels.GUIPanelMovable;
import com.client.Graphics.DrawableResource;
import com.client.Graphics.GraphicsAbstract;
import com.game.characters.spells.AbstractSpell;
import com.server.command.*;
import com.utilities.UtilityMath;

/**
 * Class that encapsulates drawing the the spells bar.
 * @author Ian
 */
public final class GUIPanelPlayerSpells extends GUIPanelMovable { //Not really movable, but in principle so.
	/** The instance. */
	private static GUIPanelPlayerSpells instance = null;
	/** Resource bar image. */
	private static DrawableResource resource_bar;
	/** List of all the spell books we know. */
	private final static ArrayList<SpellBook> books = new ArrayList<SpellBook>();
	/** The current index of the spellbook we're using. */
	private static int index = 0;
	
	/** Constructor. */
	private GUIPanelPlayerSpells() {
		super(null);
		
		resource_bar = ResourceLoader.loadImageResource(ResourceLoader.DIRECTORY_IMAGES_GUI+"bar.png",1.5f);
		
		/*"Open a Stargate"
		"Armageddon"
		"Dragon's Flame"
		"Flight"
		"Cocaine"
		"LSD"
		"Zorch"
		"Zot"*/
	}
	/** Returns the instance of the spells bar.  Using singleton pattern. */
	public final static GUIPanelPlayerSpells getInstance() {
		if (instance==null) instance = new GUIPanelPlayerSpells();
		return instance;
	}
	
	/** Gets a command representing casting the currently selected spell. */
	public final static ComparableCommand castSomething(Point target_location) {
		for (SpellBook sb : books) {
			if (sb.active) {
				return sb.getCommandSpell(target_location);
			}
		}
		new JWorldDialogError("No spell book was active . . . somehow.","Fatal Error",null);
		return null;
	}
	
	/**
	 * Adds a spell to the list of spells the player knows about.
	 * @param id the spell's id (used for ordering).
	 * @param spell the spell the new book should correspond to.
	 */
	public final static void addSpell(int id, AbstractSpell spell) {
		books.add(new SpellBook(id,spell));
		index = books.size()-1;
	}
	
	/**
	 * Selects the next spellbook downwards.
	 */
	public final static void selectDown() {
		if (books.size()==0) return;
		index = UtilityMath.mod(index+1,books.size());
		
		for (SpellBook sb : books) sb.deselect();
		books.get(index).select();
	}
	/**
	 * Selects the next spellbook upwards.
	 */
	public final static void selectUp() {
		if (books.size()==0) return;
		index = UtilityMath.mod(index-1,books.size());
		
		for (SpellBook sb : books) sb.deselect();
		books.get(index).select();
	}
	
	/**
	 * Updates the spellbooks.  Primarily an animation thing, but also serves
	 * to update the list of books we know about.
	 */
	public final static void update() {
		if (Main.getSelf()==null) return;
		
		List<AbstractSpell> spell_list = Main.getSelf().getSpellInventory().getSpellSet();
		if (spell_list.size()!=books.size()) {
			books.clear();
			for (AbstractSpell spell : spell_list) {
				addSpell(books.size(),spell);
			}
			books.get(0).select();
		}
		for (SpellBook sb : books) {
			sb.update();
		}
	}
	
	/** {@inheritDoc} */
	@Override public final void draw() {
		GraphicsAbstract.drawResource(resource_bar,
				0,0,-1,-1,
				GraphicsAbstract.getWidth()-30,GraphicsAbstract.getHeight()-250,-1,-1);
		for (SpellBook sb : books) {
			sb.draw(books.size());
		}
	}
}