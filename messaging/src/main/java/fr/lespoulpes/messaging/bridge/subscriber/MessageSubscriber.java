package fr.lespoulpes.messaging.bridge.subscriber;

/**
 * A message subscriber acts as a listener for incoming message.
 * Generally it's materialized with a single endless thread reading for the messages.
 * This simple interface hides all the complexity.
 */
public interface MessageSubscriber {
    void subscribe();
}
