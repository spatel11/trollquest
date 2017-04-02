package com.utilities.configfile;

/**
 * A configuration parameter.
 * @author Ian
 */
public final class ConfigParameter {
	/** The default value with which to imbue the parameter. */
	public final Object _default_value;
	/** The value of the parameter. */
	public Object _value;
	
	/**
	 * Constructor.
	 * @param default_value the default value for the parameter to have.
	 */
	public ConfigParameter(Object default_value) {
		_default_value = default_value;
		_value = _default_value;
	}
	
	/** Set the parameter back to its default. */
	public final void setToDefault() {
		_value = _default_value;
	}
	/** Set the parameter to a value. */
	public final void setTo(Object value) {
		_value = value;
	}
	/** Gets the value of the parameter. */
	public final Object getValue() {
		return _value;
	}
}