package hu.bme.dtt.conferenceportal.dao;

import hu.bme.dtt.conferenceportal.entity.Article;
import hu.futurion.mt.dao.GenericDao;

import java.util.List;

import javax.ejb.Local;

@Local
public interface ArticleDao extends GenericDao<Article> {

	/**
	 * Visszaadaj az �sszes cikket.
	 * 
	 * @return cikkek.
	 */
	public List<Article> getAllArticles();
}
