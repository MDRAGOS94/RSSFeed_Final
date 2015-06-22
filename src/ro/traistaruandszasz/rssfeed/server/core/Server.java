package ro.traistaruandszasz.rssfeed.server.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import ro.traistaruandszasz.rssfeed.server.gui.ServerGUI;


public class Server {
    private static final int LISTENING_PORT = 2002;
    private static ServerSocket serverSocket;

    public static void main(String[] args) throws IOException {
	// Open server socket for listening.
	try {
	    serverSocket = new ServerSocket(LISTENING_PORT);
	    System.out.println("Server started on port " + LISTENING_PORT);
	} catch (IOException exception) {
	    System.err.println("Can not start listening on port "
		    + LISTENING_PORT);
	    exception.printStackTrace();
	    System.exit(-1);
	}

	ServerGUI GUI = ServerGUI.getInstance();
	((Thread) GUI).setDaemon(true);
	((Thread) GUI).start();

	// Start ServerDispatcher thread.
	ServerCore serverCore = new ServerCore(GUI);
	((Thread) serverCore).setDaemon(true);
	((Thread) serverCore).start();
	while (GUI.getServerGuiFrame() == null) {
	    try {
		TimeUnit.SECONDS.sleep(2);
	    } catch (InterruptedException exception) {
		exception.printStackTrace();
	    }
	}

	// Accept and handle client connections.
	while (GUI.getServerGuiFrame().isVisible())
	    try {
		Socket socket = serverSocket.accept();
		ClientInfo clientInfo = new ClientInfo();
		clientInfo.setSocket(socket);
		ClientListener clientListener = new ClientListener(clientInfo,
			serverCore);
		ClientSender clientSender = new ClientSender(clientInfo,
			serverCore);
		clientInfo.setClientListener(clientListener);
		clientInfo.setClientSender(clientSender);
		clientInfo.setClientId(ClientInfo.nextClientIndex++);
		clientListener.setDaemon(true);
		clientListener.start();
		clientSender.setDaemon(true);
		clientSender.start();
		serverCore.addClient(clientInfo);
	    } catch (IOException exception) {
		exception.printStackTrace();
	    }

	System.out.println("Exiting.");
    }

}
