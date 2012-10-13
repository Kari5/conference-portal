package hu.bme.dtt.conferenceportal.entity;

import java.io.Serializable;
import java.util.Collection;

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

@Entity(name = "User_")
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5821534526043723923L;
	@Column(name = "USER_EMAIL")
	private String eMail;
	@Column(name = "USER_FIRST_NAME")
	private String firstName;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USER_ID")
	private Long id;
	@Column(name = "USER_LAST_NAME")
	private String lastName;
	@Column(name = "USER_PASSWORD")
	private String password;
	@ManyToMany(targetEntity = hu.bme.dtt.conferenceportal.entity.Role.class,
			cascade = { CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinTable(name = "Role_User", joinColumns = @JoinColumn(name = "USER_ID"),
			inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
	private Collection<Role> roles;
	@Column(name = "USER_TEL")
	private String tel;
	@Column(name = "USER_NAME")
	private String userName;

	public String geteMail() {
		return eMail;
	}

	public String getFirstName() {
		return firstName;
	}

	public Long getId() {
		return id;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPassword() {
		return password;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public String getTel() {
		return tel;
	}

	public String getUserName() {
		return userName;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
