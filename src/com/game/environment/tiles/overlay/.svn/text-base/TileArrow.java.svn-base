package com.game.environment.tiles.overlay;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.client.Main;
import com.client.Graphics.Animation;
import com.client.Graphics.DrawableResource;
import com.client.Graphics.DrawableResourceAnimation;
import com.client.Graphics.DrawableResourceImage;
import com.utilities.UtilityImage;

/**
 * An arrow overlay pointing in the direction specified
 * 
 * @author ian
 * 
 */
public abstract class TileArrow extends TileOverlay {
	private static final long serialVersionUID = 3312595596341318636L;

	/**
	 * Creates a new arrow tile overlay
	 * 
	 * @param x
	 *            the x location of the tile in the Cartesian plane
	 * @param y
	 *            the y location of the tile in the Cartesian plane
	 */
	public TileArrow(int x, int y) {
		super(x, y);
	}

	protected Animation getColorImages() {
		Animation images = ((DrawableResourceAnimation) (super.getResource()))
				.getAnimation();
		images.cloneLastFrame();
		images.cloneLastFrame();
		UtilityImage.colorMultiply(images.getFrame(0), new Color(255, 255, 0));
		UtilityImage.colorMultiply(images.getFrame(1), new Color(255, 0, 0));
		UtilityImage.colorMultiply(images.getFrame(2), new Color(0, 255, 255));
		return images;
	}

	protected DrawableResource getResource(Animation images) {
		BufferedImage image = null;
		switch (Main.move_type) {
			case MOVE_TO_ATTACK:
				image = images.getFrame(1);
				break;
			case MOVE_TO_CAST:
				image = images.getFrame(2);
				break;
			case MOVE: // fallthrough
			default:
				image = images.getFrame(0);
		}
		return new DrawableResourceImage(image);
	}

}
