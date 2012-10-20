package hu.bme.dtt.conferenceportal.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Location implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3993579242993428896L;
	@Column(name = "LOCATION_ADDRESS")
	private String address;
	@Column(name = "GOOGLE_MAPS")
	private String googleMaps;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "LOCATION_ID")
	private Long id;
	@Column(name = "LOCATION_NAME", nullable=false)
	private String name;

	public String getAddress() {
		return address;
	}

	public String getGoogleMaps() {
		return googleMaps;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setGoogleMaps(String googleMaps) {
		this.googleMaps = googleMaps;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
}
