package pp2016.team19.client.comm;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.concurrent.LinkedBlockingQueue;

import pp2016.team19.shared.Message;

/**
 * <h1>Builds the connection for the Client and handles the receiving and the
 * transmitting of messages.</h1>
 * 
 * The HandlerClient Class is responsible for building the Socket for the
 * Client-Side and connecting with the Server. This Class submits methods for
 * the Client-Engine in order to send and receive messages. Therefore it runs
 * the Threads ReceiverClient and TransmitterClient. The TimerTask is also run
 * for sending the MessPing-Messages in a defined period of time to the Server.
 * 
 * @author Bulut , Taner , 5298261
 * 
 */

public class HandlerClient {
	// Gathers the Messages to the Server in a Queue
	public LinkedBlockingQueue<Message> outputQueue = new LinkedBlockingQueue<>();
	// Connecting with the Server-Port
	private Socket server;
	// Receiving Messages by a Thread
	private ReceiverClient receiver;
	// Sending Messages by a Thread
	private TransmitterClient transmitter;
	// Executes the TimerTask
	private Timer pingTimer;
	// Stating the Connection State for closing the Sockets
	private boolean closeNetwork;
	// Stating the Connection State 
	private boolean connectedState1;
	// Stating the Connection State
	private boolean connectedState2;

	/**
	 * Builds the Socket for the Client-Side and starts the Threads for sending
	 * and receiving through ReceiverClient and TransmitterClient. The TimerTask
	 * is also started for sending MessPing messages to the server.
	 * 
	 * @author Bulut , Taner , 5298261
	 * @param adresse
	 *            defines the Server-Address
	 */
	public HandlerClient(String adresse) {
		this.pingTimer = new Timer();
		this.closeNetwork = false;
		//Connects with the ServerSocket
		while (this.server == null) {
			try {
				// this.server = new Socket("62.143.243.85", 33333);
				this.server = new Socket(adresse, 44444);
				this.connectedState1 = true;
				this.connectedState2 = true;
			} catch (UnknownHostException e) {
				System.out.println("ERROR: HandlerClient");
				e.printStackTrace();
			} catch (IOException e) {
				// Handles the failed attempt to connect with the Server
				System.out.println("ERROR: >>>>>>>>>>HandlerClient SERVER UNREACHABLE<<<<<<<<<<");
				e.printStackTrace();
				System.out.println(
						"Connection server cannot be built! \n\n Please check : \n 1. The game-server is started? \n 2. The client follows the appropriate serveraddress? \n 3. The server-port and the client-port do match? \n\n Start the game again afterwards! \n Connection-Error!");
				System.exit(0);
			}
		}
		// Starts the Threads and the TimerTask
		startComponents();
	}

	/**
	 * Starting the Threads and the TimerTask.Initializing the ReceiverClient
	 * and TransmitterClient instances and starts the Threads for sending and
	 * receiving messages. The TimerTask is also started for sending MessPing
	 * messages to the server.
	 * 
	 * @author Bulut , Taner , 5298261
	 */
	private void startComponents() {
		System.out.println("HandlerClient.startComponents()");
		// Initializing and starting the Threads for receiving and transmitting
		// messages
		transmitter = new TransmitterClient(server);
		receiver = new ReceiverClient(server, this);
		transmitter.start();
		receiver.start();
		// The Timer 'pingTimer' executes the TimerTask-PingCheckClient within a
		// certain interval
		this.pingTimer.scheduleAtFixedRate(new PingCheckClient(this), 3000, 3000);
	}

	/**
	 * Closing the Socket, the TimerTask and stops the running of the
	 * application. This method allows to stop the connection between Server and
	 * Client. Closes the Socket of the Client.
	 * 
	 * @author Bulut , Taner , 5298261
	 * @param errorMessage
	 *            defines the expected Error-Message
	 */
	public void close(String errorMessage) {
		try {
			System.out.println("CLOSED: HandlerClient");
			// Cancelling the Timer
			this.pingTimer.cancel();
			// Closing the Socket
			this.getServer().close();
			System.out.println(errorMessage);
			// Terminates the currently running Java Virtual Machine
			System.exit(1);
		} catch (IOException e) {
			System.out.println("ERROR: HandlerClient.close(String errorMessage)");
			e.printStackTrace();
		}
	}

	/**
	 * Sending messages to the Server through the TransmitterClient-Thread
	 * 
	 * @author Bulut , Taner , 5298261
	 * @param message
	 *            defines the Message that is written into the
	 *            ObjectOutputStream and sent to the Server
	 */
	public void sendMessageToServer(Message message) {
		// Writes the Message into the ObjectOutputStream
		transmitter.writeMessage(message);
	}

	/**
	 * Returning messages from the Server through the ReceiverClient-Thread
	 * 
	 * @author Bulut , Taner , 5298261
	 * @return the Message-object that is read from the ObjectInputStream
	 */
	public Message getMessageFromServer() {
		// Getting the Message from the LinkedBlockingQueue 'messagesFromServer'
		return receiver.getMessage();
	}

	/**
	 * Returning the LinkedBlockingQueue that collects the messages that are
	 * going to be sent to the Server through the TransmitterClient-Thread.
	 * Allows an efficient testing of the sending operation
	 * 
	 * @author Bulut , Taner , 5298261
	 * @return the LinkedBlockingQueue that gathers the Message objects
	 *         that are going to be sent to the Server
	 */
	public LinkedBlockingQueue<Message> getOutputQueue() {
		return this.outputQueue = transmitter.getQueueMessagesToServer();
	}

	/**
	 * Returning the instance of TransmitterClient
	 * 
	 * @author Bulut , Taner , 5298261
	 * @return the instance of TransmitterClient
	 */
	public TransmitterClient getTransmitterC() {
		return this.transmitter;
	}

	/**
	 * Returning the instance of ReceiverClient
	 * 
	 * @author Bulut , Taner , 5298261
	 * @return the instance of ReceiverClient
	 */
	public ReceiverClient getReceiverC() {
		return this.receiver;
	}

	/**
	 * The following methods are standard Get-/Set-Methods that do not need
	 * further explanation
	 * 
	 * @author Bulut , Taner , 5298261
	 */

	/**
	 * Sets the 'closeNetwork' attribute 
	 * 
	 * @author Bulut , Taner , 5298261
	 * @param closeNetwork
	 *            the boolean variable sets the connection state of the Client
	 * 
	 */
	public void setCloseNetwork(boolean closeNetwork) {
		this.closeNetwork = closeNetwork;
	}

	/**
	 * Gets the 'closeNetwork' attribute
	 * 
	 * @author Bulut , Taner , 5298261
	 * @return the connection state of the Client
	 * 
	 */
	public boolean getCloseNetwork() {
		return this.closeNetwork;
	}

	/**
	 * Sets the 'connectedState1' attribute
	 * 
	 * @author Bulut , Taner , 5298261
	 * @param connectedState1
	 *            the boolean variable sets the connection state of the Client
	 *            for the first Connection-Check via PingCheck
	 * 
	 */
	public void setConnectedState1(boolean connectedState1) {
		this.connectedState1 = connectedState1;
	}

	/**
	 * Gets the 'connectedState1' attribute
	 * 
	 * @author Bulut , Taner , 5298261
	 * @return the connection state of the Client regarding the first
	 *         Connection-Check via PingCheck
	 * 
	 */
	public boolean getConnectedState1() {
		return this.connectedState1;
	}

	/**
	 * Sets the 'connectedState2' attribute
	 * 
	 * @author Bulut , Taner , 5298261
	 * @param connectedState2
	 *            the boolean variable sets the connection state of the Client
	 *            for the second Connection-Check via PingCheck
	 * 
	 */
	public void setConnectedState2(boolean connectedState2) {
		this.connectedState2 = connectedState2;
	}

	/**
	 * Gets the 'connectedState2' attribute
	 * 
	 * @author Bulut , Taner , 5298261
	 * @return the connection state of the Client regarding the second
	 *         Connection-Check via PingCheck
	 * 
	 */
	public boolean getConnectedState2() {
		return this.connectedState2;
	}

	/**
	 * Gets the 'server' attribute
	 * 
	 * @author Bulut , Taner , 5298261
	 * @return the Socket for Client-Side
	 * 
	 */
	public Socket getServer() {
		return this.server;
	}

	/**
	 * Sets the 'server' attribute
	 * 
	 * @author Bulut , Taner , 5298261
	 * @param server
	 *            defining the Socket for the Client-Side
	 * 
	 */
	public void setServer(Socket server) {
		this.server = server;
	}

}