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

/**
 * Egy Konferenciát reprezentáló entitás.
 * 
 */
@Entity
public class Conference implements Serializable {
	/**
	 * Serialization id.
	 */
	private static final long serialVersionUID = 3816330077764844751L;
	/**
	 * A Konferenciához tartozó cikkek listája.
	 */
	@ManyToMany(targetEntity = hu.bme.dtt.conferenceportal.entity.Article.class, cascade = { CascadeType.MERGE })
	@JoinTable(name = "Conference_Article", joinColumns = @JoinColumn(name = "CONFERENCE_ID"), inverseJoinColumns = @JoinColumn(name = "ARTICLE_ID"))
	private Collection<Article> articles;
	/**
	 * A Konferencia leírása.
	 */
	@Column(name = "CONFERENCE_DESCRIPTION")
	private String description;
	/**
	 * A Konferencia befejezési ideje.
	 */
	@Column(name = "CONFERENCE_END_DATE")
	private Date endDate;
	/**
	 * A kulcs.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CONFERENCE_ID")
	private Long id;
	/**
	 * A Konferencia helye.
	 */
	@ManyToOne(cascade = { CascadeType.MERGE }, targetEntity = hu.bme.dtt.conferenceportal.entity.Location.class)
	@JoinColumn(name = "LOCATION_ID")
	private Location location;
	/**
	 * A Konferenciát felvevõ felhasználó.
	 */
	@ManyToOne(cascade = { CascadeType.MERGE }, targetEntity = hu.bme.dtt.conferenceportal.entity.User.class)
	@JoinColumn(name = "USER_ID", nullable = false)
	private User owner;
	/**
	 * A Konferencia résztvevõi.
	 */
	@ManyToMany(targetEntity = hu.bme.dtt.conferenceportal.entity.Article.class, cascade = { CascadeType.MERGE })
	@JoinTable(name = "Conference_User", joinColumns = @JoinColumn(name = "CONFERENCE_ID"), inverseJoinColumns = @JoinColumn(name = "USER_ID"))
	private Collection<User> participants;
	/**
	 * A Konferencia program pontjai.
	 */
	@OneToMany(targetEntity = hu.bme.dtt.conferenceportal.entity.Program.class, cascade = { CascadeType.MERGE })
	@JoinColumn(name = "PROGRAM_ID")
	private Collection<Program> programs;
	/**
	 * A Konferencia kérdései.
	 */
	@OneToMany(targetEntity = hu.bme.dtt.conferenceportal.entity.Question.class, cascade = { CascadeType.MERGE })
	@JoinColumn(name = "QUESTION_ID")
	private Collection<Question> questions;
	/**
	 * A Konferencia rövid címe.
	 */
	@Column(name = "CONFERENCE_SHORT_TITLE", nullable = false)
	private String shortTitle;
	/**
	 * A Konferencia kezdési ideje.
	 */
	@Column(name = "CONFERENCE_START_DATE")
	private Date startDate;
	/**
	 * A Konferencia összefoglalása.
	 */
	@Column(name = "CONFERENCE_SUMMARY")
	private String summary;
	/**
	 * A Konferencia kulcsszavai.
	 */
	@ManyToMany(targetEntity = hu.bme.dtt.conferenceportal.entity.Tag.class, cascade = { CascadeType.MERGE })
	@JoinTable(name = "Conference_User", joinColumns = @JoinColumn(name = "CONFERENCE_ID"), inverseJoinColumns = @JoinColumn(name = "TAG_ID"))
	private Collection<Tag> tags;
	/**
	 * A Konferencia címe.
	 */
	@Column(name = "CONFERENCE_TITLE", nullable = false)
	private String title;

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
		builder.append("Conference :\narticles=");
		builder.append(articles);
		builder.append(",\ndescription=");
		builder.append(description);
		builder.append(",\nendDate=");
		builder.append(endDate);
		builder.append(",\nid=");
		builder.append(id);
		builder.append(",\nlocation=");
		builder.append(location);
		builder.append(",\nowner=");
		builder.append(owner);
		builder.append(",\nparticipants=");
		builder.append(participants);
		builder.append(",\nprograms=");
		builder.append(programs);
		builder.append(",\nquestions=");
		builder.append(questions);
		builder.append(",\nshortTitle=");
		builder.append(shortTitle);
		builder.append(",\nstartDate=");
		builder.append(startDate);
		builder.append(",\nsummary=");
		builder.append(summary);
		builder.append(",\ntags=");
		builder.append(tags);
		builder.append(",\ntitle=");
		builder.append(title);
		return builder.toString();
	}

	/**
	 * @return the articles
	 */
	public Collection<Article> getArticles() {
		return articles;
	}

	/**
	 * @param articles
	 *            the articles to set
	 */
	public void setArticles(Collection<Article> articles) {
		this.articles = articles;
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
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * @return the owner
	 */
	public User getOwner() {
		return owner;
	}

	/**
	 * @param owner
	 *            the owner to set
	 */
	public void setOwner(User owner) {
		this.owner = owner;
	}

	/**
	 * @return the participants
	 */
	public Collection<User> getParticipants() {
		return participants;
	}

	/**
	 * @param participants
	 *            the participants to set
	 */
	public void setParticipants(Collection<User> participants) {
		this.participants = participants;
	}

	/**
	 * @return the programs
	 */
	public Collection<Program> getPrograms() {
		return programs;
	}

	/**
	 * @param programs
	 *            the programs to set
	 */
	public void setPrograms(Collection<Program> programs) {
		this.programs = programs;
	}

	/**
	 * @return the questions
	 */
	public Collection<Question> getQuestions() {
		return questions;
	}

	/**
	 * @param questions
	 *            the questions to set
	 */
	public void setQuestions(Collection<Question> questions) {
		this.questions = questions;
	}

	/**
	 * @return the shortTitle
	 */
	public String getShortTitle() {
		return shortTitle;
	}

	/**
	 * @param shortTitle
	 *            the shortTitle to set
	 */
	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * @param summary
	 *            the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * @return the tags
	 */
	public Collection<Tag> getTags() {
		return tags;
	}

	/**
	 * @param tags
	 *            the tags to set
	 */
	public void setTags(Collection<Tag> tags) {
		this.tags = tags;
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

}
