package com.client.GUI.panels;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.client.GUI.Controls;
import com.client.GUI.panels.player.GUIPanelPlayerBars;
import com.client.GUI.panels.player.GUIPanelPlayerCreate;
import com.client.GUI.panels.player.GUIPanelPlayerInventory;
import com.client.GUI.panels.player.GUIPanelPlayerQuests;
import com.client.GUI.panels.player.GUIPanelPlayerSpells;
import com.client.GUI.panels.player.GUIPanelPlayerStats;

/**
 * Defines a way to access the panels that go in the game that the user
 * can interact with.
 * @author Ian
 */
public abstract class GUIPanels {
	//Activate-able panels (one ONE may exist at a time).
	//TODO: Quit panel, setup character panel(s)
	/** Inventory panel. */
	private final static GUIPanel panel_inventory;
	/** Message panel. */
	private final static GUIPanel panel_message;
	/** Character creation panel. */
	private final static GUIPanel panel_player_create;
	/** Quit panel. */
	private final static GUIPanel panel_quit;
	/** Quests panel. */
	private final static GUIPanel panel_quests;
	/** The player's statistics panel. */
	private final static GUIPanel panel_statistics;
	/** The NPC interaction panel. */
	private final static GUIPanelInteractMerchant panel_merchant;
	/** The active panel. */
	private static GUIPanel panel_active = null;
	
	//Static panels (these ALL exist ALL the time).
	/** The player's HUD (main bars). */
	private final static GUIPanel panel_bars = GUIPanelPlayerBars.getInstance();
	/** Control bar panel. */
	private final static GUIPanel panel_control_bar = GUIPanelControlBar.getInstance();
	/** Spells panel. */
	private final static GUIPanel panel_spells = GUIPanelPlayerSpells.getInstance();
	
	/** Static initializer. */
	static {
		panel_inventory = GUIPanelPlayerInventory.getInstance();
		panel_message = GUIPanelMessage.getInstance();
		panel_player_create = GUIPanelPlayerCreate.getInstance();
		panel_quit = GUIPanelQuit.getInstance();
		panel_quests = GUIPanelPlayerQuests.getInstance();
		panel_statistics = GUIPanelPlayerStats.getInstance();
		panel_merchant = GUIPanelInteractMerchant.getInstance();
	}
	
	/**
	 * Opens a panel.
	 * @param opened the panel to be opened.
	 */
	public final static synchronized void openPanel(GUIPanel opened) {
		if (panel_active!=null) return;
		panel_active = opened;
		panel_active.openPanel();
	}
	/** Forces the player creation panel open. */
	public final static synchronized void openPanelCreate() {
		//System.out.println("###############OPENING CHARATER CREATION PANEL####################");
		panel_active = panel_player_create;
		panel_active.openPanel();
	}
	/** Closes the open panel. */
	public final static synchronized void closePanel() {
		if (panel_active==null) return;
		if (panel_active==panel_player_create) return;
		//System.out.println("###############CLOSING PANEL####################");
		panel_active.closePanel();
	}
	
	/** Forces a panel open. */
	public final static synchronized void openPanelForce(GUIPanel panel) {
		panel_active = null;
		openPanel(panel);
	}
	
	/**
	 * Toggles whether a given panel is open or closed.
	 * @param toggled the panel to be toggled.
	 */
	private final static synchronized void toggle(GUIPanel toggled) {
		if (panel_active!=null) closePanel();
		else openPanel(toggled);
	}
	/** Toggles the inventory panel. */
	public final static synchronized void togglePanelInventory() { toggle(panel_inventory); }
	/** Toggles the message panel. */
	public final static synchronized void togglePanelMessage() { toggle(panel_message); }
	/** Toggles the quests panel. */
	public final static synchronized void togglePanelQuests() { toggle(panel_quests); }
	/** Toggles the statistics panel. */
	public final static synchronized void togglePanelStatistics() { toggle(panel_statistics); }
	/** Toggles the merchant panel. */
	public final static synchronized void togglePanelMerchant() {
		if (!panel_merchant.canBeOpened()) {
			if (panel_active==panel_merchant) panel_active = null;
			return;
		}
		toggle(panel_merchant);
	}
	
	/** Returns if there's a modal panel open. */
	public static boolean hasActivePanel() {
		return (panel_active!=null);
	}
	
	/**
	 * Chooses what panel to open, if any, based on a keypress.
	 * @param ke the key event.
	 * @return if the input did anything.
	 */
	public final static synchronized boolean processKey(KeyEvent ke) {
		if (ke.isControlDown()) {
			switch (ke.getExtendedKeyCode()) {
				case Controls.OC_INVENTORY: togglePanelInventory(); return true;
				case Controls.OC_MESSAGES: togglePanelMessage(); return true;
				case Controls.OC_QUESTS: togglePanelQuests(); return true;
				case Controls.OC_STATS: togglePanelStatistics(); return true;
				case Controls.OC_MERCHANT: togglePanelMerchant(); return true;
			}
		} else {
			switch (ke.getExtendedKeyCode()) {
				case Controls.EXIT: toggle(panel_quit); return true;
			}
		}
		if (panel_active!=null) {
			if (panel_active.processKey(ke)) return true;
		}
		return false;
	}
	
	/**
	 * Tells all the panels to process a mouse click command.
	 * @param me the mouse event.
	 * @return if panel(s) could have done anything.
	 */
	public final static synchronized boolean processMouseClick(MouseEvent me) {
		boolean result = false;
		if (panel_active!=null) panel_active.processMouseClick(me);
		result |= panel_bars.processMouseClick(me);
		result |= panel_control_bar.processMouseClick(me);
		result |= panel_spells.processMouseClick(me);
		if (panel_active!=null) return true;
		return result;
	}
	/**
	 * Tells all the panels to process a mouse movement command.
	 * @param me the mouse event.
	 */
	public final static synchronized void processMouseMove(MouseEvent me) {
		//System.out.println("panel_active = "+panel_active);
		if (panel_active!=null) panel_active.processMouseMove(me);
		panel_bars.processMouseMove(me);
		panel_control_bar.processMouseMove(me);
		panel_spells.processMouseMove(me);
	}
	
	/** Draws all the panels. */
	public final static synchronized void draw() {
		//Static panels (can be drawn over, somewhat).
		panel_bars.drawRecursive();
		panel_control_bar.drawRecursive();
		panel_spells.drawRecursive();
		
		//Activate-able panels.
		if (panel_active!=null) panel_active.drawRecursive();
	}

	/**
	 * The observer update method.  This is called by a closing panel, to allow
	 * this class to know that it is now safe to open a new panel, because the
	 * closing panel has now fully closed.
	 */
	public final static synchronized void observerUpdate() {
		//System.out.println("###############CLOSED PANEL####################");
		panel_active = null;
	}
}