package hu.bme.dtt.conferenceportal.factory;

import hu.bme.dtt.conferenceportal.dao.ArticleDao;
import hu.bme.dtt.conferenceportal.dao.LocationDao;
import hu.bme.dtt.conferenceportal.dao.TagDao;
import hu.bme.dtt.conferenceportal.entity.Article;
import hu.bme.dtt.conferenceportal.entity.Conference;
import hu.bme.dtt.conferenceportal.entity.Location;
import hu.bme.dtt.conferenceportal.entity.Program;
import hu.bme.dtt.conferenceportal.entity.Question;
import hu.bme.dtt.conferenceportal.entity.Tag;
import hu.bme.dtt.conferenceportal.util.SimplePdf;
import hu.bme.dtt.conferenceportal.util.StateContainer;
import hu.bme.dtt.conferenceportal.util.StateHolder;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name("factoryMethods")
@Scope(ScopeType.STATELESS)
public class FactoryMethods {

	@Factory(value = "conferenceStateHolder", scope = ScopeType.CONVERSATION)
	public StateHolder<Conference> conferenceStateHolder() {
		return new StateHolder<Conference>();
	}

	@Factory(value = "tagsStateContainer", scope = ScopeType.CONVERSATION)
	public StateContainer<Tag> tagsStateContainer() {
		StateContainer<Tag> result = new StateContainer<Tag>();
		try {
			TagDao tagDao = (TagDao) InitialContext.doLookup("ConferencePortal-ear/tagDao/local");
			result.setList(tagDao.getAllTags());
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Factory(value = "locationsStateContainer", scope = ScopeType.CONVERSATION)
	public StateContainer<Location> locationsStateContainer() {
		StateContainer<Location> result = new StateContainer<Location>();
		try {
			LocationDao locationDao = (LocationDao) InitialContext
					.doLookup("ConferencePortal-ear/locationDao/local");
			result.setList(locationDao.getAllLocation());
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Factory(value = "articlesStateContainer", scope = ScopeType.CONVERSATION)
	public StateContainer<Article> articlesStateContainer() {
		StateContainer<Article> result = new StateContainer<Article>();
		try {
			ArticleDao articleDao = (ArticleDao) InitialContext
					.doLookup("ConferencePortal-ear/articleDao/local");
			result.setList(articleDao.getAllArticles());
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Factory(value = "selectedProgramStateHolder", scope = ScopeType.PAGE)
	public StateHolder<Program> selectedProgramStateHolder() {
		return new StateHolder<Program>();
	}

	@Factory(value = "selectedQuestionStateHolder", scope = ScopeType.PAGE)
	public StateHolder<Question> selectedQuestionStateHolder() {
		return new StateHolder<Question>();
	}

	@Factory(value = "simplePdfStateHolder", scope = ScopeType.PAGE)
	public StateHolder<SimplePdf> simplePdfStateHolder() {
		return new StateHolder<SimplePdf>();
	}

}
