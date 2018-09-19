package fr.lespoulpes.messaging.kafka.publisher;

import fr.lespoulpes.messaging.bridge.publisher.MessageFactory;
import fr.lespoulpes.messaging.bridge.publisher.MessagePublisherDependenciesHolder;
import fr.lespoulpes.messaging.bridge.publisher.MessageWriter;
import fr.lespoulpes.messaging.bridge.publisher.MessageWritingExceptionHandler;
import fr.lespoulpes.messaging.bridge.publisher.producer.exceptions.MessageProductionExceptionHandler;
import fr.lespoulpes.messaging.kafka.KafkaMessage;
import fr.lespoulpes.messaging.kafka.publisher.producer.KafkaMessageProducerConfiguration;

public class KafkaMessagePublisherDependenciesHolder<K, V> implements MessagePublisherDependenciesHolder<K, V, KafkaMessage<K, V>> {
    private final KafkaMessageProducerConfiguration<K, V> producerConfiguration;
private final MessageWriter<K, V, KafkaMessage<K, V>> messageWriter;
    public KafkaMessagePublisherDependenciesHolder(KafkaMessageProducerConfiguration<K, V> producerConfiguration) {
        this.producerConfiguration = producerConfiguration;
        this.messageWriter = new KafkaMessageWriter<>(producerConfiguration);
    }

    @Override
    public MessageWriter<K, V, KafkaMessage<K, V>> writer() {
        return this.messageWriter;
    }

    @Override
    public MessageProductionExceptionHandler productionExceptionHandler() {
        return null;
    }

    @Override
    public MessageWritingExceptionHandler<K, V, KafkaMessage<K, V>> writingExceptionHandler() {
        return null;
    }

    @Override
    public MessageFactory<K, V, KafkaMessage<K, V>> messageFactory() {
        return null;
    }
}
