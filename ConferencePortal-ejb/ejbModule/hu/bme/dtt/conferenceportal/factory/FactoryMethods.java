package hu.bme.dtt.conferenceportal.factory;

import hu.bme.dtt.conferenceportal.dao.TagDao;
import hu.bme.dtt.conferenceportal.entity.Conference;
import hu.bme.dtt.conferenceportal.entity.Program;
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
			TagDao tagDao = (TagDao) InitialContext
					.doLookup("ConferencePortal-ear/tagDao/local");
			result.setList(tagDao.getAllTags());
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

	@Factory(value = "simplePdfStateHolder", scope = ScopeType.PAGE)
	public StateHolder<SimplePdf> simplePdfStateHolder() {
		return new StateHolder<SimplePdf>();
	}

}
