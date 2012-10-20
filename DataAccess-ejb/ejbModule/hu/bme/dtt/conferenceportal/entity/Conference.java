package hu.bme.dtt.conferenceportal.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Conference implements Serializable {

	private static final long serialVersionUID = 3816330077764844751L;
	
	@ManyToMany(targetEntity = hu.bme.dtt.conferenceportal.entity.Article.class,
			cascade = { CascadeType.MERGE })
	@JoinTable(name = "Conference_Article", joinColumns = @JoinColumn(name = "CONFERENCE_ID"),
			inverseJoinColumns = @JoinColumn(name = "ARTICLE_ID"))
	private Collection<Article> articles;
	
	
	@Column(name = "CONFERENCE_DESCRIPTION")
	private String description;
	
	
	@Column(name = "CONFERENCE_END_DATE")
	private Date endDate;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CONFERENCE_ID")
	private Long id;
	
	
	@ManyToOne(cascade = { CascadeType.MERGE },
			targetEntity = hu.bme.dtt.conferenceportal.entity.Location.class)
	@JoinColumn(name = "LOCATION_ID")
	private Location location;
	
	
	@ManyToOne(cascade = { CascadeType.MERGE },
			targetEntity = hu.bme.dtt.conferenceportal.entity.User.class)
	@JoinColumn(name = "USER_ID", nullable=false)
	private User owner;
	
	
	@ManyToMany(targetEntity = hu.bme.dtt.conferenceportal.entity.Article.class,
			cascade = { CascadeType.MERGE })
	@JoinTable(name = "Conference_User", joinColumns = @JoinColumn(name = "CONFERENCE_ID"),
			inverseJoinColumns = @JoinColumn(name = "USER_ID"))
	private Collection<User> participants;
	
	
	@OneToMany(targetEntity = hu.bme.dtt.conferenceportal.entity.Program.class,
			cascade = { CascadeType.MERGE })
	@JoinColumn(name = "PROGRAM_ID")
	private Collection<Program> programs;
	
	
	@OneToMany(targetEntity = hu.bme.dtt.conferenceportal.entity.Question.class,
			cascade = { CascadeType.MERGE })
	@JoinColumn(name = "QUESTION_ID")
	private Collection<Question> questions;
	
	
	@Column(name = "CONFERENCE_SHORT_TITLE", nullable=false)
	private String shortTitle;
	
	
	@Column(name = "CONFERENCE_START_DATE")
	private Date startDate;
	
	
	@Column(name = "CONFERENCE_SUMMARY")
	private String summary;
	
	
	@ManyToMany(targetEntity = hu.bme.dtt.conferenceportal.entity.Tag.class,
			cascade = { CascadeType.MERGE })
	@JoinTable(name = "Conference_User", joinColumns = @JoinColumn(name = "CONFERENCE_ID"),
			inverseJoinColumns = @JoinColumn(name = "TAG_ID"))
	private Collection<Tag> tags;
	
	
	@Column(name = "CONFERENCE_TITLE", nullable=false)
	private String title;

	public Collection<Article> getArticles() {
		return articles;
	}

	public String getDescription() {
		return description;
	}

	public Date getEndDate() {
		return endDate;
	}

	public Long getId() {
		return id;
	}

	public Location getLocation() {
		return location;
	}

	public User getOwner() {
		return owner;
	}

	public Collection<User> getParticipants() {
		return participants;
	}

	public Collection<Program> getPrograms() {
		return programs;
	}

	public Collection<Question> getQuestions() {
		return questions;
	}

	public String getShortTitle() {
		return shortTitle;
	}

	public Date getStartDate() {
		return startDate;
	}

	public String getSummary() {
		return summary;
	}

	public Collection<Tag> getTags() {
		return tags;
	}

	public String getTitle() {
		return title;
	}

	public void setArticles(Collection<Article> articles) {
		this.articles = articles;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public void setParticipants(Collection<User> participants) {
		this.participants = participants;
	}

	public void setPrograms(Collection<Program> programs) {
		this.programs = programs;
	}

	public void setQuestions(Collection<Question> questions) {
		this.questions = questions;
	}

	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public void setTags(Collection<Tag> tags) {
		this.tags = tags;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
