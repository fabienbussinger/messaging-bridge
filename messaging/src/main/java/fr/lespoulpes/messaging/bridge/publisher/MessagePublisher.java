package fr.lespoulpes.messaging.bridge.publisher;

import fr.lespoulpes.messaging.bridge.publisher.producer.MessageProducer;

import java.io.Closeable;

public interface MessagePublisher<K, V> extends Closeable {
    void publish(MessageProducer<K, V> messageProducer) throws MessagePublisherException;
}
