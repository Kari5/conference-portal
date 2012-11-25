package hu.bme.dtt.conferenceportal.session;

import hu.bme.dtt.conferenceportal.dao.ConferenceDao;
import hu.bme.dtt.conferenceportal.dao.UserDao;
import hu.bme.dtt.conferenceportal.entity.Conference;
import hu.bme.dtt.conferenceportal.entity.User;
import hu.bme.dtt.conferenceportal.util.StateHolder;

import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.security.Identity;

@Name("homePageBackBean")
@Scope(ScopeType.PAGE)
public class HomePageBackBean {

	/**
	 * logoláshoz logger.
	 */
	public static final Logger logger = Logger.getLogger(HomePageBackBean.class);
	/**
	 * A kiválasztott konferencia stateHolder-e.
	 */
	@In(create = true)
	StateHolder<Conference> conferenceStateHolder;

	/**
	 * Az elérhetõ konfernciák.
	 */
	List<Conference> conferences;

	/**
	 * Konferenciák adatbázisának kezeléséhez dao.
	 */
	ConferenceDao conferenceDao;
	/**
	 * Felhasználó lekérdezéséhez.
	 */
	UserDao userDao;

	/**
	 * Inicializáló függvény. Adatbázisból behúzza az elérhetõ konferncia
	 * neveket.
	 */
	@Create
	public void init() {
		conferences = new ArrayList<Conference>();
		try {
			conferenceDao = InitialContext.doLookup("ConferencePortal-ear/conferenceDao/local");
			userDao = InitialContext.doLookup("ConferencePortal-ear/userDao/local");

			conferences = conferenceDao.conferences();
			logger.info("Visszakapott konf. száma: " + conferences.size());
		} catch (NamingException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * Átirányít a konferencia készítõ oldalra.
	 * 
	 * @return navigation string
	 */
	public String createConference() {
		logger.debug("redirect to create conference page");
		conferenceStateHolder.setSelected(null);
		return "createConference";
	}

	/**
	 * Visszadja, hogy a bejelentkezett felhasználó a tulajdonosa a kiválasztott
	 * konferenciának.
	 * 
	 * @return true ha igen, false ha nem.
	 */
	public boolean checkOwner() {
		boolean result = false;
		if (Identity.instance().isLoggedIn()) {
			if (conferenceStateHolder.getSelected().getOwner().getUserName()
					.equals(Identity.instance().getCredentials().getUsername())) {
				result = true;
			}
		}
		return result;
	}

	/**
	 * Visszadja, hogy a felhasználó jelentkezett-e a konferenciára.
	 * 
	 * @return true ha igen, false ha nem
	 */
	public boolean checkSubscription() {
		boolean result = false;
		if (Identity.instance().isLoggedIn() && (conferenceStateHolder.getSelected() != null)
				&& (conferenceStateHolder.getSelected().getParticipants() != null)) {
			for (User user : conferenceStateHolder.getSelected().getParticipants()) {
				if (user.getUserName().equals(Identity.instance().getCredentials().getUsername())) {
					result = true;
					break;
				}
			}
		}
		return result;
	}

	/**
	 * Feliratkoztatja a felhasználót a konferenciára.
	 * 
	 * @return navigációs érték
	 */
	public String subscribe() {
		logger.debug("Subscribe to conference!");
		if (Identity.instance().isLoggedIn()) {
			Conference conference = conferenceDao.addParticipant(conferenceStateHolder
					.getSelected().getId(), Identity.instance().getCredentials().getUsername());
			if (conference != null) {
				conferenceStateHolder.setSelected(conference);
			}
			FacesMessages.instance().add("Subscribed to conference " + conference.getShortTitle());
			return "home";
		} else {
			return "login";
		}
	}

	/**
	 * Leiratkoztatja a felhasználót a konferenciáról.
	 * 
	 * @return navigációs érték
	 */
	public String unsubscribe() {
		logger.debug("Subscribe from conference!");
		if (Identity.instance().isLoggedIn()) {
			conferences.remove(conferenceStateHolder.getSelected());
			Conference conference = conferenceDao.removeParticipant(conferenceStateHolder
					.getSelected().getId(), Identity.instance().getCredentials().getUsername());
			if (conference != null) {
				conferenceStateHolder.setSelected(conference);
			}
			conferences.add(conferenceStateHolder.getSelected());
			FacesMessages.instance().add(
					"Unsubscribed from conference " + conference.getShortTitle());
			return "home";
		} else {
			return "login";
		}
	}

	// /**
	// * Egy azonosító alapján adatbázisból lekéri a konfernciát, és beállítja
	// kiválastott konferenciának.
	// * @param id
	// */
	// public void changeSelectedConference(Long id){
	// Conference conference;
	// try {
	// conference=conferenceDao.findByPrimaryKey(id);
	// } catch (Exception e) {
	// logger.error(id+". azonosítójú konferencia nem található!");
	// return;
	// }
	// changeSelectedConference(conference);
	// }

	/**
	 * Átállítja a kiválasztott konferenciát.
	 * 
	 * @param conference
	 *            beállítíandó konferencia.
	 */
	public String changeSelectedConference(Conference conference) {
		logger.debug("Changing selected conference id=" + conference.getId());
		conferenceStateHolder.setSelected(conference);
		return "home";
	}

	/**
	 * @return the conferences
	 */
	public List<Conference> getConferences() {
		return conferences;
	}

	/**
	 * @param conferences
	 *            the conferences to set
	 */
	public void setConferences(List<Conference> conferences) {
		this.conferences = conferences;
	}

}
