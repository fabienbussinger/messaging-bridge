package fr.lespoulpes.messaging.bridge.publisher;

import fr.lespoulpes.messaging.bridge.Message;

public interface MessageWritingExceptionHandler<K, V, T extends Message<K, V>> {
    /**
     * This method only occurs when writing a message to the subsystem
     *
     * @param e       {@link MessageWriterException} that happened while reading the messages
     * @param message The message which writing failed
     */
    void handleMessageWritingException(MessageWriterException e, T message);
}
