package hu.bme.dtt.conferenceportal.entity;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity(name = "Role_")
public class Role implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1924718093608833635L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ROLE_ID")
	private Long id;
	@Column(name = "ROLE_NAME")
	private String name;
	@ManyToMany(targetEntity = hu.bme.dtt.conferenceportal.entity.User.class,
			cascade = { CascadeType.MERGE })
	@JoinTable(name = "Role_User", joinColumns = @JoinColumn(name = "ROLE_ID"),
			inverseJoinColumns = @JoinColumn(name = "USER_ID"))
	private Collection<User> users;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Collection<User> getUsers() {
		return users;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUsers(Collection<User> users) {
		this.users = users;
	}
}
