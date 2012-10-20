package hu.bme.dtt.conferenceportal.entity;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;

import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.IndexColumn;

/**
 * Egy kérdést reprezentáló entitás.
 * 
 */
@Entity
public class Question implements Serializable {
	/**
	 * Serialization id.
	 */
	private static final long serialVersionUID = -7355668985294535699L;
	/**
	 * A kérdéshez kapcsolódó válaszok.
	 */
	@CollectionOfElements(fetch = FetchType.EAGER)
	@JoinTable(name = "Answer", joinColumns = @JoinColumn(name = "QUESTION_ID"))
	@IndexColumn(name = "ANSWER_ID", base = 1)
	@Fetch(FetchMode.JOIN)
	@Column(name = "ANSWER", nullable = false)
	private Collection<String> answers;
	/**
	 * A kulcs.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "QUESTION_ID")
	private Long id;
	/**
	 * A kérdés szövege.
	 */
	@Column(name = "QUESTION_TEXT", nullable = false)
	private String question;

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
		builder.append("Question :\nanswers=");
		builder.append(answers);
		builder.append(",\nid=");
		builder.append(id);
		builder.append(",\nquestion=");
		builder.append(question);
		return builder.toString();
	}

	/**
	 * @return the answers
	 */
	public Collection<String> getAnswers() {
		return answers;
	}

	/**
	 * @param answers
	 *            the answers to set
	 */
	public void setAnswers(Collection<String> answers) {
		this.answers = answers;
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
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * @param question
	 *            the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}
}
