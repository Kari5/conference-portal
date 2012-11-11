package hu.bme.dtt.conferenceportal.session;

import hu.bme.dtt.conferenceportal.entity.Conference;
import hu.bme.dtt.conferenceportal.entity.Program;
import hu.bme.dtt.conferenceportal.entity.Tag;
import hu.bme.dtt.conferenceportal.util.StateHolder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

	/** Logoláshoz logger. */
	private Logger logger = Logger.getLogger(EditConferenceBackBean.class);

	/**
	 * Init függvény, ami eldönti, hogy új konferncia lesz létrehozva, vagy egy
	 * meglévõt kell szerkeszteni.
	 */
	@Create
	public void init() {
		if (conferenceStateHolder.getSelected() != null) {
			conference = conferenceStateHolder.getSelected();
			newConfernece = false;
		} else {
			conference = new Conference();
			newConfernece = true;
		}

		// TESZTHEZ:
		Program p1 = new Program();
		p1.setDescription("LEírása p1");
		p1.setTitle("1. program");
		p1.setStart(new Date());
		p1.setEnd(new Date());

		Program p2 = new Program();
		p2.setDescription("LEírása p2");
		p2.setTitle("2. program");
		p2.setStart(new Date());
		p2.setEnd(new Date());
		List<Program> programs = new ArrayList<Program>();
		programs.add(p1);
		programs.add(p2);

		Tag t1 = new Tag();
		t1.setName("Tag1");

		Tag t2 = new Tag();
		t2.setName("Tag2");
		List<Tag> tags = new ArrayList<Tag>();
		tags.add(t1);
		tags.add(t2);

		conference.setPrograms(programs);
		conference.setTags(tags);
	}

	/**
	 * Teszteléshez. Kilogolja az elmentendõ konferencia részleteit.
	 */
	public void print() {
		logger.info(conference.toString());
		this.conferenceStateHolder.setSelected(conference);
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

}
