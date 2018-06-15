package fr.lespoulpes.messaging.kafka;

import fr.lespoulpes.messaging.bridge.Message;
import fr.lespoulpes.messaging.bridge.MessageProducer;
import fr.lespoulpes.messaging.bridge.MessagePublisher;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public class KafkaMessagePublisher<K, V> implements MessagePublisher<K, V> {
	private static final Logger LOG = LogManager.getLogger(KafkaMessagePublisher.class);
	private final String topic;
	private final KafkaProducer<K, V> kafkaProducer;

	public KafkaMessagePublisher(Supplier<KafkaProducer<K, V>> kafkaProducer, String topic) {
		this.topic = topic;
		this.kafkaProducer = kafkaProducer.get();
	}

	@Override
	public void publish(MessageProducer<K, V> producer) {
		Message<K, V> message = producer.produce();

		if (this.canBeSent(message)) {
			this.kafkaProducer.send(new ProducerRecord<>(this.topic, message.getKey(), message.getValue()),
					new Callback() {
						@Override
						public void onCompletion(RecordMetadata metadata, Exception exception) {
							if (exception == null) {
								LOG.info("Message sent to topic {} - partition {} - offset {}", metadata.topic(),
										metadata.partition(), metadata.offset());
							} else {
								LOG.error("Message sending failed to topic {} - partition {} - offset {}",
										metadata.topic(), metadata.partition(), metadata.offset(), exception);
							}
						}
					});
			this.kafkaProducer.flush();
		}
	}

	private boolean canBeSent(Message<K, V> message) {
		return message != null && message.getValue() != null;
	}

	@Override
	public void close() {
		this.kafkaProducer.close();
	}
}
