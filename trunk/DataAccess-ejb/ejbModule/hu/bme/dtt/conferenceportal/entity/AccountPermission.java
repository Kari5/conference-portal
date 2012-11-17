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
 * Entitás amely a felhasználókhoz és a szerepkörökhöz tartozó engedélyeket
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
	 * Az engedélyezett cselekvés.
	 */
	@PermissionAction
	private String action;
	/**
	 * Ez adja meg, hogy felhasználói, vagy szerepkör engedélyrõl van szó.
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
	 * Az engedély jogosultja.
	 */
	@PermissionUser
	@PermissionRole
	private String recipient;
	/**
	 * Az engedélyezett cselekvés elszenvedõje.
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
