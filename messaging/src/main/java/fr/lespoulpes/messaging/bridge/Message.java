package fr.lespoulpes.messaging.bridge;

import java.util.Optional;

/**
 * A message is materialized by a key, used for partitioning and a value.
 * 
 * The key is not mandatory and can be null
 *
 * @param <K>
 * @param <V>
 */
public interface Message<K, V> {
	Optional<K> getKey();
	V getValue();
	
	static <K,V> Message<K,V> create(K key, V value) {
		return new Message<K,V>() {
			@Override
			public Optional<K> getKey() {
				return Optional.ofNullable(key);
			}

			@Override
			public V getValue() {
				return value;
			}
		};
	}
	
	static <K,V> Message<K,V> create(V value) {
		return new Message<K,V>() {
			@Override
			public Optional<K> getKey() {
				return Optional.empty();
			}

			@Override
			public V getValue() {
				return value;
			}
		};
	}
}
