package fr.lespoulpes.messaging.kafka.subscriber;

import fr.lespoulpes.messaging.kafka.KafkaMessageConfiguration;

import java.util.Set;

public interface KafkaMessageConsumerConfiguration<K, V> extends KafkaMessageConfiguration<K, V> {
    Set<String> getTopics();

    String getGroupId();
}
