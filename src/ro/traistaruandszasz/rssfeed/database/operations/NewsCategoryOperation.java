package ro.traistaruandszasz.rssfeed.database.operations;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import ro.traistaruandszasz.rssfeed.database.configuration.HibernateUtil;
import ro.traistaruandszasz.rssfeed.database.model.Category;
import ro.traistaruandszasz.rssfeed.database.model.News;

public class NewsCategoryOperation {
    private static Session session;
    
    public static boolean addCategoryToNews(int idNews, int idCategory) {
	try {
	    session = HibernateUtil.getSessionFactory().openSession();
	    session.beginTransaction();

	    News news = (News) session.get(News.class, idNews);
	    Category category = (Category) session.get(Category.class,
		    idCategory);
	    news.addCategory(category);

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

    public static boolean addNewsToCategory(int idCategory, int idNews) {
	try {
	    session = HibernateUtil.getSessionFactory().openSession();
	    session.beginTransaction();

	    Category category = (Category) session.get(Category.class,
		    idCategory);
	    News news = (News) session.get(News.class, idNews);
	    category.addNews(news);

	    session.save(category);
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
    }

}
