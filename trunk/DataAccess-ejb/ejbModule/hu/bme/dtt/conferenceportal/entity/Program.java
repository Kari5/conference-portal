package hu.bme.dtt.conferenceportal.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Program implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7705315014604055395L;
	@Column(name = "PROGRAM_DESCRIPTION")
	private String description;
	@Column(name = "PROGRAM_END")
	private Date end;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PROGRAM_ID")
	private Long id;
	@Column(name = "PROGRAM_START")
	private Date start;
	@Column(name = "PROGRAM_TITLE", nullable = false)
	private String title;

	public String getDescription() {
		return description;
	}

	public Date getEnd() {
		return end;
	}

	public Long getId() {
		return id;
	}

	public Date getStart() {
		return start;
	}

	public String getTitle() {
		return title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public void setTitle(String title) {
		this.title = title;
	}

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
}
