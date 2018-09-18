package fr.lespoulpes.messaging.bridge.publisher;

public class MessagePublisherException extends Exception {
    private final MessageWriterExceptionPolicy policy;

    public MessagePublisherException(MessageWriterExceptionPolicy policy, Throwable cause) {
        super(cause);
        this.policy = policy;
    }

    public MessageWriterExceptionPolicy getPolicy() {
        return policy;
    }

}
