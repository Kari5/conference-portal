/**
 * 
 */
package hu.bme.dtt.conferenceportal.dao;

import hu.bme.dtt.conferenceportal.entity.Conference;
import hu.futurion.mt.dao.GenericDao;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.naming.NamingException;

/**
 * @author Karcsi
 * 
 */
@Local
public interface ConferenceDao extends GenericDao<Conference> {

	/**
	 * Egy meglévõ konferenciát ír felül.
	 * 
	 * @param conference
	 *            felülírandó konferencia
	 */
	public void updateConference(Conference conference);

	/**
	 * Visszaadja az összes konferenciát.
	 * 
	 * @return konferenciák.
	 */
	public List<Conference> conferences();

	/**
	 * Hozzáadja a konfernciához a felhasználót.
	 * 
	 * @param conferenceId
	 *            A kenferencia azonosítója.
	 * @param userName
	 *            A felhasználónév.
	 * @return A megváltoztatott konferencia objektum.
	 */
	public Conference addParticipant(final Long conferenceId, final String userName);

	/**
	 * Kiveszia a konfernciából a felhasználót.
	 * 
	 * @param conferenceId
	 *            A kenferencia azonosítója.
	 * @param userName
	 *            A felhasználónév.
	 * @return A megváltoztatott konferencia objektum.
	 */
	public Conference removeParticipant(final Long conferenceId, final String userName);

	/**
	 * A parrméterek alapján keres a konferenciák között. A címnél whitespace
	 * karakterek alapján szétszedi, és mindegyik alapján keres. A két megadott
	 * idõpont közötti konferenciákat nézi. Ha valamely paraméter NULL akkor nem
	 * veszi figyelembe.
	 * 
	 * @param title
	 *            A cím amí alapján keres.
	 * @param tags
	 *            A kulcsszavak.
	 * @param startDate
	 *            A kezdeti dátum.
	 * @param endDate
	 *            A vég dátum.
	 * @param location
	 *            A helyszín.
	 * @return A találati lista.
	 * @throws NamingException
	 */
	public List<Conference> searchConferences(final String title, final List<String> tags,
			final Date startDate, final Date endDate, final String location) throws NamingException;

}
