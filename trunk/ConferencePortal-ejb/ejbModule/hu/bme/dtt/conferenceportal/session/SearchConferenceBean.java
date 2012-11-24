package hu.bme.dtt.conferenceportal.session;

import hu.bme.dtt.conferenceportal.dao.ConferenceDao;
import hu.bme.dtt.conferenceportal.entity.Conference;

import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name(value = "searchConference")
@Scope(ScopeType.PAGE)
public class SearchConferenceBean {
	final static Logger LOGGER = Logger.getLogger(SearchConferenceBean.class);
	/**
	 * Konferenciák adatbázisának kezeléséhez dao.
	 */
	private ConferenceDao conferenceDao;
	private String searchTitle;
	private List<Conference> searchResults;

	@Create
	public void onCreate() {
		try {
			conferenceDao = InitialContext.doLookup("ConferencePortal-ear/conferenceDao/local");
		} catch (NamingException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	public void executeSearch() {
		setSearchResults(new ArrayList<Conference>());
		List<Conference> conferences = conferenceDao.conferences();
		String[] splitSearchTitle = searchTitle.split("\\s+");
		for (Conference conference : conferences) {
			for (String s : splitSearchTitle) {
				if (conference.getShortTitle().toLowerCase().contains(s.toLowerCase())
						|| conference.getTitle().toLowerCase().contains(s.toLowerCase())) {
					getSearchResults().add(conference);
					break;
				}
			}
		}
	}

	/**
	 * @param searchTitle
	 *            the searchTitle to set
	 */
	public void setSearchTitle(String searchTitle) {
		this.searchTitle = searchTitle;
	}

	/**
	 * @return the searchTitle
	 */
	public String getSearchTitle() {
		return searchTitle;
	}

	/**
	 * @param searchResults
	 *            the searchResults to set
	 */
	public void setSearchResults(List<Conference> searchResults) {
		this.searchResults = searchResults;
	}

	/**
	 * @return the searchResults
	 */
	public List<Conference> getSearchResults() {
		return searchResults;
	}
}
