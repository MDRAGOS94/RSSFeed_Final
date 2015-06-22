package ro.traistaruandszasz.rssfeed.server.core;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import ro.traistaruandszasz.rssfeed.socket.handle.SocketMessage;

public class ClientListener extends Thread {
    private ServerCore serverCore;
    private ClientInfo clientInfo;
    private ObjectInputStream objectInputStream;

    public ClientListener(ClientInfo clientInfo, ServerCore serverCore)
	    throws IOException {
	this.clientInfo = clientInfo;
	this.serverCore = serverCore;
	Socket socket = clientInfo.getSocket();
	this.objectInputStream = new ObjectInputStream(socket.getInputStream());
    }

    /**
     * Until interrupted, reads messages from the client socket, forwards them
     * to the server core's queue and notifies the server core.
     */
    public void run() {
	try {
	    while (!isInterrupted()) {
		SocketMessage socketMessage = (SocketMessage) objectInputStream
			.readObject();
		if (socketMessage == null) {
		    break;
		}
		serverCore.dispatchMessage(clientInfo, socketMessage);
	    }	    
	} catch (IOException exception) {
	    // Communication is broken. Interrupt both listener and sender
	    // threads.
	    clientInfo.getClientSender().interrupt();
	    serverCore.deleteClient(clientInfo);
	    
	    System.out.println("Socket received from: "
		    + clientInfo.getSocket().getInetAddress()
		    + " is invalid (communication interrupted) !");
	} catch (ClassNotFoundException exception) {
	    System.out.println("Class not found !");
	}
    }

}
