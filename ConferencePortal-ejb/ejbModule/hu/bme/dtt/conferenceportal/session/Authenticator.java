package hu.bme.dtt.conferenceportal.session;

import org.jboss.logging.Logger;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.management.IdentityManager;

/**
 * Komponens felhaszn�l� authentik�l�sra.
 */
@Name("authenticator")
@Scope(ScopeType.EVENT)
public class Authenticator {
	/**
	 * Napl�z�shoz.
	 */
	private static final Logger LOGGER = Logger.getLogger(Authenticator.class);
	/**
	 * Objektum amelyben elt�roljuk a session sz�m�ra a felhaszn�l�nevet �s
	 * jelsz�t.
	 */
	@In
	private Identity identity;
	/**
	 * Objektum authentik�ci� elv�gz�s�re.
	 */
	@In
	private IdentityManager identityManager;

	/**
	 * Az authentik�ci�t elv�gz� f�ggv�ny.
	 * 
	 * @return sikeress�g
	 */
	public boolean authenticate() {
		LOGGER.info("authenticating " + identity.getCredentials().getUsername());

		if (identityManager.authenticate(identity.getCredentials().getUsername(), identity
				.getCredentials().getPassword())) {
			LOGGER.info("authentication successful");
			return true;
		}
		LOGGER.info("authentication failed");
		return false;
	}
}
