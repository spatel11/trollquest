package com.utilities.configfile;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Configuration File class.  Stores a sequence of parameters in a config file
 * for saving and loading.
 * 
 * All parameters have a default value to use in the event that a file can't be
 * read (or simply doesn't exist yet).  Typical usage looks like:
 * 
 * ConfigFile cfg_file = new ConfigFile( filename );
 * cfg_file.parameterAdd("param1",param1);
 * cfg_file.parameterAdd("param2",param2);
 * cfg_file.parameterAdd("param2",param3);
 * 
 * cfg_file.load();
 * 
 * #This will equal the original param1 if no file could be found or that's what the file said.
 * Object loaded_param1 = cfg_file.parameterGet("param1");
 * 
 * #Change loaded_param1 here
 * 
 * cfg_file.parameterSet("param1",loaded_param1)
 * 
 * #Saves a configuration file.  The most recent values of the paramters are written.
 * cfg_file.save();
 * 
 * @author Ian
 */
public final class ConfigFile {
	/** Names of all the attributes. */
	private final List<String> names = new ArrayList<String>();
	/** List of all the parameters. */
	private final Map<String,ConfigParameter> parameters = new HashMap<String,ConfigParameter>();
	/** Whether to output warnings (can't find file, etc.).  Probably should disable for release. */
	private final static boolean OUTPUT_WARNINGS = true;
	/** The filename. */
	private final String filename;
	
	/** Constructor.  Give it the configuration file's name. */
	public ConfigFile(String filename) {
		this.filename = filename;
	}
	/** Output a warning. */
	private final void outputWarning(String msg) {
		if (!OUTPUT_WARNINGS) return;
		System.out.println("Configuration File Error: "+msg);
	}
	
	/**
	 * Attempt to load the file.  All parameters will be checked first.  If the configuration
	 * file is up to date (i.e., it exists, can be read, and it's contents match one-to-one
	 * with the parameters that have been added), then the file's contents will overwrite all
	 * the default values.  
	 * 
	 * If, however, there is any problem whatsoever, NO parameters will be changed.
	 */
	public final void load() {
		for (String name : names) {
			parameters.get(name).setToDefault();
		}
		
		List<Object> values = new ArrayList<Object>();
		ObjectInputStream is = null;
		try {
			is = new ObjectInputStream(new FileInputStream(filename));
			for (@SuppressWarnings("unused") String name : names) {
				Object obj = is.readObject();
				values.add(obj);
			}
		} catch (Exception e) {
			outputWarning("Could not load:");
			outputWarning(e.getMessage());
			return;
		} finally {
			try { if (is!=null) is.close();
			} catch (IOException e) {
				outputWarning(e.getMessage());
			}
		}
		for (int i=0;i<names.size();++i) {
			parameters.get(names.get(i)).setTo(values.get(i));
		}
	}
	
	/** Attempts to save the configuration file. */
	public final void save() {
		ObjectOutputStream os = null;
		try {
			os = new ObjectOutputStream(new FileOutputStream(filename));

			for (String name : names) {
				os.writeObject( parameters.get(name).getValue() );
			}
		} catch (Exception e) {
			for (String name : names) {
				parameters.get(name).setToDefault();
			}
			outputWarning("Could not save:");
			outputWarning(e.getMessage());
		} finally {
			try { if (os!=null) os.close();
			} catch (IOException e) {
				outputWarning(e.getMessage());
			}
		}
	}
	
	/**
	 * Adds a parameter to the configuration file.  Note that that this method must be
	 * called before load() if you intend for the class to load anything from the file.
	 * 
	 * @param name the name of the parameter.
	 * @param default_value the default value to set it to.
	 * @throws IllegalArgumentException if this parameter already exists.
	 */
	public final void parameterAdd(String name, Object default_value) throws IllegalArgumentException {
		if (parameters.containsKey(name)) throw new IllegalArgumentException("This parameter name has already been added!");
		names.add(name);
		parameters.put(name,new ConfigParameter(default_value));
	}
	/**
	 * Gets the value of the parameter.
	 * 
	 * @param name the name of the parameter to get.
	 * @return the parameter's value.
	 * @throws IllegalArgumentException if the parameter specified by the name does not exist.
	 */
	public final Object parameterGet(String name) throws IllegalArgumentException {
		if (!parameters.containsKey(name)) throw new IllegalArgumentException("This parameter name does not exist!");
		return parameters.get(name).getValue();
	}
	/**
	 * Sets the value of the parameter.  This should be called before save() if you want to
	 * have the contents of the file reflect your changes.
	 * 
	 * @param name the name of the parameter to set.
	 * @param obj the value to set the parameter to.
	 * @throws IllegalArgumentException if the parameter specified by the name does not exist.
	 */
	public final void parameterSet(String name, Object obj) throws IllegalArgumentException {
		if (!parameters.containsKey(name)) throw new IllegalArgumentException("This parameter name does not exist!");
		parameters.get(name).setTo(obj);
	}
}