package fr.lespoulpes.messaging.kafka.subscriber;

import fr.lespoulpes.messaging.bridge.subscriber.MessageReader;
import fr.lespoulpes.messaging.kafka.KafkaMessage;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class KafkaMessageReader<K, V> implements MessageReader<K, V, KafkaMessage<K, V>> {
    private final KafkaConsumer<K, V> kafkaConsumer;

    public KafkaMessageReader(KafkaConsumer<K, V> kafkaConsumer) {
        this.kafkaConsumer = kafkaConsumer;
    }


    @Override
    public List<KafkaMessage<K, V>> read() {
        return StreamSupport.stream(kafkaConsumer.poll(Long.MAX_VALUE).spliterator(), false)
                .map(cr -> new KafkaMessage<>(new TopicPartition(cr.topic(), cr.partition()), cr.key(), cr.value())).collect(Collectors.toList());
    }

    @Override
    public void close() {
        kafkaConsumer.close();
    }

    @Override
    public void shutdown() {
        this.kafkaConsumer.wakeup();
    }
}
