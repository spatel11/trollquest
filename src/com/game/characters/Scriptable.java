/**
 * 
 */
package com.game.characters;

/**
 * @author TBworkstation
 *
 */
public interface Scriptable {

  /**
   * Returns the script file location from the project directory
   * 
   * @return a string representing the file path from the project directory to
   *         the nanoC script file this creature will run
   */
  public abstract String getScript();
}
