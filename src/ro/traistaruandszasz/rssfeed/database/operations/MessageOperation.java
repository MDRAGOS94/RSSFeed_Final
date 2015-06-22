package ro.traistaruandszasz.rssfeed.database.operations;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import ro.traistaruandszasz.rssfeed.database.configuration.HibernateUtil;
import ro.traistaruandszasz.rssfeed.database.model.Category;
import ro.traistaruandszasz.rssfeed.database.model.Message;

public class MessageOperation {
    private static Session session;
    
    public static boolean insertMessage(String text) {
	try {
	    session = HibernateUtil.getSessionFactory().openSession();
	    session.beginTransaction();

	    Query query = session
		    .createSQLQuery("INSERT INTO message (text) "
			    + "VALUES (:text)");
	    query.setParameter("text", text);
	    query.executeUpdate();

	    session.getTransaction().commit();
	    
	    return true;
	} catch (Exception e) {
	    e.printStackTrace();
	    return false;
	} finally {
	    session.close();
	}
    }
    
    public static List<String> getAllMessages() {
	try {
	    session = HibernateUtil.getSessionFactory().openSession();

	    String sql = "SELECT * FROM message";
	    SQLQuery query = session.createSQLQuery(sql);
	    query.addEntity(Message.class);
	    List<Message> results = query.list();

	    List<String> listOfString = new ArrayList<String>();
	    for (int i = 0; i < results.size(); i++) {
		listOfString.add(new String(results.get(i).getIdmessage() + "#" + results.get(i).getText()));
		System.out.println(listOfString.get(i));
	    }
	    
	    return listOfString;
	} catch (Exception e) {
	    e.printStackTrace();
	    return null;
	} finally {
	    session.close();
	}
    }
    
    public static void main(String[] args) {
	//getAllMessages();
	

    }

}
