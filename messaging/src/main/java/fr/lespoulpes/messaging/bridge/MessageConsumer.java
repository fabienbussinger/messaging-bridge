package fr.lespoulpes.messaging.bridge;

public interface MessageConsumer<K, V, T extends MessageConsumerConfiguration> {
	void consume(Message<K, V> message) throws MessageConsumptionException;
	
	T getConfiguration();
}
