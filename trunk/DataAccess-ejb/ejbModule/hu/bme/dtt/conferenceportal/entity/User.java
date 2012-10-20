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

import org.jboss.seam.annotations.security.management.UserFirstName;
import org.jboss.seam.annotations.security.management.UserLastName;
import org.jboss.seam.annotations.security.management.UserPassword;
import org.jboss.seam.annotations.security.management.UserPrincipal;
import org.jboss.seam.annotations.security.management.UserRoles;

/**
 * Egy felhasználót reprezentáló entitás.
 * 
 */
@Entity(name = "User_")
public class User implements Serializable {
	/**
	 * Serialization id.
	 */
	private static final long serialVersionUID = -5821534526043723923L;
	/**
	 * A felhasználó e-mail címe.
	 */
	@Column(name = "USER_EMAIL")
	private String eMail;
	/**
	 * A felhasználó keresztneve.
	 */
	@UserFirstName
	@Column(name = "USER_FIRST_NAME")
	private String firstName;
	/**
	 * A kulcs.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USER_ID")
	private Long id;
	/**
	 * A felhasználó vezetékneve.
	 */
	@UserLastName
	@Column(name = "USER_LAST_NAME")
	private String lastName;
	/**
	 * A felhasználó jelszava hashelve.
	 */
	@UserPassword(hash = "sha")
	@Column(name = "USER_PASSWORD", nullable = false)
	private String password;
	/**
	 * A felhasználó jogosultságai.
	 */
	@UserRoles
	@ManyToMany(targetEntity = hu.bme.dtt.conferenceportal.entity.Role.class, cascade = { CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinTable(name = "Role_User", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
	private Collection<Role> roles;
	/**
	 * A felhasználó telefonszáma.
	 */
	@Column(name = "USER_TEL")
	private String tel;
	/**
	 * A felhasználónév.
	 */
	@UserPrincipal
	@Column(name = "USER_NAME", nullable = false)
	private String userName;

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
		builder.append("User :\neMail=");
		builder.append(eMail);
		builder.append(",\nfirstName=");
		builder.append(firstName);
		builder.append(",\nid=");
		builder.append(id);
		builder.append(",\nlastName=");
		builder.append(lastName);
		builder.append(",\npassword=");
		builder.append(password);
		builder.append(",\nroles=");
		builder.append(roles);
		builder.append(",\ntel=");
		builder.append(tel);
		builder.append(",\nuserName=");
		builder.append(userName);
		return builder.toString();
	}

	/**
	 * @return the eMail
	 */
	public String geteMail() {
		return eMail;
	}

	/**
	 * @param eMail
	 *            the eMail to set
	 */
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
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
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the roles
	 */
	public Collection<Role> getRoles() {
		return roles;
	}

	/**
	 * @param roles
	 *            the roles to set
	 */
	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * @param tel
	 *            the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
