package fr.lespoulpes.messaging.bridge.publisher;

import fr.lespoulpes.messaging.bridge.Message;

import java.io.Closeable;

public interface MessagePublisher<K, V, T extends Message<K, V>> extends Closeable {
    void publish(T message) throws MessagePublisherException;
}
