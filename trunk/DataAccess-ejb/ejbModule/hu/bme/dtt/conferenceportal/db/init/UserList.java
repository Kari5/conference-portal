package hu.bme.dtt.conferenceportal.db.init;

import hu.bme.dtt.conferenceportal.entity.User;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Inicializáló XML olvasását elõsegítõ objektum. A beolvasott felhasználókat
 * tárolja.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "userlist")
public class UserList {
	/**
	 * A beolvasott felhasználók.
	 */
	@XmlElement(name = "user")
	private List<User> users;

	/**
	 * @return the users
	 */
	public List<User> getUsers() {
		return users;
	}

	/**
	 * @param users
	 *            the users to set
	 */
	public void setUsers(List<User> users) {
		this.users = users;
	}
}
