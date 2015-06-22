package ro.traistaruandszasz.rssfeed.database.configuration;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    private static SessionFactory buildSessionFactory() {
	try {
	    // Create the SessionFactory from hibernate.cfg.xml
	    Configuration configuration = new Configuration();
	    configuration.configure("/ro/traistaruandszasz/rssfeed/database/configuration/hibernate.cfg.xml");
	    System.out.println("Hibernate Configuration loaded !");

	    ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
		    .applySettings(configuration.getProperties()).build();
	    System.out.println("Hibernate serviceRegistry created !");

	    SessionFactory sessionFactory = configuration
		    .buildSessionFactory(serviceRegistry);

	    return sessionFactory;
	} catch (Throwable throwableException) {
	    System.err.println("Initial SessionFactory creation failed !" + throwableException);
	    throwableException.printStackTrace();
	    throw new ExceptionInInitializerError(throwableException);
	}
    }

    public static SessionFactory getSessionFactory() {
	if (sessionFactory == null) {
	    sessionFactory = buildSessionFactory();
	}
	return sessionFactory;
    }
    
    public static boolean deleteById(Class<?> type, Serializable id) {
	Session session = HibernateUtil.getSessionFactory().openSession();
	
	Object persistentInstance = session.load(type, id);
	if (persistentInstance != null) {
	    session.beginTransaction();
	    session.delete(persistentInstance);
	    session.getTransaction().commit();
	    
	    return true;
	}
	
	return false;
    }

}
