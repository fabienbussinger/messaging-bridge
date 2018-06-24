package fr.lespoulpes.messaging.kafka.subscriber.consumer;

import fr.lespoulpes.messaging.kafka.KafkaMessageConfigurationImpl;

import java.util.Set;

public class KafkaMessageConsumerConfigurationImpl<K, V> extends KafkaMessageConfigurationImpl<K, V>
        implements fr.lespoulpes.messaging.kafka.subscriber.KafkaMessageConsumerConfiguration<K, V> {
    private final Set<String> topics;
    private final String groupId;

    public KafkaMessageConsumerConfigurationImpl(KafkaMessageConfigurationImpl<K, V> messageConfiguration, Set<String> topics, String groupId) {
        super(messageConfiguration);
        this.topics = topics;
        this.groupId = groupId;
    }

    public Set<String> getTopics() {
        return topics;
    }

    public String getGroupId() {
        return groupId;
    }
}
