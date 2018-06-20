package fr.lespoulpes.messaging.kafka;

import fr.lespoulpes.messaging.bridge.MessageConsumptionException;
import fr.lespoulpes.messaging.bridge.subscriber.MessageSubscriberTemplate;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class KafkaMessageSubscriberTemplate<K, V> implements MessageSubscriberTemplate<K, V, KafkaMessage<K, V>> {
    private final KafkaConsumer<K, V> kafkaConsumer;

    public KafkaMessageSubscriberTemplate(KafkaConsumer<K, V> kafkaConsumer) {
        this.kafkaConsumer = kafkaConsumer;
    }

    @Override
    public List<KafkaMessage<K, V>> read() {
        return StreamSupport.stream(kafkaConsumer.poll(Long.MAX_VALUE).spliterator(), false)
                .map(cr -> new KafkaMessage<>(new TopicPartition(cr.topic(), cr.partition()), cr.key(), cr.value())).collect(Collectors.toList());
    }

    @Override
    public void consume(KafkaMessage<K, V> message) {

    }

    @Override
    public void handleOnMessageProcessing(MessageConsumptionException e, KafkaMessage<K, V> message) {

    }

    @Override
    public void handleOnMessageProcessing(Exception e, KafkaMessage<K, V> message) {

    }

    @Override
    public void handleOnMessageReading(Exception e) {

    }

    @Override
    public void finish() {

    }
}
