package ro.traistaruandszasz.rssfeed.database.operations;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import ro.traistaruandszasz.rssfeed.database.configuration.HibernateUtil;
import ro.traistaruandszasz.rssfeed.database.model.Resource;
import ro.traistaruandszasz.rssfeed.database.model.User;

public class ResourceOperation {
    private static Session session;
    
    public static int getIdResource(int idUser, String url) {
	try {
	    session = HibernateUtil.getSessionFactory().openSession();
	    
	    Criteria criteria = session.createCriteria(Resource.class);
	    Resource resource = (Resource) criteria
		    .createAlias("user", "userTable")
		    .add(Restrictions.eq("userTable.iduser", idUser))
		    .add(Restrictions.eq("url", url))
                    .uniqueResult();
	    
	    return resource.getIdresource();
	} catch (HibernateException hibernateException) {
	    hibernateException.printStackTrace();

	    return 0;
	} finally {
	    session.close();
	}
    }
    
    public static boolean addNewResourceToUser(int idUser, String url,
	    String description) {
	try {
	    session = HibernateUtil.getSessionFactory().openSession();

	    Resource resource = new Resource();
	    resource.setUrl(url);
	    resource.setDescription(description);

	    User user = (User) session.get(User.class, idUser);
	    user.addResource(resource);

	    session.beginTransaction();
	    session.save(user);
	    session.getTransaction().commit();

	    return true;
	} catch (HibernateException hibernateException) {
	    hibernateException.printStackTrace();

	    return false;
	} finally {
	    session.close();
	}
    }

    public static boolean deleteResource(int idResource) {
	try {
	    session = HibernateUtil.getSessionFactory().openSession();

	    Resource resource = (Resource) session.get(Resource.class,
		    idResource);

	    session.beginTransaction();
	    session.delete(resource);
	    session.getTransaction().commit();

	    return true;
	} catch (HibernateException hibernateException) {
	    hibernateException.printStackTrace();

	    return false;
	}
    }

    public static void main(String[] args) {
    }

}
