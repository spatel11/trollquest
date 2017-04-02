package com.utilities;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

/**
 * Encapsulates a bunch of image helper functions.
 * @author Ian
 */
public abstract class UtilityImage {
	/**
	 * Executes a deep copy of an image.
	 * @param src the source image.
	 * @return the new image.
	 */
	public final static BufferedImage copy(BufferedImage src) {
		ColorModel cm = src.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = src.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	/**
	 * Flips an image vertically.
	 * @param bi the image.
	 * @return the image.
	 */
	public final static BufferedImage flipImageVertical(BufferedImage bi) {
		AffineTransform tx = AffineTransform.getScaleInstance(1,-1);
		tx.translate(0,-bi.getHeight(null));
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		return op.filter(bi,null);
	}
	/**
	 * Rescales an image.
	 * @param bi the image.
	 * @param scalar the scalar to rescale by.
	 * @return the image.
	 */
	public final static BufferedImage rescale(BufferedImage bi, float scalar) {
		AffineTransform tx = AffineTransform.getScaleInstance(scalar,scalar);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BICUBIC);
		return op.filter(bi,null);
	}
	/**
	 * Multiply an image by a color.
	 * @param bi the image.
	 * @param mult_color the color.
	 */
	public final static void colorMultiply(BufferedImage bi, Color mult_color) {
		int rgba[] = new int[4];
		for (int x=0;x<bi.getWidth();++x) {
			for (int y=0;y<bi.getHeight();++y) {
				decodeRGBA(bi.getRGB(x,y),rgba);
				rgba[0] = UtilityMath.rndint( rgba[0]*mult_color.  getRed()/255.0 );
				rgba[1] = UtilityMath.rndint( rgba[1]*mult_color.getGreen()/255.0 );
				rgba[2] = UtilityMath.rndint( rgba[2]*mult_color. getBlue()/255.0 );
				bi.setRGB(x,y,encodeRGBA(rgba));
			}
		}
	}
	
	/**
	 * Function that decodes an integer representing a pixel into an
	 * array storing the components separately.
	 * @param argb the pixel.
	 * @param rgb the array containing the output channels.
	 */
	public final static void decodeRGBA(int argb, int rgb[]) {
		rgb[0] = (0x00FF0000&argb)>>16;
		rgb[1] = (0x0000FF00&argb)>> 8;
		rgb[2] = (0x000000FF&argb);
		rgb[3] = (0xFF000000&argb)>>24;
	}
	
	/**
	 * Function that encodes an array representing the individual color
	 * channels of a pixel into an integer representing that pixel.
	 * @param rgb the array containing the output channels.
	 * @return the encoded ARGB integer.
	 */
	public final static int encodeRGBA(int rgb[]) {
		int argb = 0;
		argb |= (rgb[0]<<16);
		argb |= (rgb[1]<< 8);
		argb |= (rgb[2]    );
		argb |= (rgb[3]<<24);
		return argb;
	}
}
