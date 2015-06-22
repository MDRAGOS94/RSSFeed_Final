package ro.traistaruandszasz.rssfeed.database.operations;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import ro.traistaruandszasz.rssfeed.database.configuration.HibernateUtil;
import ro.traistaruandszasz.rssfeed.database.model.Category;
import ro.traistaruandszasz.rssfeed.database.model.News;
import ro.traistaruandszasz.rssfeed.database.model.User;

public class UserOperation {
    private static Session session;

    public static int getIdUser(String name) {
	try {
	    session = HibernateUtil.getSessionFactory().openSession();

	    Criteria criteria = session.createCriteria(User.class);
	    User user = (User) criteria.add(Restrictions.eq("name", name))
		    .uniqueResult();

	    return user.getIduser();
	} catch (HibernateException hibernateException) {
	    hibernateException.printStackTrace();

	    return 0;
	} finally {
	    session.close();
	}
    }

    public static boolean insertUser(String name, String password) {
	try {
	    User user = new User();
	    user.setName(name);
	    user.setPassword(password);

	    session = HibernateUtil.getSessionFactory().openSession();
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

    public static boolean updatePasswordUser(int idUser, String password) {
	try {
	    session = HibernateUtil.getSessionFactory().openSession();

	    User user = (User) session.get(User.class, idUser);
	    user.setPassword(password);

	    session.beginTransaction();
	    session.update(user);
	    session.getTransaction().commit();

	    return true;
	} catch (HibernateException hibernateException) {
	    hibernateException.printStackTrace();

	    return false;
	} finally {
	    session.close();
	}
    }

    public static boolean updateNameUser(int idUser, String name) {
	try {
	    session = HibernateUtil.getSessionFactory().openSession();

	    User user = (User) session.get(User.class, idUser);
	    user.setName(name);

	    session.beginTransaction();
	    session.update(user);
	    session.getTransaction().commit();

	    return true;
	} catch (HibernateException hibernateException) {
	    hibernateException.printStackTrace();

	    return false;
	} finally {
	    session.close();
	}
    }

    public static boolean userAlreadyExists(String name, String password) {
	try {
	    session = HibernateUtil.getSessionFactory().openSession();

	    Criteria criteria = session.createCriteria(User.class);
	    User user = (User) criteria.add(Restrictions.eq("name", name))
		    .add(Restrictions.eq("password", password)).uniqueResult();

	    if (user == null) {
		return false;
	    } else {
		return true;
	    }
	} catch (HibernateException hibernateException) {
	    hibernateException.printStackTrace();

	    return false;
	} finally {
	    session.close();
	}
    }

    public static List<String> getAllNewsForUser(int idUser) {
	try {
	    session = HibernateUtil.getSessionFactory().openSession();

	    Criteria criteria = session.createCriteria(News.class);
	    @SuppressWarnings("unchecked")
	    List<News> listOfNews = (List<News>) criteria
		    .createAlias("user", "userTable")
		    .add(Restrictions.eq("userTable.iduser", idUser)).list();

	    List<String> listOfStrings = new ArrayList<String>();
	    for (int i = 0; i < listOfNews.size(); i++) {
		String sql = "SELECT id_category_FKnewscategory FROM newscategory WHERE id_news_FKnewscategory ='"
			+ listOfNews.get(i).getIdnews() + "'";
		SQLQuery query = session.createSQLQuery(sql);
		List results = query.list();
		String resultCategory = "null";
		Category category = null;
		if (results.size() > 0) {
		    category = (Category) session.get(Category.class,
			    (int) results.get(0));
		    resultCategory = category.getName();
		}

		listOfStrings
			.add(new String(listOfNews.get(i).getIdnews() + "#"
				+ listOfNews.get(i).getId_user_FKnews() + "#"
				+ listOfNews.get(i).getTitle() + "#"
				+ listOfNews.get(i).getDate() + "#"
				+ listOfNews.get(i).getDescription() + "#"
				+ listOfNews.get(i).getSource() + "#"
				+ resultCategory));
		System.out.println(listOfStrings.get(i));
	    }

	    return listOfStrings;
	} catch (HibernateException hibernateException) {
	    hibernateException.printStackTrace();

	    return null;
	} finally {
	    session.close();
	}
    }

    public static List<String> getAllUsers() {
	try {
	    session = HibernateUtil.getSessionFactory().openSession();

	    Criteria criteria = session.createCriteria(User.class);
	    @SuppressWarnings("unchecked")
	    List<User> listOfAllUsers = (List<User>) criteria.list();

	    List<String> listOfStrings = new ArrayList<String>();
	    for (int i = 0; i < listOfAllUsers.size(); i++) {
		User user = (User) session.get(User.class, listOfAllUsers
			.get(i).getIduser());
		listOfStrings.add(new String(listOfAllUsers.get(i).getIduser()
			+ "#" + user.getName()));
		System.out.println(listOfStrings.get(i));
	    }

	    return listOfStrings;
	} catch (HibernateException hibernateException) {
	    hibernateException.printStackTrace();

	    return null;
	} finally {
	    session.close();
	}
    }

    public static String getUserNameById(int idUser) {
	try {
	    session = HibernateUtil.getSessionFactory().openSession();

	    User user = (User) session.get(User.class, idUser);

	    return user.getName();
	} catch (HibernateException hibernateException) {
	    hibernateException.printStackTrace();

	    return null;
	} finally {
	    session.close();
	}
    }

    public static void main(String[] args) {
	getAllUsers();
    }

}
