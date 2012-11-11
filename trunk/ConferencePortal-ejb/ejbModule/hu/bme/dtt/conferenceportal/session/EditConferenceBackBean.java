package hu.bme.dtt.conferenceportal.session;

import hu.bme.dtt.conferenceportal.dao.TagDao;
import hu.bme.dtt.conferenceportal.entity.Conference;
import hu.bme.dtt.conferenceportal.entity.Program;
import hu.bme.dtt.conferenceportal.entity.Tag;
import hu.bme.dtt.conferenceportal.util.StateContainer;
import hu.bme.dtt.conferenceportal.util.StateHolder;

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

	/** Tag-ek kezeléséhez dao. */
	private TagDao tagDao;

	/** Új tag elméntéséhez név. */
	private String tagName;

	/** Programok listája. */
	private List<Program> programs;

	/** Szerkesztésre kiválasztott program. */
	private Program selectedProgram;

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
			conference = conferenceStateHolder.getSelected();
			programs = (ArrayList<Program>) conference.getPrograms();
			newConfernece = false;
		} else {
			conference = new Conference();
			programs = new ArrayList<Program>();
			newConfernece = true;
		}

		// TESZTHEZ:
		Program p1 = new Program();
		p1.setDescription("Leírása p1");
		p1.setTitle("1. program");
		p1.setId(1L);
		p1.setStart(new Date());
		p1.setEnd(new Date());

		Program p2 = new Program();
		p2.setDescription("Leírása p2 programról. Marha érdekes lesz, mindenkinek sok szeretettel ajánlom, meg még kell valami szöveg ide sa dfanmskflmnslkf lkmaf.");
		p2.setTitle("2. program");
		p2.setId(2L);
		p2.setStart(new Date());
		p2.setEnd(new Date());
		programs.add(p1);
		programs.add(p2);

		Tag t1 = new Tag();
		t1.setName("Tag1");

		Tag t2 = new Tag();
		t2.setName("Tag2");
		Tag t3 = new Tag();
		t3.setName("Tag3");
		Tag t4 = new Tag();
		t4.setName("Tag4");
		Tag t5 = new Tag();
		t5.setName("Tag5");
		Tag t6 = new Tag();
		t6.setName("Tag6");
		List<Tag> tags = new ArrayList<Tag>();
		tags.add(t1);
		tags.add(t2);
		tags.add(t3);
		tags.add(t4);
		tags.add(t5);
		tags.add(t6);

		conference.setPrograms(programs);
		// conference.setTags(tags);

		try {
			tagDao = (TagDao) InitialContext
					.doLookup("ConferencePortal-ear/tagDao/local");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		makeTagSelectItems();
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

	/**
	 * Átállítja a kiválasztott programot.
	 * 
	 * @param program
	 *            az új kiválasztott program
	 */
	public void changeSelectedProgram(Program program) {
		logger.info("changeSelected program meghívódott.");
		this.selectedProgram = program;
		logger.info("Új kiválasztott program: " + this.selectedProgram);
	}

	/**
	 * A kiválasztott program új program lesz.
	 */
	public void newSelectedProgram() {
		logger.info("A kiválasztott program új program lesz.");
		this.selectedProgram = new Program();
	}

	/**
	 * Berakja a programok listájába az aktuálisan szerkesztett programot.
	 */
	public void saveProgram() {
		programs.add(selectedProgram);
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
		return selectedProgram;
	}

	/**
	 * @param selectedProgram
	 *            the selectedProgram to set
	 */
	public void setSelectedProgram(Program selectedProgram) {
		this.selectedProgram = selectedProgram;
	}

}
