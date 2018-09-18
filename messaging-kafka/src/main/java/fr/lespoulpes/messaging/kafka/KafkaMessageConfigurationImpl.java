package fr.lespoulpes.messaging.kafka;

import org.apache.kafka.common.serialization.Serde;

public class KafkaMessageConfigurationImpl<K, V> implements KafkaMessageConfiguration<K, V> {
    private final String[] bootstrapServers;
    private final Serde<K> keySerDe;
    private final Serde<V> valueSerDe;

    private KafkaMessageConfigurationImpl(String[] bootstrapServers, Serde<K> keySerDe, Serde<V> valueSerDe) {
        this.bootstrapServers = bootstrapServers;
        this.keySerDe = keySerDe;
        this.valueSerDe = valueSerDe;
    }

    public KafkaMessageConfigurationImpl(KafkaMessageConfiguration<K, V> messageConfiguration) {
        this(messageConfiguration.getBootstrapServers(), messageConfiguration.getKeySerDe(), messageConfiguration.getValueSerDe());
    }

    public String[] getBootstrapServers() {
        return bootstrapServers;
    }

    public Serde<K> getKeySerDe() {
        return keySerDe;
    }

    public Serde<V> getValueSerDe() {
        return valueSerDe;
    }
}
