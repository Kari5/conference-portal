package hu.bme.dtt.conferenceportal.session;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import hu.bme.dtt.conferenceportal.dao.UserDao;
import hu.bme.dtt.conferenceportal.entity.User;

import org.jboss.logging.Logger;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.management.IdentityManager;
import org.jboss.seam.security.management.IdentityStore;
import org.jboss.seam.security.management.JpaIdentityStore;

@Name("registrationBackBean")
@Scope(ScopeType.PAGE)
public class Registration {

	@In
	Credentials credentials;
	
	IdentityStore identityStore;
	
	private User newUser;
	
	private static final Logger LOGGER = Logger.getLogger(Authenticator.class);
	
	@Create
	public void init(){
		newUser=new User();
		identityStore=(IdentityStore)Component.getInstance(JpaIdentityStore.class, true);
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
				LOGGER.info("Bels� hiba!");
				return "#";
			}
	    	if(identityStore.userExists(credentials.getUsername())){
	    		LOGGER.info("Felhaszn�l�n�v m�r l�tezik!");
	    		return "#";
	    	}
	    	
	    	newUser.setUserName(credentials.getUsername());
	    	newUser.setPassword(credentials.getPassword());
	    	if(identityStore.createUser(newUser.getUserName(), newUser.getPassword(), newUser.getFirstName(), newUser.getLastName())){
	    		User user=userDao.getUser(newUser.getUserName());
	    		user.seteMail(newUser.geteMail());
	    		user.setTel(newUser.getTel());
	    		LOGGER.info("Sikeres ment�s!");
	    		return "home";
	    	}
	    	LOGGER.info( "V�ratlan hiba!");
	    	return "#";
	    }

}
