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
	public User getUser(final String userName) {
		StringBuilder queryString = new StringBuilder();
		queryString.append("FROM User_ as u ");
		queryString.append("	WHERE");
		queryString.append("		u.userName = ?");
		return (User) executeQuerySingleResult(queryString.toString(), userName);
	}

}
