package hu.bme.dtt.conferenceportal.session;

import hu.bme.dtt.conferenceportal.dao.ConfereneceDao;
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
import org.jboss.seam.faces.FacesMessages;

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
	ConfereneceDao conferenceDao;

	/**
	 * Logiaki v�ltoz�, ha �j konfernecia lesz mentve, akkor igaz, k�l�nben
	 * hamis.
	 */
	private boolean newConfernece;

	/** Az �sszes el�rhet� tag. */
	@In(create = true)
	private StateContainer<Tag> tagsStateContainer;

	/** A tag-ek selectItem-k�nt. */
	private List<SelectItem> tagsSelectItems;

	/** Kiv�lasztott tag-ek. */
	private List<String> selectedTags;

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
		logger.info("init megh�v�dott");
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
		p1.setDescription("Le�r�sa p1");
		p1.setTitle("1. program");
		p1.setId(1L);
		p1.setStart(new Date());
		p1.setEnd(new Date());

		Program p2 = new Program();
		p2.setDescription("Le�r�sa p2 programr�l. Marha �rdekes lesz, mindenkinek sok szeretettel aj�nlom, meg m�g kell valami sz�veg ide sa dfanmskflmnslkf lkmaf.");
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
			conferenceDao = (ConfereneceDao) InitialContext
					.doLookup("ConferencePortal-ear/conferenceDao/local");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		makeTagSelectItems();
		selectedTags = new ArrayList<String>();
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
		logger.info("changeTags megh�v�dott");
		List<Tag> tags = new ArrayList<Tag>();

		for (String tagNames : selectedTags) {
			try {
				tags.add(tagDao.searchTag(tagNames));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info(tags.size() + " db. tag hozz�adva.");
		conference.setTags(tags);
	}

	/** A tagStateContainer-b�l �ssze�ll�tja a tagsSelectItems list�t. */
	public void makeTagSelectItems() {
		logger.info("makeTagsSelectItems megh�v�dott");
		tagsSelectItems = new ArrayList<SelectItem>();
		for (Tag t : tagsStateContainer.getList()) {
			this.tagsSelectItems.add(new SelectItem(t.getName(), t.getName()));
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
		logger.info("changeSelected program megh�v�dott.");
		if (programs.contains(program)) {
			oldProgramIndex = programs.indexOf(program);
			programs.remove(program);
			logger.info("Kiv�lasztott program ideiglenesen elt�vol�tva a list�b�l.");
		}
		this.selectedProgramStateHolder.setSelected(program);
		oldProgram = program.clone();
		logger.info("�j kiv�lasztott program: "
				+ this.selectedProgramStateHolder);
		logger.info("oldProgramIndex: " + oldProgramIndex + ", oldProgram: "
				+ oldProgram);
	}

	/**
	 * A kiv�lasztott program �j program lesz.
	 */
	public void newSelectedProgram() {
		logger.info("A kiv�lasztott program �j program lesz.");
		oldProgramIndex = null;
		oldProgram = null;
		this.selectedProgramStateHolder.setSelected(new Program());
		logger.info("oldProgramIndex: " + oldProgramIndex + ", oldProgram: "
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
	 * Elmenti a szerkeszt�s alatt �ll� konferenci�t. Kezeli, hogy �j
	 * konferenci�r�l van-e sz�, vagy egy r�git kell-e updatelni. V�g�l
	 * be�ll�tja a conferenceStateHolder-be az elmentett konferenci�t.
	 */
	public void saveConference() {
		logger.info("saveConference megh�v�dott.");
		if (newConfernece) {
			// FIXME:[Kari] ha lesznek user-ek, akkor az owner mez�t ki kell
			// t�lteni!
			conferenceDao.save(conference);
			newConfernece = false;
			FacesMessages.instance().add("Sikeres ment�s!");
		} else {
			conferenceDao.updateConference(conference);
			FacesMessages.instance().add("Sikeres m�dos�t�s!");
		}
		this.conferenceStateHolder.setSelected(conference);
	}

	/**
	 * Tesztel�shez. Kilogolja az elmentend� konferencia r�szleteit.
	 */
	public void print() {
		logger.info(conference.toString());
		this.conferenceStateHolder.setSelected(conference);
	}

	/**
	 * Tesztel�shez, megv�ltoztatja, hogy �j konferenci�r�l van-e sz�.
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

}
