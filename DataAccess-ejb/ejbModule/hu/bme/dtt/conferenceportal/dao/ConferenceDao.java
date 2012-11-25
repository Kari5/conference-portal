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

	/**
	 * A parrm�terek alapj�n keres a konferenci�k k�z�tt. A c�mn�l whitespace
	 * karakterek alapj�n sz�tszedi, �s mindegyik alapj�n keres. A k�t megadott
	 * id�pont k�z�tti konferenci�kat n�zi. Ha valamely param�ter NULL akkor nem
	 * veszi figyelembe.
	 * 
	 * @param title
	 *            A c�m am� alapj�n keres.
	 * @param tags
	 *            A kulcsszavak.
	 * @param startDate
	 *            A kezdeti d�tum.
	 * @param endDate
	 *            A v�g d�tum.
	 * @param location
	 *            A helysz�n.
	 * @return A tal�lati lista.
	 * @throws NamingException
	 */
	public List<Conference> searchConferences(final String title, final List<String> tags,
			final Date startDate, final Date endDate, final String location) throws NamingException;

}
