package com.client;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.client.Dialog.JWorldDialogError;
import com.client.Graphics.DrawableResource;
import com.client.Graphics.DrawableResourceImage;
import com.client.Graphics.ParametersFont;
import com.client.Graphics.ParametersImage;
import com.utilities.Function;
import com.utilities.UtilityImage;

/**
 * Class to encapsulate loading an asset.  If the operation fails,
 * informs the user that there was a problem, and then gracefully
 * exits, instead of throwing a stack trace or anything nasty like
 * that.  Also defines some nice directory variables.
 * @author Ian
 */
public abstract class ResourceLoader {
	/** The data directory. */
	public static final String DIRECTORY_DATA =
		System.getProperty("user.dir")+File.separator+"data"+File.separator;
	/** The data/images directory. */
	public static final String DIRECTORY_IMAGES =
		DIRECTORY_DATA+"images"+File.separator;
	/** The data/images/GUI directory. */
	public static final String DIRECTORY_IMAGES_GUI =
		DIRECTORY_IMAGES+"GUI"+File.separator;
	/** The data/images/GUI/items directory. */
	public static final String DIRECTORY_IMAGES_GUI_ITEMS =
		DIRECTORY_IMAGES_GUI+"items"+File.separator;
	/** The data/images/GUI/tiles directory. */
	public static final String DIRECTORY_IMAGES_TILES =
		DIRECTORY_IMAGES+"tiles"+File.separator;
	/** The data/images/GUI/spells directory. */
	public static final String DIRECTORY_IMAGES_SPELLS =
		DIRECTORY_IMAGES+"spells"+File.separator;
	/** The data/images/characters directory. */
	public static final String DIRECTORY_IMAGES_CHARACTERS =
		DIRECTORY_IMAGES+"characters"+File.separator;
	/** The data/fonts directory. */
	public static final String DIRECTORY_DATA_FONT =
		DIRECTORY_DATA+"fonts"+File.separator;
	
	/** Cached data for faster loading. */
	private final static Map<ParametersImage,DrawableResource> loaded_image_resources = new HashMap<ParametersImage,DrawableResource>();
	/** Cached data for faster loading. */
	private final static Map<String,Font> loaded_fonts = new HashMap<String,Font>();
	/** Cached data for faster loading. */
	private final static Map<ParametersFont,Font> loaded_sized_fonts = new HashMap<ParametersFont,Font>();
	
	/**
	 * Whether an error is being shown.  Needed so that different threads can't create
	 * errors at the same time.
	 */
	private static Boolean has_made_error = false;
	
	/**
	 * Shows an "Asset Loading Error" with the given message, and then exits when the user clicks OK.
	 * @param msg the message to show to the user.
	 */
	public static void showError(final String msg) {
		synchronized(has_made_error) {
			if (!has_made_error) {
				has_made_error = true;
				new JWorldDialogError(
						msg,
						"Asset Loading Error",
						new Function() {
							@Override public void execute(Object... arguments) {
								System.exit(1);
							}
						}
				);
			}
		}
	}
	
	/**
	 * Makes a new file.  Could potentially be extended with more functionality.
	 * @param path the path to make the file from.
	 * @return the file.
	 */
	private final static synchronized File loadFile(String path) {
		return new File(path);
	}
	/**
	 * Loads an image.  Shows an error and exits if this is not possible.
	 * @param path the path to load.
	 * @return the image.
	 */
	private final static synchronized BufferedImage loadImage(String path) {
		BufferedImage image = null;
		try { image = ImageIO.read(loadFile(path));
		} catch (IOException e) { showError(path); }
		return image;
	}
	/**
	 * Attempts to load an image.  If this cannot be done, shows an error and exits.
	 * Only loads resources as necessary.  Uses a cached copy if possible.
	 * @param path the path to the resource.
	 * @return the resource.
	 */
	public final static synchronized DrawableResource loadImageResource(String path) {
		ParametersImage param = new ParametersImage(path,1.0f);
		if (!loaded_image_resources.containsKey(path)) {
			BufferedImage image = loadImage(path);
			loaded_image_resources.put(param,new DrawableResourceImage(image));
		}
		return loaded_image_resources.get(param);
	}
	/**
	 * Attempts to load an image.  If this cannot be done, shows an error and exits.
	 * Only loads resources as necessary.  Uses a cached copy if possible.
	 * @param path the path to the resource.
	 * @param scalar the amount to resize.
	 * @return the resource.
	 */
	public final static synchronized DrawableResource loadImageResource(String path, float scalar) {
		ParametersImage param = new ParametersImage(path,scalar);
		if (!loaded_image_resources.containsKey(param)) {
			BufferedImage image = loadImage(path);
			image = UtilityImage.rescale(image,scalar);
			loaded_image_resources.put(param,new DrawableResourceImage(image));
		}
		return loaded_image_resources.get(param);
	}

	/**
	 * Loads a font.  Caches the fonts that it loads.  If you ask for an already loaded
	 * font at a given size, it will return the cached copy.  If you want a different
	 * size of an already loaded texture, a new subtype will be generated from the
	 * cached superfont.
	 * @param name the font's name.
	 * @param size the size to load.
	 * @return the font.
	 */
	public final static synchronized Font loadFont(String name, int size) {
		ParametersFont params = new ParametersFont(name,size);
		if (!loaded_sized_fonts.containsKey(params)) {
			String path = ResourceLoader.DIRECTORY_DATA_FONT + name;
			if (!loaded_fonts.containsKey(name)) {
				Font new_font = null;
				try {
					new_font = Font.createFont(Font.TRUETYPE_FONT,loadFile(path));
				} catch (FontFormatException e) {
					showError(path);
				} catch (IOException e) {
					showError(path);
				}
				loaded_fonts.put(name,new_font);
			}
			Font new_font = loaded_fonts.get(name).deriveFont(Font.PLAIN,size);
			//Font new_font = new Font( "SansSerif", Font.BOLD + Font.ITALIC, 12 );
			loaded_sized_fonts.put(params,new_font);
		}
		return loaded_sized_fonts.get(params);
	}
}