package hu.bme.dtt.conferenceportal.session;

import hu.bme.dtt.conferenceportal.dao.UserDao;
import hu.bme.dtt.conferenceportal.entity.User;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.RunAsOperation;
import org.jboss.seam.security.management.IdentityManager;
import org.jboss.seam.security.management.IdentityStore;
import org.jboss.seam.security.management.JpaIdentityStore;

@Name("registrationBackBean")
@Scope(ScopeType.PAGE)
public class Registration {

	@In
	Credentials credentials;
	@In
	Identity identity;

	@In
	IdentityManager identityManager;

	private User newUser;

	private UserDao userDao;

	private static final Logger LOGGER = Logger.getLogger(Authenticator.class);

	@Create
	public void init() {
		newUser = new User();
		try {
			userDao = InitialContext
					.doLookup("ConferencePortal-ear/userDao/local");
		} catch (NamingException e) {
			LOGGER.error("Belsõ hiba!" + e.getMessage(), e);
		}
	}

	public User getNewUser() {
		return newUser;
	}

	public void setNewUser(User newUser) {
		this.newUser = newUser;
	}

	public boolean saveNewUser() {
		newUser.setUserName(credentials.getUsername());
		newUser.setPassword(credentials.getPassword());
		//FIXME: át kell írni, csak teszthez.
		IdentityStore identityStore = (IdentityStore) Component.getInstance(
				JpaIdentityStore.class, true);
		identityStore.createUser(credentials.getUsername(), credentials.getPassword(), null, null);
//		new RunAsOperation() {
//			@Override
//			public void execute() {
//				addRole("admin");
//				if (identityManager.userExists(credentials.getUsername())) {
//					FacesMessages.instance().add("A felhasználó már létezik!");
//					return;
//				}
//				if (identityManager.createUser(newUser.getUserName(),
//						newUser.getPassword(), newUser.getFirstName(),
//						newUser.getLastName())) {
//					User user = userDao.getUser(newUser.getUserName());
//					user.seteMail(newUser.geteMail());
//					user.setTel(newUser.getTel());
//					LOGGER.info("Sikeres mentés!");
//					identity.login();
//				}
//			}
//		}.run();
		return true;
	}

}
