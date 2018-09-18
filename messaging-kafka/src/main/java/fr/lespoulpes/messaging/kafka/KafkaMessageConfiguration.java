package fr.lespoulpes.messaging.kafka;

import fr.lespoulpes.messaging.bridge.subscriber.consumer.configuration.MessageConsumerConfiguration;
import fr.lespoulpes.messaging.kafka.configuration.KafkaGlobalConfiguration;
import org.apache.kafka.common.serialization.Serde;

public interface KafkaMessageConfiguration<K, V> extends MessageConsumerConfiguration, KafkaGlobalConfiguration {
    Serde<K> getKeySerDe();

    Serde<V> getValueSerDe();
}
