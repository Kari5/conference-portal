package hu.bme.dtt.conferenceportal.db.init;

import org.jboss.annotation.ejb.Management;

/**
 * Az adatb�zis inicializ�l� <code>Service</code> interf�sze az alkalmaz�s
 * szerver fel�.
 */
@Management
public interface ConferenceDatabaseInit {
	/**
	 * A <code>Service</code> l�trehoz�sakor megh�vott f�ggv�ny.
	 * 
	 * @throws Exception
	 *             hiba
	 */
	void create() throws Exception;

	/**
	 * A <code>Service</code> �letciklus�nak v�g�n megh�vott f�ggv�ny.
	 */
	void destroy();

	/**
	 * A <code>Service</code> elind�t�sakor megh�vott f�ggv�ny.
	 * 
	 * @throws Exception
	 *             hiba
	 */
	void start() throws Exception;

	/**
	 * A <code>Service</code> meg�ll�t�sakor megh�vott f�ggv�ny.
	 */
	void stop();
}
