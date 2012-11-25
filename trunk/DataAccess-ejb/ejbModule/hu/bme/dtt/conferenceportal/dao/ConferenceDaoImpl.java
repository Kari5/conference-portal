package hu.bme.dtt.conferenceportal.dao;

import hu.bme.dtt.conferenceportal.entity.Article;
import hu.bme.dtt.conferenceportal.entity.Conference;
import hu.bme.dtt.conferenceportal.entity.Location;
import hu.bme.dtt.conferenceportal.entity.Program;
import hu.bme.dtt.conferenceportal.entity.Question;
import hu.bme.dtt.conferenceportal.entity.Tag;
import hu.bme.dtt.conferenceportal.entity.User;
import hu.futurion.mt.dao.GenericDaoImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		List<Question> questions = new ArrayList<Question>();
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

			if ((conference.getQuestions() != null) && !conference.getQuestions().isEmpty()) {
				LOGGER.debug("Attaching questions");
				QuestionDao questionDao = InitialContext
						.doLookup("ConferencePortal-ear/questionDao/local");
				for (Question question : conference.getQuestions()) {
					questions.add(questionDao.findByPrimaryKey(question.getId()));
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
			attachedConference.setQuestions(questions);
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

	/**
	 * {@inheritDoc}
	 * 
	 * @throws NamingException
	 */
	@Override
	public List<Conference> searchConferences(String title, List<String> tags, Date startDate,
			Date endDate, String location) throws NamingException {
		LOGGER.debug("Searching conferences! title=" + title + " tags="
				+ Arrays.toString(tags.toArray()) + " startDate=" + startDate + " endDate="
				+ endDate + " location=" + location);
		if (((title == null) || title.isEmpty()) && ((tags == null) || tags.isEmpty())
				&& (startDate == null) && (endDate == null)
				&& ((location == null) || location.isEmpty())) {
			LOGGER.debug("No search conditions, returning all.");
			return conferences();
		}

		List<Conference> searchResult = new ArrayList<Conference>();
		StringBuilder queryString = new StringBuilder();
		queryString.append("FROM Conference c ");
		if (startDate != null) {
			queryString.append("WHERE c.startDate >= ? ");
			if (endDate != null) {
				queryString.append("AND c.endDate <= ? ");
				searchResult = (List<Conference>) executeQueryMultipleResult(
						queryString.toString(), startDate, endDate);
			} else {
				searchResult = (List<Conference>) executeQueryMultipleResult(
						queryString.toString(), startDate);
			}
		} else {
			if (endDate != null) {
				queryString.append("WHERE c.endDate <= ? ");
				searchResult = (List<Conference>) executeQueryMultipleResult(
						queryString.toString(), endDate);
			} else {
				searchResult = (List<Conference>) executeQueryMultipleResult(queryString.toString());
			}
		}
		LOGGER.debug("Search result size by dates: " + searchResult.size());
		if (!searchResult.isEmpty()) {
			Map<Integer, List<Conference>> rankedSearchResult = new HashMap<Integer, List<Conference>>();
			String[] splitTitle = null;
			if (title != null) {
				splitTitle = title.split("\\s+");
			}
			String[] splitLocation = null;
			if (location != null) {
				splitLocation = location.split("\\s+");
			}
			for (Conference conference : searchResult) {
				int rank = 0;
				if (splitTitle != null) {
					for (String titlePart : splitTitle) {
						if ((((conference.getShortTitle() != null)
								&& (conference.getTitle() != null) && (conference.getShortTitle()
								.toLowerCase().contains(titlePart.toLowerCase()))) || conference
								.getTitle().toLowerCase().contains(titlePart.toLowerCase()))) {
							rank++;
						}
					}
				}
				if (splitLocation != null) {
					for (String locationPart : splitLocation) {
						if ((conference.getLocation() != null)
								&& (conference.getLocation().getName().toLowerCase()
										.contains(locationPart.toLowerCase()) || conference
										.getLocation().getAddress().toLowerCase()
										.contains(locationPart.toLowerCase()))) {
							rank++;
						}
					}
				}
				if ((tags != null) && !tags.isEmpty()) {
					TagDao tagDao = (TagDao) InitialContext
							.doLookup("ConferencePortal-ear/tagDao/local");
					for (String tagName : tags) {
						Tag tag = tagDao.searchTag(tagName);
						if ((conference.getTags() != null) && conference.getTags().contains(tag)) {
							rank++;
						}
					}
				}
				if (rank > 0) {
					if (rankedSearchResult.containsKey(rank)) {
						List<Conference> rankedConferences = rankedSearchResult.get(rank);
						rankedConferences.add(conference);
						rankedSearchResult.put(rank, rankedConferences);
					} else {
						ArrayList<Conference> rankedConferences = new ArrayList<Conference>();
						rankedConferences.add(conference);
						rankedSearchResult.put(rank, rankedConferences);
					}
				}
				LOGGER.debug("Conference rank: id=" + conference.getId() + " rank=" + rank);
			}
			List<Conference> finalSearchResult = new ArrayList<Conference>();
			Integer maxRank = (splitLocation != null ? splitLocation.length : 0)
					+ (splitTitle != null ? splitTitle.length : 0)
					+ (tags != null ? tags.size() : 0);
			for (int i = maxRank; i > 0; i--) {
				if (rankedSearchResult.containsKey(i)) {
					finalSearchResult.addAll(rankedSearchResult.get(i));
				}
			}
			return finalSearchResult;
		} else {
			return searchResult;
		}
	}
}
