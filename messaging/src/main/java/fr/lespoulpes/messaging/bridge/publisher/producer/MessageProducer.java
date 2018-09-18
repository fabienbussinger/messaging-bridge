package fr.lespoulpes.messaging.bridge.publisher.producer;

import fr.lespoulpes.messaging.bridge.Message;
import fr.lespoulpes.messaging.bridge.publisher.producer.exceptions.MessageProductionException;

/**
 * Contract used by a publisher to retrieve a {@code Message}.
 * Client must implement their own logic in order to produce a message that could be handled by the publisher
 * @param <K>
 * @param <V>
 * @param <T>
 */
@FunctionalInterface
public interface MessageProducer<K, V, T extends Message<K, V>> {
    T produce() throws MessageProductionException;
}
