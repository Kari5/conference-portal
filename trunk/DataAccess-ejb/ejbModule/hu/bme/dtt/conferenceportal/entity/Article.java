package hu.bme.dtt.conferenceportal.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Article implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2729039797476763987L;
	@Column(name = "ARTICLE_AUTHOR")
	private String author;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ARTICLE_ID")
	private Long id;
	@Column(name = "ARTICLE_PDF_PATH")
	private String pdfPath;
	@Column(name = "ARTICLE_TITLE")
	private String title;
	@Column(name = "ARTICLE_URL")
	private String url;
	@OneToMany(cascade = { CascadeType.MERGE },
			targetEntity = hu.bme.dtt.conferenceportal.entity.User.class)
	@JoinColumn(name = "USER_ID")
	private User user;

	public String getAuthor() {
		return author;
	}

	public Long getId() {
		return id;
	}

	public String getPdfPath() {
		return pdfPath;
	}

	public String getTitle() {
		return title;
	}

	public String getUrl() {
		return url;
	}

	public User getUser() {
		return user;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPdfPath(String pdfPath) {
		this.pdfPath = pdfPath;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
