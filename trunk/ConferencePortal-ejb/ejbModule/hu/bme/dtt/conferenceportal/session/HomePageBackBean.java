package hu.bme.dtt.conferenceportal.session;

import java.util.HashMap;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import hu.bme.dtt.conferenceportal.dao.ConferenceDao;
import hu.bme.dtt.conferenceportal.dao.ConferenceDaoImpl;
import hu.bme.dtt.conferenceportal.entity.Conference;
import hu.bme.dtt.conferenceportal.util.StateHolder;

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
	 * logol�shoz logger.
	 */
	public static final Logger logger = Logger
			.getLogger(HomePageBackBean.class);
	/**
	 * A kiv�lasztott konferencia stateHolder-e.
	 */
	@In(create=true)
	StateHolder<Conference> conferenceStateHolder;
	
	/**
	 * Az  el�rhet� konfernci�k.
	 * Kulcs: a konferencia azonos�t�ja
	 * �rt�k: a konferencia r�vid c�me
	 */
	HashMap<Long, String> conferenceMap;
	
	/**
	 * Konferenci�k adatb�zis�nak kezel�s�hez dao.
	 */
	ConferenceDao conferenceDao;
	
	/**
	 * Inicializ�l� f�ggv�ny.
	 * Adatb�zisb�l beh�zza az el�rhet� konferncia neveket.
	 */
	@Create
	public void init(){
		conferenceMap=new HashMap<Long, String>();
		try {
			conferenceDao=InitialContext.doLookup("ConferencePortal-ear/conferenceDao/local");
			
			for(Conference c: conferenceDao.conferences()){
				conferenceMap.put(c.getId(), c.getShortTitle());
			}
			logger.info("Visszakapott konf. sz�ma: "+conferenceMap.size());
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public HashMap<Long, String> getConferenceMap() {
		return conferenceMap;
	}

	public void setConferenceMap(HashMap<Long, String> conferenceMap) {
		this.conferenceMap = conferenceMap;
	}
	
	/**
	 * Egy azonos�t� alapj�n adatb�zisb�l lek�ri a konfernci�t, �s be�ll�tja kiv�lastott konferenci�nak.
	 * @param id
	 */
	public void changeSelectedConference(Long id){
		Conference conference;
		try {
			conference=conferenceDao.findByPrimaryKey(id);
		} catch (Exception e) {
			logger.error(id+". azonos�t�j� konferencia nem tal�lhat�!");
			return;
		}
		changeSelectedConference(conference);
	}
	
	
	/**
	 * �t�ll�tja a kiv�lasztott konferenci�t.
	 * @param conference be�ll�t�and� konferencia.
	 */
	public void changeSelectedConference(Conference conference){
		this.conferenceStateHolder.setSelected(conference);
	}
	
	
}
