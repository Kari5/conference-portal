package hu.bme.dtt.conferenceportal.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Egy helyszínt reprezentáló entitás.
 * 
 */
@Entity
public class Location implements Serializable {
	/**
	 * Serialization id.
	 */
	private static final long serialVersionUID = -3993579242993428896L;
	/**
	 * A helyszín címe.
	 */
	@Column(name = "LOCATION_ADDRESS")
	private String address;
	/**
	 * A helyszín GoogleMaps azonosítója.
	 */
	@Column(name = "GOOGLE_MAPS")
	private String googleMaps;
	/**
	 * A kulcs.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "LOCATION_ID")
	private Long id;
	/**
	 * A helyszín neve.
	 */
	@Column(name = "LOCATION_NAME", nullable = false)
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
		builder.append("Location :\naddress=");
		builder.append(address);
		builder.append(",\ngoogleMaps=");
		builder.append(googleMaps);
		builder.append(",\nid=");
		builder.append(id);
		builder.append(",\nname=");
		builder.append(name);
		return builder.toString();
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the googleMaps
	 */
	public String getGoogleMaps() {
		return googleMaps;
	}

	/**
	 * @param googleMaps
	 *            the googleMaps to set
	 */
	public void setGoogleMaps(String googleMaps) {
		this.googleMaps = googleMaps;
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
