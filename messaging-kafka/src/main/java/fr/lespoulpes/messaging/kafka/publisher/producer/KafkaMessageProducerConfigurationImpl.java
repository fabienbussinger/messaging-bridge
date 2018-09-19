package fr.lespoulpes.messaging.kafka.publisher.producer;

import fr.lespoulpes.messaging.kafka.KafkaMessageConfiguration;
import fr.lespoulpes.messaging.kafka.KafkaMessageConfigurationImpl;

import java.util.Set;

public class KafkaMessageProducerConfigurationImpl<K, V> extends KafkaMessageConfigurationImpl<K, V>
        implements KafkaMessageProducerConfiguration<K, V> {
    private final String clientId;
    private final Set<String> topics;

    public KafkaMessageProducerConfigurationImpl(KafkaMessageConfiguration<K, V> messageConfiguration, String clientId, Set<String> topics) {
        super(messageConfiguration);
        this.clientId = clientId;
        this.topics = topics;
    }

    @Override
    public Set<String> getTopics() {
        return null;
    }

    public String getClientId() {
        return clientId;
    }
}
