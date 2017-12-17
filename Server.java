/***
 * EchoServer
 * Example of a TCP server
 * Date: 10/01/04
 * Authors:
 */


import java.io.*;
import java.net.*;
import java.util.*;

public class Server  {
 	/**
  	* main method
	* @param EchoServer port
  	* 
  	**/
    public static void main(String args[]){ 
        ServerSocket listenSocket;
        BufferedReader stdIn = null;
        PrintStream socOut = null;
        BufferedReader socIn = null;
  
  	if (args.length != 1) {
          System.out.println("Usage: java EchoServer <EchoServer port>");
          System.exit(1);
  	}
	try {

		/**
		 * connection to server
		 */
		listenSocket = new ServerSocket(Integer.parseInt(args[0])); //port
		System.out.println("Server ready..."); 
		
		while (true) {
			Socket clientSocket = listenSocket.accept();
			System.out.println("Connexion from:" + clientSocket.getInetAddress());
			//reading thread
			ServerThread ctSendingToClient = new ServerThread(clientSocket,true);
			ctSendingToClient.start();
			
			//listening thread
			ServerThread ctReceivingFromClient = new ServerThread(clientSocket,false);
			ctReceivingFromClient.start();
			
			//print the history of messages in the chat
			for(int i=0; i<ServerThread.history.size(); i++) 
			{
				ctSendingToClient.runSending(ServerThread.history.get(i));
			}
	
		}
        } catch (Exception e) {
            System.err.println("Error in Server:" + e);
        }
      }
  }
