package com.ai;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import com.ai.ASTCells.ASTCell;
import com.ai.reference.ExecutionException;

/**
 * Holds all of the parsed {@link Monster} and {@link NPC} strategies in a {@link HashMap} {@link #_strategies}.
 * 
 * <p> All Parsed Strategies are in the form of a {@link SequenceASTCell}</p>
 * <p>
 *   The key value for each strategy in {@link #_strategies} is the string name of the file it was parsed from.
 *   e.g. "data/NanoScripts/gameScripts/Monster.nano"
 * </p>
 * 
 * This Class is also responsible for actual parsing of all strategies.
 * {@link #parseStrategies(String)} is the method that creates a key/value pairing of a nanoC filename and the
 * {@link ASTCell} that represents the parsed file.
 * 
 * @author Martin Tice
 * 
 * @param <T>
 */
public class AST {

	/**Flag for turning on and off debugging print statements00*/
	private static final boolean DEBUG = false;
	/** Holds all of the parsed nanoC strategy files. key/value = {@link String}, {@link ASTCell}*/
	private HashMap<String, ASTCell> _strategies;
	/** Used to parse all of the nanoC strategy files.*/
	private NanoParser _parser;

//getters and setters
	public HashMap<String, ASTCell> getStrategies(){
		return _strategies;
	}
	
	/**
	 * Initializes {@link #_strategies}, parses all strategies, and places them into {@link #_strategies}.
	 * Calls {@link #parseStrategies(String)} to parse the strategies with the same String that is passed in as a parameter.
	 * 
	 * @param strategyDirectory is a {@link String} that is the file name of the directory holding all of the strategies.
	 */
	public AST(String strategyDirectory) {
		_strategies = new HashMap<String, ASTCell>();
		parseStrategies(strategyDirectory);
	}

	/**
	 * Parses all NanoC strategy files and adds them to {@link #_strategies}.
	 * Gets all children files, parses them with {@link #_parser}, calls {@link NanoParser#getResult()} and
	 * adds the returned {@link SequenceASTCell} to {@link #_strategies} as the value paired with the filename as the key.
	 * @param strategyDirectory is the name of the file directory holding all of the nanoC strategies.
	 */
	private void parseStrategies(String strategyDirectory) {
		File dir = new File(strategyDirectory);
		if (dir.isDirectory()) {
			File[] children = dir.listFiles();
			for (int i = 0; i < children.length; i++) {
				try {
					if (!children[i].isHidden()) {
						if (DEBUG) {
							System.out.println("AST.parseStrategies from: "
									+ children[i].getName());
						}
						_parser = new NanoParser(children[i].getAbsolutePath());
						_parser.parse();
						_strategies.put(children[i].getPath(),
								_parser.getResult());
					}
				} catch (IOException e) {
					System.out
							.println("AST.parseStrategies failed to parse all files. comleted: "
									+ (i - 1) + " files");
					e.printStackTrace();
				}
			}
		}
		if(DEBUG)System.out.println("PARSEDALLSTRATEGIESPARSEDALLSTRATEGIESppppppppppppppppppppppppppppppppppppppppppppppppp");
	}

	/**
	 * Executes a single strategy from a file location.
	 * 
	 * @param key
	 *            the key in {@link #_strategies} that links to a specific
	 *            strategy.
	 */
	public synchronized void executeStrategy(String key, VM c) {
		if (c.getPlayerCount() == 0)
			return;
		try {
			if(DEBUG)System.out.println("AST.executeStrategy : " + key+" "+_strategies.containsKey(key));
			if(DEBUG)System.out.println("AST.executeStrategy : " + _strategies.keySet());
			_strategies.get(key).execute(c);
		} catch (ExecutionException e) {
			System.out
					.println("AST.executeStrategy Failed to execute strategy");
			e.printStackTrace();
		}
	}

	/**
	 * Executes all strategies in {@link #_strategies}.
	 * 
	 * @param c is the {@link VM} object to pass through all of the {@link ASTCell#execute(VM)} methods.
	 * 
	 */
	public void executeAll(VM c) {
		Set<String> keys = _strategies.keySet();
		for (String key : keys) {
			try {
				_strategies.get(key).execute(c);
			} catch (ExecutionException e) {
				System.out.println("VM.executeAll found an error during executiond");
				e.printStackTrace();
			}
		}
	}

}
