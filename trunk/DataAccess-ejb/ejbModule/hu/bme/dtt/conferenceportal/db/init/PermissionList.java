package hu.bme.dtt.conferenceportal.db.init;

import hu.bme.dtt.conferenceportal.entity.AccountPermission;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Inicializáló XML olvasását elõsegítõ objektum. A beolvasott engedélyeket
 * tárolja.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "permissionlist")
public class PermissionList {
	/**
	 * A beolvasott engedélyek.
	 */
	@XmlElement(name = "permission")
	private List<AccountPermission> permissions;

	/**
	 * @return the permissions
	 */
	public List<AccountPermission> getPermissions() {
		return permissions;
	}

	/**
	 * @param permissions
	 *            the permissions to set
	 */
	public void setPermissions(List<AccountPermission> permissions) {
		this.permissions = permissions;
	}
}
