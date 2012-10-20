package hu.bme.dtt.conferenceportal.dao;

import hu.bme.dtt.conferenceportal.entity.Role;
import hu.futurion.mt.dao.GenericDaoImpl;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class RoleDaoImpl extends GenericDaoImpl<Role> implements RoleDao {
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
	public Collection<Role> getAllRoles() {
		return (Collection<Role>) executeQueryMultipleResult("FROM Role_");
	}

}
