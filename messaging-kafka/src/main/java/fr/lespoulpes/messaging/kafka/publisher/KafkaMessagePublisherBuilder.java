package fr.lespoulpes.messaging.kafka.publisher;

import fr.lespoulpes.messaging.bridge.publisher.MessagePublisherBuilder;
import fr.lespoulpes.messaging.bridge.publisher.MessageWriter;
import fr.lespoulpes.messaging.bridge.publisher.producer.exceptions.MessageProductionExceptionHandler;
import fr.lespoulpes.messaging.kafka.KafkaMessage;
import fr.lespoulpes.messaging.kafka.publisher.producer.KafkaMessageProducerConfiguration;

public class KafkaMessagePublisherBuilder<K, V> implements MessagePublisherBuilder<K, V, KafkaMessage<K, V>> {
    private final KafkaMessageProducerConfiguration<K, V> producerConfiguration;
private final MessageWriter<K, V, KafkaMessage<K, V>> messageWriter;
    public KafkaMessagePublisherBuilder(KafkaMessageProducerConfiguration<K, V> producerConfiguration) {
        this.producerConfiguration = producerConfiguration;
        this.messageWriter = new KafkaMessageWriter<>(producerConfiguration);
    }

    @Override
    public MessageWriter<K, V, KafkaMessage<K, V>> writer() {
        return this.messageWriter;
    }

    @Override
    public MessageProductionExceptionHandler<K, V, KafkaMessage<K, V>> exceptionHandler() {
        return null;
    }
}
