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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result
				+ ((googleMaps == null) ? 0 : googleMaps.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (!(obj instanceof Location)) {
			return false;
		}
		Location other = (Location) obj;
		if (address == null) {
			if (other.address != null) {
				return false;
			}
		} else if (!address.equals(other.address)) {
			return false;
		}
		if (googleMaps == null) {
			if (other.googleMaps != null) {
				return false;
			}
		} else if (!googleMaps.equals(other.googleMaps)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

}
