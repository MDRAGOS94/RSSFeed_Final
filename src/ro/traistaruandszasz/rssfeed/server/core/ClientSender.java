package ro.traistaruandszasz.rssfeed.server.core;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import ro.traistaruandszasz.rssfeed.socket.handle.SocketMessage;

public class ClientSender extends Thread {
    private Vector<SocketMessage> listOfSocketMessages = new Vector<>();
    private ServerCore serverCore;
    private ClientInfo clientInfo;
    private ObjectOutputStream objectOutputStream;

    public ClientSender(ClientInfo clientInfo, ServerCore serverCore)
	    throws IOException {
	this.clientInfo = clientInfo;
	this.serverCore = serverCore;
	Socket socket = clientInfo.getSocket();
	objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
    }

    /**
     * Adds given message to the message queue and notifies this thread
     * (actually getNextMessageFromQueue method) that a message is arrived.
     * sendMessage is called by other threads (ServeDispatcher).
     */
    public synchronized void sendMessage(SocketMessage socketMessage) {
	listOfSocketMessages.add(socketMessage);
	notify();
    }

    /**
     * @return and deletes the next message from the message queue. If the queue
     *         is empty, falls in sleep until notified for message arrival by
     *         sendMessage method.
     */
    private synchronized SocketMessage getNextMessageFromQueue()
	    throws InterruptedException {
	while (listOfSocketMessages.size() == 0) {
	    wait();
	}
	SocketMessage socketMessage = (SocketMessage) listOfSocketMessages
		.get(0);
	listOfSocketMessages.removeElementAt(0);

	return socketMessage;
    }

    /**
     * Sends given message to the client's socket.
     */
    private void sendMessageToClient(SocketMessage aMessage) {
	try {
	    objectOutputStream.writeObject(aMessage);
	    objectOutputStream.flush();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Until interrupted, reads messages from the message queue and sends them
     * to the client's socket.
     */
    public void run() {
	try {
	    while (!isInterrupted()) {
		SocketMessage socketMessage = getNextMessageFromQueue();
		sendMessageToClient(socketMessage);
	    }
	} catch (Exception exception) {
	    // Communication is broken. Interrupt both listener and sender
	    // threads
	    clientInfo.getClientListener().interrupt();
	    serverCore.deleteClient(clientInfo);
	    System.out
		    .println("A communication error occured, connection closed! ");
	}
    }

}
