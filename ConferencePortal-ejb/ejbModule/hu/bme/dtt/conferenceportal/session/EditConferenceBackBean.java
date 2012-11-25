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
	 * A kiv�lasztott konferncia stateHolder-e.
	 */
	@In(create = true)
	private StateHolder<Conference> conferenceStateHolder;

	/**
	 * A szerkeszt�sre fentartott konferencia.
	 */
	private Conference conference;

	/**
	 * Konferencia t�bl�hoz dao.
	 */
	ConferenceDao conferenceDao;

	/**
	 * Logiaki v�ltoz�, ha �j konfernecia lesz mentve, akkor igaz, k�l�nben
	 * hamis.
	 */
	private boolean newConference;

	/** Az �sszes el�rhet� tag. */
	@In(create = true)
	private StateContainer<Tag> tagsStateContainer;

	/** A tag-ek selectItem-k�nt. */
	private List<SelectItem> tagsSelectItems;

	/** Kiv�lasztott tag-ek. */
	private List<String> selectedTags;

	/** Az �sszes el�rhet� cikk. */
	@In(create = true)
	private StateContainer<Article> articlesStateContainer;

	/** Cikkek selectItem-k�nt. */
	private List<SelectItem> articlesSeletcItems;

	/** Kiv�lasztott cikk id-ik stringk�nt. */
	private List<String> selectedArticles;

	/** Tag-ek kezel�s�hez dao. */
	private TagDao tagDao;

	/** �j tag elm�nt�s�hez n�v. */
	private String tagName;

	/** Programok list�ja. */
	private List<Program> programs;

	/** Szerkeszt�sre kiv�lasztott program. */
	@In(create = true)
	private StateHolder<Program> selectedProgramStateHolder;
	/**
	 * A l�tez� helysz�nek.
	 */
	@In(create = true)
	private StateContainer<Location> locationsStateContainer;

	/**
	 * A l�tez� helysz�nek select item-k�nt.
	 */
	private List<SelectItem> locationSelectItems;

	/**
	 * A kiv�lasztott helysz�n.
	 */
	private String selectedLocationId;
	/**
	 * A helsz�nek DAO-ja.
	 */
	private LocationDao locationDao;

	/**
	 * �j helysz�n ment�s�hez location objektum.
	 */
	private Location newLocation;

	/**
	 * Szerkeszt�s alatt �ll� program ideiglenes t�rol�helye. Arra a c�lra van,
	 * ha a v�ltoz�sokat m�gsem akarja elmenteni a felhaszn�l�.
	 */
	private Program oldProgram;

	/**
	 * A szerkeszt�s alatt �ll� program indexe.
	 */
	private Integer oldProgramIndex;

	/** Logol�shoz logger. */
	private static final Logger logger = Logger
			.getLogger(EditConferenceBackBean.class);

	/**
	 * Init f�ggv�ny, ami eld�nti, hogy �j konferncia lesz l�trehozva, vagy egy
	 * megl�v�t kell szerkeszteni.
	 */
	@Create
	public void init() {
		logger.debug("init megh�v�dott");
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
	 * L�trehozza a helysz�n select itemeket.
	 */
	private void makeLocationSelectItems() {
		logger.debug("makeLocationSelectItems megh�v�dott");
		locationSelectItems = new ArrayList<SelectItem>();
		for (Location loc : locationsStateContainer.getList()) {
			locationSelectItems.add(new SelectItem(loc.getId().toString(), loc
					.getName() + "(" + loc.getAddress() + ")"));
		}
	}

	/**
	 * Elment egy �j helysz�nt, friss�ti az el�rhet� helysz�nek list�j�t, majd
	 * elk�sz�ti a selectItemeket bel�l�k.
	 */
	public void saveNewLocation() {
		newLocation = locationDao.saveLocation(newLocation);
		locationsStateContainer.setList(locationDao.getAllLocation());
		makeLocationSelectItems();
	}

	/**
	 * Be�ll�tja a helysz�nt.
	 * 
	 * @throws Exception
	 *             lek�rdez�si hiba.
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
		logger.info("�j helysz�n: " + location.getName());
		conference.setLocation(location);
		logger.info("�j helysz�n rendben be�ll�tva!");
	}

	/**
	 * Elment egy �j tag-et, �s friss�ti a list�kat.
	 */
	public void saveNewTag() {
		tagDao.saveTag(tagName);
		tagsStateContainer.setList(tagDao.getAllTags());
		makeTagSelectItems();
	}

	/**
	 * Be�ll�tja a kiv�lasztott tag-eket. A nev�k alapj�n lek�ri adatb�zisb�l
	 * �ket.
	 */
	public void changeTags() {
		logger.debug("changeTags megh�v�dott");
		List<Tag> tags = new ArrayList<Tag>();

		for (String tagNames : selectedTags) {
			try {
				tags.add(tagDao.searchTag(tagNames));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.debug(tags.size() + " db. tag hozz�adva.");
		conference.setTags(tags);
	}

	/** A tagStateContainer-b�l �ssze�ll�tja a tagsSelectItems list�t. */
	public void makeTagSelectItems() {
		logger.debug("makeTagsSelectItems megh�v�dott");
		tagsSelectItems = new ArrayList<SelectItem>();
		for (Tag t : tagsStateContainer.getList()) {
			tagsSelectItems.add(new SelectItem(t.getName(), t.getName()));
		}
	}

	public void makeArticleSelectitems() {
		logger.info("makeArticleSelectItems megh�v�dott");
		articlesSeletcItems = new ArrayList<SelectItem>();
		for (Article a : articlesStateContainer.getList()) {
			articlesSeletcItems.add(new SelectItem(a.getId(), a.getTitle()));
		}
	}

	/**
	 * �t�ll�tja a kiv�lasztott programot. A kiv�lasztott programot ideiglenesen
	 * kiveszi a programok list�j�b�l, hogy mikor elmentj�k, ne ker�lj�n be
	 * k�tszer ugyan az a programpont.
	 * 
	 * @param program
	 *            az �j kiv�lasztott program
	 */
	public void changeSelectedProgram(Program program) {
		logger.debug("changeSelected program megh�v�dott.");
		if (programs.contains(program)) {
			oldProgramIndex = programs.indexOf(program);
			programs.remove(program);
			logger.debug("Kiv�lasztott program ideiglenesen elt�vol�tva a list�b�l.");
		}
		selectedProgramStateHolder.setSelected(program);
		oldProgram = program.clone();
		logger.debug("�j kiv�lasztott program: " + selectedProgramStateHolder);
		logger.debug("oldProgramIndex: " + oldProgramIndex + ", oldProgram: "
				+ oldProgram);
	}

	/**
	 * A kiv�lasztott program �j program lesz.
	 */
	public void newSelectedProgram() {
		logger.debug("A kiv�lasztott program �j program lesz.");
		oldProgramIndex = null;
		oldProgram = null;
		selectedProgramStateHolder.setSelected(new Program());
		logger.debug("oldProgramIndex: " + oldProgramIndex + ", oldProgram: "
				+ oldProgram);
	}

	/**
	 * Berakja a programok list�j�ba az aktu�lisan szerkesztett programot.
	 */
	public void saveProgram() {
		if (oldProgramIndex == null) {
			programs.add(getSelectedProgram());
		} else {
			programs.add(oldProgramIndex, getSelectedProgram());
		}
	}

	/**
	 * A szerkeszt�s alatt �ll� programot rollBack-eli. Vissza�rja az
	 * ideiglenesen let�rolt oldProgramot.
	 */
	public void rollBackProgram() {
		if (oldProgram != null) {
			programs.add(oldProgramIndex, oldProgram.clone());
		}
	}

	/**
	 * Elmenti az �jonnan kiv�lasztott cikkeket.
	 */
	public void saveArticles() {
		conference.setArticles(selectedArticlesFromDb());
	}

	/**
	 * Elmenti a szerkeszt�s alatt �ll� konferenci�t. Kezeli, hogy �j
	 * konferenci�r�l van-e sz�, vagy egy r�git kell-e updatelni. V�g�l
	 * be�ll�tja a conferenceStateHolder-be az elmentett konferenci�t.
	 */
	public void saveConference() {
		logger.info("saveConference megh�v�dott.");
		if ((conference.getTitle() == null) || conference.getTitle().isEmpty()) {
			FacesMessages.instance().add("C�m megad�sa k�telez�!");
			return;
		}
		if ((conference.getShortTitle() == null)
				|| conference.getShortTitle().isEmpty()) {
			FacesMessages.instance().add("R�vid c�m megad�sa k�telez�!");
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
			FacesMessages.instance().add("Sikeres ment�s!");
		} else {
			conference.setPrograms(programs);
			conferenceDao.updateConference(conference);
			FacesMessages.instance().add("Sikeres m�dos�t�s!");
		}
		conferenceStateHolder.setSelected(conference);
	}

	/**
	 * A selectedArticles list�n v�gigiter�l, �s adatb�zisb�l lek�ri az id-khoz
	 * tartoz� cikkeket.
	 * 
	 * @return a lek�rdezett cikkek.
	 */
	private List<Article> selectedArticlesFromDb() {
		logger.info("Cikkek lek�rdez�se adatb�zisb�l! SelectedArticles sz�ma: "
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
			logger.info(id.toString() + ". azonos�t�j� cikk keres�se!");
			Long id_Long = Long.parseLong(id);
			try {
				result.add(articleDao.findByPrimaryKey(id_Long));
			} catch (Exception e) {
				logger.error(id + ". azonos�t�ju cikk nem tal�lhat�!");
			}
		}
		logger.info(result.size() + " db. cikk lek�rdezve!");
		return result;
	}

	/**
	 * Tesztel�shez. Kilogolja az elmentend� konferencia r�szleteit.
	 */
	public void print() {
		logger.info(conference.toString());
		conferenceStateHolder.setSelected(conference);
	}

	/**
	 * Tesztel�shez, megv�ltoztatja, hogy �j konferenci�r�l van-e sz�.
	 */
	public void changeNew() {
		newConference = !newConference;
	}

	/**
	 * Teszthez, ki�rja, ha v�ltozott a kiv�lasztott cikkek.
	 */
	public void articleChange() {
		logger.info(selectedArticles.size() + " db. cikk van kiv�lasztva!");
		// if (selectedArticles.size() > 0) {
		// logger.info("selectedArticles.get(0)=" + selectedArticles.get(0));
		// }
	}

	/**
	 * Teszthez, ki�rja, ha v�ltozott a kiv�lasztott hely.
	 */
	public void locationChange() {
		logger.info(selectedLocationId + ". hely kiv�lasztva!");
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
