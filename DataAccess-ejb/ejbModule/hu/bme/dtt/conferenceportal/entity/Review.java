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
 * Egy b�r�latot reprezent�l� entit�s.
 * 
 */
@Entity
public class Review implements Serializable {
	/**
	 * Serialization id.
	 */
	private static final long serialVersionUID = 7637778059315772207L;
	/**
	 * A kulcs.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "REVIEW_ID")
	private Long id;
	/**
	 * Az �rt�kel�s sz�mszer�s�tve.
	 */
	@Column(name = "REVIEW_SCORE")
	private Double score;
	/**
	 * A b�r�lat sz�vege.
	 */
	@Column(name = "REVIEW_TEXT")
	private String text;
	/**
	 * A b�r�lat szerz�je.
	 */
	@ManyToOne(cascade = { CascadeType.MERGE }, targetEntity = hu.bme.dtt.conferenceportal.entity.User.class)
	@JoinColumn(name = "USER_ID")
	private User user;

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
	 * @return the score
	 */
	public Double getScore() {
		return score;
	}

	/**
	 * @param score
	 *            the score to set
	 */
	public void setScore(Double score) {
		this.score = score;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
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
		builder.append("Review :\nid=");
		builder.append(id);
		builder.append(",\nscore=");
		builder.append(score);
		builder.append(",\ntext=");
		builder.append(text);
		builder.append(",\nuser=");
		builder.append(user);
		return builder.toString();
	}
}
