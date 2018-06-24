package fr.lespoulpes.messaging.bridge.subscriber;

import fr.lespoulpes.messaging.bridge.Message;
import fr.lespoulpes.messaging.bridge.subscriber.consumer.MessageConsumer;
import fr.lespoulpes.messaging.bridge.subscriber.consumer.MessageConsumptionException;

/**
 * Defines all the step of a message consumption<br>
 * This interface acts as a Mediator between dedicated classes that handles specific part of the message's consumption process
 *
 * @param <K>
 * @param <V>
 * @param <T>
 */
public interface MessageSubscriberTemplate<K, V, T extends Message<K, V>> extends MessageReader<K, V, T>, MessageConsumer<K, V, T> {

    /**
     * Tells to handle this {@link MessageConsumptionException} that should allow a proper behaviour
     * This method can only be called after a failed processing
     *
     * @param e       {@link MessageConsumptionException}
     * @param message Current {@link Message} for which the processing has failed
     */
    void handleOnMessageProcessing(MessageConsumptionException e, T message);

    /**
     * Tells to handle an unexpected exception.
     * Most of the time, this means to drop the current message, to avoid other processing.
     *
     * @param e       {@link Exception} The {@link MessageConsumer} might not have handled properly the processing.
     * @param message Current {@link Message} for which the processing has failed
     */
    void handleOnMessageProcessing(Exception e, T message);

    /**
     * This method only occurs when reading the messages
     *
     * @param e {@link Exception} that happened while reading the messages
     */
    void handleOnMessageReading(Exception e);

    /**
     * The method signals the end of life of the {@link MessageSubscriber}
     */
    void finish();
}
