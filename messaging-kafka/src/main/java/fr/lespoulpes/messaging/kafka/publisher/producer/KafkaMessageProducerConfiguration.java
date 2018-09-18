package fr.lespoulpes.messaging.kafka.publisher.producer;

import fr.lespoulpes.messaging.kafka.KafkaMessageConfiguration;

import java.util.Set;

public interface KafkaMessageProducerConfiguration<K, V> extends KafkaMessageConfiguration<K, V> {
    String getClientId();

    Set<String> getTopics();
}
