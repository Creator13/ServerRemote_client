package org.creator13.serverremote.client;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

	private static Socket socket = null;
	private static BufferedReader in = null;
	private static boolean ableToConnect = true;
	
	public static void main(String[] args) throws IOException, InterruptedException {
		try {
			System.out.println("Connecting to server \"127.0.0.1\"...");
			while (!connectToServer())
				retryConnect();
			openStream();
			whileConnected();
			
		}
		catch (EOFException e) {
			System.out.println("Client terminated connection.");
			
		}
		catch (IOException e) {
			e.printStackTrace();
			
		}
		finally {
			socket.close();
			in.close();
			
		}
		
	}
	
	private static void retryConnect() throws IOException {
		new Thread(
			new Runnable() {
				
				@Override
				public void run() {
					try {
						for (int i = 20; i >=0; i--) {
							Thread.sleep(1000);
							System.out.println("Attempting to connect " + i + " more seconds");
							
						}
						
					} 
					catch (InterruptedException e) {}
					finally {
						ableToConnect = false;
						System.out.println("Failed to connect to server.");
						System.exit(1);
						
					}
					
				}
				
			}
			
		).start();
		while(ableToConnect && !connectToServer()) {}
		
	}

	private static boolean connectToServer() throws IOException {
		try {
			socket = new Socket(InetAddress.getByName("127.0.0.1"), 25566);
			System.out.println("Connected to remote server!");
			return true;
			
		}
		catch (ConnectException e) {
			return false;
			
		}
		
	}
	
	private static void openStream() throws IOException {
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
	}
	
	private static void whileConnected() throws IOException {
		String message = null;
		do {
			message = in.readLine();
			System.out.println("Server: " + message);
			
		}
		while (! message.contains("kick remote client"));
		
	}
	
}
