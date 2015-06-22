package ro.traistaruandszasz.rssfeed.junit;

import static org.junit.Assert.assertTrue;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ro.traistaruandszasz.rssfeed.database.configuration.HibernateUtil;
import ro.traistaruandszasz.rssfeed.database.operations.UserOperation;

public class ServerSideTesting {
    static Configuration configuration;
    static ServiceRegistry serviceRegistry;
    static Session session;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
	configuration = new Configuration();
	configuration.configure("/ro/traistaruandszasz/rssfeed/database/configuration/hibernate.cfg.xml");
	serviceRegistry = new StandardServiceRegistryBuilder()
	    .applySettings(configuration.getProperties()).build();
	@SuppressWarnings("unused")
	SessionFactory sessionFactory = configuration
		    .buildSessionFactory(serviceRegistry);
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
	session = HibernateUtil.getSessionFactory().openSession();
    }

    @After
    public void tearDown() throws Exception {
	session.close();
    }

    @Test
    @Transactional
    public void testInsertUser() {
	assertTrue(UserOperation.insertUser("GIGEL", "12345"));
    }

}
