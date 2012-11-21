/**
 * 
 */
package hu.bme.dtt.conferenceportal.dao;

import hu.bme.dtt.conferenceportal.entity.Conference;
import hu.futurion.mt.dao.GenericDao;

import java.util.List;

import javax.ejb.Local;

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

}
