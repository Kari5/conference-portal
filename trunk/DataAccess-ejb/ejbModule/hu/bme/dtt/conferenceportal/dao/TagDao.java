package hu.bme.dtt.conferenceportal.dao;

import hu.bme.dtt.conferenceportal.entity.Tag;
import hu.futurion.mt.dao.GenericDao;

import java.util.List;

import javax.ejb.Local;

@Local
public interface TagDao extends GenericDao<Tag> {

	/**
	 * Visszaadja az összes tag-et.
	 * 
	 * @return összes tag.
	 */
	public List<Tag> getAllTags();

	/**
	 * Elment egy új tag-et. Ellenõrzi, hogy nem szerepelt-e már az
	 * adatbázisban, és ha már szerepelt, akkor nem menti el megint.
	 * 
	 * @param name
	 *            a tag neve
	 */
	public void saveTag(String name);

	/**
	 * A neve alapján visszaadja a tag-et.
	 * 
	 * @param name
	 *            tag neve
	 * @return tag
	 */
	public Tag searchTag(String name);
}
