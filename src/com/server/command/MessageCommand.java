/**
 * @author Stephen Patel (spatel11@unm.edu)
 */
package com.server.command;

import com.client.Message;
import com.game.characters.GameCharacter;
import com.game.characters.Player;
import com.game.quest.Quest;
import com.game.quest.QuestEvent;

/**
 * This Command sends a {@link String} message to all currently connected
 * players.
 * 
 * @author Stephen Patel
 * 
 */
public class MessageCommand extends ComparableCommand {

  /**
   * SerialVersionUID.
   */
  private static final long serialVersionUID= -4399726775390226470L;
  /**
   * The message to send.
   */
  private String message;

  /**
   * Constructs a new MessageCommand from the input player.
   * 
   * @param playerName
   *          the player sending this message.
   * @param targetListener
   *          the player the message is directed towards if necessary, if not
   *          necessary, pass in null
   * @param message
   *          the message to be displayed
   */
  public MessageCommand(String playerName, String targetListener, String message) {
    super(playerName, 0, 0, 0, true);
    this.message= message;
    listener= targetListener;
  }

  /**
   * Adds a message to the chatlog from this Player.
   */
  @Override
  public ComparableCommand execute() {
    Message msg= new Message(this.charName, message);
    msg.setComplete();
    state.addMessage(msg);

    // this is to keep the player's quest events synchronized across the network
    GameCharacter talker= state.getCharacter(charName);

    if (listener == null) {
      return null;
    }

    if (state.getCharacter(this.listener) instanceof Player) {
      Player listener= (Player) state.getCharacter(this.listener);
      Quest pcQuest= listener.getPlayersQuest();

      if (pcQuest != null) {
        QuestEvent qe= talker.getQuestEvent();
        pcQuest.checkTop(qe);
      }
    }

    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder= new StringBuilder();
    builder.append("MessageCommand [");
    if (message != null) {
      builder.append("message=");
      builder.append(message);
      builder.append(", ");
    }
    if (charName != null) {
      builder.append("charName=");
      builder.append(charName);
      builder.append(", ");
    }
    if (getClass() != null) {
      builder.append("getClass()=");
      builder.append(getClass());
    }
    builder.append("]");
    return builder.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isValid() {
    if (state == null) return false;
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object o) {
    if (!super.equals(o)) return false;
    if (o == null) return false;
    if (!(o instanceof MessageCommand)) return false;
    if (!this.charName.equals(((MessageCommand) o).charName)) return false;
    if (!this.message.equals(((MessageCommand) o).message)) return false;
    return true;
  }

  /* ======================= private parts ========================= */
  private final String listener;
}
