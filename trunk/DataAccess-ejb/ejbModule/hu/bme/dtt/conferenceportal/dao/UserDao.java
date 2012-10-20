package hu.bme.dtt.conferenceportal.dao;

import hu.bme.dtt.conferenceportal.entity.User;
import hu.futurion.mt.dao.GenericDao;

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
}
