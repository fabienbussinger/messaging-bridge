package fr.lespoulpes.messaging.bridge.subscriber.consumer.exceptions;

import fr.lespoulpes.messaging.bridge.Message;
import fr.lespoulpes.messaging.bridge.subscriber.MessageReaderException;
import fr.lespoulpes.messaging.bridge.subscriber.consumer.MessageConsumer;

public interface MessageConsumptionExceptionHandler<K, V, T extends Message<K, V>> {
    /**
     * Tells to handle this {@link MessageConsumptionException} that should allow a proper behaviour
     * This method can only be called after a failed processing
     *
     * @param e       {@link MessageConsumptionException}
     * @param message Current {@link Message} for which the processing has failed
     */
    void handleMessageConsumptionException(MessageConsumptionException e, T message);

    /**
     * Tells to handle an unexpected exception.
     * Most of the time, this means to drop the current message, to avoid other processing.
     *
     * @param e       {@link RuntimeException} The {@link MessageConsumer} might not have handled properly the processing.
     * @param message Current {@link Message} for which the processing has failed
     */
    void handleMessageConsumptionException(RuntimeException e, T message);

    /**
     * This method only occurs when reading the messages
     *
     * @param e {@link MessageReaderException} that happened while reading the messages
     */
    void handleMessageReadingException(MessageReaderException e);
}
