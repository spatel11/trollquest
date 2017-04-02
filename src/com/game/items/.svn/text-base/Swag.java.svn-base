/**
 * 
 */
package com.game.items;

import java.io.Serializable;

import com.client.ResourceLoader;
import com.client.Graphics.DrawableResource;
import com.game.quest.QuestEvent;
import com.game.quest.QuestEvent.QuestAction;

/**
 * @author TBworkstation
 */
public class Swag implements Serializable {
	private static final long serialVersionUID = -9180388267329577343L;

	/**
	 * The associated value of the particular piece of Swag. Useful for Merchant's
	 * selling requirements.
	 */
	public final int VALUE;

	/** The 'weight' of the item as an integer. Affects movement speed */
	public final int ENCUMBERANCE;

	/** The title of the swag item */
	public final String NAME;
	
	
	/**
	 * Creates a new piece of swag with at least a specified value and
	 * encumberance
	 * 
	 * @param name
	 *          the name of the swag
	 * @param value
	 *          the value of the swag
	 * @param encumberance
	 *          the 'weight' of the swag
	 */
	public Swag(String name, int value, int encumberance) {
		NAME = name;
		VALUE = value;
		ENCUMBERANCE = encumberance;
		ACQUIRE_EVENT = new QuestEvent("player", QuestAction.ACQUIRE, null, this);
	}

	/**
	 * Returns a {@link DrawableResource} based on an icon image we load from a file
	 * 
	 * @return a {@link DrawableResource} that represents a {@link Swag} item
	 */
	public DrawableResource getResource() {
    return ResourceLoader.loadImageResource(
        ResourceLoader.DIRECTORY_IMAGES_GUI_ITEMS + "backpack.png", 0.5f);
	}

	@Override public boolean equals(Object o) {
		if (o.getClass() != this.getClass()) {
			return false;
		}

		Swag testSwag = (Swag) o;

		if (testSwag.VALUE != this.VALUE) {
			return false;

		}
		if (testSwag.ENCUMBERANCE != this.ENCUMBERANCE) {
			return false;
		}
		if (!testSwag.NAME.equalsIgnoreCase(this.NAME)) {
			return false;
		}

		return true;
	}
	
	@Override public int hashCode() {
		int hash= 0;
		hash+= VALUE + ENCUMBERANCE;
		hash+= NAME.hashCode();

		return hash;
	}
	
	/**
	 * Returns the loot's {@link QuestEvent} 
	 * 
	 * @return the loot's {@link QuestEvent}
	 */
	public QuestEvent getQuestEvent() {
		return ACQUIRE_EVENT;
	}
	
	/* ========================= PRIVATE PARTS ================================ */
	private final QuestEvent ACQUIRE_EVENT;
}
