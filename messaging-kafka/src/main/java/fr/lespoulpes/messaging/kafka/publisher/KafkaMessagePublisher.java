package fr.lespoulpes.messaging.kafka.publisher;

import fr.lespoulpes.messaging.bridge.Message;
import fr.lespoulpes.messaging.bridge.publisher.MessagePublisher;
import fr.lespoulpes.messaging.bridge.publisher.MessagePublisherException;
import fr.lespoulpes.messaging.kafka.KafkaMessage;
import fr.lespoulpes.messaging.kafka.publisher.producer.KafkaMessageProducerConfigurationImpl;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.RetriableException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

public class KafkaMessagePublisher<K, V> implements MessagePublisher<K, V, KafkaMessage<K, V>> {
    private static final Logger LOG = LogManager.getLogger(KafkaMessagePublisher.class);
    private final KafkaProducer<K, V> kafkaProducer;

    public KafkaMessagePublisher(KafkaMessageProducerConfigurationImpl<K, V> configuration) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                configuration.getBootstrapServers());
        props.put(ProducerConfig.CLIENT_ID_CONFIG, configuration.getClientId());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                configuration.getKeySerDe().serializer().getClass().getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                configuration.getValueSerDe().serializer().getClass().getName());
        this.kafkaProducer = new KafkaProducer<>(props);
    }

    /**
     * A message is eligible for sending if it is not null and if it has a value
     *
     * @param message
     * @return true if the message has a value. Implies not null
     */
    private boolean canBeSent(Message<K, V> message) {
        return message != null && message.getValue() != null;
    }

    @Override
    public void close() {
        this.kafkaProducer.close();
    }

    @Override
    public void publish(KafkaMessage<K, V> message) throws MessagePublisherException {
        AtomicReference<Exception> kafkaException = new AtomicReference<>();
        if (this.canBeSent(message)) {
            this.kafkaProducer.send(new ProducerRecord<>(message.getTopicPartition().topic(), message.getKey(), message.getValue()),
                    (metadata, exception) -> {
                        if (exception == null) {
                            LOG.info("Message sent to topic {} - partition {} - offset {}", metadata.topic(),
                                    metadata.partition(), metadata.offset());
                        } else {
                            LOG.error("Message sending failed to topic {} - partition {} - offset {}",
                                    metadata.topic(), metadata.partition(), metadata.offset(), exception);
                            kafkaException.set(exception);
                        }
                    });
            this.kafkaProducer.flush();
        }
        if (kafkaException.get() != null) {
            Exception current = kafkaException.get();
            throw new MessagePublisherException(current instanceof RetriableException ? MessagePublisherException.Policy.Retriable : MessagePublisherException.Policy.NonRetriable, current);
        }
    }
}
