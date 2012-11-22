package hu.bme.dtt.conferenceportal.session;

import hu.bme.dtt.conferenceportal.dao.ConferenceDao;
import hu.bme.dtt.conferenceportal.dao.QuestionDao;
import hu.bme.dtt.conferenceportal.entity.Conference;
import hu.bme.dtt.conferenceportal.entity.Question;
import hu.bme.dtt.conferenceportal.util.StateHolder;

import java.util.ArrayList;
import java.util.Collection;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name(value = "questionBean")
@Scope(ScopeType.PAGE)
public class QuestionBackBean {
	private static final Logger LOGGER = Logger.getLogger(QuestionBackBean.class);
	/**
	 * A kiválasztott konferncia stateHolder-e.
	 */
	@In(create = true)
	private StateHolder<Conference> conferenceStateHolder;
	private String question;
	private String answer;
	private QuestionDao questionDao;
	private Question selectedQuestion;
	private ConferenceDao conferenceDao;

	@Create
	public void onCreate() {
		try {
			questionDao = InitialContext.doLookup("ConferencePortal-ear/questionDao/local");
			conferenceDao = (ConferenceDao) InitialContext
					.doLookup("ConferencePortal-ear/conferenceDao/local");
		} catch (NamingException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	public void saveQuestion() throws Exception {
		if (question != null) {
			LOGGER.debug("Saving question");
			Question question = new Question();
			question.setQuestion(this.question);
			questionDao.save(question);
			Conference selected = conferenceStateHolder.getSelected();
			Collection<Question> questions = selected.getQuestions();
			questions.add(question);
			selected.setQuestions(questions);
			conferenceDao.update(selected);
			this.question = null;
		}
	}

	public void saveAnswer() throws Exception {
		LOGGER.debug("Saving answer");
		ArrayList<String> answers = new ArrayList<String>();
		answers.add(answer);
		selectedQuestion.setAnswers(answers);
		questionDao.update(selectedQuestion);
	}

	public void changeSelectedQuestion(Question question) {
		selectedQuestion = question;
	}

	/**
	 * @return the selectedQuestion
	 */
	public Question getSelectedQuestion() {
		return selectedQuestion;
	}

	/**
	 * @param selectedQuestion
	 *            the selectedQuestion to set
	 */
	public void setSelectedQuestion(Question selectedQuestion) {
		this.selectedQuestion = selectedQuestion;
	}

	/**
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * @param question
	 *            the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}

	/**
	 * @return the answer
	 */
	public String getAnswer() {
		return answer;
	}

	/**
	 * @param answer
	 *            the answer to set
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}
}
