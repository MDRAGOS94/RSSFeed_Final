package ro.traistaruandszasz.rssfeed.server.core;

import java.net.Socket;
import java.util.Iterator;
import java.util.Vector;

import ro.traistaruandszasz.rssfeed.server.gui.ServerGUI;
import ro.traistaruandszasz.rssfeed.socket.handle.ProcessMessageFromClient;
import ro.traistaruandszasz.rssfeed.socket.handle.SocketMessage;


public class ServerCore extends Thread {
    private static Vector<SocketMessage> listOfSocketMessages = new Vector<>();
    private static Vector<ClientInfo> listOfClients = new Vector<>();
    private static ServerGUI serverGUI;
    private ProcessMessageFromClient processMessageFromClient;

    public ServerCore(ServerGUI serverGUI) {
	this.serverGUI = serverGUI;
	this.processMessageFromClient = new ProcessMessageFromClient();
    }

    private ClientInfo getClientFromID(int ID) {
	
	 Iterator<ClientInfo> it = listOfClients.iterator();

	 while(it.hasNext()) {
	     ClientInfo currentClient = it.next();
	      if(currentClient.getClientId() == ID)
		 return currentClient;
	}
	 return null;
    }
    
    /**
     * Adds given client to the server's client list.
     */
    public synchronized void addClient(ClientInfo clientInfo) {
	listOfClients.add(clientInfo);
	serverGUI.getServerGuiFrame().updateClientsCount(listOfClients.size());
	serverGUI.getServerGuiFrame().updateMessages("New client connected from: "
		+ clientInfo.getSocket().getInetAddress().getHostAddress());
    }

    /**
     * Deletes given client from the server's client list if the client is in
     * the list.
     */
    public synchronized void deleteClient(ClientInfo clientInfo) {
	int clientIndex = listOfClients.indexOf(clientInfo);
	if (clientIndex != -1) {
	    listOfClients.removeElementAt(clientIndex);
	    serverGUI.getServerGuiFrame().updateClientsCount(listOfClients.size());
	    serverGUI.getServerGuiFrame().updateMessages("Client connected from: "
		    + clientInfo.getSocket().getInetAddress().getHostAddress()
		    + " was disconnected");
	}
    }

    /**
     * Adds given message to the dispatcher's message queue and notifies this
     * thread to wake up the message queue reader (getNextMessageFromQueue
     * method). dispatchMessage method is called by other threads
     * (ClientListener) when a message is arrived.
     */
    public synchronized void dispatchMessage(ClientInfo clientInfo,
	    SocketMessage socketMessage) {
	Socket socket = clientInfo.getSocket();
	String senderIP = socket.getInetAddress().getHostAddress();
	int senderId = clientInfo.getClientId();
	socketMessage.setClientId(senderId);
	listOfSocketMessages.add(socketMessage);
	serverGUI.getServerGuiFrame().updateMessages("New message arrived from :"
		+ senderIP);
	notify();
    }

    /**
     * @return returns and deletes the next message from the message queue. If
     *         there is no messages in the queue, falls in sleep until notified
     *         by dispatchMessage method.
     */
    private synchronized SocketMessage getNextMessageFromQueue()
	    throws InterruptedException {
	while (listOfSocketMessages.size() == 0) {
	    wait();
	}
	SocketMessage socketMessage = (SocketMessage) listOfSocketMessages.get(0);
	listOfSocketMessages.removeElementAt(0);
	
	return socketMessage;
    }

    /**
     * Sends given message to all clients in the client list. Actually the
     * message is added to the client sender thread's message queue and this
     * client sender thread is notified.
     */
    public static synchronized void sendMessageToAllClients(SocketMessage aMessage) {
	for (int i = 0; i < listOfClients.size(); i++) {
	    ClientInfo clientInfo = (ClientInfo) listOfClients.get(i);
	    clientInfo.getClientSender().sendMessage(aMessage);
	    serverGUI.getServerGuiFrame().updateMessages("Responding to :"
		    + clientInfo.getSocket().getInetAddress().getHostAddress());
	}
    }

    /**
     * Sends given message to the client. Actually the message is added to the
     * client sender thread's message queue and this client sender thread is
     * notified.
     */
    private synchronized void sendMessageToClient(int clientIndex,
	    SocketMessage aMessage) {
	ClientInfo clientInfo = getClientFromID(clientIndex);
	if(clientInfo == null) 
	     return;
	clientInfo.getClientSender().sendMessage(aMessage);
	serverGUI.getServerGuiFrame().updateMessages("Responding to :"
		+ clientInfo.getSocket().getInetAddress().getHostAddress());
    }

    /**
     * Infinitely reads messages from the queue and dispatch them to all clients
     * connected to the server.
     */
    public void run() {
	try {
	    while (true) {
		SocketMessage socketMessageToServer = getNextMessageFromQueue();
		SocketMessage socketMessageToClient = processMessageFromClient
			.processNextMesageFromServerCoreMessageQueue(socketMessageToServer);
		
		sendMessageBackToClient(socketMessageToClient);
	    }
	} catch (InterruptedException exception) {
	    System.out.println("Thread interrupted. Stop its execution.");
	}
    }

    private void sendMessageBackToClient(SocketMessage processedMessage) {
	int clientId = processedMessage.getClientId();
	if (clientId != -1) {
	    sendMessageToClient(clientId, processedMessage);
	} else {
	    sendMessageToAllClients(processedMessage);
	}
    }

}
