package hu.bme.dtt.conferenceportal.session;

import hu.bme.dtt.conferenceportal.dao.ConferenceDao;
import hu.bme.dtt.conferenceportal.entity.Conference;
import hu.bme.dtt.conferenceportal.entity.Tag;
import hu.bme.dtt.conferenceportal.util.StateContainer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

/**
 * Keresési oldal háttérlogikája.
 */
@Name(value = "searchConference")
@Scope(ScopeType.PAGE)
public class SearchConferenceBean {
	/**
	 * Naplózáshoz.
	 */
	final static Logger LOGGER = Logger.getLogger(SearchConferenceBean.class);
	/**
	 * Konferenciák adatbázisának kezeléséhez dao.
	 */
	private ConferenceDao conferenceDao;
	/**
	 * Cím ami alapján keresünk.
	 */
	private String searchTitle;
	/**
	 * Keresés eredménye.
	 */
	private List<Conference> searchResults;
	/**
	 * Az összes elérhetõ tag.
	 */
	@In(create = true)
	private StateContainer<Tag> tagsStateContainer;
	/**
	 * A tag-ek selectItem-ként.
	 */
	private List<SelectItem> tagsSelectItems;
	/**
	 * Kiválasztott tag-ek.
	 */
	private List<String> selectedTags;
	/**
	 * Kezdeti idõpont.
	 */
	private Date startDate;
	/**
	 * Végsõ idõpont.
	 */
	private Date endDate;
	/**
	 * Helyszín.
	 */
	private String location;

	/**
	 * Komponens létrehozásakor hívodik meg.
	 */
	@Create
	public void onCreate() {
		try {
			searchTitle = null;
			searchResults = new ArrayList<Conference>();
			setTagsSelectItems(new ArrayList<SelectItem>());
			for (Tag t : tagsStateContainer.getList()) {
				getTagsSelectItems().add(new SelectItem(t.getName(), t.getName()));
			}
			setSelectedTags(new ArrayList<String>());
			conferenceDao = InitialContext.doLookup("ConferencePortal-ear/conferenceDao/local");
		} catch (NamingException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	public String concatSelectedTags() {
		String result = null;
		for (String tag : selectedTags) {
			if (result == null) {
				result = tag;
			} else {
				result += "," + tag;
			}
		}
		return result;
	}

	public void executeSearch() throws NamingException {
		setSearchResults(conferenceDao.searchConferences(searchTitle, selectedTags, startDate,
				endDate, location));
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

	/**
	 * @param selectedTags
	 *            the selectedTags to set
	 */
	public void setSelectedTags(List<String> selectedTags) {
		this.selectedTags = selectedTags;
	}

	/**
	 * @return the selectedTags
	 */
	public List<String> getSelectedTags() {
		return selectedTags;
	}

	/**
	 * @param tagsSelectItems
	 *            the tagsSelectItems to set
	 */
	public void setTagsSelectItems(List<SelectItem> tagsSelectItems) {
		this.tagsSelectItems = tagsSelectItems;
	}

	/**
	 * @return the tagsSelectItems
	 */
	public List<SelectItem> getTagsSelectItems() {
		return tagsSelectItems;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
}
