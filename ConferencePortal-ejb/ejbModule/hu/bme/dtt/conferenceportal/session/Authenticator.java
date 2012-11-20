package hu.bme.dtt.conferenceportal.session;

import org.jboss.logging.Logger;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.management.IdentityManager;

/**
 * Komponens felhasználó authentikálásra.
 */
@Name("authenticator")
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
	 * A fehasználónév.
	 */
	private String userName;
	/**
	 * A jelszó.
	 */
	private String password;

	/**
	 * Az authentikációt elvégzõ függvény.
	 * 
	 * @return sikeresség
	 */
	public boolean authenticate() {
		LOGGER.info("authenticating " + userName);

		if (identityManager.authenticate(userName, password)) {
			LOGGER.info("authentication successful");
			identity.getCredentials().setUsername(userName);
			identity.getCredentials().setPassword(password);
			return true;
		}
		LOGGER.info("authentication failed");
		return false;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
}
