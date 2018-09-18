package fr.lespoulpes.messaging.bridge.shared;

/**
 * Contract to notify the end of life and to "close" further processing
 */
public interface Closeable {
    void close();
}
