package hu.bme.dtt.conferenceportal.dao;

import hu.bme.dtt.conferenceportal.entity.User;
import hu.futurion.mt.dao.GenericDaoImpl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * A <code>User</code> tábla elérését biztosító <code>DataAccessObject</code>.
 * 
 * @see hu.bme.dtt.conferenceportal.entity.User User
 * 
 * @author DTT
 * 
 */
@Stateless(name = "userDao")
public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao {
	/**
	 * ...
	 */
	@PersistenceContext(name = "ConferencePortal")
	private EntityManager entityManager;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User getUser(final String userName, final String password) {
		StringBuilder queryString = new StringBuilder();
		queryString.append("FROM User_ as u ");
		queryString.append("	WHERE");
		queryString.append("		u.USER_NAME = ?");
		queryString.append("	AND");
		queryString.append("		u.USER_PASSWORD = ?");
		return (User) executeQuerySingleResult(queryString.toString(),
				userName, password);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean userNameExists(final String userName) {
		StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT COUNT(*) FROM User_ as u ");
		queryString.append("	WHERE");
		queryString.append("		u.USER_NAME = ?");
		Integer result = (Integer) executeQuerySingleResult(
				queryString.toString(), userName);
		return result > 0;
	}

}
