package hu.bme.dtt.conferenceportal.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

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
@XmlAccessorType(XmlAccessType.FIELD)
public class AccountPermission implements Serializable {
	/**
	 * Serialization ID.
	 */
	private static final long serialVersionUID = -5628863031792429938L;
	/**
	 * Az engedélyezett cselekvés.
	 */
	@PermissionAction
	@Column(name = "ACTION")
	@XmlElement(name = "action")
	private String action;
	/**
	 * Ez adja meg, hogy felhasználói, vagy szerepkör engedélyrõl van szó.
	 */
	@PermissionDiscriminator
	@Column(name = "DISCRIMINATOR")
	@XmlElement(name = "discriminator")
	private String discriminator;
	/**
	 * A rekord kulcs.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Integer permissionId;

	/**
	 * Az engedély jogosultja.
	 */
	@PermissionUser
	@PermissionRole
	@Column(name = "RECIPIENT")
	@XmlElement(name = "recipient")
	private String recipient;
	/**
	 * Az engedélyezett cselekvés elszenvedõje.
	 */
	@PermissionTarget
	@Column(name = "TARGET")
	@XmlElement(name = "target")
	private String target;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof AccountPermission)) {
			return false;
		}
		AccountPermission other = (AccountPermission) obj;
		if (action == null) {
			if (other.action != null) {
				return false;
			}
		} else if (!action.equals(other.action)) {
			return false;
		}
		if (discriminator == null) {
			if (other.discriminator != null) {
				return false;
			}
		} else if (!discriminator.equals(other.discriminator)) {
			return false;
		}
		if (permissionId == null) {
			if (other.permissionId != null) {
				return false;
			}
		} else if (!permissionId.equals(other.permissionId)) {
			return false;
		}
		if (recipient == null) {
			if (other.recipient != null) {
				return false;
			}
		} else if (!recipient.equals(other.recipient)) {
			return false;
		}
		if (target == null) {
			if (other.target != null) {
				return false;
			}
		} else if (!target.equals(other.target)) {
			return false;
		}
		return true;
	}

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((discriminator == null) ? 0 : discriminator.hashCode());
		result = prime * result + ((permissionId == null) ? 0 : permissionId.hashCode());
		result = prime * result + ((recipient == null) ? 0 : recipient.hashCode());
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		return result;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Type: AccountPermission \naction=");
		builder.append(action);
		builder.append(",\ndiscriminator=");
		builder.append(discriminator);
		builder.append(",\npermissionId=");
		builder.append(permissionId);
		builder.append(",\nrecipient=");
		builder.append(recipient);
		builder.append(",\ntarget=");
		builder.append(target);
		return builder.toString();
	}

}
