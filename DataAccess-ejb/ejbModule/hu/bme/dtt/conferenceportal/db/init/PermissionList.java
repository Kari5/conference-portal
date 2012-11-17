package hu.bme.dtt.conferenceportal.db.init;

import hu.bme.dtt.conferenceportal.entity.AccountPermission;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Inicializ�l� XML olvas�s�t el�seg�t� objektum. A beolvasott enged�lyeket
 * t�rolja.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "permissionlist")
public class PermissionList {
	/**
	 * A beolvasott enged�lyek.
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
