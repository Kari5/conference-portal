package hu.futurion.mt.dao;

import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.logging.Logger;

/**
 * Generikus DAO Manager.
 * 
 * @author Windisch.Karoly
 * @param <T>
 *            tipizáláshoz, entitás osztályt vár.
 */
public abstract class GenericDaoImpl<T> implements GenericDao<T> {

	/**
	 * {@inheritDoc}
	 */
	public abstract EntityManager getEntityManager();

	/** Statikus logger az osztályhoz. */
	private static Logger LOGGER = Logger.getLogger(GenericDaoImpl.class);

	/** Publikus default konstruktor, ha injektálni akarjuk az entityManagert. */
	public GenericDaoImpl() {
	}

	/**
	 * Visszaadja a generikus paraméter (T) osztályát. (A logolásnál pl. ezzel
	 * iratom ki az aktuális entitás osztályának a nevét.)
	 * 
	 * @return T parameter osztálya
	 */
	@SuppressWarnings("unchecked")
	public Class<T> getEntityClass() {
		final ParameterizedType ptype = (ParameterizedType) getClass()
				.getGenericSuperclass();
		return (Class<T>) ptype.getActualTypeArguments()[0];
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean save(final T obj) {
		getEntityManager().persist(obj);
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	public T update(final T obj) {
		return getEntityManager().merge(obj);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean remove(final T obj) {
		getEntityManager().refresh(obj);
		getEntityManager().remove(obj);
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean contains(final T obj) {
		return getEntityManager().contains(obj);
	}

	/**
	 * {@inheritDoc}
	 */
	public T findByPrimaryKey(final Object pk) throws Exception {
		final T result = getEntityManager().find(getEntityClass(), pk);

		if (result == null) {
			LOGGER.debug(getEntityClass().getName()
					+ " - Keresés nem adott eredményt, "
					+ getEntityClass().getName() + " " + pk
					+ " elsõdleges kulccsal nem található.");
			throw new SQLException(" - Keresés nem adott eredményt, "
					+ getEntityClass().getName() + " " + pk
					+ " elsõdleges kulccsal nem található.");
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public Object executeNamedQuerySingleResult(final String namedQuery,
			final Object... params) {
		LOGGER.debug("Lekérdezés elindult!");
		LOGGER.debug(getEntityClass().getName() + " - entityManager is null: "
				+ (getEntityManager() == null));
		final Query query = getEntityManager().createNamedQuery(namedQuery);
		LOGGER.debug(getEntityClass().getName() + " - Query is null: "
				+ (query == null));
		LOGGER.debug(getEntityClass().getName() + " - Query is: " + namedQuery
				+ " " + query.toString());
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i + 1, params[i]);
			}
		}

		final Object result = query.getSingleResult();
		if (result == null) {
			LOGGER.debug(getEntityClass().getName()
					+ " - Keresés nem adott eredményt.");
		}

		LOGGER.debug("Lekérdezés rendben volt!");
		LOGGER.debug("Osztály: " + result.getClass());

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<?> executeNamedQueryMultipleResult(final String namedQuery,
			final Object... params) {
		final Query query = getEntityManager().createNamedQuery(namedQuery);
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i + 1, params[i]);
			}
		}
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	public Object executeQuerySingleResult(final String queryString,
			final Object... params) {
		LOGGER.debug(getEntityClass().getName() + " - entityManager is null: "
				+ (getEntityManager() == null));

		final Query query = getEntityManager().createQuery(queryString);

		LOGGER.debug(getEntityClass().getName() + " - Query is: " + queryString);
		if (params != null) {
			final StringBuilder builder = new StringBuilder();
			builder.append("Az átadott paraméterek száma: " + params.length);

			for (int i = 0; i < params.length; i++) {
				query.setParameter(i + 1, params[i]);
				builder.append("\n  \t params[" + i + "]=" + params[i]);
			}
			LOGGER.debug(builder.toString());
		} else {
			LOGGER.debug("Nincs átadott paraméter.");
		}
		List<Object> result = null;
		try {
			result = query.getResultList();
		} catch (final Exception e) {
			LOGGER.debug(e.getMessage());
		}
		if (result == null || result.size() < 1) {
			LOGGER.debug(getEntityClass().getName()
					+ " - Keresés nem adott eredményt.");
			return null;
		}
		if (result.size() > 1) {
			LOGGER.debug(getEntityClass().getName()
					+ " - Keresés több eredményt adott, eredmények száma: "
					+ result.size());
			return null;
		}

		LOGGER.debug("Lekérdezés rendben volt!");
		LOGGER.debug("Visszaadott " + result.get(0).getClass());

		return result.get(0);

	}

	/**
	 * {@inheritDoc}
	 */
	public List<?> executeQueryMultipleResult(final String queryString,
			final Object... params) {
		final Query query = getEntityManager().createQuery(queryString);
		LOGGER.debug(getEntityClass().getName() + " - Query is: " + queryString);
		LOGGER.debug("Vannak paraméterek? - " + (params != null));
		if (params != null) {
			final StringBuilder builder = new StringBuilder();
			builder.append("Az átadott paraméterek száma: " + params.length);

			for (int i = 0; i < params.length; i++) {
				query.setParameter(i + 1, params[i]);
				builder.append("\n \t params[" + i + "]=" + params[i]);
			}
			LOGGER.debug(builder.toString());
		} else {
			LOGGER.debug("Nincs átadott paraméter.");
		}
		final List<?> result = query.getResultList();
		LOGGER.debug("A visszaadott lista hossza: " + result.size());
		return result;
	}

	/**
	 * Módosítás mûveletet végrehajtó metódus.
	 * 
	 * @param queryString
	 *            A lekérdezés
	 * @param params
	 *            A lekérdezés paraméterei - opcionális
	 * @return módosítás eredménye
	 */
	public int executeUpdate(final String queryString, final Object... params) {
		final Query query = getEntityManager().createQuery(queryString);
		if (params != null) {
			final StringBuilder builder = new StringBuilder();
			builder.append("Az átadott paraméterek száma: " + params.length);

			for (int i = 0; i < params.length; i++) {
				query.setParameter(i + 1, params[i]);
				builder.append("\n \t params[" + i + "]=" + params[i]);
			}
			LOGGER.debug(builder.toString());
		} else {
			LOGGER.debug("Nincs átadott paraméter.");
		}
		return query.executeUpdate();
	}

}
