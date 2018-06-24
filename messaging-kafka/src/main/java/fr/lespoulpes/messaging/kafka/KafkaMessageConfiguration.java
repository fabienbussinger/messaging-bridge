package fr.lespoulpes.messaging.kafka;

import fr.lespoulpes.messaging.bridge.subscriber.consumer.MessageConsumerConfiguration;
import org.apache.kafka.common.serialization.Serde;

public interface KafkaMessageConfiguration<K, V> extends MessageConsumerConfiguration {
    String[] getBootstrapServers();

    Serde<K> getKeySerDe();

    Serde<V> getValueSerDe();
}
