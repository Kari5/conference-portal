package hu.bme.dtt.conferenceportal.dao;

import hu.bme.dtt.conferenceportal.entity.AccountPermission;
import hu.bme.dtt.conferenceportal.exception.ConferencePortalDataException;
import hu.futurion.mt.dao.GenericDaoImpl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * AccountPermission objektumok kezelését elõsegítõ objektum.
 */
@Stateless(name = "permissionDao")
public class AccountPermissionDaoImpl extends GenericDaoImpl<AccountPermission> implements
		AccountPermissionDao {
	/**
	 * ...
	 */
	@PersistenceContext(name = "ConferencePortal")
	private EntityManager entityManager;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean checkPermission(final AccountPermission permission) {
		StringBuilder queryString = new StringBuilder();
		queryString.append("FROM AccountPermission as p ");
		queryString.append("WHERE ");
		queryString.append("p.target = ? ");
		queryString.append("AND ");
		queryString.append("p.action = ? ");
		queryString.append("AND ");
		queryString.append("p.discriminator = ? ");
		queryString.append("AND ");
		queryString.append("p.recipient = ? ");
		AccountPermission result = (AccountPermission) executeQuerySingleResult(
				queryString.toString(), permission.getTarget(), permission.getAction(),
				permission.getDiscriminator(), permission.getRecipient());
		if (result == null) {
			return false;
		}
		return true;
	}

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
	public void savePermission(final AccountPermission permission)
			throws ConferencePortalDataException {
		if ((permission.getAction() != null) && (permission.getDiscriminator() != null)
				&& (permission.getRecipient() != null) && (permission.getTarget() != null)) {
			save(permission);
		} else {
			throw new ConferencePortalDataException("Permission not valid!");
		}
	}

}
