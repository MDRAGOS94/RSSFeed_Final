package ro.traistaruandszasz.rssfeed.database.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the comment database table.
 */
@Entity
public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private int idcomment;

    private int id_friend_FKcomment;

    private int id_news_FKcomment;

    @Temporal(TemporalType.DATE)
    private Date date;

    private String text;

    // bi-directional many-to-one association to Friendship
    @ManyToOne
    @JoinColumn(name = "id_friend_FKcomment")
    private Friendship friendship;
    
    // bi-directional many-to-one association to News
    @ManyToOne
    @JoinColumn(name = "id_news_FKcomment")
    private News news;

    public Comment() {
    }

    public int getIdcomment() {
	return this.idcomment;
    }

    public void setIdcomment(int idcomment) {
	this.idcomment = idcomment;
    }

    public int getId_friend_FKcomment() {
	return id_friend_FKcomment;
    }

    public void setId_friend_FKcomment(int id_friend_FKcomment) {
	this.id_friend_FKcomment = id_friend_FKcomment;
    }

    public int getId_news_FKcomment() {
	return this.id_news_FKcomment;
    }

    public void setId_news_FKcomment(int id_news_FKcomment) {
	this.id_news_FKcomment = id_news_FKcomment;
    }

    public Date getDate() {
	return this.date;
    }

    public void setDate(Date date) {
	this.date = date;
    }

    public String getText() {
	return this.text;
    }

    public void setText(String text) {
	this.text = text;
    }

    public Friendship getFriendship() {
	return this.friendship;
    }

    public void setFriendship(Friendship friendship) {
	this.friendship = friendship;
    }

    
    public News getNews() {
        return news;
    }
    

    public void setNews(News news) {
        this.news = news;
    }
    
    
}