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
import javax.persistence.OneToMany;

@Entity
public class Review implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7637778059315772207L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "REVIEW_ID")
	private Long id;
	@Column(name = "REVIEW_SCORE")
	private Double score;
	@Column(name = "REVIEW_TEXT")
	private String text;
	@ManyToOne(cascade = { CascadeType.MERGE },
			targetEntity = hu.bme.dtt.conferenceportal.entity.User.class)
	@JoinColumn(name = "USER_ID")
	private User user;

	public Long getId() {
		return id;
	}

	public Double getScore() {
		return score;
	}

	public String getText() {
		return text;
	}

	public User getUser() {
		return user;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
