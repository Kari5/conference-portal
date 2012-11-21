package hu.bme.dtt.conferenceportal.session;

import org.jboss.logging.Logger;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.management.IdentityManager;

/**
 * Komponens felhasználó authentikálásra.
 */
@Name("authenticator")
@Scope(ScopeType.EVENT)
public class Authenticator {
	/**
	 * Naplózáshoz.
	 */
	private static final Logger LOGGER = Logger.getLogger(Authenticator.class);
	/**
	 * Objektum amelyben eltároljuk a session számára a felhasználónevet és
	 * jelszót.
	 */
	@In
	private Identity identity;
	/**
	 * Objektum authentikáció elvégzésére.
	 */
	@In
	private IdentityManager identityManager;

	/**
	 * Az authentikációt elvégzõ függvény.
	 * 
	 * @return sikeresség
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
