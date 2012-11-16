package hu.bme.dtt.conferenceportal.dao;

import hu.bme.dtt.conferenceportal.entity.Conference;
import hu.bme.dtt.conferenceportal.entity.Tag;
import hu.futurion.mt.dao.GenericDaoImpl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

@Stateless(name = "conferenceDao")
public class ConferenceDaoImpl extends GenericDaoImpl<Conference> implements
		ConferenceDao {

	public static final Logger logger = Logger
			.getLogger(ConferenceDaoImpl.class);

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
	public void updateConference(Conference conference) {
		List<Tag> tags = new ArrayList<Tag>();
		// List<Program> programs = new ArrayList<Program>();
		try {
			TagDao tagDao = InitialContext
					.doLookup("ConferencePortal-ear/tagDao/local");
			for (Tag t : conference.getTags()) {
				tags.add(tagDao.findByPrimaryKey(t.getId()));
			}

			// ProgramDao programDao = InitialContext
			// .doLookup("ConferencePortal-ear/programDao/local");
			// for (Program p : conference.getPrograms()) {
			// programs.add(programDao.updateOrSaveProgram(p));
			// }

			Conference attachedConference = findByPrimaryKey(conference.getId());
			// TODO:[Kari] valószínüleg majd a articels-t is attache-olni kell.
			attachedConference.setArticles(conference.getArticles());
			// TODO:[Kari] valószínüleg majd a location-t is attache-olni kell.
			attachedConference.setLocation(conference.getLocation());
			attachedConference.setTags(tags);
			attachedConference.setDescription(conference.getDescription());
			attachedConference.setEndDate(conference.getEndDate());
			attachedConference.setStartDate(conference.getStartDate());
			attachedConference.setTitle(conference.getTitle());
			attachedConference.setShortTitle(conference.getShortTitle());
			// attachedConference.setPrograms(conference.getPrograms());
			// FIXME: Programok elmentését javítani!
			attachedConference.setSummary(conference.getSummary());

		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean save(Conference conference) {
		List<Tag> tags = new ArrayList<Tag>();
		// List<Program> programs = new ArrayList<Program>();
		try {
			TagDao tagDao = InitialContext
					.doLookup("ConferencePortal-ear/tagDao/local");
			for (Tag t : conference.getTags()) {
				tags.add(tagDao.findByPrimaryKey(t.getId()));
			}
			// ProgramDao programDao = InitialContext
			// .doLookup("ConferencePortal-ear/programDao/local");
			// for (Program p : conference.getPrograms()) {
			// programs.add(programDao.updateOrSaveProgram(p));
			// }
		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		conference.setTags(tags);
		conference.setPrograms(null);
		// FIXME: Programok mentését javítani
		entityManager.persist(conference);
		return true;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Conference> conferences() {
		return (List<Conference>)executeQueryMultipleResult("FROM Conference");
	}

}
