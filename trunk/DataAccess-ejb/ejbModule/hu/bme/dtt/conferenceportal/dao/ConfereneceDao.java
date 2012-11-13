/**
 * 
 */
package hu.bme.dtt.conferenceportal.dao;

import hu.bme.dtt.conferenceportal.entity.Conference;
import hu.futurion.mt.dao.GenericDao;

import javax.ejb.Local;

/**
 * @author Karcsi
 * 
 */
@Local
public interface ConfereneceDao extends GenericDao<Conference> {

	/**
	 * Egy meglévõ konferenciát ír felül.
	 * 
	 * @param conference
	 *            felülírandó konferencia
	 */
	public void updateConference(Conference conference);

}
