package fr.lespoulpes.messaging.bridge.subscriber.consumer;

import fr.lespoulpes.messaging.bridge.Message;

/**
 * Defines what should be done to consume a message.
 *
 * @param <K> Type of the message's key
 * @param <V> Type of the message's value
 */
public interface MessageConsumer<K, V, T extends Message<K, V>> {
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
}
