package com.game.characters;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.client.Graphics.AnimationsOfACharacter;
import com.client.Graphics.AnimationsOfACharacter.ActionType;
import com.client.Graphics.DrawableResource;
import com.game.environment.Directions;
import com.game.environment.GameMap;
import com.game.items.Loot;
import com.game.quest.QuestEvent;
import com.game.quest.QuestEvent.QuestAction;

/**
 * Note that this file cannot be named "Character", because that clashes with
 * Java's Character.
 * 
 * @author TBworkstation
 * @author Ian
 */
public abstract class GameCharacter implements Serializable {
  /** Character's serial ID */
  private static final long serialVersionUID= -3275993088819995176L;

  /** The unique name of the character as seen by other characters */
  public final String name;

  /** The gender of the character as seen by other characters */
  public final Gender gender;

  /**
   * Creates a new game character with the specified name, {@link Gender} and a
   * directory to an animation set.
   * 
   * @param name
   *          the name of the game character
   * @param gender
   *          the gender of the game character
   * @param anim_directory
   *          the string directory of the animation directory
   * @param e
   *          the {@link QuestEvent} of the game character
   */
  public GameCharacter(String name, Gender gender, String anim_directory,
      QuestEvent e) {
    this.name= name;
    this.gender= gender;
    this.inventory= new Loot(0);
    this.animations= new AnimationsOfACharacter(anim_directory, 0.5f);
    yLoc= xLoc= 0;
    this.anim_directory= anim_directory;
    this.questEvent= e;
  }

  /**
   * Sets the direction of the player.
   * 
   * @param direction
   *          the new {@link Directions} the game character will face
   */
  public void setDirection(Directions direction) {
    this.direction= direction;
  }

  /**
   * Returns the direction the player is facing.
   * 
   */
  public Directions getDirection() {
    return direction;
  }

  /**
   * Sets the location of the character. Whenever the character is commanded to
   * move, this method should be called with the new location.
   * 
   * @param p
   *          the location of the character in the standard Cartesian plane
   */
  public void setPosition(Point p) {
    xLoc= p.x;
    yLoc= p.y;
  }

  /**
   * Returns the player's location.
   * 
   * @return a point object composed of the player's current location on a
   *         {@link GameMap}
   */
  public Point getPosition() {
    return new Point(xLoc, yLoc);
  }

  /**
   * Sets the x sublocation of the character.
   * 
   * @param x
   *          the x sublocation of the character in the standard Cartesian plane
   */
  public void setSubX(double x) {
    subX= x;
  }

  /**
   * Sets the y sublocation of the character.
   * 
   * @param y
   *          the y sublocation of the character in the standard Cartesian plane
   */
  public void setSubY(double y) {
    subY= y;
  }

  /**
   * Returns the player's x sublocation.
   * 
   * @return the double x subposition for animation purposes. has no effect
   *         otherwise
   */
  public double getSubX() {
    return subX;
  }

  /**
   * Returns the player's y sublocation.
   * 
   * @return the double y subposition for animation purposes. has no effect
   *         otherwise
   */
  public double getSubY() {
    return subY;
  }

  /**
   * Sets the sublocation velocity of the character.
   * 
   * @param x
   *          the x sublocation velocity of the character in the standard
   *          Cartesian plane
   */
  public void setSubXvel(double x) {
    subXvel= x;
  }

  /**
   * Sets the sublocation velocity of the character.
   * 
   * @param y
   *          the y sublocation velocity of the character in the standard
   *          Cartesian plane
   */
  public void setSubYvel(double y) {
    subYvel= y;
  }

  /**
   * Returns the player's x sublocation velocity.
   * 
   * @return the double x subvelocity for the player. only used for animation
   *         purposes. no effect otherwise
   */
  public double getSubXvel() {
    return subXvel;
  }

  /**
   * Returns the player's y sublocation velocity.
   * 
   * @return the double y subvelocity for the player. only used for animation
   *         purposes. no effect otherwise
   */
  public double getSubYvel() {
    return subYvel;
  }

  /**
   * Sets the action state.
   * 
   * @param action_state
   *          the {@link ActionType} of the player telling the GUI to draw the
   *          correct action of the character
   */
  public void setActionState(ActionType action_state) {
    this.action_state= action_state;
  }

  /**
   * Returns the action state.
   * 
   * @return the player's current {@link ActionType} referring to their current
   *         action they are performing
   */
  public ActionType getActionState() {
    return action_state;
  }

  /**
   * Gets the resource that should be drawn for this character.
   * 
   * @return the {@link DrawableResource} based off of the player's current
   *         {@link Directions} and {@link ActionType}
   */
  public DrawableResource getResource() {
    return animations.getResource(getActionState(), getDirection());
  }

  /**
   * Returns the inventory of the character
   * 
   * @return A {@link Loot} object that represents the inventory of the
   *         character
   */
  public Loot getInventory() {
    return inventory;
  }

  /**
   * Record the specific gender of the character
   * 
   * @author taylor
   * 
   */
  public enum Gender {
    /** Designates a male character */
    MALE,
    /** Designates a female character */
    FEMALE,
    /** Designates a genderless character */
    IT;
  }

  @Override
  public boolean equals(Object o) {
    if (this.name == ((GameCharacter) o).name) {
      return true;
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash= 0;

    hash+= name.hashCode();

    return hash;

  }

  /**
   * Sets the player's current {@link GameMap} to the {@link GameMap} associated
   * with the mapName
   * 
   * @param mapName
   *          the name of the {@link GameMap}
   */
  public void setMap(String mapName) {
    currentMap= mapName;
  }

  /**
   * Returns the {@link GameCharacter}'s current {@link GameMap}
   * 
   * @return the string name of the {@link GameMap} the {@link GameCharacter} is
   *         located on
   */
  public String getMap() {
    return currentMap;
  }

  /**
   * Returns the GameCharacter's {@link QuestEvent}. This depends on the type of
   * subclass calls this. If it is an NPC it should return a {@link QuestEvent}
   * with a TALK {@link QuestAction}. If it is a Monster or player it should
   * return a DIE {@link QuestAction}, with the only thing varying is the
   * PlayerName in the event
   * 
   * @return the {@link QuestEvent} associated with the character. For
   *         {@link GameCharacter}s this is either a DIE or TALK
   *         {@link QuestAction}
   */
  public QuestEvent getQuestEvent() {
    return questEvent;
  }

  /* ============================ PRIVATE PARTS ============================= */
  private final Loot inventory;
  private int xLoc;
  private int yLoc;
  private transient double subX= 0.0; // GUI related
  private transient double subY= 0.0; // GUI related
  private transient double subXvel= 0.0; // GUI related
  private transient double subYvel= 0.0; // GUI related
  private Directions direction= Directions.RD;
  private transient ActionType action_state= ActionType.STAND; // GUI related
  private String currentMap;
  private final String anim_directory;
  private final QuestEvent questEvent;

  /** The directory where this character's animations are stored. */
  private transient AnimationsOfACharacter animations;

  private void writeObject(ObjectOutputStream out) throws IOException {
    out.defaultWriteObject();
  }

  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
    in.defaultReadObject();
    this.animations= new AnimationsOfACharacter(anim_directory, 0.5f);
    this.action_state= ActionType.STAND;
  }
}
