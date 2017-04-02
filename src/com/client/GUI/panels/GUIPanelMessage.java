package com.client.GUI.panels;

import java.awt.event.KeyEvent;
import java.util.Deque;
import java.util.Iterator;

import com.client.Main;
import com.client.Message;
import com.client.ResourceLoader;
import com.client.Dialog.JWorldDialogError;
import com.client.Graphics.DrawableResource;
import com.client.Graphics.GraphicsAbstract;
import com.server.command.MessageCommand;
import com.utilities.UtilityArray;
import com.utilities.UtilityMath;

/**
 * Panel that encapsulates drawing messages.
 * @author Ian
 */
public class GUIPanelMessage extends GUIPanelMovable { //Not really movable, but in principle so.
	/** The instance. */
	private final static GUIPanelMessage instance = new GUIPanelMessage();
	
	/** The resource of the message panel image. */
	private final static DrawableResource message_panel;
	/*private Message[] chat = {
			//"[14:51:16] Ian: Hello!",
			//"[14:51:48] Joeseph: Howdy!",
			//"[14:52:03] Server: AM SERVER.  PLEASE TO BE FEARINK ME!!!",
			new Message("Ian","Okay then . . ."),
			new Message("Joeseph","LOL"),
			new Message("Ian","So . . . whaddup, Joe?"),
	};*/
	/** The chat messages */
	private Message[] chat = {
			null,
			null,
			null//new Message("SERVER","Hi!  Welcome to TrollQuest!") //Not really from the server
	};
	
	/** The message we're currently working on. */
	private final Message working_message;
	/** The text of the message we're currently working on. */
	private final StringBuilder working_message_text = new StringBuilder();
	
	/** Static intializer.  Loads the message panel. */
	static {
		message_panel = ResourceLoader.loadImageResource(ResourceLoader.DIRECTORY_IMAGES_GUI+"message.png");
	}
	/** Constructor. */
	private GUIPanelMessage() {
		super(null);
		UtilityArray.reverse(chat);
		for (Message m : chat) { if (m!=null) { m.setComplete(); } }
		working_message = new Message(Main.getSelfName());
		working_message.setMessage("");
	}
	/** Returns an instance of the message panel.  Using singleton pattern. */
	public static GUIPanelMessage getInstance() {
		return instance;
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * Adds characters to the current message.  Somewhat tricky key encoding stuff
	 * that actually has a simple solution in Java.  
	 */
	@Override public boolean processKey(KeyEvent ke) {
		synchronized(working_message_text) {
			int key_code = ke.getExtendedKeyCode();
			if (key_code==KeyEvent.VK_BACK_SPACE||key_code==KeyEvent.VK_DELETE) {
				if (working_message_text.length()==0) return true;
				working_message_text.deleteCharAt(working_message_text.length()-1);
			} else if (key_code==KeyEvent.VK_ENTER) {
        Main.sendCommand(new MessageCommand(Main.getSelfName(), null,
            working_message_text.toString()));
				//The Java API is retarded.  Yes; the second index is correct.
				working_message_text.delete(0,working_message_text.length());
			} else {
				char c = ke.getKeyChar();
				if (c!=KeyEvent.CHAR_UNDEFINED) {
					working_message_text.append(c);
				}
			}
			working_message.setMessage(working_message_text.toString());
		}
		return true;
	}

	/**
	 * Updates the chat messages and the currently editing message to reflect the
	 * internal state of the game and the current time, respectively.
	 */
	public void update() {
		Deque<Message> chat_log = Main.getState().getChatLog();
		if (chat_log.size()>chat.length) {
			new JWorldDialogError("The chat length is too long!","Runtime Error",null);
		}
		Iterator<Message> iter = chat_log.descendingIterator();
		int i = 0;
		while (iter.hasNext()) {
			chat[i] = iter.next();
			++i;
		}
		
		working_message.updateTime();
	}
	
	/** {@inheritDoc} */
	@Override public void draw() {
		update(); //Updates could happen on their own thread, but really we only care right before we draw.
		
		GraphicsAbstract.drawResource(message_panel,
				0,0,-1,-1,
				150,-5,-1,-1);
		
		working_message.draw(5);
		
		GraphicsAbstract.setFont(GraphicsAbstract.font10);
		int y = 19;
		int i = 0;
		for (Message message : chat) {
			if (message!=null) {
				double alpha = 255.0 * Math.pow(Math.abs(  (4.0-i)/4.0  ),0.7);
				message.setAlpha( UtilityMath.clamp(UtilityMath.rndint(alpha),0,255) );
				message.draw(y);
			}
			y += 12;
			++i;
		}
	}
}