package fr.lespoulpes.messaging.bridge;

/**
 * Define the production of a message. Exception should be handled by the producer itself.
 * If a RuntimeException occurs within produce call, then no message is sent.
 * The message will not be sent to the underlying system if its null or the value of the message itself is null
 *
 * @param <K>
 * @param <V>
 */
@FunctionalInterface
public interface MessageProducer<K, V> {
	Message<K, V> produce();
}
