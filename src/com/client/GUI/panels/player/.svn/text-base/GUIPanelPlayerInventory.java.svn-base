package com.client.GUI.panels.player;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.util.LinkedHashSet;
import java.util.Set;

import com.client.Main;
import com.client.MainHandlerInput;
import com.client.ResourceLoader;
import com.client.GUI.collision.GUICollisionObject;
import com.client.GUI.collision.GUICollisionObjectBox;
import com.client.GUI.collision.GUICollisionObjectCircle;
import com.client.GUI.panels.GUIButton;
import com.client.GUI.panels.GUIPanelMovable;
import com.client.GUI.panels.GUIPanels;
import com.client.Graphics.DrawableResource;
import com.client.Graphics.GraphicsAbstract;
import com.game.characters.GameCharacter;
import com.game.items.IllegalInventoryException;
import com.game.items.Loot;
import com.game.items.Swag;
import com.game.items.armor.Armor;
import com.game.items.weapon.Weapon;
import com.server.command.ComparableCommand;
import com.server.command.DropCommand;
import com.server.command.EquipCommand;
import com.utilities.Function;
import com.utilities.UtilityString;

/**
 * Inventory panel.
 * 
 * @author Ian
 */
public final class GUIPanelPlayerInventory extends GUIPanelMovable {
	/** The instance. */
	private final static GUIPanelPlayerInventory instance;
	/** The resource of the inventory image. */
	private final static DrawableResource resource_panel;
	/** The resource of the inventory slots. */
	private final static Set<InventorySlot> slots;
	/** The buttons to equip or drop an item. */
	private final GUIButton button_equip, button_drop;

	/** Static intializer. */
	static {
		resource_panel = ResourceLoader
				.loadImageResource(ResourceLoader.DIRECTORY_IMAGES_GUI
						+ "inventory.png");
		slots = new LinkedHashSet<InventorySlot>(); // Must come before
													// instance.
		instance = new GUIPanelPlayerInventory(); // Must come after
													// resource_panel.
	}

	/**
	 * Constructor. Sets up the inventory panel, including setting up all the
	 * slot objects and adding collision objects so that the user can interact.
	 */
	private GUIPanelPlayerInventory() {
		super(null);

		componentResized(null);

		GUICollisionObject collide_close = new GUICollisionObjectCircle(this,
				13.0);
		collide_close.setPositionRel(255, 411);
		collide_close.setFunctionClick(new Function() {
			@Override
			public void execute(Object... arguments) {
				GUIPanels.closePanel();
			}
		});
		objects.add(collide_close);

		int index = 0;
		for (int slot_y = 0; slot_y < Loot.MAX_SIZE_Y; ++slot_y) {
			for (int slot_x = 0; slot_x < Loot.MAX_SIZE_X; ++slot_x) {
				int rel_x = 27 + slot_x * 38;
				int rel_y = 326 - slot_y * 36;

				GUICollisionObject co = new GUICollisionObjectBox(this, 31, 31);
				co.setPositionRel(rel_x, rel_y);

				final InventorySlot is = new InventorySlot(this, index++);
				is.setPositionRel(rel_x, rel_y);

				co.setFunctionClick(new Function() {
					@Override
					public void execute(Object... arguments) {
						InventorySlot is2 = null;
						for (InventorySlot is3 : slots) {
							if (is3.isSelected()) {
								is2 = is3;
								break;
							}
						}
						if (is2 == null)
							is.toggleSelected();
						else {
							is2.setSelected(false);
							if (is.isEmpty() || is2.isEmpty()) {
								InventorySlot.swapContents(is, is2);
							}
						}
						button_equip.hide();
						button_drop.hide();
						for (InventorySlot is3 : slots) {
							if (is3.isSelected() && !is3.isEmpty()) {
								button_equip.show();
								button_drop.show();
								break;
							}
						}
					}
				});
				co.setFunctionEnter(new Function() {
					@Override
					public void execute(Object... arguments) {
						is.setHovering(true);
					}
				});
				co.setFunctionExit(new Function() {
					@Override
					public void execute(Object... arguments) {
						is.setHovering(false);
					}
				});

				slots.add(is);
				objects.add(co);
			}
		}

		button_equip = new GUIButton(this, "Equip");
		button_equip.setPositionRel(25, 43);
		button_equip.hide();
		button_drop = new GUIButton(this, "Drop");
		button_drop.setPositionRel(25, 20);
		button_drop.hide();
		button_equip.setFunctionPressed(new Function() {
			@Override
			public void execute(Object... arguments) {
				for (InventorySlot is : slots) {
					if (is.isSelected()) {
						is.setSelected(false);
						Swag s = is.getSwag();
						if(s instanceof Weapon || s instanceof Armor){
							System.err.println(s);
							ComparableCommand equip = new EquipCommand(Main.player_name, s);
							synchronized(MainHandlerInput.command_queue){
								MainHandlerInput.command_queue.add(equip);
							}
							MainHandlerInput.sendFirst();
						}
						break;
					}
				}
			}
		});
		button_drop.setFunctionPressed(new Function() {
			@Override
			public void execute(Object... arguments) {
				for (InventorySlot is : slots) {
					if (is.isSelected()) {
						is.setSelected(false);
						GameCharacter player = Main.getSelf();
						Swag s;
						try {
							s = player.getInventory().remove(
									is.inventory_index);
						} catch (IllegalInventoryException e) {
							break;
						}
						ComparableCommand drop = new DropCommand(Main
								.getState(), Main.player_name, new Loot(s));
						synchronized (MainHandlerInput.command_queue) {
							MainHandlerInput.command_queue.add(drop);
						}
						MainHandlerInput.sendFirst();
						break;
					}
				}
			}
		});
		children.put("Equip", button_equip);
		children.put("Drop", button_drop);
	}

	/** Returns an instance of the inventory panel. Using singleton pattern. */
	public static GUIPanelPlayerInventory getInstance() {
		return instance;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Good for reinitializing certain things.
	 */
	@Override
	public void componentResized(ComponentEvent ce) {
		setPositionRel(GraphicsAbstract.getWidth() / 2 - resource_panel.width
				/ 2, GraphicsAbstract.getHeight() / 2 - resource_panel.height
				/ 2);
	}

	/** {@inheritDoc} */
	@Override
	public void draw() {
		Point pos_abs = getPositionAbs();
		GraphicsAbstract.drawResource(resource_panel, 0, 0, -1, -1, pos_abs.x,
				pos_abs.y, -1, -1);
		// InventorySlot selected = null;
		for (InventorySlot is : slots) {
			// if (is.isSelected()&&!is.isEmpty()) selected = is;
			is.draw();
		}

		GraphicsAbstract.setFont(GraphicsAbstract.font12);
		GraphicsAbstract.setColor(new Color(255, 255, 50));
		GraphicsAbstract.drawText(
				UtilityString.toCommaString(Main.getSelf().getInventory()
						.getMoolah()), pos_abs.x + 202, pos_abs.y + 28);
	}
}