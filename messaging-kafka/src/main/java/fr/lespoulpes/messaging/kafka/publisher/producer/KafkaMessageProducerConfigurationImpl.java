package fr.lespoulpes.messaging.kafka.publisher.producer;

import fr.lespoulpes.messaging.kafka.KafkaMessageConfiguration;
import fr.lespoulpes.messaging.kafka.KafkaMessageConfigurationImpl;

public class KafkaMessageProducerConfigurationImpl<K, V> extends KafkaMessageConfigurationImpl<K, V>
        implements KafkaMessageProducerConfiguration<K, V> {
    private final String clientId;

    public KafkaMessageProducerConfigurationImpl(KafkaMessageConfiguration<K, V> messageConfiguration, String clientId) {
        super(messageConfiguration);
        this.clientId = clientId;
    }

    public String getClientId() {
        return clientId;
    }
}
