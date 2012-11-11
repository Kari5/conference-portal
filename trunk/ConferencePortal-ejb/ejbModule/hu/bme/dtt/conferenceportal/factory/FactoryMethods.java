package hu.bme.dtt.conferenceportal.factory;

import hu.bme.dtt.conferenceportal.entity.Conference;
import hu.bme.dtt.conferenceportal.util.StateHolder;

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

}
