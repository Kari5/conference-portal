package hu.bme.dtt.conferenceportal.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
	@ManyToMany(targetEntity = hu.bme.dtt.conferenceportal.entity.Article.class,
			fetch = FetchType.EAGER)
	@JoinTable(name = "Conference_Article", joinColumns = @JoinColumn(name = "CONFERENCE_ID"),
			inverseJoinColumns = @JoinColumn(name = "ARTICLE_ID"))
	@Fetch(value = FetchMode.SUBSELECT)
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
	@ManyToOne(targetEntity = hu.bme.dtt.conferenceportal.entity.Location.class,
			fetch = FetchType.EAGER)
	@JoinColumn(name = "LOCATION_ID")
	private Location location;
	/**
	 * A Konferenciát felvevõ felhasználó.
	 */
	@ManyToOne(targetEntity = hu.bme.dtt.conferenceportal.entity.User.class,
			fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_ID", nullable = false)
	private User owner;
	/**
	 * A Konferencia résztvevõi.
	 */
	@ManyToMany(targetEntity = hu.bme.dtt.conferenceportal.entity.User.class,
			fetch = FetchType.EAGER)
	@JoinTable(name = "Conference_User", joinColumns = @JoinColumn(name = "CONFERENCE_ID"),
			inverseJoinColumns = @JoinColumn(name = "USER_ID"))
	@Fetch(value = FetchMode.SUBSELECT)
	private Collection<User> participants;
	/**
	 * A Konferencia program pontjai.
	 */
	@OneToMany(targetEntity = hu.bme.dtt.conferenceportal.entity.Program.class,
			cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinColumn(name = "CONFERENCE_ID")
	@Fetch(value = FetchMode.SUBSELECT)
	private Collection<Program> programs;
	/**
	 * A Konferencia kérdései.
	 */
	@OneToMany(targetEntity = hu.bme.dtt.conferenceportal.entity.Question.class,
			cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinColumn(name = "CONFERENCE_ID")
	@Fetch(value = FetchMode.SUBSELECT)
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
	@ManyToMany(targetEntity = hu.bme.dtt.conferenceportal.entity.Tag.class,
			fetch = FetchType.EAGER)
	@JoinTable(name = "Conference_Tags", joinColumns = @JoinColumn(name = "CONFERENCE_ID"),
			inverseJoinColumns = @JoinColumn(name = "TAG_ID"))
	@Fetch(value = FetchMode.SUBSELECT)
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((articles == null) ? 0 : articles.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result + ((participants == null) ? 0 : participants.hashCode());
		result = prime * result + ((programs == null) ? 0 : programs.hashCode());
		result = prime * result + ((questions == null) ? 0 : questions.hashCode());
		result = prime * result + ((shortTitle == null) ? 0 : shortTitle.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((summary == null) ? 0 : summary.hashCode());
		result = prime * result + ((tags == null) ? 0 : tags.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		if (!(obj instanceof Conference)) {
			return false;
		}
		Conference other = (Conference) obj;
		if (articles == null) {
			if (other.articles != null) {
				return false;
			}
		} else {
			for (Article article : articles) {
				if (!other.articles.contains(article)) {
					return false;
				}
			}
		}
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (endDate == null) {
			if (other.endDate != null) {
				return false;
			}
		} else if (!endDate.equals(other.endDate)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (location == null) {
			if (other.location != null) {
				return false;
			}
		} else if (!location.equals(other.location)) {
			return false;
		}
		if (owner == null) {
			if (other.owner != null) {
				return false;
			}
		} else if (!owner.equals(other.owner)) {
			return false;
		}
		if (participants == null) {
			if (other.participants != null) {
				return false;
			}
		} else {
			for (User participant : participants) {
				if (!other.participants.contains(participant)) {
					return false;
				}
			}
		}
		if (programs == null) {
			if (other.programs != null) {
				return false;
			}
		} else {
			for (Program program : programs) {
				if (!other.programs.contains(program)) {
					return false;
				}
			}
		}
		if (questions == null) {
			if (other.questions != null) {
				return false;
			}
		} else {
			for (Question question : questions) {
				if (!other.questions.contains(question)) {
					return false;
				}
			}
		}
		if (shortTitle == null) {
			if (other.shortTitle != null) {
				return false;
			}
		} else if (!shortTitle.equals(other.shortTitle)) {
			return false;
		}
		if (startDate == null) {
			if (other.startDate != null) {
				return false;
			}
		} else if (!startDate.equals(other.startDate)) {
			return false;
		}
		if (summary == null) {
			if (other.summary != null) {
				return false;
			}
		} else if (!summary.equals(other.summary)) {
			return false;
		}
		if (tags == null) {
			if (other.tags != null) {
				return false;
			}
		} else {
			for (Tag tag : tags) {
				if (!other.tags.contains(tag)) {
					return false;
				}
			}
		}
		if (title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!title.equals(other.title)) {
			return false;
		}
		return true;
	}

}
