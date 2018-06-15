package fr.lespoulpes.messaging.bridge;

public interface Deserializer<V, T> {
	T deserialize(V object);
}
