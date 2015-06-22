package ro.traistaruandszasz.rssfeed.database.model;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the resource database table.
 */
@Entity
public class Resource implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private int idresource;

    private int id_user_FKresource;

    private String description;

    private String url;

    // bi-directional many-to-one association to User
    @ManyToOne
    @JoinColumn(name = "id_user_FKresource")
    private User user;

    public Resource() {
    }

    public int getIdresource() {
	return this.idresource;
    }

    public void setIdresource(int idresource) {
	this.idresource = idresource;
    }

    public int getId_user_FKresource() {
	return id_user_FKresource;
    }

    public void setId_user_FKresource(int id_user_FKresource) {
	this.id_user_FKresource = id_user_FKresource;
    }

    public String getDescription() {
	return this.description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public String getUrl() {
	return this.url;
    }

    public void setUrl(String url) {
	this.url = url;
    }

    public User getUser() {
	return this.user;
    }

    public void setUser(User user) {
	this.user = user;
    }

}
