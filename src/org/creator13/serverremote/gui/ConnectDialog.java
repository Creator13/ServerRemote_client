/*
 * Main constructor code was generated by WindowBuilder Pro.
 * 
 * 
 */

package org.creator13.serverremote.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class ConnectDialog extends JDialog implements Runnable {
	
	private static final long serialVersionUID = 8840520454043160314L;
	private final JPanel contentPanel = new JPanel();
	private JTextField IP_field;
	private JTextField ID_field;
	private JPasswordField passwordField;
	private JLabel checkLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new Thread(new ConnectDialog()).start();
		
	}

	/**
	 * Create the dialog.
	 */
	public ConnectDialog() {
		setTitle("Connect");
		setIconImage(new ImageIcon("res/img/serverIcon_small.png").getImage());
		setResizable(false);
		setBounds(100, 100, 493, 200);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel imageLabel = new JLabel(new ImageIcon("res/img/server_icon.png"));
		imageLabel.setBounds(0, 0, 128, 128);
		contentPanel.add(imageLabel);
		
		IP_field = new JTextField();
		IP_field.setBounds(197, 8, 188, 20);
		IP_field.addActionListener(new ServerPinger());
		contentPanel.add(IP_field);
		IP_field.setColumns(10);
		
		JLabel ID_label = new JLabel("Identification:");
		ID_label.setEnabled(false);
		ID_label.setBounds(138, 62, 67, 14);
		contentPanel.add(ID_label);
		
		ID_field = new JTextField();
		ID_field.setEnabled(false);
		ID_field.setBounds(215, 59, 92, 20);
		contentPanel.add(ID_field);
		ID_field.setColumns(10);
		
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setEnabled(false);
		passwordLabel.setBounds(138, 90, 50, 14);
		contentPanel.add(passwordLabel);
		
		JLabel IP_label = new JLabel("Server IP:");
		contentPanel.add(IP_label);
		IP_label.setBounds(138, 8, 49, 20);
		
		JButton testServer = new JButton("Test server");
		testServer.setBounds(395, 7, 87, 23);
		testServer.addActionListener(new ServerPinger());
		contentPanel.add(testServer);
		
		checkLabel = new JLabel("Click \"check server\" to test connection");
		checkLabel.setBounds(197, 33, 298, 14);
		contentPanel.add(checkLabel);
		
		passwordField = new JPasswordField();
		passwordField.setEnabled(false);
		passwordField.setBounds(215, 87, 92, 20);
		contentPanel.add(passwordField);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			
			JCheckBox useDefaultServerBox = new JCheckBox("Use as default server");			
			useDefaultServerBox.setEnabled(false);
			buttonPane.add(useDefaultServerBox);
			
			JButton connectButton = new JButton("Connect");
			connectButton.setActionCommand("OK");
			buttonPane.add(connectButton);
			getRootPane().setDefaultButton(connectButton);
		
		}
	
	}

	@Override
	public void run() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			ConnectDialog dialog = new ConnectDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private class ServerPinger implements ActionListener {
		
		private InetAddress inetAddress;
		private int port;
		
		@Override
		public void actionPerformed(ActionEvent event) {
			setLabelText("Trying to connect...");
			new Thread(
				(new Runnable() {
				
					@Override
					public void run() {
						String address = getAddressFromTextField();
						try {
							inetAddress = getInetAddress(address);
							
							port = getPort(address);
							if (port == -1) {
								setLabelText("Something went wrong while parsing port, try again");
								return;
								
							}
							
						}
						catch (UnknownHostException e) {
							setLabelText("Enter a valid IP address");
							
						}
						
						Boolean serverIsOnline = null;
						try {
							serverIsOnline = inetAddress.isReachable(10000);
							
						} catch (IOException e) {
							e.printStackTrace();
							
						}
						
						if (serverIsOnline) {
							setLabelText("Server is reachable");
							
						}
						else {
							setLabelText("Can't reach server");
							
						}
						
					}
				
				}
				
			), "ServerPinger").start();
			
		}
		
		private String getAddressFromTextField() {
			return IP_field.getText();
			
		}

		private InetAddress getInetAddress(String address) throws UnknownHostException {
			String[] splittedAddress = address.split(":");
			return InetAddress.getByName(splittedAddress[0]);
			
		}
		
		private int getPort(String address) {
			String[] splittedAddress = address.split(":");
			try {
				return Integer.valueOf(splittedAddress[1]);
				
			}
			catch (NumberFormatException ex) {
				return -1;
				
			}
			catch (NullPointerException | ArrayIndexOutOfBoundsException ex) {
				return -2;
				
			}
			
		}
		
		private void setLabelText(final String text) {
			SwingUtilities.invokeLater(
				new Runnable() {
				
					@Override
					public void run() {
						checkLabel.setText(text);
						
					}
				
				}
				
			);
			
		}
		
	}
	
}
