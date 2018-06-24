package fr.lespoulpes.messaging.bridge.subscriber.consumer;

public class MessageConsumptionException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private final Policy policy;

    private MessageConsumptionException(Policy policy, String message, Throwable cause) {
        super(message, cause);
        this.policy = policy;
    }

    public static MessageConsumptionExceptionBuilder builder() {
        return new MessageConsumptionExceptionBuilder();
    }

    public Policy getPolicy() {
        return policy;
    }

    public enum Policy {
        DROP, REPLAY, MOVE,
    }

    public static class MessageConsumptionExceptionBuilder {
        private MessageConsumptionException.Policy policy;
        private String message;
        private Throwable cause;

        public MessageConsumptionExceptionBuilder withPolicy(MessageConsumptionException.Policy policy) {
            this.policy = policy;
            return this;
        }

        public MessageConsumptionExceptionBuilder message(String message) {
            this.message = message;
            return this;
        }

        public MessageConsumptionExceptionBuilder cause(Throwable cause) {
            this.cause = cause;
            return this;
        }

        public MessageConsumptionException build() {
            return new MessageConsumptionException(policy, message, cause);
        }
    }
}
