package hu.bme.dtt.conferenceportal.session;

import org.jboss.logging.Logger;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.management.IdentityManager;

/**
 * Komponens felhaszn�l� authentik�l�sra.
 */
@Name("authenticator")
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
	 * A fehaszn�l�n�v.
	 */
	private String userName;
	/**
	 * A jelsz�.
	 */
	private String password;

	/**
	 * Az authentik�ci�t elv�gz� f�ggv�ny.
	 * 
	 * @return sikeress�g
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
