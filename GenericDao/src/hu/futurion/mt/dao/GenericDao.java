package hu.futurion.mt.dao;

import java.util.List;

import javax.persistence.EntityManager;

/**
 * Generikus DAO interfész.
 * 
 * @author Windisch.Karoly
 * @param <T>
 *            tipizáláshoz változó, egy entitás osztályt vár
 */
public interface GenericDao<T> {

	/**
	 * Visszaadja az entityManager-t. Minden osztály, aki megvalósítja
	 * kötelezõen implementálja ezt a metódust, ez által lehet injektálni az
	 * EntityManager-t
	 * 
	 * @return the entityManager
	 */
	EntityManager getEntityManager();

	/**
	 * Visszaadja a generikus paraméter (T) osztályát.
	 * 
	 * @return T parameter osztálya
	 */
	Class<T> getEntityClass();

	/**
	 * Entitás perzisztensé tétele. Lementi az adott entitást az adtabázisba
	 * 
	 * @param obj
	 *            Elmentendõ objektum
	 * @return true -> sikeres volt a mentés, false -> sikertelen volt
	 */
	boolean save(final T obj);

	/**
	 * Módosítja az adott entitást. Az átadott objektuomt update-eli.
	 * 
	 * @param obj
	 *            Módosítandó entitás
	 * @return A módosult entitás
	 * @throws Exception
	 *             módisítási hiba
	 */
	T update(T obj) throws Exception;

	/**
	 * Entitás törlése. Törli az átadott entitást.
	 * 
	 * @param obj
	 *            törlendõ entitás
	 * @return true -> siekres törlés, false -> sikertelen törlés
	 */
	boolean remove(T obj);

	/**
	 * Ellenõrzi, hogy adott entitás benne van-e az adatbázisban.
	 * 
	 * @param obj
	 *            Ellenõrizendõ entitás
	 * @return true-> benne van, false -> nincs benne
	 */
	boolean contains(T obj);

	// /**
	// * Perzisztensként visszaadja az entitást az elsõdleges kuclsa alapján.
	// *
	// * @param id elsõdleges kulcs
	// * @return a megtalált entitás
	// */
	// T load(Object id);

	/**
	 * Entitás lekérdezése elsõdleges kulcs alapján.
	 * 
	 * @param pk
	 *            elsõdleges kulcs
	 * @return A megtalált entitás
	 * @throws Exception
	 *             kivétel
	 */
	T findByPrimaryKey(Object pk) throws Exception;

	/**
	 * Végrehajt egy egy eredményt visszaadó "nevesített" lekérdezést.
	 * 
	 * @param namedQuery
	 *            A lekérdezés
	 * @param params
	 *            A lekérdezés paraméterei
	 * @return A lekérdezés eredménye
	 */
	Object executeNamedQuerySingleResult(String namedQuery, Object... params);

	/**
	 * Több eredményt visszaadó "nevesített" lekérdezés.
	 * 
	 * @param namedQuery
	 *            A lekérdezés
	 * @param params
	 *            A lekérdezés paraméterei
	 * @return Eredmény lista
	 */
	List<?> executeNamedQueryMultipleResult(String namedQuery, Object... params);

	/**
	 * Végrehajt egy egy eredményt visszaadó lekérdezést.
	 * 
	 * @param namedQuery
	 *            A lekérdezés
	 * @param params
	 *            A lekérdezés paraméterei
	 * @return A lekérdezés eredménye
	 */
	Object executeQuerySingleResult(String namedQuery, Object... params);

	/**
	 * Több eredményt visszaadó lekérdezés.
	 * 
	 * @param namedQuery
	 *            A lekérdezés
	 * @param params
	 *            A lekérdezés paraméterei
	 * @return Eredmény lista
	 */
	List<?> executeQueryMultipleResult(String namedQuery, Object... params);

	/**
	 * Módosítás mûveletet végrehajtó metódus.
	 * 
	 * @param queryString
	 *            A lekérdezés
	 * @param params
	 *            A lekérdezés paraméterei - opcionális
	 * @return módosítás eredménye
	 */
	int executeUpdate(String queryString, Object... params);
}
