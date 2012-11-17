package hu.bme.dtt.conferenceportal.entity;

import java.io.Serializable;
import java.util.Arrays;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

/**
 * Egy cikket reprezentáló entitás.
 * 
 */
@Entity
public class Article implements Serializable {
	/**
	 * Serialization id.
	 */
	private static final long serialVersionUID = -2729039797476763987L;
	/**
	 * A cikk szerzõje, szerzõi.
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
	 * A cikk pdf változatának elérési útvonala.
	 */
	@Column(name = "ARTICLE_PDF_PATH")
	private String pdfPath;
	/**
	 * A cikk címe.
	 */
	@Column(name = "ARTICLE_TITLE", nullable = false)
	private String title;
	/**
	 * A cikk URL elérési útvonala.
	 */
	@Column(name = "ARTICLE_URL")
	private String url;

	/**
	 * A feltöltött file neve.
	 */
	@Column(name = "ARTICLE_FILE_NAME")
	private String fileName;

	/**
	 * A feltöltött fájl típustá adja meg. Alapvetõen application/pdf lesz.
	 */
	@Column(name = "ARTICLE_MIME")
	private String mime;

	/**
	 * A feltöltött pdf byte[] formában.
	 */
	@Lob
	@Column(name = "ARTICLE_BYTES")
	private byte[] data;

	/**
	 * A cikk hossza.
	 */
	@Column(name = "ARTICLE_LENGTH")
	private Long length;

	/**
	 * A cikket feltöltõ user.
	 */
	@ManyToOne(cascade = { CascadeType.MERGE }, targetEntity = hu.bme.dtt.conferenceportal.entity.User.class)
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;

	/**
	 * Visszadja az objektum sztring reprezentációját.
	 * 
	 * <p>
	 * Formátum:
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
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the mime
	 */
	public String getMime() {
		return mime;
	}

	/**
	 * @param mime
	 *            the mime to set
	 */
	public void setMime(String mime) {
		this.mime = mime;
	}

	/**
	 * @return the data
	 */
	public byte[] getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(byte[] data) {
		this.data = data;
	}

	/**
	 * @return the length
	 */
	public Long getLength() {
		return length;
	}

	/**
	 * @param length
	 *            the length to set
	 */
	public void setLength(Long length) {
		this.length = length;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + Arrays.hashCode(data);
		result = prime * result
				+ ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((length == null) ? 0 : length.hashCode());
		result = prime * result + ((mime == null) ? 0 : mime.hashCode());
		result = prime * result + ((pdfPath == null) ? 0 : pdfPath.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Article)) {
			return false;
		}
		Article other = (Article) obj;
		if (author == null) {
			if (other.author != null) {
				return false;
			}
		} else if (!author.equals(other.author)) {
			return false;
		}
		if (!Arrays.equals(data, other.data)) {
			return false;
		}
		if (fileName == null) {
			if (other.fileName != null) {
				return false;
			}
		} else if (!fileName.equals(other.fileName)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (length == null) {
			if (other.length != null) {
				return false;
			}
		} else if (!length.equals(other.length)) {
			return false;
		}
		if (mime == null) {
			if (other.mime != null) {
				return false;
			}
		} else if (!mime.equals(other.mime)) {
			return false;
		}
		if (pdfPath == null) {
			if (other.pdfPath != null) {
				return false;
			}
		} else if (!pdfPath.equals(other.pdfPath)) {
			return false;
		}
		if (title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!title.equals(other.title)) {
			return false;
		}
		if (url == null) {
			if (other.url != null) {
				return false;
			}
		} else if (!url.equals(other.url)) {
			return false;
		}
		if (user == null) {
			if (other.user != null) {
				return false;
			}
		} else if (!user.equals(other.user)) {
			return false;
		}
		return true;
	}

}
