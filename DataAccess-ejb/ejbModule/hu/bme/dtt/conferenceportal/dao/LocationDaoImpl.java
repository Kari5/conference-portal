package hu.bme.dtt.conferenceportal.dao;

import hu.bme.dtt.conferenceportal.entity.Location;
import hu.futurion.mt.dao.GenericDaoImpl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

@Stateless(name = "locationDao")
public class LocationDaoImpl extends GenericDaoImpl<Location> implements LocationDao {
	private static final Logger LOGGER = Logger.getLogger(ArticleDaoImpl.class);
	@PersistenceContext(name = "ConferencePortal")
	private EntityManager entityManager;

	@Override
	public List<Location> getAllLocation() {
		return (List<Location>) executeQueryMultipleResult("FROM Location");
	}

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

}
