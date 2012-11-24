package hu.bme.dtt.conferenceportal.session;

import hu.bme.dtt.conferenceportal.dao.ConferenceDao;
import hu.bme.dtt.conferenceportal.entity.Conference;
import hu.bme.dtt.conferenceportal.entity.User;

import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.security.Identity;

@Name(value = "listSubs")
public class ListSubsBean {
	final static Logger LOGGER = Logger.getLogger(ListSubsBean.class);
	/**
	 * Konferenciák adatbázisának kezeléséhez dao.
	 */
	ConferenceDao conferenceDao;

	@Create
	public void onCreate() {
		try {
			conferenceDao = InitialContext.doLookup("ConferencePortal-ear/conferenceDao/local");
		} catch (NamingException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	public List<Conference> getSubbedConferences() {
		List<Conference> subbedConferences = new ArrayList<Conference>();
		if (Identity.instance().isLoggedIn()) {
			List<Conference> conferences = conferenceDao.conferences();
			for (Conference conference : conferences) {
				for (User user : conference.getParticipants()) {
					if (user.getUserName().equals(
							Identity.instance().getCredentials().getUsername())) {
						subbedConferences.add(conference);
						break;
					}
				}
			}
		}
		return subbedConferences;
	}
}
