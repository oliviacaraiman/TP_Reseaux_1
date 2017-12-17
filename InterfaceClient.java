import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class InterfaceClient {

	BufferedReader in;
	PrintStream out;
	JFrame frame = new JFrame("Chat");
	JTextField textField = new JTextField(40);
	JTextArea messageArea = new JTextArea(40, 40);
	
	/**
	 * interface constructor
	 * @param clientSocket 
	 * @throws IOException
	 */

	public InterfaceClient(Socket clientSocket) throws IOException {
		
		out = new PrintStream(clientSocket.getOutputStream());
		textField.setEditable(true);
		textField.setSelectedTextColor(Color.BLUE);
		messageArea.setEditable(false);
		frame.getContentPane().add(textField, "North");
		frame.getContentPane().add(new JScrollPane(messageArea), "Center");
		frame.pack();

		// Add Listeners
		textField.addActionListener(new ActionListener() {
			/**
			 * @param e
			 *  sends the message when the user touches "ENTER"
			 */
			public void actionPerformed(ActionEvent e) {
				// writes the message on the socket 
				out.println(textField.getText());
				textField.setText("");
			}
		});
	}

}
