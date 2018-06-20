package fr.lespoulpes.messaging.kafka;

import fr.lespoulpes.messaging.bridge.MessageImpl;
import org.apache.kafka.common.TopicPartition;

public class KafkaMessage<K, V> extends MessageImpl<K, V> {
    private final TopicPartition topicPartition;

    public KafkaMessage(TopicPartition topicPartition, K key, V value) {
        super(key, value);
        this.topicPartition = topicPartition;
    }

    public TopicPartition getTopicPartition() {
        return topicPartition;
    }
}
