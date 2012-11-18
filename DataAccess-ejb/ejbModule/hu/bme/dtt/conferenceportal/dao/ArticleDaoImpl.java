/**
 * 
 */
package hu.bme.dtt.conferenceportal.dao;

import hu.bme.dtt.conferenceportal.entity.Article;
import hu.futurion.mt.dao.GenericDaoImpl;

import java.util.List;

import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

/**
 * @author Karcsi
 * 
 */
@Stateless(name = "articleDao")
public class ArticleDaoImpl extends GenericDaoImpl<Article> implements
		ArticleDao {

	private static final Logger logger = Logger.getLogger(ArticleDaoImpl.class);
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
	public boolean save(Article article) {
		try {
			UserDao userDao = InitialContext
					.doLookup("ConferencePortal-ear/userDao/local");
			article.setUser(userDao.findByPrimaryKey(article.getUser().getId()));
			entityManager.persist(article);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Article> getAllArticles() {
		return (List<Article>) executeQueryMultipleResult("FROM Article");
	}

}
