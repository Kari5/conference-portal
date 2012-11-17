package hu.bme.dtt.conferenceportal.util;

import java.io.Serializable;

/**
 * Egy pdf t�rol�s�ra alkalmas oszt�ly. byte-k�nt t�rolja az adatokat.
 * 
 * @author Karcsi
 * 
 */
public class SimplePdf implements Serializable {

	/**
	 * Soros�that�s�ghoz azonos�t�
	 */
	private static final long serialVersionUID = -124556820916573561L;

	/**
	 * pdf mime t�pusa.
	 */
	private static final String MIME = "application/pdf";

	/**
	 * Adatok bytk�nt.
	 */
	private byte[] data;

	/**
	 * Adatok hossza.
	 */
	private Long length;

	/**
	 * file neve
	 */
	private String name;

	/**
	 * @return the data
	 */
	public byte[] getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(byte[] data) {
		this.data = data;
	}

	/**
	 * @return the length
	 */
	public Long getLength() {
		return length;
	}

	/**
	 * @param length
	 *            the length to set
	 */
	public void setLength(Long length) {
		this.length = length;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the mime
	 */
	public static String getMime() {
		return MIME;
	}

}
