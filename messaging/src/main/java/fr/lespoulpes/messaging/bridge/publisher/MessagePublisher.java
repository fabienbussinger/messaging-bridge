package fr.lespoulpes.messaging.bridge.publisher;

import fr.lespoulpes.messaging.bridge.Message;
import fr.lespoulpes.messaging.bridge.publisher.producer.MessageProducer;

import java.io.Closeable;

public interface MessagePublisher<K, V, T extends Message<K, V>> extends Closeable {
    void publish(MessageProducer<K, V, T> producer) throws MessagePublisherException;
}
