package hu.bme.dtt.conferenceportal.dao;

import hu.bme.dtt.conferenceportal.entity.Role;
import hu.bme.dtt.conferenceportal.entity.User;
import hu.bme.dtt.conferenceportal.exception.ConferencePortalDataException;
import hu.futurion.mt.dao.GenericDao;

import java.util.Collection;

/**
 * A UserDaoImpl interf�sze.
 * 
 * @see UserDaoImpl
 * 
 */
public interface UserDao extends GenericDao<User> {
	/**
	 * A <code>getUser</code> f�ggv�ny lek�rdezi az adatb�zisb�l a User rekordot
	 * a megadott userName �s password param�terek alapj�n.
	 * 
	 * @param userName
	 *            A felhaszn�l�n�v
	 * @param password
	 *            A jelsz�
	 * @return A User objektum
	 */
	User getUser(final String userName, final String password);

	/**
	 * A <code>userNameExists</code> f�ggv�ny visszadja, hogy szerepel-e m�r a
	 * param�terben megadott felhaszn�l�n�v az adatb�zisban.
	 * 
	 * @param userName
	 *            A keresett felhaszn�l�n�v.
	 * @return L�tezik-a m�r.
	 */
	boolean userNameExists(final String userName);

	/**
	 * A <code>getUsersByRoles</code> f�ggv�ny a egy vagy t�bb param�terben
	 * megadott jogosults�ghoz tartoz� felhaszn�l�k list�j�t adja vissza.
	 * 
	 * @param roles
	 *            A jogosults�gok.
	 * @return A felhaszn�l�k list�ja.
	 * @throws ConferencePortalDataException
	 */
	Collection<User> getUsersByRoles(final Role... roles)
			throws ConferencePortalDataException;
}
