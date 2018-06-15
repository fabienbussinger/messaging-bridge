package fr.lespoulpes.messaging.bridge;

public interface Serializer<T,V> {
	V serialize(T object);
}
