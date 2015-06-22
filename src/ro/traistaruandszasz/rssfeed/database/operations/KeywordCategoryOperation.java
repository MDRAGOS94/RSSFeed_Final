package ro.traistaruandszasz.rssfeed.database.operations;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import ro.traistaruandszasz.rssfeed.database.configuration.HibernateUtil;
import ro.traistaruandszasz.rssfeed.database.model.Category;
import ro.traistaruandszasz.rssfeed.database.model.Keyword;
import ro.traistaruandszasz.rssfeed.database.model.News;

public class KeywordCategoryOperation {
    private static Session session;
    
    public static boolean addCategoryToKeyword(int idKeyword, int idCategory) {
	try {
	    session = HibernateUtil.getSessionFactory().openSession();
	    
	    Keyword keyword = (Keyword) session.get(Keyword.class, idKeyword);
	    Category category = (Category) session.get(Category.class,
		    idCategory);

	    keyword.addCategory(category);
	    
	    session.beginTransaction();
	    session.save(keyword);
	    session.getTransaction().commit();

	    return true;
	} catch (HibernateException hibernateException) {
	    hibernateException.printStackTrace();

	    return false;
	} finally {
	    session.close();
	}
    }

    public static boolean addKeywordToCategory(int idCategory, int idKeyword) {
	try {
	    session = HibernateUtil.getSessionFactory().openSession();
	    
	    Category category = (Category) session.get(Category.class,
		    idCategory);
	    Keyword keyword = (Keyword) session.get(News.class, idKeyword);
	    category.addKeyword(keyword);

	    session.beginTransaction();
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
	KeywordCategoryOperation.addKeywordToCategory(2, 3);
    }

}
