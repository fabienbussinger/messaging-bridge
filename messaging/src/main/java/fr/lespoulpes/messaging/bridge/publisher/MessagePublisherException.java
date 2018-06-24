package fr.lespoulpes.messaging.bridge.publisher;

public class MessagePublisherException extends Exception {
    private final Policy policy;

    public MessagePublisherException(Policy policy, Throwable cause) {
        super(cause);
        this.policy = policy;
    }

    public Policy getPolicy() {
        return policy;
    }

    public enum Policy {
        NonRetriable, Retriable
    }
}
