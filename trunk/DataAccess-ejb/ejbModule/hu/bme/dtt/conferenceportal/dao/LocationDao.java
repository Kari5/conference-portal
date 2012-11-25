package hu.bme.dtt.conferenceportal.dao;

import hu.bme.dtt.conferenceportal.entity.Location;
import hu.futurion.mt.dao.GenericDao;

import java.util.List;

import javax.ejb.Local;

@Local
public interface LocationDao extends GenericDao<Location> {
	List<Location> getAllLocation();

	Location saveLocation(Location location);
}
