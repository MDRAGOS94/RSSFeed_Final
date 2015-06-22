package ro.traistaruandszasz.rssfeed.server.gui;

public class ServerGUI extends Thread {
    private static ServerGUI instance = null;
    private MyFrame serverGuiFrame;

    private ServerGUI() {
    }
    
    public MyFrame getServerGuiFrame() {
	return serverGuiFrame;
    }

    public void setServerGuiFrame(MyFrame serverGuiFrame) {
	this.serverGuiFrame = serverGuiFrame;
    }

    public static ServerGUI getInstance() {
	if (instance == null) {
	    instance = new ServerGUI();
	}

	return instance;
    }

    public void run() {
	setServerGuiFrame(new MyFrame());
    }

}
