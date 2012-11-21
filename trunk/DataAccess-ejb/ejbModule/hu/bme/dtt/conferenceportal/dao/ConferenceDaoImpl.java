package hu.bme.dtt.conferenceportal.dao;

import hu.bme.dtt.conferenceportal.entity.Article;
import hu.bme.dtt.conferenceportal.entity.Conference;
import hu.bme.dtt.conferenceportal.entity.Location;
import hu.bme.dtt.conferenceportal.entity.Program;
import hu.bme.dtt.conferenceportal.entity.Tag;
import hu.bme.dtt.conferenceportal.entity.User;
import hu.futurion.mt.dao.GenericDaoImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

@Stateless(name = "conferenceDao")
public class ConferenceDaoImpl extends GenericDaoImpl<Conference> implements ConferenceDao {

	public static final Logger LOGGER = Logger.getLogger(ConferenceDaoImpl.class);

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
		LOGGER.debug("updateConference method called id=" + conference.getId());
		List<Tag> tags = new ArrayList<Tag>();
		List<Program> programs = new ArrayList<Program>();
		List<Article> articles = new ArrayList<Article>();
		List<User> participants = new ArrayList<User>();
		try {
			if ((conference.getTags() != null) && !conference.getTags().isEmpty()) {
				LOGGER.debug("Attaching tags");
				TagDao tagDao = InitialContext.doLookup("ConferencePortal-ear/tagDao/local");
				for (Tag t : conference.getTags()) {
					tags.add(tagDao.findByPrimaryKey(t.getId()));
				}
			}

			if ((conference.getPrograms() != null) && !conference.getPrograms().isEmpty()) {
				LOGGER.debug("Attaching programs");
				ProgramDao programDao = InitialContext
						.doLookup("ConferencePortal-ear/programDao/local");
				for (Program p : conference.getPrograms()) {
					programs.add(programDao.updateOrSaveProgram(p));
				}
			}

			if ((conference.getArticles() != null) && !conference.getArticles().isEmpty()) {
				LOGGER.debug("Attaching articles");
				ArticleDao articleDao = InitialContext
						.doLookup("ConferencePortal-ear/articleDao/local");
				for (Article article : conference.getArticles()) {
					articles.add(articleDao.findByPrimaryKey(article.getId()));
				}
			}

			if ((conference.getParticipants() != null) && !conference.getParticipants().isEmpty()) {
				LOGGER.debug("Attaching participants");
				UserDao userDao = InitialContext.doLookup("ConferencePortal-ear/userDao/local");
				for (User user : conference.getParticipants()) {
					participants.add(userDao.findByPrimaryKey(user.getId()));
				}
			}

			Location location = null;
			if (conference.getLocation() != null) {
				LOGGER.debug("Attaching location");
				LocationDao locationDao = InitialContext
						.doLookup("ConferencePortal-ear/locationDao/local");
				location = locationDao.findByPrimaryKey(conference.getLocation().getId());
			}

			LOGGER.debug("Updating conference!");
			Conference attachedConference = findByPrimaryKey(conference.getId());
			attachedConference.setArticles(articles);
			attachedConference.setLocation(location);
			attachedConference.setTags(tags);
			attachedConference.setDescription(conference.getDescription());
			attachedConference.setEndDate(conference.getEndDate());
			attachedConference.setStartDate(conference.getStartDate());
			attachedConference.setTitle(conference.getTitle());
			attachedConference.setShortTitle(conference.getShortTitle());
			attachedConference.setPrograms(programs);
			// FIXME: Programok elmentését javítani!
			attachedConference.setSummary(conference.getSummary());
			attachedConference.setParticipants(participants);
			entityManager.flush();
			LOGGER.debug("Update finished!");
		} catch (NamingException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Conference addParticipant(Long conferenceId, String userName) {
		LOGGER.debug("Adding participant: " + userName);
		Conference conference = null;
		try {
			UserDao userDao = InitialContext.doLookup("ConferencePortal-ear/userDao/local");
			User user = userDao.getUser(userName);
			conference = findByPrimaryKey(conferenceId);
			Collection<User> participants = conference.getParticipants();
			participants.add(user);
			conference.setParticipants(participants);
			entityManager.flush();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		LOGGER.debug("Participant added!");
		return conference;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Conference removeParticipant(Long conferenceId, String userName) {
		LOGGER.debug("Removing participant: " + userName);
		Conference conference = null;
		try {
			UserDao userDao = InitialContext.doLookup("ConferencePortal-ear/userDao/local");
			User user = userDao.getUser(userName);
			conference = findByPrimaryKey(conferenceId);
			Collection<User> participants = conference.getParticipants();
			participants.remove(user);
			conference.setParticipants(participants);
			entityManager.flush();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		LOGGER.debug("Participant removed!");
		return conference;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean save(Conference conference) {
		List<Tag> tags = new ArrayList<Tag>();
		List<Program> programs = new ArrayList<Program>();
		try {
			TagDao tagDao = InitialContext.doLookup("ConferencePortal-ear/tagDao/local");
			for (Tag t : conference.getTags()) {
				tags.add(tagDao.findByPrimaryKey(t.getId()));
			}
			ProgramDao programDao = InitialContext
					.doLookup("ConferencePortal-ear/programDao/local");
			for (Program p : conference.getPrograms()) {
				programs.add(programDao.updateOrSaveProgram(p));
			}
		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		conference.setTags(tags);
		conference.setPrograms(programs);
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
		return (List<Conference>) executeQueryMultipleResult("FROM Conference");
	}

}
