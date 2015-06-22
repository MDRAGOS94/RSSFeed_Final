package ro.traistaruandszasz.rssfeed.database.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The persistent class for the user database table.
 */
@Entity
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private int iduser;

    private String name;

    private String password;

    // bi-directional many-to-one association to Friendship
    @OneToMany(mappedBy = "user1")
    private List<Friendship> friendships1 = new ArrayList<Friendship>(0);

    // bi-directional many-to-one association to Friendship
    @OneToMany(mappedBy = "user2")
    private List<Friendship> friendships2 = new ArrayList<Friendship>(0);

    // bi-directional many-to-one association to News
    @OneToMany(mappedBy = "user")
    private List<News> news = new ArrayList<News>(0);

    // bi-directional many-to-one association to Resource
    @OneToMany(mappedBy = "user")
    private List<Resource> resources = new ArrayList<Resource>(0);

    public User() {
    }

    public int getIduser() {
	return this.iduser;
    }

    public void setIduser(int iduser) {
	this.iduser = iduser;
    }

    public String getName() {
	return this.name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getPassword() {
	return this.password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public List<Friendship> getFriendships1() {
	return this.friendships1;
    }

    public void setFriendships1(List<Friendship> friendships1) {
	this.friendships1 = friendships1;
    }

    public Friendship addFriendships1(Friendship friendships1) {
	getFriendships1().add(friendships1);
	friendships1.setUser1(this);

	return friendships1;
    }

    public Friendship removeFriendships1(Friendship friendships1) {
	getFriendships1().remove(friendships1);
	friendships1.setUser1(null);

	return friendships1;
    }

    public List<Friendship> getFriendships2() {
	return this.friendships2;
    }

    public void setFriendships2(List<Friendship> friendships2) {
	this.friendships2 = friendships2;
    }

    public Friendship addFriendships2(Friendship friendships2) {
	getFriendships2().add(friendships2);
	friendships2.setUser2(this);

	return friendships2;
    }

    public Friendship removeFriendships2(Friendship friendships2) {
	getFriendships2().remove(friendships2);
	friendships2.setUser2(null);

	return friendships2;
    }

    public List<News> getNews() {
	return this.news;
    }

    public void setNews(List<News> news) {
	this.news = news;
    }

    public News addNews(News news) {
	getNews().add(news);
	news.setUser(this);

	return news;
    }

    public News removeNews(News news) {
	getNews().remove(news);
	news.setUser(null);

	return news;
    }

    public List<Resource> getResources() {
	return this.resources;
    }

    public void setResources(List<Resource> resources) {
	this.resources = resources;
    }

    public Resource addResource(Resource resource) {
	getResources().add(resource);
	resource.setUser(this);

	return resource;
    }

    public Resource removeResource(Resource resource) {
	getResources().remove(resource);
	resource.setUser(null);

	return resource;
    }

}
