
/***
 * ServerThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */

import java.io.*;
import java.net.*;
import java.util.LinkedList;

public class ServerThread extends Thread {
	static LinkedList<ServerThread> listThreadsSending = new LinkedList<>();
	static LinkedList<ServerThread> listThreadsReceiving = new LinkedList<>();
	static LinkedList<String> history = new LinkedList<>();

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
	 * receives a request from client then sends an echo to the client
	 * 
	 * @param clientSocket
	 *            the client socket
	 **/

	public void run() {

		if (sender) {
			runSending(null);
		} else {
			runReceiving();
		}
	}

	public void runSending(String msg) {

		try {
					//BufferedReader stdIn = null;
			//stdIn = new BufferedReader(new InputStreamReader(System.in));
			PrintStream socOut = new PrintStream(clientSocket.getOutputStream());
			
			if(msg!= null) socOut.println(msg);
			// ecrit dans le socket

		} catch (Exception e) {
			System.err.println("Error in runSending:" + e);
		}
	}

	public void runReceiving() {
		// String out = "";
		try {
			BufferedReader socIn = null;
			socIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			while (true) {
				// String out = "";
				String out = socIn.readLine(); // lit depuis le socket

				// out = out + socLine;
				out = "Member " + this.id + " : " + out;
				System.out.println(out);

				for (int i = 0; (i < listThreadsSending.size()); i++) {
					listThreadsSending.get(i).runSending(out);
				}

				//history.add(out);
				//File file = new File("C:\\Users\\ocaraiman\\workspace\\Chat\\history.txt");
				//FileOutputStream save = new FileOutputStream(file);
				FileWriter fw = new FileWriter("D:\\java\\TP1_Reseaux\\src\\history.txt",true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(out+"\n");
				bw.newLine();
				bw.close();
				//save.close();
			}

		} catch (Exception e) {
			System.err.println("Error in runReceiving:" + e);
		}

	}

}
