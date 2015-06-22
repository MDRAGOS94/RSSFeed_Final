package ro.traistaruandszasz.rssfeed.server.core;

import java.net.Socket;

public class ClientInfo {
    public static int nextClientIndex = 0;
    private int clientId;
    private int clientDataBaseID;
    private Socket socket;
    private ClientListener clientListener;
    private ClientSender clientSender;
    
 /*   public static int getNextClientIndex() {
        return nextClientIndex;
    }
    public static void setNextClientIndex(int nextClientIndex) {
        ClientInfo.nextClientIndex = nextClientIndex;
    }*/
    public int getClientId() {
        return clientId;
    }
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
    public Socket getSocket() {
        return socket;
    }
    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    public ClientListener getClientListener() {
        return clientListener;
    }
    public void setClientListener(ClientListener clientListener) {
        this.clientListener = clientListener;
    }
    public ClientSender getClientSender() {
	return clientSender;
    }
    public void setClientSender(ClientSender clientSender) {
        this.clientSender = clientSender;
    }
    public int getClientDataBaseID() {
	return clientDataBaseID;
    }
    public void setClientDataBaseID(int clientDataBaseID) {
	this.clientDataBaseID = clientDataBaseID;
    }
 }
