package com.client.Dialog;

import java.awt.Frame;
import java.awt.TextField;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.client.Graphics.GraphicsAbstract;
import com.utilities.Function;
import com.utilities.configfile.ConfigFile;

/**
 * Class encapsulating a dialog to request the username from the user.
 * @author Ian
 */
public class JWorldDialogUser extends JWorldDialog {
	/** Required for some reason. */
	private static final long serialVersionUID = 3275263538660059024L;
	
	/** The username text field. */
	private TextField field_username;
	/** The value of that username entered. */
	private String value_username;
	
	/** The configuration file storing everything. */
	private final static ConfigFile cfg_file = new ConfigFile(
			"data"+File.separatorChar+"config"+File.separatorChar+"user.txt"
	);
	
	/** Static initializer. */
	static {
		cfg_file.parameterAdd("name","<Enter your username here>");
	}

	/** Adds the request username field. */
	private void setupUsername() {
		addText(this,"Enter your username:",10,10,0,10);

		JPanel field_username_panel = new JPanel();
		field_username_panel.setLayout(new BoxLayout(field_username_panel,BoxLayout.X_AXIS));

		field_username = new TextField();
		field_username.setFont(GraphicsAbstract.font12fw);
		field_username.setColumns(30);
		field_username.setText(value_username);

		field_username_panel.add(field_username);
		JWorldDialog.setBorderOn(field_username_panel,20,10,0,5);
		
		field_username_panel.setAlignmentX(LEFT_ALIGNMENT);

		add(field_username_panel);
	}
	/**
	 * Constructor.
	 * @param close_function a function to call when the dialog closes.
	 */
	public JWorldDialogUser(final Function close_function) {
		super((Frame)(null),"Enter Username",true);
		
		cfg_file.load();
		value_username = (String)( cfg_file.parameterGet("name") );
		
		setupUsername();
		
		this.addWindowListener(new WindowListener() {
			@Override public void windowActivated(WindowEvent we) {}
			@Override public void windowClosed(WindowEvent we) {
				value_username = field_username.getText();
				cfg_file.parameterSet("name",value_username);
				cfg_file.save();
				
				close_function.execute(value_username);
			}
			@Override public void windowClosing(WindowEvent we) { System.exit(0); }
			@Override public void windowDeactivated(WindowEvent we) {}
			@Override public void windowDeiconified(WindowEvent we) {}
			@Override public void windowIconified(WindowEvent we) {}
			@Override public void windowOpened(WindowEvent we) {}
	    });
		
		finish();
	}
}