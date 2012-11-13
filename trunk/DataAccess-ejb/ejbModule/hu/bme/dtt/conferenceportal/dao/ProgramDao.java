package hu.bme.dtt.conferenceportal.dao;

import hu.bme.dtt.conferenceportal.entity.Program;
import hu.futurion.mt.dao.GenericDao;

import javax.ejb.Local;

@Local
public interface ProgramDao extends GenericDao<Program> {

	/**
	 * Egy l�tez� programpontot m�dos�t, vagy ha nem l�tezik a programpont,
	 * akkor elmenti azt.
	 * 
	 * @param p
	 *            m�dos�tand� vagy mentend� program.
	 * @return a m�dos�tott vagy elmentett program
	 */
	public Program updateOrSaveProgram(Program p);
}
