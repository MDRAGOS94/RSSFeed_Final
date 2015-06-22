package ro.traistaruandszasz.rssfeed.database.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * The persistent class for the category database table.
 */
@Entity
@Table(name = "category")
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private int idcategory;

    private String name;

    private String description;

    // bi-directional many-to-many association to Keyword
    @ManyToMany(mappedBy = "categories")
    private List<Keyword> keywords = new ArrayList<Keyword>(0);

    // bi-directional many-to-many association to News
    @ManyToMany(mappedBy = "categories")
    private List<News> news = new ArrayList<News>(0);

    public Category() {
    }

    public int getIdcategory() {
	return this.idcategory;
    }

    public void setIdcategory(int idcategory) {
	this.idcategory = idcategory;
    }

    public String getName() {
	return this.name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getDescription() {
	return this.description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public List<Keyword> getKeywords() {
	return this.keywords;
    }

    public void setKeywords(List<Keyword> keywords) {
	this.keywords = keywords;
    }

    public List<News> getNews() {
	return news;
    }

    public void setNews(List<News> news) {
	this.news = news;
    }

    public void addNews(News news) {
	this.news.add(news);
    }

    public void addKeyword(Keyword keyword) {
	this.keywords.add(keyword);
    }
    
}
