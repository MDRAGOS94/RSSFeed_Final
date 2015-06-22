package ro.traistaruandszasz.rssfeed.database.operations;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import ro.traistaruandszasz.rssfeed.database.configuration.HibernateUtil;
import ro.traistaruandszasz.rssfeed.database.model.Category;
import ro.traistaruandszasz.rssfeed.database.model.News;
import ro.traistaruandszasz.rssfeed.database.model.User;

public class NewsOperation {
    private static Session session;
    
    public static int getIdNews(int idUser, String title) {
	try {
	    session = HibernateUtil.getSessionFactory().openSession();
	    
	    Criteria criteria = session.createCriteria(News.class);
	    News resource = (News) criteria
		    .createAlias("user", "userTable")
		    .add(Restrictions.eq("userTable.iduser", idUser))
		    .add(Restrictions.eq("title", title))
                    .uniqueResult();
	    
	    return resource.getIdnews();
	} catch (HibernateException hibernateException) {
	    hibernateException.printStackTrace();

	    return 0;
	} finally {
	    session.close();
	}
    }
    
    public static boolean addNewNewsToUser(int idUser, String title, Date date,
	    String description, String source) {
	try {
	    session = HibernateUtil.getSessionFactory().openSession();
	    
	    News news = new News();
	    news.setId_user_FKnews(idUser);
	    news.setTitle(title);
	    news.setDate(date);
	    news.setDescription(description);
	    news.setSource(source);

	    User user = (User) session.get(User.class, idUser);
	    user.addNews(news);
	    
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

    public static boolean addNewNewsToUserAndNewsCategory(int idUser, String title, Date date, String description, String source, int idCategory) {
	try {
	    session = HibernateUtil.getSessionFactory().openSession();
	    
	    News news = new News();
	    User user = (User) session.get(User.class, idUser);
	    news.setUser(user);
	    
	    Category category = (Category) session.get(Category.class, idCategory);
	    news.getCategories().add(category);
	   
	    news.setTitle(title);
	    news.setDate(date);
	    news.setDescription(description);
	    news.setSource(source);
	    
	    Criteria criteria = session.createCriteria(News.class);
	    @SuppressWarnings("unchecked")
	    List<News> listOfAllNews = (List<News>) criteria.list();
	    
	    news.setIdnews(listOfAllNews.get(listOfAllNews.size() - 1).getIdnews() + 1);
	    
	    session.beginTransaction();
	    session.save(news);
	    session.getTransaction().commit();
	   
	    return true;
	} catch (HibernateException hibernateException) {
	    hibernateException.printStackTrace();

	    return false;
	} finally {
	    session.close();
	}
    }
    
    public static void main(String[] args) {
	//addNewNewsToUserAndNewsCategory(83, "STIRE 8 - TITLU", new Date(), "STIRE 8 - DESCRIERE", "URL - STIRE 8", 1);
    }
    
}
