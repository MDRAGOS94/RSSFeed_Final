package ro.traistaruandszasz.rssfeed.database.operations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import ro.traistaruandszasz.rssfeed.database.configuration.HibernateUtil;
import ro.traistaruandszasz.rssfeed.database.model.Category;
import ro.traistaruandszasz.rssfeed.database.model.Keyword;
import ro.traistaruandszasz.rssfeed.database.model.News;
import ro.traistaruandszasz.rssfeed.database.model.User;

public class KeywordOperation {
    private static Session session;
    
    public static int getIdKeyword(String name) {
	try {
	    session = HibernateUtil.getSessionFactory().openSession();
	    
	    Criteria criteria = session.createCriteria(Keyword.class);
	    Keyword keyword = (Keyword) criteria
		    .add(Restrictions.eq("name", name))
                    .uniqueResult();
	    
	    return keyword.getIdkeyword();
	} catch (HibernateException hibernateException) {
	    hibernateException.printStackTrace();

	    return 0;
	} finally {
	    session.close();
	}
    }
    
    public static boolean insertKeyword(String name) {
	try {
	    Keyword keyword = new Keyword();
	    keyword.setName(name);

	    session = HibernateUtil.getSessionFactory().openSession();
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
    
    public static boolean updateKeyword(int idKeyword, String name) {
	try {
	    session = HibernateUtil.getSessionFactory().openSession();
	    
	    Keyword keyword = (Keyword) session.get(Keyword.class, idKeyword);
	    keyword.setName(name);

	    session.beginTransaction();
	    session.update(keyword);
	    session.getTransaction().commit();

	    return true;
	} catch (HibernateException hibernateException) {
	    hibernateException.printStackTrace();

	    return false;
	} finally {
	    session.close();
	}
    }
    
    public static boolean addNewKeywordToCategoryAndKeywordCategory(int idCategory, String name) {
	try {
	    session = HibernateUtil.getSessionFactory().openSession();
	    
	    Keyword keyword = new Keyword();
	    keyword.setName(name);
	    Category category = (Category) session.get(Category.class, idCategory);
	    keyword.getCategories().add(category);
	        
	    Criteria criteria = session.createCriteria(Keyword.class);
	    @SuppressWarnings("unchecked")
	    List<Keyword> listOfAllKeyword = (List<Keyword>) criteria.list();
	    if (listOfAllKeyword.size() > 0)
		keyword.setIdkeyword(listOfAllKeyword.get(listOfAllKeyword.size() - 1).getIdkeyword() + 1);
	    else 
		keyword.setIdkeyword(1);
	    
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
    
    public static List<String> getAllKeywordForCategory(int idCategory) {
	try {
	    session = HibernateUtil.getSessionFactory().openSession();
	    Category category = (Category) session.get(Category.class, idCategory);
	    
	    List<Keyword> listOfKeywords = category.getKeywords();
	    
	    List<String> listOfStrings = new ArrayList<String>();
	    for (int i = 0; i < listOfKeywords.size(); i++) {
		listOfStrings.add(listOfKeywords.get(i).getName());
	    }
	    
	    return listOfStrings;
	} catch (HibernateException hibernateException) {
	    hibernateException.printStackTrace();

	    return null;
	} finally {
	    session.close();
	}
    }
    
    public static void main(String[] args) {
	//addNewKeywordToCategoryAndKeywordCategory(2, "bani");
	
	//getAllKeywordForCategory(2);
    }
    
}
