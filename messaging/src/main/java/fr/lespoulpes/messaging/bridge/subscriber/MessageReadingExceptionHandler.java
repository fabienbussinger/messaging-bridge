package fr.lespoulpes.messaging.bridge.subscriber;

import fr.lespoulpes.messaging.bridge.Message;

public interface MessageReadingExceptionHandler<K, V, T extends Message<K, V>> {
    /**
     * This method only occurs when reading the messages
     *
     * @param messageReader {@link MessageReader} responsible of this exception
     * @param e {@link MessageReaderException} that happened while reading the messages
     */
    void handleMessageReadingException(MessageReader<K, V, T> messageReader, MessageReaderException e);
}
