package hu.bme.dtt.conferenceportal.dao;

import hu.bme.dtt.conferenceportal.entity.AccountPermission;
import hu.bme.dtt.conferenceportal.exception.ConferencePortalDataException;

import org.jboss.annotation.ejb.Local;

/**
 * Interf�sz enged�lyek kezel�s�hez.
 * 
 * @see AccountPermissionDaoImpl
 */
@Local
public interface AccountPermissionDao {

	/**
	 * Meng�zi, hogy szerepel e az adott enged�ly az adatb�zisban.
	 * 
	 * @param permission
	 *            Az enged�ly.
	 * @return Tal�lt-e ilyen enged�lyt. (true tal�lt, false nem)
	 */
	boolean checkPermission(final AccountPermission permission);

	/**
	 * Elmenti a param�terben kapott <code>AccountPermission</code> objektumot.
	 * 
	 * @param permission
	 *            A menteni k�v�nt objektum.
	 * @throws ConferencePortalDataException
	 *             Nem megfelel� enged�ly eset�n.
	 */
	void savePermission(final AccountPermission permission) throws ConferencePortalDataException;
}
