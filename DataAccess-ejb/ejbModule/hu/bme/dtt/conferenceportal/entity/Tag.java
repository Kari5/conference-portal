package hu.bme.dtt.conferenceportal.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Tag implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6758940717394272183L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TAG_ID")
	private Long id;
	@Column(name = "TAG_NAME")
	private String name;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
}
