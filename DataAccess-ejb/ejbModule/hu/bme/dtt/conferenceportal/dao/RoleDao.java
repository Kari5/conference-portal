package hu.bme.dtt.conferenceportal.dao;

import hu.bme.dtt.conferenceportal.entity.Role;
import hu.futurion.mt.dao.GenericDao;

import java.util.Collection;

public interface RoleDao extends GenericDao<Role> {
	/**
	 * A <code>getAllRoles</code> függvény visszadja az összes jogosultságot.
	 * 
	 * @return A jogosultságok listája.
	 */
	Collection<Role> getAllRoles();

}
