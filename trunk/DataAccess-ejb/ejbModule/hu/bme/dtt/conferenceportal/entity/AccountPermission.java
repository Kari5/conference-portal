package hu.bme.dtt.conferenceportal.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.jboss.seam.annotations.security.permission.PermissionAction;
import org.jboss.seam.annotations.security.permission.PermissionDiscriminator;
import org.jboss.seam.annotations.security.permission.PermissionRole;
import org.jboss.seam.annotations.security.permission.PermissionTarget;
import org.jboss.seam.annotations.security.permission.PermissionUser;

/**
 * Entit�s amely a felhaszn�l�khoz �s a szerepk�r�kh�z tartoz� enged�lyeket
 * tartalmazza.
 * 
 */
@Entity
public class AccountPermission implements Serializable {
	/**
	 * Serialization ID.
	 */
	private static final long serialVersionUID = -5628863031792429938L;
	/**
	 * Az enged�lyezett cselekv�s.
	 */
	@PermissionAction
	private String action;
	/**
	 * Ez adja meg, hogy felhaszn�l�i, vagy szerepk�r enged�lyr�l van sz�.
	 */
	@PermissionDiscriminator
	private String discriminator;
	/**
	 * A rekord kulcs.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer permissionId;
	/**
	 * Az enged�ly jogosultja.
	 */
	@PermissionUser
	@PermissionRole
	private String recipient;
	/**
	 * Az enged�lyezett cselekv�s elszenved�je.
	 */
	@PermissionTarget
	private String target;

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @return the discriminator
	 */
	public String getDiscriminator() {
		return discriminator;
	}

	/**
	 * @return the permissionId
	 */
	public Integer getPermissionId() {
		return permissionId;
	}

	/**
	 * @return the recipient
	 */
	public String getRecipient() {
		return recipient;
	}

	/**
	 * @return the target
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * @param action
	 *            the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @param discriminator
	 *            the discriminator to set
	 */
	public void setDiscriminator(String discriminator) {
		this.discriminator = discriminator;
	}

	/**
	 * @param permissionId
	 *            the permissionId to set
	 */
	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}

	/**
	 * @param recipient
	 *            the recipient to set
	 */
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	/**
	 * @param target
	 *            the target to set
	 */
	public void setTarget(String target) {
		this.target = target;
	}

}
