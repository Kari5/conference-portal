package hu.bme.dtt.conferenceportal.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Question {
	// TODO
	@Column(name = "QUESTION_ID")
	private Collection<String> answers;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "QUESTION_ID")
	private Long id;
	@Column(name = "QUESTION_TEXT")
	private String question;
}
