package fr.lespoulpes.messaging.bridge.publisher;

public class MessageWriterException extends Exception {
    private final MessageWriterExceptionPolicy policy;
    public MessageWriterException(MessageWriterExceptionPolicy policy, Throwable cause) {
        super(cause);
        this.policy = policy;
    }
    public MessageWriterException(MessageWriterExceptionPolicy policy) {
        super();
        this.policy = policy;
    }

    public MessageWriterExceptionPolicy getPolicy() {
        return policy;
    }
}
