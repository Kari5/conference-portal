package hu.bme.dtt.conferenceportal.dao;

import hu.bme.dtt.conferenceportal.entity.AccountPermission;
import hu.bme.dtt.conferenceportal.exception.ConferencePortalDataException;

import org.jboss.annotation.ejb.Local;

/**
 * Interfész engedélyek kezeléséhez.
 * 
 * @see AccountPermissionDaoImpl
 */
@Local
public interface AccountPermissionDao {

	/**
	 * Mengézi, hogy szerepel e az adott engedély az adatbázisban.
	 * 
	 * @param permission
	 *            Az engedély.
	 * @return Talált-e ilyen engedélyt. (true talált, false nem)
	 */
	boolean checkPermission(final AccountPermission permission);

	/**
	 * Elmenti a paraméterben kapott <code>AccountPermission</code> objektumot.
	 * 
	 * @param permission
	 *            A menteni kívánt objektum.
	 * @throws ConferencePortalDataException
	 *             Nem megfelelõ engedély esetén.
	 */
	void savePermission(final AccountPermission permission) throws ConferencePortalDataException;
}
