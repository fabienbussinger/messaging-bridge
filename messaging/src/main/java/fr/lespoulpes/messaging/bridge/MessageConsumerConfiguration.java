package fr.lespoulpes.messaging.bridge;

public interface MessageConsumerConfiguration<K, V, T, U> {
	MessageSerDes<K, V, T, U> getMessageSerDes();
}
