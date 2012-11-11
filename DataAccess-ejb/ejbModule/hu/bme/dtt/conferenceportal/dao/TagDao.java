package hu.bme.dtt.conferenceportal.dao;

import hu.bme.dtt.conferenceportal.entity.Tag;
import hu.futurion.mt.dao.GenericDao;

import java.util.List;

import javax.ejb.Local;

@Local
public interface TagDao extends GenericDao<Tag> {

	/**
	 * Visszaadja az �sszes tag-et.
	 * 
	 * @return �sszes tag.
	 */
	public List<Tag> getAllTags();

	/**
	 * Elment egy �j tag-et. Ellen�rzi, hogy nem szerepelt-e m�r az
	 * adatb�zisban, �s ha m�r szerepelt, akkor nem menti el megint.
	 * 
	 * @param name
	 *            a tag neve
	 */
	public void saveTag(String name);

	/**
	 * A neve alapj�n visszaadja a tag-et.
	 * 
	 * @param name
	 *            tag neve
	 * @return tag
	 */
	public Tag searchTag(String name);
}
