package fr.lespoulpes.messaging.kafka.publisher.producer;

import fr.lespoulpes.messaging.kafka.KafkaMessageConfiguration;

public interface KafkaMessageProducerConfiguration<K, V> extends KafkaMessageConfiguration<K, V> {
    String getClientId();
}
