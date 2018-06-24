package fr.lespoulpes.messaging.bridge.subscriber;

import fr.lespoulpes.messaging.bridge.Message;

import java.util.List;

public interface MessageReader<K, V, T extends Message<K, V>> {
    /**
     * Returns a bunch of Message.
     * This call is blocking and should never return null
     *
     * @return {@link List} of messages read
     */
    List<T> read();
}
