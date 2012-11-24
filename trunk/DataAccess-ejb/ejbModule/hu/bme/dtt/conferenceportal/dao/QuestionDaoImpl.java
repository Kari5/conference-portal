package hu.bme.dtt.conferenceportal.dao;

import hu.bme.dtt.conferenceportal.entity.Question;
import hu.futurion.mt.dao.GenericDaoImpl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

@Stateless(name = "questionDao")
public class QuestionDaoImpl extends GenericDaoImpl<Question> implements QuestionDao {
	private static final Logger LOGGER = Logger.getLogger(QuestionDaoImpl.class);
	@PersistenceContext(name = "ConferencePortal")
	private EntityManager entityManager;

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public Question update(Question question) {
		try {
			Question attachedQuestion = findByPrimaryKey(question.getId());
			attachedQuestion.setQuestion(question.getQuestion());
			attachedQuestion.setAnswers(question.getAnswers());
			return super.update(attachedQuestion);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}
}
