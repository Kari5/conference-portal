package hu.bme.dtt.conferenceportal.session;

import hu.bme.dtt.conferenceportal.dao.UserDao;
import hu.bme.dtt.conferenceportal.entity.User;

import java.util.Arrays;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.RunAsOperation;
import org.jboss.seam.security.management.IdentityManager;
import org.jboss.seam.security.management.PasswordHash;

/**
 * A h�tt�rlogik�t tartalmazza felhaszn�l� adatainak megv�ltoztat�s�ra.
 */
@Name(value = "editAccountBean")
public class EditAccountBean {
	/**
	 * Napl�z�shoz.
	 */
	private static final Logger LOGGER = Logger.getLogger(EditAccountBean.class);
	/**
	 * @see UserDao
	 */
	private UserDao userDao;
	/**
	 * ...
	 */
	@In
	private IdentityManager identityManager;
	/**
	 * ...
	 */
	@In
	private Identity identity;
	/**
	 * A megadott r�gi jelsz�.
	 */
	private String oldPassword;
	/**
	 * A megadott �j jelsz�.
	 */
	private String newPassword;
	/**
	 * A felhaszn�l� adatait t�rol�objektum.
	 */
	private User user;

	/**
	 * Inicializ�l� f�ggv�ny. Lek�rdezi a bejelentkezett felhaszn�l� adatait.
	 */
	@Create
	public void init() {
		try {
			userDao = InitialContext.doLookup("ConferencePortal-ear/userDao/local");
			user = userDao.getUser(identity.getCredentials().getUsername());
		} catch (NamingException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * Ellen�rzi a r�gi jelsz� helyess�g�t, �s megv�ltoztatja az �jra.
	 */
	public void changePassword() {
		LOGGER.debug("Changing password!");
		if ((user != null) && identity.isLoggedIn()) {
			String olPasswordHash = new PasswordHash().generateSaltedHash(oldPassword, identity
					.getCredentials().getUsername(), PasswordHash.ALGORITHM_SHA);
			if (user.getPassword().equals(olPasswordHash)) {
				LOGGER.debug("Old password match!");
				// FIXME: r�j�nni mi�rt nem megy csak sim�n az authentik�lt
				// user-el elv�gezni
				new RunAsOperation() {

					@Override
					public void execute() {
						List<String> grantedRoles = identityManager.getGrantedRoles(identity
								.getCredentials().getUsername());
						LOGGER.debug("GrantedRoles: " + Arrays.toString(grantedRoles.toArray()));
						for (String role : grantedRoles) {
							addRole(role);
						}
						identityManager.changePassword(identity.getCredentials().getUsername(),
								newPassword);
					}
				}.run();
			} else {
				LOGGER.debug("Old password invalid!");
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("Invalid password!"));
			}
		} else {
			LOGGER.debug("Not logged in or user is NULL!");
		}
	}

	/**
	 * Elmenti a v�ltoztat�sokat az adatb�zisba.
	 */
	public void changeAccountSettings() {
		LOGGER.debug("Changing account settings!");
		if ((user != null) && identity.isLoggedIn()) {
			try {
				userDao.update(user);
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}
		} else {
			LOGGER.debug("Not logged in or user is NULL!");
		}
	}

	/**
	 * @return the oldPassword
	 */
	public String getOldPassword() {
		return oldPassword;
	}

	/**
	 * @param oldPassword
	 *            the oldPassword to set
	 */
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	/**
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * @param newPassword
	 *            the newPassword to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

}
