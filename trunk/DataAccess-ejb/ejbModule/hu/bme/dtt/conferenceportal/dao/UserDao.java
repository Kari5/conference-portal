package hu.bme.dtt.conferenceportal.dao;

import hu.bme.dtt.conferenceportal.entity.User;
import hu.futurion.mt.dao.GenericDao;

public interface UserDao extends GenericDao<User> {
	/**
	 * A <code>getUser</code> függvény lekérdezi az adatbázisból a User rekordot
	 * a megadott userName és password paraméterek alapján.
	 * 
	 * @param userName
	 *            A felhasználónév
	 * @param password
	 *            A jelszó
	 * @return A User objektum
	 */
	User getUser(final String userName, final String password);
}
