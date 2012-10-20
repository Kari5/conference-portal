package hu.bme.dtt.conferenceportal.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Egy kulcsszót reprezentáló entitás.
 * 
 */
@Entity
public class Tag implements Serializable {
	/**
	 * Serialization id.
	 */
	private static final long serialVersionUID = 6758940717394272183L;
	/**
	 * Kulcs
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TAG_ID")
	private Long id;
	/**
	 * A kulcsszó neve.
	 */
	@Column(name = "TAG_NAME", nullable = false)
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
		builder.append("Tag :\nid=");
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
