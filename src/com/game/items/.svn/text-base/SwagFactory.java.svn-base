/**
 * 
 */
package com.game.items;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author TBworkstation
 * 
 */
public abstract class SwagFactory {

	/**
	 * Creates a new instance of the {@link Swag} parameter
	 * 
	 * @param s
	 *            the {@link Swag} you will copy
	 * @return a copy of the parameter {@link Swag}
	 */
	public static Swag getNewInstance(Swag s) {
		Constructor<? extends Swag> con = null;
		try {
			con = s.getClass().getConstructor(s.NAME.getClass(), int.class, int.class);
		} catch (NoSuchMethodException e) {
			return paramlessSwag(s);
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return junkSwag(s);
	}

	private static Swag junkSwag(Swag s) {
		Constructor<? extends Swag> con = null;
		Swag newSwag = null;
		try {
			con = s.getClass().getConstructor(s.NAME.getClass(), int.class, int.class);
			newSwag = con.newInstance(s.NAME, s.VALUE, s.ENCUMBERANCE);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return newSwag;
	}

	private static Swag paramlessSwag(Swag s) {
		Constructor<? extends Swag> con = null;
		Swag newSwag = null;
		try {
			con = s.getClass().getConstructor();
			newSwag = con.newInstance();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return newSwag;
	}
}
