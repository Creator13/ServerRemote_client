package org.creator13.serverremote.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
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
	private JTextField textField;
	private JTextField textField_1;
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
		setResizable(false);
		setBounds(100, 100, 493, 200);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel iconLabel = new JLabel(new ImageIcon("res/img/server_icon.png"));
		iconLabel.setBounds(0, 0, 128, 128);
		contentPanel.add(iconLabel);
		
		textField = new JTextField();
		textField.setBounds(197, 8, 188, 20);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		JLabel identificationLabel = new JLabel("Identification:");
		identificationLabel.setEnabled(false);
		identificationLabel.setBounds(138, 62, 67, 14);
		contentPanel.add(identificationLabel);
		
		textField_1 = new JTextField();
		textField_1.setEnabled(false);
		textField_1.setBounds(215, 59, 92, 20);
		contentPanel.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setEnabled(false);
		passwordLabel.setBounds(138, 90, 50, 14);
		contentPanel.add(passwordLabel);
		
		JLabel lblServerIp = new JLabel("Server IP:");
		contentPanel.add(lblServerIp);
		lblServerIp.setBounds(138, 8, 49, 20);
		
		checkLabel = new JLabel("");
		checkLabel.setBounds(197, 33, 280, 14);
		contentPanel.add(checkLabel);
		
		JButton btnNewButton = new JButton("Test server");
		btnNewButton.setBounds(395, 7, 87, 23);
		btnNewButton.addActionListener(new ServerPinger(textField.getText()));
		contentPanel.add(btnNewButton);
		
		JLabel lblClickcheckServer = new JLabel("Click \"check server\" to test connection");
		lblClickcheckServer.setBounds(197, 33, 298, 14);
		contentPanel.add(lblClickcheckServer);
		
		passwordField = new JPasswordField();
		passwordField.setEnabled(false);
		passwordField.setBounds(215, 87, 92, 20);
		contentPanel.add(passwordField);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JCheckBox chckbxUseAsDefault = new JCheckBox("Use as default server");
				chckbxUseAsDefault.setEnabled(false);
				buttonPane.add(chckbxUseAsDefault);
			}
			{
				JButton okButton = new JButton("Connect");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
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
		public void actionPerformed(ActionEvent e) {
			address 
			try {
				inetAddress = getInetAddress(address);
				
				port = getPort(address);
				if (port == -1) {
					
					
				}
				
			}
			catch (UnknownHostException e) {
				checkLabel.setText("Enter a valid IP address");
				
			}
			
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
		
	}
	
}
