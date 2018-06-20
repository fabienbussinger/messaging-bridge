package fr.lespoulpes.messaging.bridge.subscriber;

import fr.lespoulpes.messaging.bridge.Message;
import fr.lespoulpes.messaging.bridge.MessageConsumptionException;

import java.util.List;

/**
 * Defines all the step of a message consumption<br>
 * Impleme
 *
 * @param <K>
 * @param <V>
 * @param <T>
 */
public interface MessageSubscriberTemplate<K, V, T extends Message<K, V>> {

    /**
     * Returns a bunch of Message.
     * This call is blocking and should never return null
     *
     * @return {@link List} of messages read
     */
    List<T> read();

    /**
     * Consume the message. The consumption must be properly done. <br>
     * We mean that if the consumption is OK, then the message should not be available for another consumption.<br>
     * Shortly, the message should be commited
     * <p>
     * If the consumption is KO. This method must raise a {@link MessageConsumptionException} which allows to specify a policy to behave differently
     *
     * @param message {@link Message} to be processed
     * @throws MessageConsumptionException An exception that tells what to do with the message
     */
    void consume(T message) throws MessageConsumptionException;

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
     * @param e       {@link Exception} The {@link fr.lespoulpes.messaging.bridge.MessageConsumer} might not have handled properly the processing.
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
