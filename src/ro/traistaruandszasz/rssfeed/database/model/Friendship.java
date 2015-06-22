package ro.traistaruandszasz.rssfeed.database.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The persistent class for the friendship database table.
 */
@Entity
public class Friendship implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private int idfriendship;

    private int id_user_FKfriendship;

    private int id_friend_FKfriendship;
    
    private String blocked;

    // bi-directional many-to-one association to Comment
    @OneToMany(mappedBy = "friendship")
    private List<Comment> comments = new ArrayList<Comment>();

    // bi-directional many-to-one association to User
    @ManyToOne
    @JoinColumn(name = "id_friend_FKfriendship")
    private User user1;

    // bi-directional many-to-one association to User
    @ManyToOne
    @JoinColumn(name = "id_user_FKfriendship")
    private User user2;

    public Friendship() {
    }

    public int getIdfriendship() {
	return this.idfriendship;
    }

    public void setIdfriendship(int idfriendship) {
	this.idfriendship = idfriendship;
    }

    public int getId_user_FKfriendship() {
	return id_user_FKfriendship;
    }

    public void setId_user_FKfriendship(int id_user_FKfriendship) {
	this.id_user_FKfriendship = id_user_FKfriendship;
    }

    public int getId_friend_FKfriendship() {
	return id_friend_FKfriendship;
    }

    public void setId_friend_FKfriendship(int id_friend_FKfriend) {
	this.id_friend_FKfriendship = id_friend_FKfriend;
    }

    public List<Comment> getComments() {
	return this.comments;
    }

    public void setComments(List<Comment> comments) {
	this.comments = comments;
    }

    public Comment addComment(Comment comment) {
	getComments().add(comment);
	comment.setFriendship(this);

	return comment;
    }

    public Comment removeComment(Comment comment) {
	getComments().remove(comment);
	comment.setFriendship(null);

	return comment;
    }

    public User getUser1() {
	return this.user1;
    }

    public void setUser1(User user1) {
	this.user1 = user1;
    }

    public User getUser2() {
	return this.user2;
    }

    public void setUser2(User user2) {
	this.user2 = user2;
    }

    public String getBlocked() {
	return blocked;
    }

    public void setBlocked(String blocked) {
	this.blocked = blocked;
    }

}
