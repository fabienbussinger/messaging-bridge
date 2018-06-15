package fr.lespoulpes.messaging.bridge;

public interface MessagePublisher<K, V> {
	void publish(MessageProducer<K, V> producer);
	void close();
}
