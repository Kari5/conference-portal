package hu.bme.dtt.conferenceportal.session;

import hu.bme.dtt.conferenceportal.dao.ConferenceDao;
import hu.bme.dtt.conferenceportal.entity.Conference;
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

@Name("homePageBackBean")
@Scope(ScopeType.PAGE)
public class HomePageBackBean {

	/**
	 * logoláshoz logger.
	 */
	public static final Logger logger = Logger
			.getLogger(HomePageBackBean.class);
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
	 * Inicializáló függvény. Adatbázisból behúzza az elérhetõ konferncia
	 * neveket.
	 */
	@Create
	public void init() {
		conferences = new ArrayList<Conference>();
		try {
			conferenceDao = InitialContext
					.doLookup("ConferencePortal-ear/conferenceDao/local");

			conferences = conferenceDao.conferences();
			logger.info("Visszakapott konf. száma: " + conferences.size());
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	public void changeSelectedConference(Conference conference) {
		this.conferenceStateHolder.setSelected(conference);
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
