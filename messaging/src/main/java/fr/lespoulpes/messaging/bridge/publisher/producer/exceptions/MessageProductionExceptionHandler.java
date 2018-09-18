package fr.lespoulpes.messaging.bridge.publisher.producer.exceptions;

import fr.lespoulpes.messaging.bridge.Message;
import fr.lespoulpes.messaging.bridge.publisher.MessageWriterException;

public interface MessageProductionExceptionHandler<K, V, T extends Message<K, V>> {
    /**
     * Tells to handle this {@link MessageProductionException} that should allow a proper behaviour
     * This method can only be called after a failed production attemps
     *
     * @param e {@link MessageProductionException}
     */
    void handleMessageProductionException(MessageProductionException e);

    /**
     * Tells to handle an unexpected exception.
     * Most of the time, this means to drop the current message, to avoid other processing.
     *
     * @param e {@link RuntimeException} The {@link fr.lespoulpes.messaging.bridge.publisher.producer.MessageProducer} might not have handled properly the processing.
     */
    void handleMessageProductionException(RuntimeException e);

    /**
     * This method only occurs when reading the messages
     *
     * @param e       {@link MessageWriterException} that happened while reading the messages
     * @param message The message which writing failed
     */
    void handleMessageWritingException(MessageWriterException e, T message);
}
