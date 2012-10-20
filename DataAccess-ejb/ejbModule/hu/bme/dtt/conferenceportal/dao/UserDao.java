package hu.bme.dtt.conferenceportal.dao;

import hu.bme.dtt.conferenceportal.entity.Role;
import hu.bme.dtt.conferenceportal.entity.User;
import hu.bme.dtt.conferenceportal.exception.ConferencePortalDataException;
import hu.futurion.mt.dao.GenericDao;

import java.util.Collection;

/**
 * A UserDaoImpl interfésze.
 * 
 * @see UserDaoImpl
 * 
 */
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

	/**
	 * A <code>userNameExists</code> függvény visszadja, hogy szerepel-e már a
	 * paraméterben megadott felhasználónév az adatbázisban.
	 * 
	 * @param userName
	 *            A keresett felhasználónév.
	 * @return Létezik-a már.
	 */
	boolean userNameExists(final String userName);

	/**
	 * A <code>getUsersByRoles</code> függvény a egy vagy több paraméterben
	 * megadott jogosultsághoz tartozó felhasználók listáját adja vissza.
	 * 
	 * @param roles
	 *            A jogosultságok.
	 * @return A felhasználók listája.
	 * @throws ConferencePortalDataException
	 */
	Collection<User> getUsersByRoles(final Role... roles)
			throws ConferencePortalDataException;
}
