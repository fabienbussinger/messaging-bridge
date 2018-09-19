package fr.lespoulpes.messaging.bridge.subscriber;

import fr.lespoulpes.messaging.bridge.shared.Closeable;
import fr.lespoulpes.messaging.bridge.shared.Shutdownable;

/**
 * A message subscriber acts as a listener for incoming message.
 * Generally it's materialized with a single endless thread reading for the messages.
 * This simple interface hides all the complexity.
 */
public interface MessageSubscriber extends Shutdownable  {
    /**
     * Start the process of listening the incoming messages
     */
    void subscribe();
}
