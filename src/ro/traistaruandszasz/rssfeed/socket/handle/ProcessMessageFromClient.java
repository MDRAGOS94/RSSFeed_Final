package ro.traistaruandszasz.rssfeed.socket.handle;

import java.util.Date;

import ro.traistaruandszasz.rssfeed.database.operations.CategoryOperation;
import ro.traistaruandszasz.rssfeed.database.operations.CommentOperation;
import ro.traistaruandszasz.rssfeed.database.operations.FriendshipOperation;
import ro.traistaruandszasz.rssfeed.database.operations.KeywordOperation;
import ro.traistaruandszasz.rssfeed.database.operations.MessageOperation;
import ro.traistaruandszasz.rssfeed.database.operations.NewsOperation;
import ro.traistaruandszasz.rssfeed.database.operations.UserOperation;
import ro.traistaruandszasz.rssfeed.server.core.ServerCore;

public class ProcessMessageFromClient {

    public SocketMessage processNextMesageFromServerCoreMessageQueue(SocketMessage message) {

	SocketMessage respondMessageToClient = new SocketMessage();
	respondMessageToClient.setSocketMessageType(message.getSocketMessageType());
	respondMessageToClient.setClientId(message.getClientId());
	SocketMessageType messageType = message.getSocketMessageType();
	
	switch(messageType) {
	
	case Logging:
	     respondMessageToClient.setMessageToServer(Integer.toString(loginClient(message.getMessageToServer())));
	  break;
	case Register:  
	     respondMessageToClient.setBooleanToServer(registerClient(message.getMessageToServer()));
	  break;
	case QueryLoadingNews:	    
	    respondMessageToClient.setListMessageToServer(UserOperation.getAllNewsForUser(Integer.parseInt(message.getMessageToServer())));
	    break;
	case QueryLoadingComment:
	    respondMessageToClient.setListMessageToServer(CommentOperation.getAllCommentsForNews(Integer.parseInt(message.getMessageToServer())));
	    break;
	case QueryLoadingCategory:
	    respondMessageToClient.setListMessageToServer(CategoryOperation.getAllCategories());
	    break;
	case QueryLoadingAllUsers:
	    respondMessageToClient.setListMessageToServer(UserOperation.getAllUsers());
	    break;
	case QueryFriendshipExists:
	    respondMessageToClient.setBooleanToServer(existsFriendshipBetweenUsers(message.getMessageToServer()));
	    break;
	case QueryExistsFriendRequest:
	    respondMessageToClient.setBooleanToServer(existsFriendRequestBetweenUsers(message.getMessageToServer()));
	    break;
	case QueryAcceptFriendRequest:
	    respondMessageToClient.setBooleanToServer(acceptFriendRequestBetweenUsers(message.getMessageToServer()));
	    break;
	case QueryInsertNews:
	    respondMessageToClient.setBooleanToServer(insertNews(message.getMessageToServer()));
	    break;
	case QueryInsertComment:
	    /*
	    SocketMessage updateSocket = new SocketMessage();
	    updateSocket.setMessageToServer(message.getMessageToServer());
	    updateSocket.setSocketMessageType(SocketMessageType.QueryUpdateCommentList);
	    ServerCore.sendMessageToAllClients(updateSocket);
	    respondMessageToClient.setBooleanToServer(insertComment(message.getMessageToServer()));
	    break;*/
	    respondMessageToClient.setBooleanToServer(insertComment(message.getMessageToServer()));
	    
	    String[] arrayString = message.getMessageToServer().split("#");
	    message.setMessageToServer(message.getMessageToServer() + "#" + UserOperation.getUserNameById(Integer.parseInt(arrayString[0])));
	    ServerCore.sendMessageToAllClients(new SocketMessage(SocketMessageType.QueryUpdateCommentList, message.getMessageToServer()));
	    break;
	case QuerySendFriendRequest:
	    respondMessageToClient.setBooleanToServer(sendFriendRequestBetweenUsers(message.getMessageToServer()));
	    break;
	case QuerySendMessage:
	    respondMessageToClient.setBooleanToServer(MessageOperation.insertMessage(message.getMessageToServer()));
	    break;
	case QueryLoadingMessages:
	    respondMessageToClient.setListMessageToServer(MessageOperation.getAllMessages());
	    break;
	case QueryInsertCategory:
	    respondMessageToClient.setBooleanToServer(CategoryOperation.insertCategory(message.getMessageToServer(), "NULL"));
	    break;
	case QueryInsertKeyword:
	    respondMessageToClient.setBooleanToServer(insertKeyword(message.getMessageToServer()));
	    break;
	case QueryLoadKeywords:
	    respondMessageToClient.setListMessageToServer(KeywordOperation.getAllKeywordForCategory(Integer.parseInt(message.getMessageToServer())));
	    break;
	case QueryFriendshipRestriction:
	    respondMessageToClient.setBooleanToServer(isBlocked(message.getMessageToServer()));
	    break;
	case QueryFriendshipSetRestriction:
	    respondMessageToClient.setBooleanToServer(setBlockedToFriendship(message.getMessageToServer()));
	    break;
	    
	default:
	    System.out.println("No messageType was found!");
	  break;
	}

	return respondMessageToClient;
    }
   

    int loginClient(String message) {

	String userName = message.split(",")[0];
	String userPassword = message.split(",")[1];
        
	if (UserOperation.userAlreadyExists(userName, userPassword) == true) {
	    return UserOperation.getIdUser(userName);
	} else {
	    return -1;
	}
    }
 
    boolean registerClient(String message) {
	String userName = message.split(",")[0];
	String userPassword = message.split(",")[1];
	
	return UserOperation.insertUser(userName, userPassword);
    }
    
    boolean existsFriendshipBetweenUsers(String ids) {
	String[] arrayString = ids.split("#");
	return FriendshipOperation.friendshipBetweenUsers(Integer.parseInt(arrayString[0]), Integer.parseInt(arrayString[1]));
    }
    
    boolean existsFriendRequestBetweenUsers(String ids) {
	String[] arrayString = ids.split("#");
	return FriendshipOperation.checkFriendRequestsForUser(Integer.parseInt(arrayString[0]), Integer.parseInt(arrayString[1]));
    }
    
    boolean acceptFriendRequestBetweenUsers(String ids) {
	String[] arrayString = ids.split("#");
	return FriendshipOperation.insertFriendship(Integer.parseInt(arrayString[0]), Integer.parseInt(arrayString[1]));
    }
    
    boolean insertNews(String ids) {
	String[] arrayString = ids.split("#");
	return NewsOperation.addNewNewsToUserAndNewsCategory(Integer.parseInt(arrayString[0]), arrayString[1], new Date(), arrayString[3], "NULL", Integer.parseInt(arrayString[5]));
    }
    
    boolean insertComment(String ids) {
	String[] arrayString = ids.split("#");
	return CommentOperation.insertCommentFinal(Integer.parseInt(arrayString[0]), Integer.parseInt(arrayString[1]), new Date(), arrayString[3]);
    }
    
    boolean sendFriendRequestBetweenUsers(String ids) {
   	String[] arrayString = ids.split("#");
   	return FriendshipOperation.insertFriendship(Integer.parseInt(arrayString[0]), Integer.parseInt(arrayString[1]));
    }

    boolean insertKeyword(String ids) {
	String[] arrayString = ids.split("#");
   	return KeywordOperation.addNewKeywordToCategoryAndKeywordCategory(Integer.parseInt(arrayString[0]), arrayString[1]);
    }

    boolean isBlocked(String message) {

   	String[] array = message.split("#");
           
   	if (FriendshipOperation.verifyIfFriendshipIsBlocked(Integer.parseInt(array[0]), Integer.parseInt(array[1]))) {
   	    System.out.println(array[0] + " " + array[1] + "true");
   	    return true;
   	} else {
   	    System.out.println(array[0] + " " + array[1] + "false");
   	    return false;
   	}
       }
    
    boolean setBlockedToFriendship(String message) {

   	String[] array = message.split("#");
           
   	FriendshipOperation.insertBlockedToFriendship(Integer.parseInt(array[0]), Integer.parseInt(array[1]));
   	
   	return true;
    }
}
