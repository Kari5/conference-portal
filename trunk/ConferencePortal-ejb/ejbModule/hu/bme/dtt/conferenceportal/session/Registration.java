package hu.bme.dtt.conferenceportal.session;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import hu.bme.dtt.conferenceportal.dao.UserDao;
import hu.bme.dtt.conferenceportal.entity.User;

import org.jboss.logging.Logger;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.security.Credentials;

@Name("registrationBackBean")
@Scope(ScopeType.PAGE)
public class Registration {

	@In
	Credentials credentials;
	
	private User newUser;
	
	private static final Logger LOGGER = Logger.getLogger(Authenticator.class);
	
	@Create
	public void init(){
		newUser=new User();
	}

	public User getNewUser() {
		return newUser;
	}

	public void setNewUser(User newUser) {
		this.newUser = newUser;
	}
	
	  public String saveNewUser(){
	    	UserDao userDao;
	    	try {
				userDao=InitialContext.doLookup("ConferencePortal-ear/userDao/local");
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				LOGGER.info("Belsõ hiba!");
				return "#";
			}
	    	if(userDao.userNameExists(credentials.getUsername())){
	    		LOGGER.info("Felhasználónév már létezik!");
	    		return "#";
	    	}
	    	
	    	newUser.setUserName(credentials.getUsername());
	    	newUser.setPassword(credentials.getPassword());
	    	
	    	if(userDao.save(newUser)){
	    		LOGGER.info("Sikeres mentés!");
	    		return "home";
	    	}
	    	LOGGER.info( "Váratlan hiba!");
	    	return "#";
	    }

}
