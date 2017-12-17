/***
 * ClientThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientThread extends Thread {
	
	private Socket clientSocket;
	private InterfaceClient ic;
	
	private boolean sender;
	public boolean keepOn = true;

	ClientThread(Socket s, boolean send, InterfaceClient ic) {
		this.clientSocket = s;
		this.sender = send;
		this.ic = ic;
	}
	
	/**
	 * receives a request from client then sends an echo to the client
	 * 
	 * @param clientSocket
	 *            the client socket
	 **/

	public void run() {

		if (sender) {
			runSending();
		} else {
			runReceiving();
		}
	}

	public void runSending() {

		try {
			

		} catch (Exception e) {
			System.err.println("Error in runSending:" + e);
		}
	}

	public void runReceiving() {
		try {
			System.out.println("received");
			BufferedReader socIn = null;
			socIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			while (true) {
				String out = socIn.readLine(); // lit depuis le socket
				//writes received messages in the chat window
				ic.messageArea.append(out + "\n");
				System.out.println(out);
			}

		} catch (Exception e) {
			System.err.println("Error in runReceiving:" + e);
		}

	}

}
