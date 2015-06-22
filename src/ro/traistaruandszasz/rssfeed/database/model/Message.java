package ro.traistaruandszasz.rssfeed.database.model;

public class Message {
    private int idmessage;
    
    private String text;

    public int getIdmessage() {
	return idmessage;
    }

    public void setIdmessage(int idmessage) {
	this.idmessage = idmessage;
    }

    public String getText() {
	return text;
    }

    public void setText(String text) {
	this.text = text;
    }
}
