package fr.lespoulpes.messaging.bridge.kafka;

import java.util.Set;

import fr.lespoulpes.messaging.bridge.MessageConsumerConfiguration;
import fr.lespoulpes.messaging.bridge.MessageSerDes;

public class KafkaMessageConsumerConfiguration<K, V, T, U> implements MessageConsumerConfiguration<K, V, T, U> {
	private final Set<String> topics;
	private final MessageSerDes<K, V, T, U> messageSerDes;

	public KafkaMessageConsumerConfiguration(Set<String> topics, MessageSerDes<K, V, T, U> messageSerDes) {
		this.topics = topics;
		this.messageSerDes = messageSerDes;
	}

	public Set<String> getTopics() {
		return topics;
	}

	@Override
	public MessageSerDes<K, V, T, U> getMessageSerDes() {
		return this.messageSerDes;
	}

}
