package hu.bme.dtt.conferenceportal.exception;

/**
 * Kivétel adatlekérési hiba esetére.
 * 
 * @author DTT
 * 
 */
public class ConferencePortalDataException extends Exception {

	public ConferencePortalDataException() {
		super();
	}

	public ConferencePortalDataException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConferencePortalDataException(String message) {
		super(message);
	}

	public ConferencePortalDataException(Throwable cause) {
		super(cause);
	}

}
