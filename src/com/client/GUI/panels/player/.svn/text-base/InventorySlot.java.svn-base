package com.client.GUI.panels.player;

import java.awt.Point;

import com.client.Main;
import com.client.ResourceLoader;
import com.client.GUI.panels.GUIPanelMovable;
import com.client.Graphics.DrawableResource;
import com.client.Graphics.GraphicsAbstract;
import com.game.items.Swag;

/**
 * Encapsulates an inventory slot.  
 * @author Ian
 */
public final class InventorySlot extends GUIPanelMovable {
	/** Resource empty tile. */
	private final static DrawableResource resource_tile_empty = ResourceLoader.loadImageResource(ResourceLoader.DIRECTORY_IMAGES_GUI+"tile_empty.png");
	/** Resource hovering tile. */
	private final static DrawableResource resource_tile_hover = ResourceLoader.loadImageResource(ResourceLoader.DIRECTORY_IMAGES_GUI+"square_hover.png");
	/** Resource selected tile. */
	private final static DrawableResource resource_tile_select = ResourceLoader.loadImageResource(ResourceLoader.DIRECTORY_IMAGES_GUI+"square_select.png");
	
	/** Whether we're hovering or selected. */
	private boolean hovering=false, selected=false;
	/** The index of the inventory that this inventory slot corresponds to. */
	public final int inventory_index;
	
	/**
	 * Constructor.
	 * @param parent the parent frame (used for positioning).
	 * @param index the index of the player's inventory this slot corresponds to.
	 */
	public InventorySlot(GUIPanelPlayerInventory parent, int index) {
		super(parent);
		inventory_index = index;
	}

	/**
	 * Returns true if the inventory slot is empty.
	 * @return whether the slot is empty.
	 */
	public synchronized boolean isEmpty() { return getSwag()==null; }

	/** Toggles whether this inventory space is selected. */
	public void toggleSelected() { selected = !selected; }
	/**
	 * Sets whether the inventory space is being hovered over.
	 * @param hover if it is.
	 */
	public void setHovering(boolean  hover) { hovering =  hover; }
	/**
	 * Sets whether the inventory space is selected.
	 * @param select if it is.
	 */
	public void setSelected(boolean select) { selected = select; }
	/** Returns true if the inventory slot is selected. */
	public boolean isSelected() { return selected; }
	/** Returns true if the inventory slot is being hovered over. */
	public boolean isHovering() { return hovering; }
	
	/**
	 * Returns the swag associated with this inventory slot.
	 * @return the swag.
	 */
	public Swag getSwag() {
		return Main.getSelf().getInventory().getSwagAt(inventory_index);
	}

	/**
	 * Swaps two components places in the inventory.  Note that it is fine if
	 * the spaces are empty.
	 * @param is1
	 * @param is2
	 */
	public static void swapContents(InventorySlot is1, InventorySlot is2) {
		Main.getSelf().getInventory().swap(is1.inventory_index,is2.inventory_index);
	}
	
	/**
	 * Draws a resource.  Still a working method.
	 * @param g
	 * @param dr
	 */
	private void drawResource(DrawableResource dr) {
		Point pos = getPositionAbs();
		GraphicsAbstract.drawResource(dr, 0,0,-1,-1, pos.x,pos.y,-1,-1);
	}
	/** {@inheritDoc} */
	@Override public void draw() {
		drawResource(resource_tile_empty);
		if (!isEmpty()) {
			drawResource(getSwag().getResource());
		}
		if      (selected) drawResource(resource_tile_select);
		else if (hovering) drawResource( resource_tile_hover);
	}
};