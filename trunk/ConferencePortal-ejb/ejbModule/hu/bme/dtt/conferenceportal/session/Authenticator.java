package hu.bme.dtt.conferenceportal.session;

import org.jboss.logging.Logger;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;

@Name("authenticator")
public class Authenticator
{
    private static final Logger LOGGER = Logger.getLogger(Authenticator.class);

    @In Identity identity;
    @In Credentials credentials;

    public boolean authenticate()
    {
    	LOGGER.info("authenticating " + credentials.getUsername());
        //write your authentication logic here,
        //return true if the authentication was
        //successful, false otherwise
        if ("admin".equals(credentials.getUsername()))
        {
            identity.addRole("admin");
            return true;
        }
        return false;
    }

}
