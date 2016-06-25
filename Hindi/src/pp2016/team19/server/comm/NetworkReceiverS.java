package pp2016.team19.server.comm;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.LinkedBlockingQueue;

import pp2016.team19.shared.Message;
import pp2016.team19.shared.ThreadWaitForMessage;

/**
 * The NetworkReceiverS Class builds the Thread for receiving Messages from the
 * Client. The Thread allows to save the Data of the InputStream. The saved
 * messages are going to be gathered by a LinkedBlockingQueue and a loop allows
 * to read these messages from the InputStream repetetively and eventually save
 * the messages in a Queue.
 * 
 * @author Bulut , Taner , 5298261
 * 
 */
public class NetworkReceiverS extends Thread {

	private NetworkHandlerS networkHandler;
	private Socket client;
	private ObjectInputStream in;
	LinkedBlockingQueue<Message> messagesFromClient = new LinkedBlockingQueue<>();
	private Message messageFC;

	/**
	 * Initializes the Socket 'client' variable so that the InputStream can be
	 * operated assigned to the Socket and initializes the NetworkHandlerS
	 * 'networkHandler'
	 * 
	 * @author Bulut , Taner , 5298261
	 */
	public NetworkReceiverS(Socket client, NetworkHandlerS networHandler) {
		this.client = client;
		this.networkHandler = networHandler;
	}

	/**
	 * Builds an instance of ObjectInputStream that reads from the InputStream
	 * and saves the Data in a LinkedBlockingQueue. A while-loop is executing
	 * the operation consistently. And each time a Message is read from the
	 * InputStream, the Thread is yielded before and waits for a certain time.
	 * 
	 * @author Bulut , Taner , 5298261
	 */
	private void receiveMessage() {
		try {
			in = new ObjectInputStream(new BufferedInputStream(client.getInputStream()));
			while (true) {
				ThreadWaitForMessage.waitFor(100L);
				messageFC = (Message) in.readObject();
				this.networkHandler.setConnectedState1(true);
				this.networkHandler.setConnectedState2(true);
				if (messageFC != null) {
					messagesFromClient.put(messageFC);
				}
			}
		} catch (EOFException e) {
			System.out.println("ERROR ObjectInputStream: NETWORKRECEiVERS");
		} catch (SocketException e) {
			this.networkHandler.close();
			System.out.println("ERROR SocketException: NETWORKRECEIVERS");
			e.printStackTrace();
		} catch (IOException | ClassNotFoundException | InterruptedException e) {
			System.out.println("readObjectError");
			e.printStackTrace();
		} finally {
			try {
				System.out.println("InputStream is closed: NETWORKRECEiVERS");
				this.in.close();
			} catch (IOException e) {
				System.out.println("ERROR: NETWORKRECEIVERS");
				e.printStackTrace();
			}
			System.exit(1);
		}
	}

	/**
	 * Runs the Thread and executes the consistent receiving of the messages by
	 * the receiveMessage() method
	 * 
	 * @author Bulut , Taner , 5298261
	 */
	@Override
	public void run() {
		this.receiveMessage();
	}

	/**
	 * Returns the LinkedBlockingQueue 'messagesFromClient'
	 * 
	 * @author Bulut , Taner , 5298261
	 */
	public LinkedBlockingQueue<Message> getMessagesFromClient() {
		return messagesFromClient;
	}

	/**
	 * Returns the Message that is polled from the Queue 'messagesFromClient'
	 * 
	 * @author Bulut , Taner , 5298261
	 */
	public Message getMessage() {
		return messagesFromClient.poll();
	}
}
