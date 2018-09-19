package fr.lespoulpes.messaging.bridge.publisher.producer;

import fr.lespoulpes.messaging.bridge.Message;
import fr.lespoulpes.messaging.bridge.publisher.producer.exceptions.MessageProductionException;

/**
 * Contract used by a publisher to retrieve a {@code Message}.
 * Client must implement their own logic in order to produce a message that could be handled by the publisher
 *
 * @param <K>
 * @param <V>
 */
@FunctionalInterface
public interface MessageProducer<K, V> {
    Message<K, V> produce() throws MessageProductionException;
}
