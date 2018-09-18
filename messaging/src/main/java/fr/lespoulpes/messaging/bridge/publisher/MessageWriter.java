package fr.lespoulpes.messaging.bridge.publisher;

import fr.lespoulpes.messaging.bridge.Message;
import fr.lespoulpes.messaging.bridge.shared.Closeable;

/**
 * This is the technical contract to handle how to write a message to the subsystem.
 */
public interface MessageWriter<K, V, T extends Message<K, V>> extends Closeable {
    /**
     * Delegate the writing of the message to the subsystem
     * @param message
     * @throws MessageWriterException
     */
    void write(T message) throws MessageWriterException;
}
