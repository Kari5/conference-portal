package hu.bme.dtt.conferenceportal.session;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import hu.bme.dtt.conferenceportal.dao.UserDao;
import hu.bme.dtt.conferenceportal.entity.User;

import org.jboss.logging.Logger;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.management.IdentityManager;

@Name("authenticator")
public class Authenticator
{
    private static final Logger LOGGER = Logger.getLogger(Authenticator.class);

    @In Identity identity;
    @In Credentials credentials;
    @In IdentityManager identityManager;
    
    public boolean authenticate()
    {
    	LOGGER.info("authenticating " + credentials.getUsername());
        
    	UserDao userDao;
    	try {
			userDao=InitialContext.doLookup("ConferencePortal-ear/userDao/local");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.info("Belsõ hiba!");
			return false;
		}
    	
        if ("admin".equals(credentials.getUsername()))
        {
            identity.addRole("admin");
            return true;
        }
        
        if(identityManager.authenticate(credentials.getUsername(), credentials.getPassword())){
        	identity.addRole("admin");
        	return true;
        }
        
        return false;
    }
    
}
