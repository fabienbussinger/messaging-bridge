package fr.lespoulpes.messaging.bridge;

public interface SerDes<T,V> {
	Serializer<T, V> getSerializer();
	
	Deserializer<V, T> getDeserializer();
}
