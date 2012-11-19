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
 * �j felhaszn�l� regisztr�cij��rt felel�s backing bean.
 */
@Name("registrationBackBean")
@Scope(ScopeType.PAGE)
public class RegistrationBean {
	/**
	 * Napl�z�shoz.
	 */
	private static final Logger LOGGER = Logger.getLogger(Authenticator.class);
	/**
	 * A felhaszn�l� bejelentkez�s�t v�gzi.
	 */
	@In
	private Identity identity;
	/**
	 * A felhaszn�l� l�trehoz�s�t v�gzi.
	 */
	@In
	private IdentityManager identityManager;
	/**
	 * Az �j felhaszn�l� adatait ideiglenesen t�rol� objektum.
	 */
	private User newUser = new User();

	/**
	 * Az �j felhaszn�l�t adatb�zisba perziszt�l�s el�tt kieg�sz�ti a tov�bi
	 * adatokkal.
	 * 
	 * @param user
	 *            A mentend� felhaszn�l�.
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
	 * L�trehozza az �j felhaszn�l�t. Ellen�rzi, hogy l�tezik-e m�r azonos nev�
	 * felhaszn�l�.
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
					FacesMessages.instance().add("A felhaszn�l� m�r l�tezik!");
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
