/**
 * 
 */
package hu.bme.dtt.conferenceportal.dao;

import java.util.List;

import hu.bme.dtt.conferenceportal.entity.Conference;
import hu.futurion.mt.dao.GenericDao;

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
	 * @return konferenci�k.
	 */
	public List<Conference> conferences();

}
