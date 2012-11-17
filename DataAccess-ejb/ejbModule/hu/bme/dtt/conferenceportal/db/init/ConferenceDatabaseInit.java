package hu.bme.dtt.conferenceportal.db.init;

import org.jboss.annotation.ejb.Management;

/**
 * Az adatbázis inicializáló <code>Service</code> interfésze az alkalmazás
 * szerver felé.
 */
@Management
public interface ConferenceDatabaseInit {
	/**
	 * A <code>Service</code> létrehozásakor meghívott függvény.
	 * 
	 * @throws Exception
	 *             hiba
	 */
	void create() throws Exception;

	/**
	 * A <code>Service</code> életciklusának végén meghívott függvény.
	 */
	void destroy();

	/**
	 * A <code>Service</code> elindításakor meghívott függvény.
	 * 
	 * @throws Exception
	 *             hiba
	 */
	void start() throws Exception;

	/**
	 * A <code>Service</code> megállításakor meghívott függvény.
	 */
	void stop();
}
