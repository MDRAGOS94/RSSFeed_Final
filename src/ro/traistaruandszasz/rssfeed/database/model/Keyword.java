package ro.traistaruandszasz.rssfeed.database.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The persistent class for the keyword database table.
 */
@Entity
@Table(name = "keyword")
@NamedQuery(name = "Keyword.findAll", query = "SELECT k FROM Keyword k")
public class Keyword implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private int idkeyword;

    private String name;

    // bi-directional many-to-many association to Category
    @ManyToMany
    @JoinTable(name = "keywordcategory", joinColumns = { @JoinColumn(name = "id_keyword_FKkeywordcategory") }, inverseJoinColumns = { @JoinColumn(name = "id_category_FKkeywordcategory") })
    private List<Category> categories = new ArrayList<Category>(0);

    public Keyword() {
    }

    public int getIdkeyword() {
	return this.idkeyword;
    }

    public void setIdkeyword(int idkeyword) {
	this.idkeyword = idkeyword;
    }

    public String getName() {
	return this.name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public List<Category> getCategories() {
	return this.categories;
    }

    public void setCategories(List<Category> categories) {
	this.categories = categories;
    }

    public void addCategory(Category category) {
	this.categories.add(category);
    }
    
}
