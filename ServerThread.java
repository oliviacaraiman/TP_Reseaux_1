
/***
 * ServerThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */

import java.io.*;
import java.net.*;
import java.util.LinkedList;

/**
 * 
 *
 */
public class ServerThread extends Thread {
	static LinkedList<ServerThread> listThreadsSending = new LinkedList<>();
	static LinkedList<ServerThread> listThreadsReceiving = new LinkedList<>();
	static LinkedList<String> history = new LinkedList<>(); //history of messages

	private Socket clientSocket;
	private int id;
	private boolean sender;
	public boolean keepOn = true;

	ServerThread(Socket s, boolean send) {
		this.clientSocket = s;
		this.sender = send;
		if (send) {
			listThreadsSending.add(this);
			id = listThreadsSending.size();
		} else {
			listThreadsReceiving.add(this);
			id = listThreadsReceiving.size();
		}
	}

	/**
	 * depending on sender value (true/false), choose the right method
	 */
	public void run() {

		if (sender) {
			runSending(null);
		} else {
			runReceiving();
		}
	}

	/**
	 * write in the socket
	 * 
	 * @param msg: message to write in the socket
	 */

	public void runSending(String msg) {

		try {
			PrintStream socOut = new PrintStream(clientSocket.getOutputStream());
			if (msg != null)
				socOut.println(msg);

		} catch (Exception e) {
			System.err.println("Error in runSending:" + e);
		}
	}

	/**
	 * read from the socket 
	 */
	public void runReceiving() {
	try {
		BufferedReader socIn = null;
		socIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	
			while (true) {
				String out = socIn.readLine(); // read from the socket

				// id attribution to each memeber of the conversation
				out = "Member " + this.id + " : " + out;

				// send the read information to all members connected to the chat
				for (int i = 0; (i < listThreadsSending.size()); i++) {
					listThreadsSending.get(i).runSending(out);
				}

				// creating a history file with all messages
				FileWriter fw = new FileWriter("history.txt",true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(out+"\n");
				bw.close();
			} 
		}catch (Exception e) {
				System.err.println("Error in runReceiving:" + e);
			}

		}

	}
