package hu.bme.dtt.conferenceportal.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Egy program pontot reprezent�l� entit�s.
 * 
 */
@Entity
public class Program implements Serializable {
	/**
	 * Serialization id.
	 */
	private static final long serialVersionUID = 7705315014604055395L;
	@Column(name = "PROGRAM_DESCRIPTION")
	/**
	 * A program pont le�r�sa.
	 */
	private String description;
	/**
	 * A program pont v�ge.
	 */
	@Column(name = "PROGRAM_END")
	private Date end;
	/**
	 * A kulcs.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PROGRAM_ID")
	private Long id;
	/**
	 * A program pont kezdete.
	 */
	@Column(name = "PROGRAM_START")
	private Date start;
	/**
	 * A program pont c�me.
	 */
	@Column(name = "PROGRAM_TITLE", nullable = false)
	private String title;

	/**
	 * Konstruktor �rt�k�tad�ssal.
	 * 
	 * @param description
	 *            le�r�s
	 * @param end
	 *            befejez�s id�pontja
	 * @param id
	 *            azonos�t�
	 * @param start
	 *            kezd�s id�pontja
	 * @param title
	 *            c�m
	 */
	public Program(String description, Date end, Long id, Date start,
			String title) {
		super();
		this.description = description;
		this.end = end;
		this.id = id;
		this.start = start;
		this.title = title;
	}

	/**
	 * Default konstruktor.
	 */
	public Program() {
		super();
	}

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
		builder.append("Program :\ndescription=");
		builder.append(description);
		builder.append(",\nend=");
		builder.append(end);
		builder.append(",\nid=");
		builder.append(id);
		builder.append(",\nstart=");
		builder.append(start);
		builder.append(",\ntitle=");
		builder.append(title);
		return builder.toString();
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the end
	 */
	public Date getEnd() {
		return end;
	}

	/**
	 * @param end
	 *            the end to set
	 */
	public void setEnd(Date end) {
		this.end = end;
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
	 * @return the start
	 */
	public Date getStart() {
		return start;
	}

	/**
	 * @param start
	 *            the start to set
	 */
	public void setStart(Date start) {
		this.start = start;
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
	 * Kl�noz� f�ggv�ny.
	 */
	public Program clone() {
		return new Program(description, end, id, start, title);
	}
}
