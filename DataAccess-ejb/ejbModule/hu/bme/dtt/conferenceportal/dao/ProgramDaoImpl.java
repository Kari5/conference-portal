/**
 * 
 */
package hu.bme.dtt.conferenceportal.dao;

import hu.bme.dtt.conferenceportal.entity.Program;
import hu.futurion.mt.dao.GenericDaoImpl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

/**
 * @author Karcsi
 * 
 */
@Stateless(name = "programDao")
public class ProgramDaoImpl extends GenericDaoImpl<Program> implements
		ProgramDao {
	private static final Logger logger = Logger.getLogger(ProgramDaoImpl.class);
	/**
	 * ...
	 */
	@PersistenceContext(name = "ConferencePortal")
	private EntityManager entityManager;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Program updateOrSaveProgram(Program p) {
		boolean saveNew = false;
		Program temp = (Program) executeQuerySingleResult(
				"FROM Program AS P WHERE P.id=? ", p.getId());
		if (temp == null) {
			logger.info("Program nem található, újként lesz elmentve. ");
			temp = new Program();
			saveNew = true;
		}
		temp.setDescription(p.getDescription());
		temp.setEnd(p.getEnd());
		temp.setStart(p.getStart());
		temp.setTitle(p.getTitle());
		if (saveNew) {
			save(temp);
			logger.info(temp.getTitle() + " programpont mentve.");
		}
		return temp;
	}
}
