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
	 * Egy megl�v� konferenci�t �r fel�l.
	 * 
	 * @param conference
	 *            fel�l�rand� konferencia
	 */
	public void updateConference(Conference conference);

	/**
	 * Visszaadja az �sszes konferenci�t.
	 * 
	 * @return konferenci�k.
	 */
	public List<Conference> conferences();

	/**
	 * Hozz�adja a konfernci�hoz a felhaszn�l�t.
	 * 
	 * @param conferenceId
	 *            A kenferencia azonos�t�ja.
	 * @param userName
	 *            A felhaszn�l�n�v.
	 * @return A megv�ltoztatott konferencia objektum.
	 */
	public Conference addParticipant(final Long conferenceId, final String userName);

	/**
	 * Kiveszia a konfernci�b�l a felhaszn�l�t.
	 * 
	 * @param conferenceId
	 *            A kenferencia azonos�t�ja.
	 * @param userName
	 *            A felhaszn�l�n�v.
	 * @return A megv�ltoztatott konferencia objektum.
	 */
	public Conference removeParticipant(final Long conferenceId, final String userName);

}
