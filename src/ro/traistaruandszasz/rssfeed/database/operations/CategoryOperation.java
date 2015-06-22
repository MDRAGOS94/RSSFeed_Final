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

public class CategoryOperation {
    private static Session session;
    
    public static int getIdCategory(String name) {
	try {
	    session = HibernateUtil.getSessionFactory().openSession();
	    
	    Criteria criteria = session.createCriteria(Category.class);
	    Category category = (Category) criteria
		    .add(Restrictions.eq("name", name))
                    .uniqueResult();
	    
	    return category.getIdcategory();
	} catch (HibernateException hibernateException) {
	    hibernateException.printStackTrace();

	    return 0;
	} finally {
	    session.close();
	}
    }
    
    public static boolean insertCategory(String name, String description) {
	try {
	    Category category = new Category();
	    category.setName(name);
	    category.setDescription(description);

	    session = HibernateUtil.getSessionFactory().openSession();
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

    public static boolean updateNameCategory(int idCategory, String name) {
	try {
	    session = HibernateUtil.getSessionFactory().openSession();
	    
	    Category category = (Category) session.get(Category.class, idCategory);
	    category.setName(name);

	    session.beginTransaction();
	    session.update(category);
	    session.getTransaction().commit();

	    return true;
	} catch (HibernateException hibernateException) {
	    hibernateException.printStackTrace();

	    return false;
	} finally {
	    session.close();
	}
    }
    
    public static boolean updateDescriptionCategory(int idCategory, String description) {
	try {
	    session = HibernateUtil.getSessionFactory().openSession();
	    
	    Category category = (Category) session.get(Category.class, idCategory);
	    category.setDescription(description);

	    session.beginTransaction();
	    session.update(category);
	    session.getTransaction().commit();

	    return true;
	} catch (HibernateException hibernateException) {
	    hibernateException.printStackTrace();

	    return false;
	} finally {
	    session.close();
	}
    }
    
    public static List<String> getAllCategories() {
	try {
	    session = HibernateUtil.getSessionFactory().openSession();

	    Criteria criteria = session.createCriteria(Category.class);
	    @SuppressWarnings("unchecked")
	    List<Category> listOfCategories = (List<Category>) criteria
		    .list();

	    List<String> listOfStrings = new ArrayList<String>();
	    for (int i = 0; i < listOfCategories.size(); i++) {
		listOfStrings.add(new String(listOfCategories.get(i).getIdcategory()
			+ "#" + listOfCategories.get(i).getName()) + "#"
			+ listOfCategories.get(i).getDescription());	
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
    }

}
