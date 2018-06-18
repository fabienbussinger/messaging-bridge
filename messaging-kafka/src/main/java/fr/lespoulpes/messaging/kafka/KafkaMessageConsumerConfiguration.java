package fr.lespoulpes.messaging.kafka;

import fr.lespoulpes.messaging.bridge.MessageConsumerConfiguration;
import org.apache.kafka.common.serialization.Serde;

import java.util.Set;

public class KafkaMessageConsumerConfiguration<K, V> implements MessageConsumerConfiguration {
    private final String[] bootstrapServers;
    private final Set<String> topics;
    private final String groupId;
    private final Serde<K> keySerDe;
    private final Serde<V> valueSerDe;

    public KafkaMessageConsumerConfiguration(String[] bootstrapServers, Set<String> topics, Serde<K> keySerDe, Serde<V> valueSerDe, String groupId) {
        this.bootstrapServers = bootstrapServers;
        this.topics = topics;
        this.keySerDe = keySerDe;
        this.valueSerDe = valueSerDe;
        this.groupId = groupId;
    }

    public Set<String> getTopics() {
        return topics;
    }

    public String[] getBootstrapServers() {
        return bootstrapServers;
    }

    public String getGroupId() {
        return groupId;
    }

    public Serde<K> getKeySerDe() {
        return keySerDe;
    }

    public Serde<V> getValueSerDe() {
        return valueSerDe;
    }
}
