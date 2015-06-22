package ro.traistaruandszasz.rssfeed.database.operations;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import ro.traistaruandszasz.rssfeed.database.configuration.HibernateUtil;
import ro.traistaruandszasz.rssfeed.database.model.Friendship;
import ro.traistaruandszasz.rssfeed.database.model.User;

public class FriendshipOperation {
    private static Session session;

    public static int getIdFriendship(int idUser, int idFriend) {
	try {
	    session = HibernateUtil.getSessionFactory().openSession();

	    Criteria criteria = session.createCriteria(Friendship.class);
	    Friendship friendship = (Friendship) criteria
		    .add(Restrictions.eq("user2.iduser", idUser))
		    .add(Restrictions.eq("user1.iduser", idFriend))
		    .uniqueResult();

	    return friendship.getIdfriendship();
	} catch (HibernateException hibernateException) {
	    hibernateException.printStackTrace();

	    return 0;
	} finally {
	    session.close();
	}
    }

    public static boolean insertFriendship(int idUser, int idFriend) {
	try {
	    session = HibernateUtil.getSessionFactory().openSession();

	    User user = (User) session.get(User.class, idUser);
	    User friend = (User) session.get(User.class, idFriend);
	    Friendship friendship = new Friendship();

	    session.beginTransaction();

	    friendship.setUser1(friend);
	    friendship.setUser2(user);
	    
	    session.save(friendship);
	    session.getTransaction().commit();

	    return true;
	} catch (HibernateException hibernateException) {
	    hibernateException.printStackTrace();

	    return false;
	} finally {
	    session.close();
	}
    }
    
    public static boolean instertFriendshipBetweenUserAndFriend(int idUser,
	    int idFriend) {
	try {
	    session = HibernateUtil.getSessionFactory().openSession();

	    User user = (User) session.get(User.class, idUser);
	    User friend = (User) session.get(User.class, idFriend);
	    Friendship friendship = new Friendship();

	    session.beginTransaction();

	    friendship.setUser1(friend);
	    friendship.setUser2(user);
	    session.save(friendship);
	    session.flush();
	    session.clear();

	    friendship.setUser1(user);
	    friendship.setUser2(friend);
	    session.save(friendship);

	    session.getTransaction().commit();

	    return true;
	} catch (HibernateException hibernateException) {
	    hibernateException.printStackTrace();

	    return false;
	} finally {
	    session.close();
	}
    }

    public static List<String> getAllFriendsForUser(int idUser) {
	try {
	    session = HibernateUtil.getSessionFactory().openSession();

	    Criteria criteria = session.createCriteria(Friendship.class);
	    @SuppressWarnings("unchecked")
	    List<Friendship> listOfFriendships = (List<Friendship>) criteria
		    .add(Restrictions.eq("user1.iduser", idUser)).list();

	    List<String> listOfStrings = new ArrayList<String>();
	    for (int i = 0; i < listOfFriendships.size(); i++) {
		User user = (User) session.get(User.class, listOfFriendships
			.get(i).getUser2().getIduser());
		listOfStrings.add(new String(listOfFriendships.get(i)
			.getUser2().getIduser()
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

    public static List<String> getAllFriendRequestsForUser(int idUser) {
	try {
	    session = HibernateUtil.getSessionFactory().openSession();

	    Criteria criteria = session.createCriteria(Friendship.class);
	    @SuppressWarnings("unchecked")
	    List<Friendship> listOfFriendships = (List<Friendship>) criteria
		    .list();

	    List<String> listOfStrings = new ArrayList<String>();

	    for (int j = 0; j < listOfFriendships.size(); j++) {
		if (listOfFriendships.get(j).getUser1().getIduser() == idUser) {
		    int myFriendId = listOfFriendships.get(j).getUser2()
			    .getIduser();

		    boolean ok = true;
		    for (int k = 0; k < listOfFriendships.size(); k++) {
			if (listOfFriendships.get(k).getUser2().getIduser() == idUser
				&& listOfFriendships.get(k).getUser1()
					.getIduser() == myFriendId) {
			    ok = false;
			}
		    }

		    if (ok == true) {
			listOfStrings.add(new String("FRIEND REQUEST FROM : "
				+ myFriendId + " TO : " + idUser));
		    }
		}
	    }

	    return listOfStrings;
	} catch (HibernateException hibernateException) {
	    hibernateException.printStackTrace();

	    return null;
	} finally {
	    session.close();
	}
    }

    public static boolean checkFriendRequestsForUser(int idUser, int idFriend) {
	try {
	    session = HibernateUtil.getSessionFactory().openSession();

	    Criteria criteria = session.createCriteria(Friendship.class);
	    @SuppressWarnings("unchecked")
	    List<Friendship> listOfFriendships = (List<Friendship>) criteria
		    .list();

	    List<String> listOfStrings = new ArrayList<String>();

	    for (int j = 0; j < listOfFriendships.size(); j++) {
		if (listOfFriendships.get(j).getUser1().getIduser() == idUser) {
		    int myFriendId = listOfFriendships.get(j).getUser2()
			    .getIduser();

		    boolean ok = true;
		    for (int k = 0; k < listOfFriendships.size(); k++) {
			if (listOfFriendships.get(k).getUser2().getIduser() == idUser
				&& listOfFriendships.get(k).getUser1()
					.getIduser() == myFriendId) {
			    ok = false;
			}
		    }

		    if (ok == true) {
			listOfStrings.add(new String("FRIEND REQUEST FROM : "
				+ myFriendId + " TO : " + idUser));
			if (idFriend == myFriendId) {
			    return true;
			}
			
		    }
		}
	    }

	    return false;
	} catch (HibernateException hibernateException) {
	    hibernateException.printStackTrace();

	    return false;
	} finally {
	    session.close();
	}
    }

    public static boolean friendshipBetweenUsers(int idUser1, int idUser2) {
	try {
	    session = HibernateUtil.getSessionFactory().openSession();

	    Criteria criteria = session.createCriteria(Friendship.class);
	    @SuppressWarnings("unchecked")
	    List<Friendship> listOfFriendships = (List<Friendship>) criteria.list();

	    int number = 0;
	    for (int i = 0; i < listOfFriendships.size(); i++) {		
		if (listOfFriendships.get(i).getUser1().getIduser() == idUser1 && 
			listOfFriendships.get(i).getUser2().getIduser() == idUser2) {
		    number++;
		}
		if (listOfFriendships.get(i).getUser2().getIduser() == idUser1 && 
			listOfFriendships.get(i).getUser1().getIduser() == idUser2) {
		    number++;
		}
	    }
	    
	    if (number == 2) {
		return true;
	    }
	    
	    return false;
	} catch (HibernateException hibernateException) {
	    hibernateException.printStackTrace();

	    return false;
	} finally {
	    session.close();
	}
    }
    
    
    public static boolean insertBlockedToFriendship(int idUser1, int idUser2) {
	try {
	    session = HibernateUtil.getSessionFactory().openSession();

	    String query = "UPDATE Friendship SET Blocked = '"+ "yes" +"' WHERE id_user_FKFriendship = '"+  idUser1+ "'"
	    	+ " AND id_friend_FKFriendship = '"+  idUser2+ "'";
	    
	    String query2 = "UPDATE Friendship SET Blocked = '"+ "yes" +"' WHERE id_user_FKFriendship = '"+  idUser2+ "'"
		    	+ " AND id_friend_FKFriendship = '"+  idUser1+ "'";
	    
	    session.getTransaction().begin();
	    session.createSQLQuery(query).executeUpdate();
	    session.createSQLQuery(query2).executeUpdate();
	    session.getTransaction().commit();
	    //session.close();
	    
	    return false;
	} catch (HibernateException hibernateException) {
	    hibernateException.printStackTrace();

	    return false;
	} finally {
	    session.close();
	}
    }
    
    public static boolean verifyIfFriendshipIsBlocked(int idUser1, int idUser2) {
	try {
	    session = HibernateUtil.getSessionFactory().openSession();

	    Criteria criteria = session.createCriteria(Friendship.class);
	    @SuppressWarnings("unchecked")
	    List<Friendship> listOfFriendships = (List<Friendship>) criteria.list();

	    int number = 0;
	
	    for (int i = 0; i < listOfFriendships.size(); i++) {
		//System.out.println(listOfFriendships.get(i).getBlocked());
		//System.out.println(listOfFriendships.get(i).getUser1().getIduser() + " " + listOfFriendships.get(i).getUser2().getIduser());
		//System.out.println();
		if (listOfFriendships.get(i).getBlocked() != null)
		if (listOfFriendships.get(i).getUser1().getIduser() == idUser1 
			&& listOfFriendships.get(i).getUser2().getIduser() == idUser2
			&& listOfFriendships.get(i).getBlocked().equals("yes")
			) {
		    number++;
		}
		if (listOfFriendships.get(i).getBlocked() != null)
		if (listOfFriendships.get(i).getUser2().getIduser() == idUser1 
			&& listOfFriendships.get(i).getUser1().getIduser() == idUser2
			&& listOfFriendships.get(i).getBlocked().equals("yes") 
			) {
		    number++;
		}
	    }

	    if (number == 2) {
		System.out.println("E PRIETENIE BLOCATA !");
		return true;
	    }
	    
	    return false;
	} catch (HibernateException hibernateException) {
	    hibernateException.printStackTrace();

	    return false;
	} finally {
	    session.close();
	}
    }
   
    
    public static void main(String[] args) {
	
	//List<String> users = getAllFriendRequestsForUser(79);
	//for (int i = 0; i < users.size(); i++) {
	  //  System.out.println(users.get(i));
	//}
	/*
	if (friendshipBetweenUsers(82, 83) == true) {
	    System.out.println("EXISTA PRIETENIE !");
	} else {
	    System.out.println("NU EXISTA PRIETENIE !");
	}*/
	
	//System.out.println(checkFriendRequestsForUser(79, 83));
	
	//insertBlockedToFriendship(79, 82);
	verifyIfFriendshipIsBlocked(79, 82);
    }

}
