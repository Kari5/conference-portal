package hu.bme.dtt.conferenceportal.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.jboss.seam.annotations.security.management.RoleName;

@Entity(name = "Role_")
public class Role implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1924718093608833635L;
	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ROLE_ID")
	private Long id;
	/**
	 * 
	 */
	@RoleName
	@Column(name = "ROLE_NAME", nullable = false)
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Role :\nid=");
		builder.append(id);
		builder.append(",\nname=");
		builder.append(name);
		return builder.toString();
	}

}
