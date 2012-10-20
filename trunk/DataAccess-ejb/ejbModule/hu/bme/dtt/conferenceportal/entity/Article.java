package hu.bme.dtt.conferenceportal.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Egy cikket reprezent�l� entit�s.
 * 
 */
@Entity
public class Article implements Serializable {
	/**
	 * Serialization id.
	 */
	private static final long serialVersionUID = -2729039797476763987L;
	/**
	 * A cikk szerz�je, szerz�i.
	 */
	@Column(name = "ARTICLE_AUTHOR", nullable = false)
	private String author;
	/**
	 * A kulcs.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ARTICLE_ID")
	private Long id;
	/**
	 * A cikk pdf v�ltozat�nak el�r�si �tvonala.
	 */
	@Column(name = "ARTICLE_PDF_PATH")
	private String pdfPath;
	/**
	 * A cikk c�me.
	 */
	@Column(name = "ARTICLE_TITLE", nullable = false)
	private String title;
	/**
	 * A cikk URL el�r�si �tvonala.
	 */
	@Column(name = "ARTICLE_URL")
	private String url;
	/**
	 * A cikket felt�lt� user.
	 */
	@ManyToOne(cascade = { CascadeType.MERGE }, targetEntity = hu.bme.dtt.conferenceportal.entity.User.class)
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;

	/**
	 * Visszadja az objektum sztring reprezent�ci�j�t.
	 * 
	 * <p>
	 * Form�tum:
	 * <ul>
	 * <li>ClassName:
	 * <li>AttributeName1=AttributeValue1
	 * <li>AttributeName2=AttributeValue2
	 * <li>...
	 * </ul>
	 * </p>
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Article :\nauthor=");
		builder.append(author);
		builder.append(",\nid=");
		builder.append(id);
		builder.append(",\npdfPath=");
		builder.append(pdfPath);
		builder.append(",\ntitle=");
		builder.append(title);
		builder.append(",\nurl=");
		builder.append(url);
		builder.append(",\nuser=");
		builder.append(user);
		return builder.toString();
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author
	 *            the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the pdfPath
	 */
	public String getPdfPath() {
		return pdfPath;
	}

	/**
	 * @param pdfPath
	 *            the pdfPath to set
	 */
	public void setPdfPath(String pdfPath) {
		this.pdfPath = pdfPath;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

}