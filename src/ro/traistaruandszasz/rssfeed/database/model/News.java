package ro.traistaruandszasz.rssfeed.database.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the news database table.
 */
@Entity
@Table(name = "news")
public class News implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private int idnews;

    private int id_user_FKnews;

    @Temporal(TemporalType.DATE)
    private Date date;

    private String description;

    private String source;

    private String title;

    // bi-directional many-to-one association to User
    @ManyToOne
    @JoinColumn(name = "id_user_FKnews")
    private User user;

    // bi-directional many-to-many association to Category
    @ManyToMany
    @JoinTable(name = "newscategory", joinColumns = { @JoinColumn(name = "id_news_FKnewscategory") }, inverseJoinColumns = { @JoinColumn(name = "id_category_FKnewscategory") })
    private List<Category> categories = new ArrayList<Category>(0);

    // bi-directional many-to-one association to News
    @OneToMany(mappedBy = "news")
    private List<Comment> comments = new ArrayList<Comment>(0);
    
    public News() {
    }

    public News(int id_user_FKnews, Date date, String description,
	    String source, String title) {
	super();
	this.id_user_FKnews = id_user_FKnews;
	this.date = date;
	this.description = description;
	this.source = source;
	this.title = title;
    }

    public int getIdnews() {
	return this.idnews;
    }

    public void setIdnews(int idnews) {
	this.idnews = idnews;
    }

    public int getId_user_FKnews() {
	return id_user_FKnews;
    }

    public void setId_user_FKnews(int id_user_FKnews) {
	this.id_user_FKnews = id_user_FKnews;
    }

    public Date getDate() {
	return this.date;
    }

    public void setDate(Date date) {
	this.date = date;
    }

    public String getDescription() {
	return this.description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public String getSource() {
	return this.source;
    }

    public void setSource(String source) {
	this.source = source;
    }

    public String getTitle() {
	return this.title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public User getUser() {
	return this.user;
    }

    public void setUser(User user) {
	this.user = user;
    }

    public List<Category> getCategories() {
	return categories;
    }

    public void setCategories(List<Category> categories) {
	this.categories = categories;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addCategory(Category category) {
	this.categories.add(category);
    }

    public void addComment(Comment comment) {
	this.comments.add(comment);
    }
    
}
