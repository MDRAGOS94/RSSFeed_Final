package ro.traistaruandszasz.rssfeed.server.api;

public interface ServerDispatcher {
    public void addClient();

    public void deleteClient();

    public void dispatchMessage();

    public void sendMessageToAllClients();

    public void sendMessageToClient();

    public void run();
    
}
