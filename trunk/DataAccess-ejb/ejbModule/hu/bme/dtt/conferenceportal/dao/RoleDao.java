package hu.bme.dtt.conferenceportal.dao;

import hu.bme.dtt.conferenceportal.entity.Role;
import hu.futurion.mt.dao.GenericDao;

import java.util.Collection;

public interface RoleDao extends GenericDao<Role> {
	/**
	 * A <code>getAllRoles</code> f�ggv�ny visszadja az �sszes jogosults�got.
	 * 
	 * @return A jogosults�gok list�ja.
	 */
	Collection<Role> getAllRoles();

}
