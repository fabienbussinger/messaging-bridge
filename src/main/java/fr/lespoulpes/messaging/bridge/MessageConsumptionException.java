package fr.lespoulpes.messaging.bridge;

public class MessageConsumptionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final boolean dropOnFailure;
	
	public static MessageConsumptionException dropOnFailure() {
		return new MessageConsumptionException(true);
	}
	
	public static MessageConsumptionException dropOnFailure(String message) {
		return new MessageConsumptionException(true, message);
	}
	
	public static MessageConsumptionException dropOnFailure(Throwable cause) {
		return new MessageConsumptionException(true, cause);
	}
	
	public static MessageConsumptionException moveToFailure() {
		return new MessageConsumptionException(false);
	}
	
	public static MessageConsumptionException moveToFailure(String message) {
		return new MessageConsumptionException(false, message);
	}
	
	public static MessageConsumptionException moveToFailure(Throwable cause) {
		return new MessageConsumptionException(false, cause);
	} 
	

	public MessageConsumptionException(boolean dropOnFailure) {
		super();
		this.dropOnFailure = dropOnFailure;
	}
	
	public MessageConsumptionException(boolean dropOnFailure, String message) {
		super(message);
		this.dropOnFailure = dropOnFailure;
	}

	public MessageConsumptionException(boolean dropOnFailure,Throwable cause) {
		super(cause);
		this.dropOnFailure = dropOnFailure;
	}
}
