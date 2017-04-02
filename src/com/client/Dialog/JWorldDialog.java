package com.client.Dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * A dialog window for all dialogs.
 * @author Ian
 */
public abstract class JWorldDialog extends JDialog {
	/** Required for some reason. */
	private static final long serialVersionUID = -977900386059872094L;

	/**
	 * Constructor.
	 * @param owner the parent frame.
	 * @param title the title of the dialog.
	 * @param modal whether the dialog is modal.
	 */
	public JWorldDialog(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
	}
	
	/**
	 * Adds an okay button that closes the window.
	 * @return
	 */
	protected JButton addOkayButton() {
		JPanel button_panel = new JPanel();
		JButton button = new JButton("OK"); 
		
		button.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		//button.setMnemonic(KeyEvent.VK_ENTER);
		button_panel.add(button); 
		button_panel.setAlignmentX(LEFT_ALIGNMENT);
		add(button_panel);//, BorderLayout.SOUTH);
		
		return button;
	}

	/**
	 * "Finish"es the dialog.  I.e., makes an okay button, selects it,
	 * centers the dialog, makes it not resizable, and sets it visible.
	 */
	protected void finish() {
		JButton btn_ok = addOkayButton();

		pack();

		center();
		
		this.rootPane.setDefaultButton(btn_ok);
		
		setResizable(false);
		setVisible(true);
	}
	
	/** Centers the dialog.  Uses some devious trickery to do so. */
	public void center() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();

		int screen_width = screenSize.width;
		int screen_height = screenSize.height;

		setLocation(
				screen_width/2 - getWidth()/2,
				screen_height/2 - getHeight()/2
		);
	}
	
	/**
	 * Sets an empty border on the given component.
	 * @param component the component.
	 * @param border width of the border.
	 */
	public final static void setBorderOn(JComponent component, int border) {
		Border padding = BorderFactory.createEmptyBorder(border,border,border,border);
		component.setBorder(padding);
	}
	/**
	 * Sets an empty border on the given component.
	 * @param component the component.
	 * @param left the left width of the border.
	 * @param right the right width of the border.
	 * @param bottom the bottom width of the border.
	 * @param top the top width of the border.
	 */
	public final static void setBorderOn(JComponent component, int left, int right, int bottom, int top) {
		Border padding = BorderFactory.createEmptyBorder(top,left,bottom,right);
		component.setBorder(padding);
	}
	/**
	 * Sets a red border on the given component of width 5.  For debugging.
	 * @param component the component.
	 */
	public final static void setColorBorderOn(JComponent component) {
		Border padding = BorderFactory.createMatteBorder(5,5,5,5,new Color(255,0,0));
		component.setBorder(padding);
	}
	
	/**
	 * Gets a JPanel containing formatted text.  This is necessary because Java Swing is
	 * retarded in many ways, including not respecting \n.
	 * @param text the text.
	 * @param left the left width of the border.
	 * @param right the right width of the border.
	 * @param bottom the bottom width of the border.
	 * @param top the top width of the border.
	 * @return the panel.
	 */
	private JPanel getAddedTextJPanel(String text, int left, int right, int bottom, int top) {
		JPanel message_pane = new JPanel();
		
		setBorderOn(message_pane,left,right,bottom,top);
		
		message_pane.setLayout(new BoxLayout(message_pane,BoxLayout.Y_AXIS));

		String[] lines = text.split("\n");
		for (String line : lines) {
			if (line.length()==0) line += " ";
			line = line.replace("\t","    ");
			
			JLabel lbl = new JLabel(line);
			message_pane.add(lbl);
			lbl.setAlignmentX(LEFT_ALIGNMENT);
		}
		message_pane.setAlignmentX(LEFT_ALIGNMENT);
		
		return message_pane;
	}
	/**
	 * Adds text to a component.
	 * @param too the component to add to.
	 * @param text the text.
	 * @param border the width of the border around that text.
	 */
	public void addText(JComponent too, String text, int border) {
		addText(too, text, border,border,border,border);
	}
	/**
	 * Adds text to a component.
	 * @param too the component to add to.
	 * @param text the text.
	 * @param left the left width of the border.
	 * @param right the right width of the border.
	 * @param bottom the bottom width of the border.
	 * @param top the top width of the border.
	 */
	public void addText(JComponent too, String text, int left, int right, int bottom, int top) {
		too.add( getAddedTextJPanel(text,left,right,bottom,top) );
	}
	/**
	 * Adds text to a component.
	 * @param too the component to add to.
	 * @param text the text.
	 * @param border the width of the border around that text.
	 */
	public void addText(JDialog too, String text, int border) {
		addText(too, text, border,border,border,border);
	}
	/**
	 * Adds text to a component.
	 * @param too the component to add to.
	 * @param text the text.
	 * @param left the left width of the border.
	 * @param right the right width of the border.
	 * @param bottom the bottom width of the border.
	 * @param top the top width of the border.
	 */
	public void addText(JDialog too, String text, int left, int right, int bottom, int top) {
		too.add( getAddedTextJPanel(text,left,right,bottom,top) );
	}
}