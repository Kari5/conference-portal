package hu.bme.dtt.conferenceportal.session;

import org.jboss.logging.Logger;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.management.IdentityManager;

@Name("authenticator")
public class Authenticator {
	private static final Logger LOGGER = Logger.getLogger(Authenticator.class);

	@In
	Identity identity;
	@In
	IdentityManager identityManager;

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
