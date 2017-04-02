package com.client.Dialog;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import com.client.Main;
import com.client.GUI.GUIMain;
import com.utilities.Function;

/**
 * An error message.
 * @author Ian
 */
public class JWorldDialogError extends JWorldDialog {
	/** Required for some reason. */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * @param msg the message to display.
	 * @param title the title on the dialog window.
	 * @param termination_callback the function to call when
	 * the user clicks OK.  Can be null.
	 */
	public JWorldDialogError(String msg, String title, final Function termination_callback) {
		super( GUIMain.hasInstance()?GUIMain.getInstance():null, title, true );
		/*try {
			Dimension parentSize = GUIMain.getInstance().getSize(); 
			Point p = GUIMain.getInstance().getLocation(); 
			setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
		} catch (NullPointerException e) {}*/
	    
	    this.addWindowListener(new WindowListener() {
			@Override public void windowActivated(WindowEvent arg0) {}
			@Override public void windowClosed(WindowEvent arg0) {
				if (termination_callback!=null) termination_callback.execute();
				Main.quit(); while (true);
			}
			@Override public void windowClosing(WindowEvent arg0) {}
			@Override public void windowDeactivated(WindowEvent arg0) {}
			@Override public void windowDeiconified(WindowEvent arg0) {}
			@Override public void windowIconified(WindowEvent arg0) {}
			@Override public void windowOpened(WindowEvent arg0) {}
	    });
	    
	    addText(this,msg,10);
		
		finish();
	}
}