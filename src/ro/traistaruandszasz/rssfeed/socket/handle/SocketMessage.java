package ro.traistaruandszasz.rssfeed.socket.handle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SocketMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    private SocketMessageType socketMessageType;
    private String messageToServer;
    private boolean booleanToServer;
    private int clientId;
    private List<String> listMessageToServer;
    
    public SocketMessage() {
    }

    public SocketMessage(SocketMessageType socketMessageType,
	    String messageToServer) {
	this.socketMessageType = socketMessageType;
	this.messageToServer = messageToServer;
    }
    
    public SocketMessageType getSocketMessageType() {
        return socketMessageType;
    }

    public void setSocketMessageType(SocketMessageType socketMessageType) {
        this.socketMessageType = socketMessageType;
    }

    public String getMessageToServer() {
        return messageToServer;
    }

    public void setMessageToServer(String messageToServer) {
        this.messageToServer = messageToServer;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
    
    public boolean isBooleanToServer() {
        return booleanToServer;
    }

    public void setBooleanToServer(boolean booleanToServer) {
        this.booleanToServer = booleanToServer;
    }

    
    public List<String> getListMessageToServer() {
	return listMessageToServer;
    }

    public void setListMessageToServer(List<String> listMessageToServer) {
	this.listMessageToServer = listMessageToServer;
    }

    


}