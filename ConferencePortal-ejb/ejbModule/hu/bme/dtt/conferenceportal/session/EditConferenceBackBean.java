package hu.bme.dtt.conferenceportal.session;

import hu.bme.dtt.conferenceportal.dao.ConferenceDao;
import hu.bme.dtt.conferenceportal.dao.TagDao;
import hu.bme.dtt.conferenceportal.dao.UserDao;
import hu.bme.dtt.conferenceportal.entity.Article;
import hu.bme.dtt.conferenceportal.entity.Conference;
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
	private boolean newConfernece;

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

	/** Kiválasztott cikk id-ik. */
	private List<Long> selectedArticles;

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
		logger.info("init meghívódott");
		if (conferenceStateHolder.getSelected() != null) {
			conference = (Conference) conferenceStateHolder.getSelected();
			programs = (List<Program>) conference.getPrograms();
			newConfernece = false;
		} else {
			conference = new Conference();
			programs = new ArrayList<Program>();
			newConfernece = true;
		}

		try {
			tagDao = (TagDao) InitialContext
					.doLookup("ConferencePortal-ear/tagDao/local");
			conferenceDao = (ConferenceDao) InitialContext
					.doLookup("ConferencePortal-ear/conferenceDao/local");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		makeTagSelectItems();
		makeArticleSelectitems();
		selectedTags = new ArrayList<String>();
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
		logger.info("changeTags meghívódott");
		List<Tag> tags = new ArrayList<Tag>();

		for (String tagNames : selectedTags) {
			try {
				tags.add(tagDao.searchTag(tagNames));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info(tags.size() + " db. tag hozzáadva.");
		conference.setTags(tags);
	}

	/** A tagStateContainer-bõl összeállítja a tagsSelectItems listát. */
	public void makeTagSelectItems() {
		logger.info("makeTagsSelectItems meghívódott");
		tagsSelectItems = new ArrayList<SelectItem>();
		for (Tag t : tagsStateContainer.getList()) {
			this.tagsSelectItems.add(new SelectItem(t.getName(), t.getName()));
		}
	}

	public void makeArticleSelectitems() {
		logger.info("makeArticleSelectItems meghívódott");
		articlesSeletcItems = new ArrayList<SelectItem>();
		for (Article a : articlesStateContainer.getList()) {
			this.articlesSeletcItems
					.add(new SelectItem(a.getId(), a.getTitle()));
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
		logger.info("changeSelected program meghívódott.");
		if (programs.contains(program)) {
			oldProgramIndex = programs.indexOf(program);
			programs.remove(program);
			logger.info("Kiválasztott program ideiglenesen eltávolítva a listából.");
		}
		this.selectedProgramStateHolder.setSelected(program);
		oldProgram = program.clone();
		logger.info("Új kiválasztott program: "
				+ this.selectedProgramStateHolder);
		logger.info("oldProgramIndex: " + oldProgramIndex + ", oldProgram: "
				+ oldProgram);
	}

	/**
	 * A kiválasztott program új program lesz.
	 */
	public void newSelectedProgram() {
		logger.info("A kiválasztott program új program lesz.");
		oldProgramIndex = null;
		oldProgram = null;
		this.selectedProgramStateHolder.setSelected(new Program());
		logger.info("oldProgramIndex: " + oldProgramIndex + ", oldProgram: "
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
	 * Elmenti a szerkesztés alatt álló konferenciát. Kezeli, hogy új
	 * konferenciáról van-e szó, vagy egy régit kell-e updatelni. Végül
	 * beállítja a conferenceStateHolder-be az elmentett konferenciát.
	 */
	public void saveConference() {
		logger.info("saveConference meghívódott.");
		if (conference.getTitle() == null || conference.getTitle().isEmpty()) {
			FacesMessages.instance().add("Cím megadása kötelezõ!");
			return;
		}
		if (conference.getShortTitle() == null
				|| conference.getShortTitle().isEmpty()) {
			FacesMessages.instance().add("Rövid cím megadása kötelezõ!");
			// FacesMessages.instance().add(msg);
			return;
		}
		if (newConfernece) {
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
			newConfernece = false;
			FacesMessages.instance().add("Sikeres mentés!");
		} else {
			conference.setPrograms(programs);
			conferenceDao.updateConference(conference);
			FacesMessages.instance().add("Sikeres módosítás!");
		}
		this.conferenceStateHolder.setSelected(conference);
	}

	/**
	 * Teszteléshez. Kilogolja az elmentendõ konferencia részleteit.
	 */
	public void print() {
		logger.info(conference.toString());
		this.conferenceStateHolder.setSelected(conference);
	}

	/**
	 * Teszteléshez, megváltoztatja, hogy új konferenciáról van-e szó.
	 */
	public void changeNew() {
		this.newConfernece = !this.newConfernece;
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
	public boolean isNewConfernece() {
		return newConfernece;
	}

	/**
	 * @param newConfernece
	 *            the newConfernece to set
	 */
	public void setNewConfernece(boolean newConfernece) {
		this.newConfernece = newConfernece;
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
		this.selectedProgramStateHolder.setSelected(selectedProgram);
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
	public List<Long> getSelectedArticles() {
		return selectedArticles;
	}

	/**
	 * @param selectedArticles
	 *            the selectedArticles to set
	 */
	public void setSelectedArticles(List<Long> selectedArticles) {
		this.selectedArticles = selectedArticles;
	}

}
