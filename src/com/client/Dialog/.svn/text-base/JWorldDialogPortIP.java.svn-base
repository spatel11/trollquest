package com.client.Dialog;

import java.awt.Frame;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.client.Graphics.GraphicsAbstract;
import com.utilities.Function;
import com.utilities.UtilityMath;
import com.utilities.configfile.ConfigFile;

/**
 * Class encapsulating a dialog to request the IP and Port from the user.
 * @author Ian
 */
public final class JWorldDialogPortIP extends JWorldDialog {
	/** Required for some reason. */
	private static final long serialVersionUID = 6914331979163084657L;

	/** Set of valid numbers. */
	private final static Set<Integer> number_set = new HashSet<Integer>();
	
	/** The values of the IP. */
	private Integer[] values_ip = new Integer[4];
	/** The value of the port. */
	private Integer value_port;
	/** Whether to make a server or not. */
	private Boolean value_make_server;
	
	/** The IP fields. */
	private TextField[] fields_ip = {
			new FixedWidthTexField(5,255,number_set),
			new FixedWidthTexField(5,255,number_set),
			new FixedWidthTexField(5,255,number_set),
			new FixedWidthTexField(5,255,number_set)
	};
	/** The port field. */
	private TextField field_port;
	/** The buttons for the server and client. */
	private ButtonGroup button_group;
	
	/** A configuration file storing default values for all this. */
	private final static ConfigFile cfg_file = new ConfigFile(
			"data"+File.separatorChar+"config"+File.separatorChar+"connection.txt"
	);

	/** Static initializer. */
	static {
		for (char c='0';c<='9';++c) {
			number_set.add((int)(c)); //VK_0 - VK-9 are the same as ASCII.
		}
		Integer[] default_ip = {127,0,0,1};
		Integer default_port = 1337;
		Boolean default_make_server = true;
		cfg_file.parameterAdd("ip",default_ip);
		cfg_file.parameterAdd("port",default_port);
		cfg_file.parameterAdd("cs",default_make_server);
	}

	/**
	 * A text field that only allows a certain number of characters from
	 * a certain subset of characters to be entered into its text field.
	 * @author Ian
	 * @author http://www.rgagnon.com/javadetails/java-0227.html
	 */
	class FixedWidthTexField extends TextField implements KeyListener,FocusListener {
		/** Required for some reason. */
		private static final long serialVersionUID = -3981776622840909849L;
		/** Length permissible. */
		private final int _len;
		/** Maximum value, if this field is an integer. */
		private final int _maximum_value;
		/** Valid characters. */
		private Set<Integer> valid_keys = null;

		/**
		 * Constructor.
		 * @param len the maximum length of the text field.
		 * @param maximum_value the maximum value, inclusive.
		 * @param valid_keys the valid keys that may be entered.
		 */
		public FixedWidthTexField(int len, int maximum_value, Set<Integer> valid_keys) {
			_len = len;
			_maximum_value = maximum_value;

			String s = ""; for (int i=0;i<len;++i) s += "1";
			setText(s);
			//setMinimumSize(getPreferredSize());
			setPreferredSize(getPreferredSize());
			//setMaximumSize(getPreferredSize());
			setText("");

			addKeyListener(this);
			addFocusListener(this);
			this.valid_keys = valid_keys;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * Could be optimized, but this is more clear.
		 */
		@Override public void keyPressed(KeyEvent e) {
			int kc = e.getExtendedKeyCode();
			
			if ((kc==KeyEvent.VK_BACK_SPACE)||
					(kc==KeyEvent.VK_DELETE) ||
					(kc==KeyEvent.VK_ENTER)|| 
					(kc==KeyEvent.VK_TAB)||
					(kc==KeyEvent.VK_CONTROL)||
					e.isActionKey())
			{
				return; //We're okay (always).
			}
			
			if (valid_keys!=null&&!valid_keys.contains(kc)) {
				Toolkit.getDefaultToolkit().beep();
				e.consume();
				return; //Invalid key.
			}
			if (getText().length() >= _len) {
				Toolkit.getDefaultToolkit().beep();
				e.consume();
				return; //Too long
			}
			
			//We must be okay.
			return;
		}
		/** {@inheritDoc} */
		@Override public void keyReleased(KeyEvent arg0) {}
		/** {@inheritDoc} */
		@Override public void keyTyped(KeyEvent arg0) {}
		
		/** {@inheritDoc} */
		@Override public void focusGained(FocusEvent arg0) {}
		/** {@inheritDoc} */
		@Override public void focusLost(FocusEvent arg0) {
			//System.out.println("LOST FORUCSSS!!!");
			try {
				int number = Integer.parseInt(getText());
				if (number<0||number>_maximum_value) {
					Toolkit.getDefaultToolkit().beep();
					setText(UtilityMath.clamp(number,0,_maximum_value).toString());
				}
			} catch (NumberFormatException exception) {
				Toolkit.getDefaultToolkit().beep();
				setText("0");
			}
		}
	};

	/** Adds the buttons to choose client or server mode. */
	private final void addQueryCS() {
		addText(this,"Choose connection method:",10,10,0,10);

		button_group = new ButtonGroup();
		JPanel radio_panel = new JPanel();
		radio_panel.setLayout(new BoxLayout(radio_panel,BoxLayout.X_AXIS));

		JRadioButton rb1 = new JRadioButton("Create Client",false);
		rb1.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent ae) {
				for (int i=0;i<4;++i) fields_ip[i].setEnabled(true);
			}
		});
		button_group.add(rb1);
		radio_panel.add(rb1);

		JRadioButton rb2 = new JRadioButton("Create Server",false);
		rb2.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent ae) {
				fields_ip[0].setText("127");
				fields_ip[1].setText(  "0");
				fields_ip[2].setText(  "0");
				fields_ip[3].setText(  "1");
				for (int i=0;i<4;++i) fields_ip[i].setEnabled(false);
			}
		});
		button_group.add(rb2);
		radio_panel.add(rb2);

		if (value_make_server) {
			Enumeration<AbstractButton> e = button_group.getElements();
			e.nextElement(); e.nextElement().doClick();
		} else {
			button_group.getElements().nextElement().doClick();
		}
		
		radio_panel.setAlignmentX(LEFT_ALIGNMENT);
		
		JWorldDialog.setBorderOn(radio_panel,15,10,0,0);

		add(radio_panel);
	}
	/** Adds the IP fields. */
	private final void addQueryIP() {
		addText(this,"Enter the IP:",10,10,0,10);

		JPanel field_ip_panel = new JPanel();
		field_ip_panel.setLayout(new BoxLayout(field_ip_panel,BoxLayout.X_AXIS));
		for (int i=0;i<4;++i) {
			fields_ip[i].setFont(GraphicsAbstract.font12fw);
			fields_ip[i].setColumns(1);
			fields_ip[i].setText(""+values_ip[i]);

			field_ip_panel.add(fields_ip[i]);
			if (i!=3) {
				//field_ip_panel.add(new Label("."));
				addText(field_ip_panel,".",0,0,0,0);
			}
		}
		JWorldDialog.setBorderOn(field_ip_panel,20,10,0,5);
		field_ip_panel.setAlignmentX(LEFT_ALIGNMENT);
		add(field_ip_panel);
	}
	/** Adds the port field. */
	private final void addQueryPort() {
		addText(this,"Enter the Port:",10,10,0,10);

		JPanel field_port_panel = new JPanel();
		field_port_panel.setLayout(new BoxLayout(field_port_panel,BoxLayout.X_AXIS));

		field_port = new FixedWidthTexField(5,65535,number_set);
		field_port.setFont(GraphicsAbstract.font12fw);
		field_port.setColumns(1);
		field_port.setText(""+value_port);

		field_port_panel.add(field_port);
		JWorldDialog.setBorderOn(field_port_panel,20,10,0,5);
		
		field_port_panel.setAlignmentX(LEFT_ALIGNMENT);

		add(field_port_panel);
	}
	
	/**
	 * Constructor.
	 * @param close_function a function to call when the dialog closes.
	 */
	public JWorldDialogPortIP(final Function close_function) {
		super((Frame)(null),"Enter IP and Port",true);

		cfg_file.load();
		values_ip = (Integer[])( cfg_file.parameterGet("ip") );
		value_port = (Integer)( cfg_file.parameterGet("port") );
		value_make_server = (Boolean)( cfg_file.parameterGet("cs") );
		
		this.addWindowListener(new WindowListener() {
			@Override public void windowActivated(WindowEvent we) {}
			@Override public void windowClosed(WindowEvent we) {
				for (int i=0;i<4;++i) values_ip[i] = Integer.parseInt(fields_ip[i].getText());
				value_port = Integer.parseInt(field_port.getText());
				value_make_server = !( button_group.getElements().nextElement().isSelected() );
				cfg_file.parameterSet("ip",values_ip);
				cfg_file.parameterSet("port",value_port);
				cfg_file.parameterSet("cs",value_make_server);
				cfg_file.save();
				
				String ip_str = values_ip[0]+"."+values_ip[1]+"."+values_ip[2]+"."+values_ip[3];
				close_function.execute(ip_str,value_port,value_make_server);
			}
			@Override public void windowClosing(WindowEvent we) { System.exit(0); }
			@Override public void windowDeactivated(WindowEvent we) {}
			@Override public void windowDeiconified(WindowEvent we) {}
			@Override public void windowIconified(WindowEvent we) {}
			@Override public void windowOpened(WindowEvent we) {}
	    });
		
		addQueryCS();
		//add(new JSeparator(SwingConstants.HORIZONTAL));
		addQueryIP();
		addQueryPort();
		
		finish();
	}
}