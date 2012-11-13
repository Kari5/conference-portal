package hu.bme.dtt.conferenceportal.dao;

import hu.bme.dtt.conferenceportal.entity.Program;
import hu.futurion.mt.dao.GenericDao;

import javax.ejb.Local;

@Local
public interface ProgramDao extends GenericDao<Program> {

	/**
	 * Egy létezõ programpontot módosít, vagy ha nem létezik a programpont,
	 * akkor elmenti azt.
	 * 
	 * @param p
	 *            módosítandó vagy mentendõ program.
	 * @return a módosított vagy elmentett program
	 */
	public Program updateOrSaveProgram(Program p);
}
