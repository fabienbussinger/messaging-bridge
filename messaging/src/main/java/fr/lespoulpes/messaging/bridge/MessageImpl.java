package fr.lespoulpes.messaging.bridge;

import java.util.Optional;

public class MessageImpl<K, V> implements Message<K, V> {
    private final Optional<K> key;
    private final V value;

    public MessageImpl(K key, V value) {
        this.key = Optional.ofNullable(key);
        this.value = value;
    }

    public MessageImpl(Message<K, V> message) {
        this.key = message.getKey();
        this.value = message.getValue();
    }

    @Override
    public Optional<K> getKey() {
        return this.key;
    }

    @Override
    public V getValue() {
        return this.value;
    }
}
