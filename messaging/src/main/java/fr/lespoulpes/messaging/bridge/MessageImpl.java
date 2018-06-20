package fr.lespoulpes.messaging.bridge;

public class MessageImpl<K, V> implements Message<K, V> {
    private final K key;
    private final V value;

    public MessageImpl(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public MessageImpl(Message<K, V> message) {
        this.key = message.getKey();
        this.value = message.getValue();
    }

    @Override
    public K getKey() {
        return this.key;
    }

    @Override
    public V getValue() {
        return this.value;
    }
}
