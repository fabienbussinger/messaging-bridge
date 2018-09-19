package fr.lespoulpes.messaging.kafka.publisher;

import fr.lespoulpes.messaging.bridge.publisher.MessageWriter;
import fr.lespoulpes.messaging.bridge.publisher.MessageWriterException;
import fr.lespoulpes.messaging.bridge.publisher.MessageWriterExceptionPolicy;
import fr.lespoulpes.messaging.kafka.KafkaMessage;
import fr.lespoulpes.messaging.kafka.publisher.producer.KafkaMessageProducerConfiguration;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.RetriableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class KafkaMessageWriter<K, V> implements MessageWriter<K, V, KafkaMessage<K, V>> {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaMessageWriter.class);

    private final KafkaMessageProducerConfiguration<K, V> producerConfiguration;
    private final KafkaProducer<K, V> kafkaProducer;

    public KafkaMessageWriter(KafkaMessageProducerConfiguration<K, V> producerConfiguration) {
        this.producerConfiguration = producerConfiguration;
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                producerConfiguration.getBootstrapServers());
        props.put(ProducerConfig.CLIENT_ID_CONFIG, producerConfiguration.getClientId());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                producerConfiguration.getKeySerDe().serializer().getClass().getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                producerConfiguration.getValueSerDe().serializer().getClass().getName());
        this.kafkaProducer = new KafkaProducer<>(props);
    }

    private ProducerRecord<K, V> create(String topic, KafkaMessage<K, V> message) {
        if (message.getKey().isPresent()) {
            return new ProducerRecord<>(topic, message.getKey().get(), message.getValue());
        } else {
            return new ProducerRecord<>(topic, message.getValue());
        }
    }

    @Override
    public void write(KafkaMessage<K, V> message) throws MessageWriterException {
        for (String topic : this.producerConfiguration.getTopics()) {
            try {
                this.kafkaProducer.send(create(topic, message), (metadata, exception) -> {
                    if (exception == null) {
                        LOG.info("Message sent to topic {} - partition {} - offset {}", metadata.topic(),
                                metadata.partition(), metadata.offset());
                    } else {
                        LOG.error("Message sending failed to topic {} - partition {} - offset {}",
                                metadata.topic(), metadata.partition(), metadata.offset(), exception);
                    }
                });
                this.kafkaProducer.flush();
            } catch (KafkaException e) {
                throw new MessageWriterException(e instanceof RetriableException ? MessageWriterExceptionPolicy.Retriable : MessageWriterExceptionPolicy.NonRetriable, e);
            }
        }
    }

    @Override
    public void close() {
        this.kafkaProducer.close();
    }
}
