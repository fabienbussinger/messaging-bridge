package fr.lespoulpes.messaging.bridge.publisher;

import fr.lespoulpes.messaging.bridge.Message;

public interface MessageFactory<K, V, T extends Message<K, V>> {
    T create(Message<K, V> keyValue);
}
