package hu.bme.dtt.conferenceportal.session;

import hu.bme.dtt.conferenceportal.dao.ArticleDao;
import hu.bme.dtt.conferenceportal.dao.ConferenceDao;
import hu.bme.dtt.conferenceportal.dao.LocationDao;
import hu.bme.dtt.conferenceportal.dao.TagDao;
import hu.bme.dtt.conferenceportal.dao.UserDao;
import hu.bme.dtt.conferenceportal.entity.Article;
import hu.bme.dtt.conferenceportal.entity.Conference;
import hu.bme.dtt.conferenceportal.entity.Location;
import hu.bme.dtt.conferenceportal.entity.Program;
import hu.bme.dtt.conferenceportal.entity.Tag;
import hu.bme.dtt.conferenceportal.util.StateContainer;
import hu.bme.dtt.conferenceportal.util.StateHolder;

import java.util.ArrayList;
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
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.security.Identity;

@Name("editConferenceBackBean")
@Scope(ScopeType.PAGE)
public class EditConferenceBackBean {

	/**
	 * A kiválasztott konferncia stateHolder-e.
	 */
	@In(create = true)
	private StateHolder<Conference> conferenceStateHolder;

	/**
	 * A szerkesztésre fentartott konferencia.
	 */
	private Conference conference;

	/**
	 * Konferencia táblához dao.
	 */
	ConferenceDao conferenceDao;

	/**
	 * Logiaki változó, ha új konfernecia lesz mentve, akkor igaz, különben
	 * hamis.
	 */
	private boolean newConference;

	/** Az összes elérhetõ tag. */
	@In(create = true)
	private StateContainer<Tag> tagsStateContainer;

	/** A tag-ek selectItem-ként. */
	private List<SelectItem> tagsSelectItems;

	/** Kiválasztott tag-ek. */
	private List<String> selectedTags;

	/** Az összes elérhetõ cikk. */
	@In(create = true)
	private StateContainer<Article> articlesStateContainer;

	/** Cikkek selectItem-ként. */
	private List<SelectItem> articlesSeletcItems;

	/** Kiválasztott cikk id-ik stringként. */
	private List<String> selectedArticles;

	/** Tag-ek kezeléséhez dao. */
	private TagDao tagDao;

	/** Új tag elméntéséhez név. */
	private String tagName;

	/** Programok listája. */
	private List<Program> programs;

	/** Szerkesztésre kiválasztott program. */
	@In(create = true)
	private StateHolder<Program> selectedProgramStateHolder;
	/**
	 * A létezõ helyszínek.
	 */
	@In(create = true)
	private StateContainer<Location> locationsStateContainer;

	/**
	 * A létezõ helyszínek select item-ként.
	 */
	private List<SelectItem> locationSelectItems;

	/**
	 * A kiválasztott helyszín.
	 */
	private String selectedLocationId;
	/**
	 * A helszínek DAO-ja.
	 */
	private LocationDao locationDao;

	/**
	 * Új helyszín mentéséhez location objektum.
	 */
	private Location newLocation;

	/**
	 * Szerkesztés alatt álló program ideiglenes tárolóhelye. Arra a célra van,
	 * ha a változásokat mégsem akarja elmenteni a felhasználó.
	 */
	private Program oldProgram;

	/**
	 * A szerkesztés alatt álló program indexe.
	 */
	private Integer oldProgramIndex;

	/** Logoláshoz logger. */
	private static final Logger logger = Logger
			.getLogger(EditConferenceBackBean.class);

	/**
	 * Init függvény, ami eldönti, hogy új konferncia lesz létrehozva, vagy egy
	 * meglévõt kell szerkeszteni.
	 */
	@Create
	public void init() {
		logger.debug("init meghívódott");
		newLocation = new Location();
		if (conferenceStateHolder.getSelected() != null) {
			conference = conferenceStateHolder.getSelected();
			programs = (List<Program>) conference.getPrograms();
			newConference = false;
		} else {
			conference = new Conference();
			programs = new ArrayList<Program>();
			newConference = true;
		}

		try {
			tagDao = (TagDao) InitialContext
					.doLookup("ConferencePortal-ear/tagDao/local");
			conferenceDao = (ConferenceDao) InitialContext
					.doLookup("ConferencePortal-ear/conferenceDao/local");
			locationDao = (LocationDao) InitialContext
					.doLookup("ConferencePortal-ear/locationDao/local");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		makeTagSelectItems();
		makeArticleSelectitems();
		makeLocationSelectItems();
		selectedTags = new ArrayList<String>();
		selectedArticles = new ArrayList<String>();
	}

	/**
	 * Létrehozza a helyszín select itemeket.
	 */
	private void makeLocationSelectItems() {
		logger.debug("makeLocationSelectItems meghívódott");
		locationSelectItems = new ArrayList<SelectItem>();
		for (Location loc : locationsStateContainer.getList()) {
			locationSelectItems.add(new SelectItem(loc.getId().toString(), loc
					.getName() + "(" + loc.getAddress() + ")"));
		}
	}

	/**
	 * Elment egy új helyszínt, frissíti az elérhetõ helyszínek listáját, majd
	 * elkészíti a selectItemeket belõlük.
	 */
	public void saveNewLocation() {
		newLocation = locationDao.saveLocation(newLocation);
		locationsStateContainer.setList(locationDao.getAllLocation());
		makeLocationSelectItems();
	}

	/**
	 * Beállítja a helyszínt.
	 * 
	 * @throws Exception
	 *             lekérdezési hiba.
	 */
	public void changeLocation() {
		logger.info("Change location id=" + selectedLocationId);
		Location location;
		try {
			location = locationDao.findByPrimaryKey(Long
					.parseLong(selectedLocationId));
		} catch (NumberFormatException e) {
			logger.error(e.getMessage());
			return;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return;
		}
		logger.info("Új helyszín: " + location.getName());
		conference.setLocation(location);
		logger.info("Új helyszín rendben beállítva!");
	}

	/**
	 * Elment egy új tag-et, és frissíti a listákat.
	 */
	public void saveNewTag() {
		tagDao.saveTag(tagName);
		tagsStateContainer.setList(tagDao.getAllTags());
		makeTagSelectItems();
	}

	/**
	 * Beállítja a kiválasztott tag-eket. A nevük alapján lekéri adatbázisból
	 * õket.
	 */
	public void changeTags() {
		logger.debug("changeTags meghívódott");
		List<Tag> tags = new ArrayList<Tag>();

		for (String tagNames : selectedTags) {
			try {
				tags.add(tagDao.searchTag(tagNames));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.debug(tags.size() + " db. tag hozzáadva.");
		conference.setTags(tags);
	}

	/** A tagStateContainer-bõl összeállítja a tagsSelectItems listát. */
	public void makeTagSelectItems() {
		logger.debug("makeTagsSelectItems meghívódott");
		tagsSelectItems = new ArrayList<SelectItem>();
		for (Tag t : tagsStateContainer.getList()) {
			tagsSelectItems.add(new SelectItem(t.getName(), t.getName()));
		}
	}

	public void makeArticleSelectitems() {
		logger.info("makeArticleSelectItems meghívódott");
		articlesSeletcItems = new ArrayList<SelectItem>();
		for (Article a : articlesStateContainer.getList()) {
			articlesSeletcItems.add(new SelectItem(a.getId(), a.getTitle()));
		}
	}

	/**
	 * Átállítja a kiválasztott programot. A kiválasztott programot ideiglenesen
	 * kiveszi a programok listájából, hogy mikor elmentjük, ne kerüljön be
	 * kétszer ugyan az a programpont.
	 * 
	 * @param program
	 *            az új kiválasztott program
	 */
	public void changeSelectedProgram(Program program) {
		logger.debug("changeSelected program meghívódott.");
		if (programs.contains(program)) {
			oldProgramIndex = programs.indexOf(program);
			programs.remove(program);
			logger.debug("Kiválasztott program ideiglenesen eltávolítva a listából.");
		}
		selectedProgramStateHolder.setSelected(program);
		oldProgram = program.clone();
		logger.debug("Új kiválasztott program: " + selectedProgramStateHolder);
		logger.debug("oldProgramIndex: " + oldProgramIndex + ", oldProgram: "
				+ oldProgram);
	}

	/**
	 * A kiválasztott program új program lesz.
	 */
	public void newSelectedProgram() {
		logger.debug("A kiválasztott program új program lesz.");
		oldProgramIndex = null;
		oldProgram = null;
		selectedProgramStateHolder.setSelected(new Program());
		logger.debug("oldProgramIndex: " + oldProgramIndex + ", oldProgram: "
				+ oldProgram);
	}

	/**
	 * Berakja a programok listájába az aktuálisan szerkesztett programot.
	 */
	public void saveProgram() {
		if (oldProgramIndex == null) {
			programs.add(getSelectedProgram());
		} else {
			programs.add(oldProgramIndex, getSelectedProgram());
		}
	}

	/**
	 * A szerkesztés alatt álló programot rollBack-eli. Visszaírja az
	 * ideiglenesen letárolt oldProgramot.
	 */
	public void rollBackProgram() {
		if (oldProgram != null) {
			programs.add(oldProgramIndex, oldProgram.clone());
		}
	}

	/**
	 * Elmenti az újonnan kiválasztott cikkeket.
	 */
	public void saveArticles() {
		conference.setArticles(selectedArticlesFromDb());
	}

	/**
	 * Elmenti a szerkesztés alatt álló konferenciát. Kezeli, hogy új
	 * konferenciáról van-e szó, vagy egy régit kell-e updatelni. Végül
	 * beállítja a conferenceStateHolder-be az elmentett konferenciát.
	 */
	public void saveConference() {
		logger.info("saveConference meghívódott.");
		if ((conference.getTitle() == null) || conference.getTitle().isEmpty()) {
			FacesMessages.instance().add("Cím megadása kötelezõ!");
			return;
		}
		if ((conference.getShortTitle() == null)
				|| conference.getShortTitle().isEmpty()) {
			FacesMessages.instance().add("Rövid cím megadása kötelezõ!");
			// FacesMessages.instance().add(msg);
			return;
		}

		if (newConference) {
			try {
				UserDao userDao = InitialContext
						.doLookup("ConferencePortal-ear/userDao/local");
				conference.setOwner(userDao.getUser(Identity.instance()
						.getCredentials().getUsername()));
			} catch (NamingException e) {
				logger.error(e.getMessage(), e);
			}
			conference.setPrograms(programs);
			conferenceDao.save(conference);
			newConference = false;
			FacesMessages.instance().add("Sikeres mentés!");
		} else {
			conference.setPrograms(programs);
			conferenceDao.updateConference(conference);
			FacesMessages.instance().add("Sikeres módosítás!");
		}
		conferenceStateHolder.setSelected(conference);
	}

	/**
	 * A selectedArticles listán végigiterál, és adatbázisból lekéri az id-khoz
	 * tartozó cikkeket.
	 * 
	 * @return a lekérdezett cikkek.
	 */
	private List<Article> selectedArticlesFromDb() {
		logger.info("Cikkek lekérdezése adatbázisból! SelectedArticles száma: "
				+ selectedArticles.size());
		List<Article> result = new ArrayList<Article>();
		ArticleDao articleDao;
		try {
			articleDao = InitialContext
					.doLookup("ConferencePortal-ear/articleDao/local");
		} catch (NamingException e) {
			logger.error(e.getMessage(), e);
			return result;
		}
		logger.info("selectedArticles.get(0)=" + selectedArticles.get(0));
		logger.info("selectedArticles.get(0)="
				+ selectedArticles.get(0).getClass());
		for (String id : selectedArticles) {
			logger.info(id.toString() + ". azonosítójú cikk keresése!");
			Long id_Long = Long.parseLong(id);
			try {
				result.add(articleDao.findByPrimaryKey(id_Long));
			} catch (Exception e) {
				logger.error(id + ". azonosítóju cikk nem található!");
			}
		}
		logger.info(result.size() + " db. cikk lekérdezve!");
		return result;
	}

	/**
	 * Teszteléshez. Kilogolja az elmentendõ konferencia részleteit.
	 */
	public void print() {
		logger.info(conference.toString());
		conferenceStateHolder.setSelected(conference);
	}

	/**
	 * Teszteléshez, megváltoztatja, hogy új konferenciáról van-e szó.
	 */
	public void changeNew() {
		newConference = !newConference;
	}

	/**
	 * Teszthez, kiírja, ha változott a kiválasztott cikkek.
	 */
	public void articleChange() {
		logger.info(selectedArticles.size() + " db. cikk van kiválasztva!");
		// if (selectedArticles.size() > 0) {
		// logger.info("selectedArticles.get(0)=" + selectedArticles.get(0));
		// }
	}

	/**
	 * Teszthez, kiírja, ha változott a kiválasztott hely.
	 */
	public void locationChange() {
		logger.info(selectedLocationId + ". hely kiválasztva!");
		// if (selectedArticles.size() > 0) {
		// logger.info("selectedArticles.get(0)=" + selectedArticles.get(0));
		// }
	}

	/**
	 * @return the conference
	 */
	public Conference getConference() {
		return conference;
	}

	/**
	 * @param conference
	 *            the conference to set
	 */
	public void setConference(Conference conference) {
		this.conference = conference;
	}

	/**
	 * @return the newConfernece
	 */
	public boolean isNewConference() {
		return newConference;
	}

	/**
	 * @param newConference
	 *            the newConfernece to set
	 */
	public void setNewConference(boolean newConference) {
		this.newConference = newConference;
	}

	/**
	 * @return the tagsSelectItems
	 */
	public List<SelectItem> getTagsSelectItems() {
		return tagsSelectItems;
	}

	/**
	 * @param tagsSelectItems
	 *            the tagsSelectItems to set
	 */
	public void setTagsSelectItems(List<SelectItem> tagsSelectItems) {
		this.tagsSelectItems = tagsSelectItems;
	}

	/**
	 * @return the selectedTags
	 */
	public List<String> getSelectedTags() {
		return selectedTags;
	}

	/**
	 * @param selectedTags
	 *            the selectedTags to set
	 */
	public void setSelectedTags(List<String> selectedTags) {
		this.selectedTags = selectedTags;
	}

	/**
	 * @return the tagName
	 */
	public String getTagName() {
		return tagName;
	}

	/**
	 * @param tagName
	 *            the tagName to set
	 */
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	/**
	 * @return the programs
	 */
	public List<Program> getPrograms() {
		return programs;
	}

	/**
	 * @param programs
	 *            the programs to set
	 */
	public void setPrograms(List<Program> programs) {
		this.programs = programs;
	}

	/**
	 * @return the selectedProgram
	 */
	public Program getSelectedProgram() {
		return selectedProgramStateHolder.getSelected();
	}

	/**
	 * @param selectedProgram
	 *            the selectedProgram to set
	 */
	public void setSelectedProgram(Program selectedProgram) {
		selectedProgramStateHolder.setSelected(selectedProgram);
	}

	/**
	 * @return the articlesStateContainer
	 */
	public StateContainer<Article> getArticlesStateContainer() {
		return articlesStateContainer;
	}

	/**
	 * @param articlesStateContainer
	 *            the articlesStateContainer to set
	 */
	public void setArticlesStateContainer(
			StateContainer<Article> articlesStateContainer) {
		this.articlesStateContainer = articlesStateContainer;
	}

	/**
	 * @return the articlesSeletcItems
	 */
	public List<SelectItem> getArticlesSeletcItems() {
		return articlesSeletcItems;
	}

	/**
	 * @param articlesSeletcItems
	 *            the articlesSeletcItems to set
	 */
	public void setArticlesSeletcItems(List<SelectItem> articlesSeletcItems) {
		this.articlesSeletcItems = articlesSeletcItems;
	}

	/**
	 * @return the selectedArticles
	 */
	public List<String> getSelectedArticles() {
		return selectedArticles;
	}

	/**
	 * @param selectedArticles
	 *            the selectedArticles to set
	 */
	public void setSelectedArticles(List<String> selectedArticles) {
		this.selectedArticles = selectedArticles;
	}

	/**
	 * @param locationsStateContainer
	 *            the locationsStateContainer to set
	 */
	public void setLocationsStateContainer(
			StateContainer<Location> locationsStateContainer) {
		this.locationsStateContainer = locationsStateContainer;
	}

	/**
	 * @return the locationsStateContainer
	 */
	public StateContainer<Location> getLocationsStateContainer() {
		return locationsStateContainer;
	}

	/**
	 * @param locationSelectItems
	 *            the locationSeletcItems to set
	 */
	public void setLocationSelectItems(List<SelectItem> locationSelectItems) {
		this.locationSelectItems = locationSelectItems;
	}

	/**
	 * @return the locationSeletcItems
	 */
	public List<SelectItem> getLocationSelectItems() {
		return locationSelectItems;
	}

	/**
	 * @return the newLocation
	 */
	public Location getNewLocation() {
		return newLocation;
	}

	/**
	 * @param newLocation
	 *            the newLocation to set
	 */
	public void setNewLocation(Location newLocation) {
		this.newLocation = newLocation;
	}

	/**
	 * @param selectedLocationId
	 *            the selectedLocationId to set
	 */
	public void setSelectedLocationId(String selectedLocationId) {
		this.selectedLocationId = selectedLocationId;
	}

	/**
	 * @return the selectedLocationId
	 */
	public String getSelectedLocationId() {
		return selectedLocationId;
	}

}
