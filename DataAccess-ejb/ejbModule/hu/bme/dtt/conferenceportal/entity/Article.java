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
	 * A felt�lt�tt file neve.
	 */
	@Column(name = "ARTICLE_FILE_NAME")
	private String fileName;

	/**
	 * A felt�lt�tt f�jl t�pust� adja meg. Alapvet�en application/pdf lesz.
	 */
	@Column(name = "ARTICLE_MIME")
	private String mime;

	/**
	 * A felt�lt�tt pdf byte[] form�ban.
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
		result = prime * result
				+ ((getAuthor() == null) ? 0 : getAuthor().hashCode());
		result = prime * result + Arrays.hashCode(getData());
		result = prime * result
				+ ((getFileName() == null) ? 0 : getFileName().hashCode());
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result
				+ ((getLength() == null) ? 0 : getLength().hashCode());
		result = prime * result
				+ ((getMime() == null) ? 0 : getMime().hashCode());
		result = prime * result
				+ ((getPdfPath() == null) ? 0 : getPdfPath().hashCode());
		result = prime * result
				+ ((getTitle() == null) ? 0 : getTitle().hashCode());
		result = prime * result
				+ ((getUrl() == null) ? 0 : getUrl().hashCode());
		result = prime * result
				+ ((getUser() == null) ? 0 : getUser().hashCode());
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
		if (getAuthor() == null) {
			if (other.getAuthor() != null) {
				return false;
			}
		} else if (!getAuthor().equals(other.getAuthor())) {
			return false;
		}
		if (!Arrays.equals(getData(), other.getData())) {
			return false;
		}
		if (getFileName() == null) {
			if (other.getFileName() != null) {
				return false;
			}
		} else if (!getFileName().equals(other.getFileName())) {
			return false;
		}
		if (getId() == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		if (getLength() == null) {
			if (other.getLength() != null) {
				return false;
			}
		} else if (!getLength().equals(other.getLength())) {
			return false;
		}
		if (getMime() == null) {
			if (other.getMime() != null) {
				return false;
			}
		} else if (!getMime().equals(other.getMime())) {
			return false;
		}
		if (getPdfPath() == null) {
			if (other.getPdfPath() != null) {
				return false;
			}
		} else if (!getPdfPath().equals(other.getPdfPath())) {
			return false;
		}
		if (getTitle() == null) {
			if (other.getTitle() != null) {
				return false;
			}
		} else if (!getTitle().equals(other.getTitle())) {
			return false;
		}
		if (getUrl() == null) {
			if (other.getUrl() != null) {
				return false;
			}
		} else if (!getUrl().equals(other.getUrl())) {
			return false;
		}
		if (getUser() == null) {
			if (other.getUser() != null) {
				return false;
			}
		} else if (!getUser().equals(other.getUser())) {
			return false;
		}
		return true;
	}

}
