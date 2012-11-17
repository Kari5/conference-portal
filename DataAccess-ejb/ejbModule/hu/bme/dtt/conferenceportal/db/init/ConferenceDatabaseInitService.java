package hu.bme.dtt.conferenceportal.db.init;

import hu.bme.dtt.conferenceportal.dao.AccountPermissionDao;
import hu.bme.dtt.conferenceportal.dao.UserDao;
import hu.bme.dtt.conferenceportal.entity.AccountPermission;
import hu.bme.dtt.conferenceportal.entity.User;
import hu.bme.dtt.conferenceportal.exception.ConferencePortalDataException;

import java.io.InputStream;

import javax.naming.InitialContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.jboss.annotation.ejb.Service;
import org.jboss.logging.Logger;

/**
 * Deploy id�ben elindul� <code>Service</code> ami ellen�rzi, hogy az
 * adatb�zisban a kezdeti �rt�kek megvannak-e.
 */
@Service
public class ConferenceDatabaseInitService implements ConferenceDatabaseInit {
	/**
	 * Deploy eset�n beolvasand� enged�lyeket tartalmaz� xml f�jl.
	 */
	private static final String INIT_PERMISSIONS = "init_permissions.xml";
	/**
	 * Deploy eset�n beolvasand� felhaszn�l�kat tartalmaz� xml f�jl.
	 */
	private static final String INIT_USERS = "init_users.xml";
	/**
	 * Napl�z�shoz.
	 */
	private static final Logger LOGGER = Logger.getLogger(ConferenceDatabaseInitService.class);
	/**
	 * @see AccountPermissionDao
	 */
	private AccountPermissionDao permissionDao;
	/**
	 * @see UserDao
	 */
	private UserDao userDao;

	/**
	 * Beolvassa az inicializ�land� enged�lyeket, megn�zi l�teznek-e, �s a
	 * hi�nyz�kat elmenti adatb�zisba.
	 */
	private void checkAndUpdateInitPermissions() {
		LOGGER.info("Checking init permissions");
		try {
			InputStream initPermissionsStream = this.getClass().getResourceAsStream(
					INIT_PERMISSIONS);
			if (initPermissionsStream == null) {
				throw new NullPointerException(
						"Initialize failure: init_permissions.xml not found!");
			}
			JAXBContext context = JAXBContext.newInstance(PermissionList.class);
			PermissionList permissionList = (PermissionList) context.createUnmarshaller()
					.unmarshal(initPermissionsStream);
			for (AccountPermission permission : permissionList.getPermissions()) {
				if (permissionDao.checkPermission(permission)) {
					LOGGER.info("Permission already exists!");
					LOGGER.info(permission.toString());
				} else {
					try {
						LOGGER.info("Permission not found!");
						LOGGER.info(permission.toString());
						permissionDao.savePermission(permission);
						LOGGER.info("Permission saved!");
					} catch (ConferencePortalDataException e) {
						LOGGER.error(e.getMessage(), e);
					}
				}
			}
		} catch (JAXBException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * Beolvassa az inicializ�land� felhaszn�l�kat, megn�zi l�teznek-e, �s a
	 * hi�nyz�kat elmenti adatb�zisba.
	 */
	private void checkAndUpdateInitUsers() {
		LOGGER.info("Checking init users");
		try {
			InputStream initPermissionsStream = this.getClass().getResourceAsStream(INIT_USERS);
			if (initPermissionsStream == null) {
				throw new NullPointerException("Initialize failure: init_users.xml not found!");
			}
			JAXBContext context = JAXBContext.newInstance(UserList.class);
			UserList userList = (UserList) context.createUnmarshaller().unmarshal(
					initPermissionsStream);
			for (User user : userList.getUsers()) {
				User persistedUser = userDao.getUser(user.getUserName());
				if (persistedUser != null) {
					LOGGER.info("User already exists!");
					LOGGER.info(persistedUser.toString());
				} else {
					LOGGER.info("User not found!");
					LOGGER.info(user.toString());
					userDao.save(user);
					LOGGER.info("User saved!");
				}
			}
		} catch (JAXBException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void create() throws Exception {
		LOGGER.info("Initializing ConferenceDatabaseInitService");
		permissionDao = InitialContext.doLookup("ConferencePortal-ear/permissionDao/local");
		userDao = InitialContext.doLookup("ConferencePortal-ear/userDao/local");
		LOGGER.info("DAO object injected");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void destroy() {
		permissionDao = null;
		userDao = null;
		LOGGER.info("DAO objects released");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void start() throws Exception {
		LOGGER.info("Starting ConferenceDatabaseInitService");
		checkAndUpdateInitPermissions();
		checkAndUpdateInitUsers();
		stop();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stop() {
		LOGGER.info("Stopping ConferenceDatabaseInitService");
		destroy();
	}

}
