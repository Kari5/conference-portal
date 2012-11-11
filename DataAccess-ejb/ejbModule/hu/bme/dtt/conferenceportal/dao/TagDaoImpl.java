package hu.bme.dtt.conferenceportal.dao;

import hu.bme.dtt.conferenceportal.entity.Tag;
import hu.futurion.mt.dao.GenericDaoImpl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

@Stateless(name = "tagDao")
public class TagDaoImpl extends GenericDaoImpl<Tag> implements TagDao {

	public static final Logger logger = Logger.getLogger(TagDaoImpl.class);

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
	@SuppressWarnings("unchecked")
	@Override
	public List<Tag> getAllTags() {
		return (List<Tag>) executeQueryMultipleResult("FROM Tag");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveTag(String name) {
		Tag tag = searchTag(name);
		if (tag != null) {
			logger.info("Tag: " + name + " már el volt mentve.");
			return;
		} else {
			tag = new Tag();
			tag.setName(name);
			save(tag);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Tag searchTag(String name) {
		return (Tag) executeQuerySingleResult("FROM Tag AS T WHERE T.name=?",
				name);
	}

}
