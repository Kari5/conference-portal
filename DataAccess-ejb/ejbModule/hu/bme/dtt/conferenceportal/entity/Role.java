package hu.bme.dtt.conferenceportal.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.jboss.seam.annotations.security.management.RoleName;

/**
 * Egy jogosultságot reprezentáló entitás.
 * 
 */
@Entity(name = "Role_")
public class Role implements Serializable {
	/**
	 * Serialization id.
	 */
	private static final long serialVersionUID = -1924718093608833635L;
	/**
	 * Kulcs
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ROLE_ID")
	private Long id;
	/**
	 * Jogosultság neve.
	 */
	@RoleName
	@Column(name = "ROLE_NAME", nullable = false)
	private String name;

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
		builder.append("Role :\nid=");
		builder.append(id);
		builder.append(",\nname=");
		builder.append(name);
		return builder.toString();
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
