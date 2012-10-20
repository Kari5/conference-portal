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

@Entity
public class Question implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7355668985294535699L;

	@CollectionOfElements(fetch = FetchType.EAGER)
	@JoinTable(name = "Answer", joinColumns = @JoinColumn(name = "QUESTION_ID"))
	@IndexColumn(name = "ANSWER_ID", base = 1)
	@Fetch(FetchMode.JOIN)
	@Column(name = "ANSWER", nullable = false)
	private Collection<String> answers;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "QUESTION_ID")
	private Long id;
	@Column(name = "QUESTION_TEXT", nullable = false)
	private String question;

	public Collection<String> getAnswers() {
		return answers;
	}

	public Long getId() {
		return id;
	}

	public String getQuestion() {
		return question;
	}

	public void setAnswers(Collection<String> answers) {
		this.answers = answers;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

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
}
