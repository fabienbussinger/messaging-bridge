package fr.lespoulpes.messaging.bridge.publisher.producer.exceptions;

import fr.lespoulpes.messaging.bridge.Message;
import fr.lespoulpes.messaging.bridge.publisher.producer.MessageProducer;

public interface MessageProductionExceptionHandler {
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
     * @param e {@link RuntimeException} The {@link MessageProducer} might not have handled properly the processing.
     */
    void handleMessageProductionException(RuntimeException e);
}
