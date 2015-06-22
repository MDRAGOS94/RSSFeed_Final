package ro.traistaruandszasz.rssfeed.database.operations;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import ro.traistaruandszasz.rssfeed.database.configuration.HibernateUtil;
import ro.traistaruandszasz.rssfeed.database.model.Comment;
import ro.traistaruandszasz.rssfeed.database.model.Friendship;
import ro.traistaruandszasz.rssfeed.database.model.News;
import ro.traistaruandszasz.rssfeed.database.model.User;

public class CommentOperation {
    private static Session session;

    public static int getIdComment(int idFriend, int idNews, Date date) {
	try {
	    session = HibernateUtil.getSessionFactory().openSession();

	    Calendar myCal = new GregorianCalendar();
	    myCal.setTime(date);

	    Calendar calendar = Calendar.getInstance();
	    calendar.set(Calendar.HOUR_OF_DAY, myCal.get(Calendar.HOUR_OF_DAY));
	    calendar.set(Calendar.MINUTE, myCal.get(Calendar.MINUTE));
	    calendar.set(Calendar.SECOND, myCal.get(Calendar.SECOND));
	    calendar.add(Calendar.SECOND, -1);
	    Date fromDate = calendar.getTime();

	    calendar.set(Calendar.HOUR_OF_DAY, myCal.get(Calendar.HOUR_OF_DAY));
	    calendar.set(Calendar.MINUTE, myCal.get(Calendar.MINUTE));
	    calendar.set(Calendar.SECOND, myCal.get(Calendar.SECOND));
	    calendar.add(Calendar.SECOND, 1);
	    Date toDate = calendar.getTime();

	    Criteria criteria = session.createCriteria(Comment.class);
	    Comment comment = (Comment) criteria
		    .add(Restrictions.eq("id_friend_FKcomment", idFriend))
		    .add(Restrictions.eq("id_news_FKcomment", idNews))
		    .add(Restrictions.between("date", fromDate, toDate))
		    .uniqueResult();

	    return comment.getIdcomment();
	} catch (HibernateException hibernateException) {
	    hibernateException.printStackTrace();

	    return 0;
	} finally {
	    session.close();
	}
    }

    public static boolean insertComment(int idFriend, int idNews, Date date,
	    String text) {
	try {
	    session = HibernateUtil.getSessionFactory().openSession();

	    Comment comment = new Comment();
	    comment.setId_friend_FKcomment(idFriend);
	    comment.setId_news_FKcomment(idNews);
	    comment.setDate(date);
	    comment.setText(text);

	    Friendship friendship = (Friendship) session.get(Friendship.class,
		    102);
	    comment.setFriendship(friendship);
	    // comment.setNews(news);

	    session = HibernateUtil.getSessionFactory().openSession();
	    session.beginTransaction();
	    session.save(comment);
	    session.getTransaction().commit();

	    return true;
	} catch (HibernateException hibernateException) {
	    hibernateException.printStackTrace();

	    return false;
	} finally {
	    session.close();
	}
    }

    public static boolean addNewCommentToNews(int idNews, int idFriend,
	    Date date, String text) {
	try {
	    session = HibernateUtil.getSessionFactory().openSession();

	    News news = (News) session.get(News.class, idNews);

	    Comment comment = new Comment();
	    comment.setId_friend_FKcomment(idFriend);
	    comment.setDate(date);
	    comment.setText(text);

	    news.addComment(comment);
	    comment.setNews(news);

	    session.beginTransaction();
	    session.update(news);
	    session.getTransaction().commit();

	    return true;
	} catch (HibernateException hibernateException) {
	    hibernateException.printStackTrace();

	    return false;
	} finally {
	    session.close();
	}
    }

    public static boolean insertCommentFinal(int idFriend, int idNews,
	    Date date, String text) {
	try {
	    session = HibernateUtil.getSessionFactory().openSession();
	    session.beginTransaction();

	    Query query = session
		    .createSQLQuery("INSERT INTO comment (id_friend_FKcomment, id_news_FKcomment, date, text) "
			    + "VALUES (:id_friend_FKcomment, :id_news_FKcomment, :date, :text)");
	    query.setParameter("id_friend_FKcomment", idFriend);
	    query.setParameter("id_news_FKcomment", idNews);
	    query.setParameter("date", date);
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

    public static boolean addNewCommentToFriendship(int idFriendFromFriendship,
	    int idNews, Date date, String text) {
	try {
	    session = HibernateUtil.getSessionFactory().openSession();

	    Friendship friendship = (Friendship) session.get(Friendship.class,
		    idFriendFromFriendship);
	    News news = (News) session.get(News.class, idNews);

	    Comment comment = new Comment();
	    comment.setId_news_FKcomment(idNews);
	    comment.setDate(date);
	    comment.setText(text);

	    friendship.addComment(comment);
	    comment.setNews(news);

	    session.beginTransaction();
	    session.update(friendship);
	    session.getTransaction().commit();

	    return true;
	} catch (HibernateException hibernateException) {
	    hibernateException.printStackTrace();

	    return false;
	} finally {
	    session.close();
	}
    }

    public static boolean updateTextComment(int idComment, String text) {
	try {
	    session = HibernateUtil.getSessionFactory().openSession();
	    session.beginTransaction();

	    Comment comment = (Comment) session.get(Comment.class, idComment);
	    comment.setText(text);

	    session.update(comment);
	    session.getTransaction().commit();

	    return true;
	} catch (HibernateException hibernateException) {
	    hibernateException.printStackTrace();

	    return false;
	} finally {
	    session.close();
	}
    }

    public static boolean deleteComment(int idComment) {
	try {
	    session = HibernateUtil.getSessionFactory().openSession();

	    Comment comment = (Comment) session.get(Comment.class, idComment);

	    session.beginTransaction();
	    session.delete(comment);
	    session.getTransaction().commit();

	    return true;
	} catch (HibernateException hibernateException) {
	    hibernateException.printStackTrace();

	    return false;
	}
    }

    public static List<String> getAllCommentsForNews(int idNews) {
	try {
	    session = HibernateUtil.getSessionFactory().openSession();

	    Criteria criteria = session.createCriteria(Comment.class);
	    @SuppressWarnings("unchecked")
	    List<Comment> listOfComments = (List<Comment>) criteria
		    .createAlias("news", "newsTable")
		    .add(Restrictions.eq("newsTable.idnews", idNews)).list();

	    List<String> listOfStrings = new ArrayList<String>();
	    for (int i = listOfComments.size() - 1; i >= 0; i--) {
		User user = (User) session.get(User.class, listOfComments
			.get(i).getFriendship().getIdfriendship());

		listOfStrings.add(new String(listOfComments.get(i)
			.getIdcomment()
			+ "#"
			+ listOfComments.get(i).getFriendship()
				.getIdfriendship()
			+ "#"
			+ listOfComments.get(i).getNews().getIdnews()
			+ "#"
			+ listOfComments.get(i).getDate()
			+ "#"
			+ listOfComments.get(i).getText()
			+ "#"
			+ user.getName()));

	    }

	    for (int i = 0; i < listOfStrings.size(); i++) {
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

    public static void main(String[] args) throws ParseException {
	// CommentOperation.getAllCommentsForNews(1 );
	//insertCommentFinal(41, 79, new Date(), "DSADSADASDSA");
    }

}
