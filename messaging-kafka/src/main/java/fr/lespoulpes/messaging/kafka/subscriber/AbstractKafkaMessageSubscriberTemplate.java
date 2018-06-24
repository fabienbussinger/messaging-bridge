package fr.lespoulpes.messaging.kafka.subscriber;

import fr.lespoulpes.messaging.bridge.subscriber.MessageSubscriberTemplate;
import fr.lespoulpes.messaging.bridge.subscriber.consumer.MessageConsumer;
import fr.lespoulpes.messaging.bridge.subscriber.consumer.MessageConsumptionException;
import fr.lespoulpes.messaging.kafka.KafkaMessage;
import fr.lespoulpes.messaging.kafka.subscriber.consumer.KafkaMessageReader;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public abstract class AbstractKafkaMessageSubscriberTemplate<K, V> implements MessageSubscriberTemplate<K, V, KafkaMessage<K, V>> {
    private static final Logger LOG = LogManager.getLogger(AbstractKafkaMessageSubscriberTemplate.class);

    private final KafkaMessageReader<K, V> messageReader;
    private final MessageConsumer<K, V, KafkaMessage<K, V>> messageConsumer;
    private final KafkaConsumer<K, V> kafkaConsumer;


    public AbstractKafkaMessageSubscriberTemplate(KafkaMessageReader<K, V> messageReader, MessageConsumer<K, V, KafkaMessage<K, V>> messageConsumer, KafkaConsumer<K, V> kafkaConsumer) {
        this.messageReader = messageReader;
        this.messageConsumer = messageConsumer;
        this.kafkaConsumer = kafkaConsumer;
    }

    @Override
    public List<KafkaMessage<K, V>> read() {
        return this.messageReader.read();
    }

    @Override
    public void consume(KafkaMessage<K, V> message) throws MessageConsumptionException {
        this.messageConsumer.consume(message);
    }

    @Override
    public void finish() {
        this.kafkaConsumer.close();
    }
}
