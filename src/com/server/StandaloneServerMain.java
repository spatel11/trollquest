/**
 * @author Stephen Patel (spatel11@unm.edu)
 */
package com.server;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.ServerSocket;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.server.factory.Servers;
import com.server.interfaces.Server;

/**
 * This class is an entry point for hosting a dedicated TrollQuest Server.
 * 
 * @author Stephen Patel
 * 
 */
public class StandaloneServerMain {

	/**
	 * Opens a window that says the server is running, and has a button to shut
	 * down the server.  If 
	 * 
	 * @param s
	 *            the server to monitor.
	 */
	private static void serverDialog(final Server s) {
		JFrame j = new JFrame();
		j.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		JButton b = new JButton("Shutdown Server");
		JLabel l = new JLabel("The Server Is Running.");
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				s.shutDown();
			}
		});
		j.addWindowListener(new WindowListener() {
			@Override
			public void windowActivated(WindowEvent arg0) {
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				s.shutDown();
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
			}
		});
		j.add(l);
		j.add(b, BorderLayout.SOUTH);
		j.setPreferredSize(new Dimension(150, 80));
		j.pack();
		j.setVisible(true);
	}

	/**
	 * Asks the user for a port to start the server on. If the port is invalid,
	 * or there is already a server on that port, the user will be notified, and
	 * the program will exit.
	 * 
	 * @param args
	 *            not used.
	 */
	public static void main(String[] args) {
		String portDialog = JOptionPane.showInputDialog(null, "Enter Port",
				"Port Specifier", JOptionPane.QUESTION_MESSAGE);
		if (portDialog == null) {
			System.exit(0);
		}
		try {
			int port = Integer.parseInt(portDialog);
			if (port > 65536) {
				JOptionPane
						.showMessageDialog(
								null,
								port
										+ " is not a valid port number.  Ports must be less than 65536",
								"PORT ERROR", JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
			ServerSocket test = new ServerSocket(port);
			test.close();
			Server s = Servers.debugServer(port, 1);
			Executors.newSingleThreadExecutor().execute(s);
			serverDialog(s);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.toString(), "PORT ERROR",
					JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}
}
