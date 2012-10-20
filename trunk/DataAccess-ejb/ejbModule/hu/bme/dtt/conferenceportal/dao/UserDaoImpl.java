package hu.bme.dtt.conferenceportal.dao;

import hu.bme.dtt.conferenceportal.entity.User;
import hu.futurion.mt.dao.GenericDaoImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao {
	@PersistenceContext(name = "ConferencePortal")
	private EntityManager entityManager;

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public User getUser(String userName, String password) {
		StringBuilder queryString = new StringBuilder();
		queryString.append("FROM User_ as u");
		queryString.append("WHERE u.USER_NAME = ? AND");
		return null;
	}

}
