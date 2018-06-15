package fr.lespoulpes.messaging.kafka;

import fr.lespoulpes.messaging.bridge.Message;
import fr.lespoulpes.messaging.bridge.MessageConsumer;
import fr.lespoulpes.messaging.bridge.MessageSubscriber;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KafkaMessageSubscriber<K, V, T, U> implements MessageSubscriber {
	private static final Logger LOG = LogManager.getLogger(KafkaMessageSubscriber.class);

	private final KafkaConsumer<K, V> kafkaConsumer;
	private final MessageConsumer<K, V, KafkaMessageConsumerConfiguration<K, V, T, U>> consumer;
	private final ExecutorService executor = Executors.newSingleThreadExecutor();

	public KafkaMessageSubscriber(MessageConsumer<K, V, KafkaMessageConsumerConfiguration<K, V, T, U>> consumer) {
		this.consumer = consumer;
		kafkaConsumer = null;
	}

	@Override
	public void subscribe() {
		this.executor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					kafkaConsumer.subscribe(consumer.getConfiguration().getTopics());

					while (true) {
						ConsumerRecords<K, V> records = kafkaConsumer.poll(Long.MAX_VALUE);

						if (records != null) {
							Set<TopicPartition> partitions = records.partitions();
							for (TopicPartition partition : partitions) {
								for (ConsumerRecord<K, V> record : records.records(partition)) {
									if (record != null && record.value() != null) {
										try {
											// traitement du message
											consumer.consume(Message.create(record.key(), record.value()));

											// manually commit for Every Message
											manuallyCommit(partition, record);

										} catch (Exception ex) {
											LOG.error(ex);
										}
									}
								}
							}
						}
					}
				} catch (WakeupException e) {
					LOG.error("Polling has been interrupted");
				} finally {
					kafkaConsumer.close();
				}
			}
		});
	}

	/**
	 * Faire un commit manuel de l'avancement de lecture des messages Kafka
	 * 
	 * @param partition
	 *            la partition sur laquelle commiter l'avancement
	 * @param record
	 *            le dernier message traité avec succès
	 */
	private void manuallyCommit(TopicPartition partition, ConsumerRecord<K, V> record) {
		this.kafkaConsumer.commitSync(Collections.singletonMap(partition, new OffsetAndMetadata(record.offset() + 1)));
		LOG.debug("message {} (partition {}) read commited to kafka", record.offset(), record.partition());
	}
}
