package com.client.Graphics;

import java.awt.image.BufferedImage;

/**
 * Concrete class.  For all the lower level graphics-backend-specific nastiness.
 * @author Ian
 */
public abstract class GraphicsCPU extends GraphicsAbstract {
	/**
	 * Draws a resource.
	 * @param resource the resource to draw.
	 * @param x1 the x position of the source rectangle.
	 * @param y1 the y position of the source rectangle.
	 * @param w1 the width of the source rectangle.
	 * @param h1 the height of the source rectangle.
	 * @param x2 the x position of the destination rectangle.
	 * @param y2 the y position of the destination rectangle.
	 * @param w2 the width of the destination rectangle.
	 * @param h2 the height of the destination rectangle.
	 */
	public final static void drawResource(DrawableResource resource,
			int x1, int y1, int w1, int h1,
			int x2, int y2, int w2, int h2)
	{
		if (resource==null) {
			System.out.println("drawResource got null!");
			return;
		}
		BufferedImage image = resource.getImage();
		
		if (w1==-1) w1 = image.getWidth();
		if (w2==-1) w2 = image.getWidth();
		if (h1==-1) h1 = image.getHeight();
		if (h2==-1) h2 = image.getHeight();
		//System.out.println(x2+" "+h2+" "+(x2+w2)+" "+y2+"  "+x1+" "+y1+" "+(x1+w1)+" "+(y1+h1));
		h2 = getHeight()-(y2+h2);
		y2 = getHeight()-y2;
		graphics.drawImage(
				image,
				x2,h2, x2+w2,y2,
				x1,y1, x1+w1,y1+h1,
				null
		);
	}
}