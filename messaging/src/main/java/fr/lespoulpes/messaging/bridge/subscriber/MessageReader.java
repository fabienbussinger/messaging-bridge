package fr.lespoulpes.messaging.bridge.subscriber;

import fr.lespoulpes.messaging.bridge.Message;
import fr.lespoulpes.messaging.bridge.shared.Closeable;
import fr.lespoulpes.messaging.bridge.shared.Shutdownable;

import java.util.List;

public interface MessageReader<K, V, T extends Message<K, V>> extends Closeable, Shutdownable {
    /**
     * Returns a bunch of Message.
     * This call is blocking and should never return null
     *
     * @return {@link List} of messages read
     * @throws MessageReaderException This exception must be thrown if the reading encountered a failure
     */
    List<T> read() throws MessageReaderException;
}
