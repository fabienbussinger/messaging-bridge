package fr.lespoulpes.messaging.bridge;

public class MessageProductionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MessageProductionException(String message, Throwable cause) {
		super(message, cause);
	}

	public MessageProductionException(String message) {
		super(message);
	}

	public MessageProductionException(Throwable cause) {
		super(cause);
	}
}
