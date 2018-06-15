package fr.lespoulpes.messaging.bridge;

public interface MessageSerDes<K, V, T, U> {
	SerDes<K, T> getKeySerDes();
	SerDes<V, U> getValueSerDes();
	
}
