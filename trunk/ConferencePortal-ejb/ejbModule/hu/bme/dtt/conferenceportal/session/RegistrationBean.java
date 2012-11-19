package hu.bme.dtt.conferenceportal.session;

import hu.bme.dtt.conferenceportal.entity.User;

import org.jboss.logging.Logger;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.RunAsOperation;
import org.jboss.seam.security.management.IdentityManager;
import org.jboss.seam.security.management.JpaIdentityStore;

/**
 * Új felhasználó regisztrácijáért felelõs backing bean.
 */
@Name("registrationBackBean")
@Scope(ScopeType.PAGE)
public class RegistrationBean {
	/**
	 * Naplózáshoz.
	 */
	private static final Logger LOGGER = Logger.getLogger(Authenticator.class);
	/**
	 * A felhasználó bejelentkezését végzi.
	 */
	@In
	private Identity identity;
	/**
	 * A felhasználó létrehozását végzi.
	 */
	@In
	private IdentityManager identityManager;
	/**
	 * Az új felhasználó adatait ideiglenesen tároló objektum.
	 */
	private User newUser = new User();

	/**
	 * Az új felhasználót adatbázisba perzisztálás elõtt kiegészíti a továbi
	 * adatokkal.
	 * 
	 * @param user
	 *            A mentendõ felhasználó.
	 */
	@Observer(JpaIdentityStore.EVENT_PRE_PERSIST_USER)
	public void creatingNewUser(User user) {
		LOGGER.debug("Adding data to user: " + user.getUserName());
		user.seteMail(newUser.geteMail());
		user.setTel(newUser.getTel());
	}

	/**
	 * @return the newUser
	 */
	public User getNewUser() {
		return newUser;
	}

	/**
	 * Létrehozza az új felhasználót. Ellenõrzi, hogy létezik-e már azonos nevû
	 * felhasználó.
	 * 
	 * @return bool
	 */
	public String saveNewUser() {
		new RunAsOperation() {
			@Override
			public void execute() {
				LOGGER.debug("Registering user: " + newUser.getUserName());
				if (identityManager.userExists(newUser.getUserName())) {
					LOGGER.info("User " + newUser.getUserName() + " already exists!");
					FacesMessages.instance().add("A felhasználó már létezik!");
					return;
				}
				identityManager.createUser(newUser.getUserName(), newUser.getPassword(),
						newUser.getFirstName(), newUser.getLastName());
				LOGGER.debug("User " + newUser.getUserName() + " created!");
				identityManager.grantRole(newUser.getUserName(), "user");
				LOGGER.debug("User role granted!");
			}
		}.addRole("admin").run();
		identity.getCredentials().setUsername(newUser.getUserName());
		identity.getCredentials().setPassword(newUser.getPassword());
		return identity.login();
	}

	/**
	 * @param newUser
	 *            set the newUser
	 */
	public void setNewUser(User newUser) {
		this.newUser = newUser;
	}

}
