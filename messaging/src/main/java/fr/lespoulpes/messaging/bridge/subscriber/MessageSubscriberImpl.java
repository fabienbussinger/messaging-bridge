package fr.lespoulpes.messaging.bridge.subscriber;

import fr.lespoulpes.messaging.bridge.Message;
import fr.lespoulpes.messaging.bridge.subscriber.consumer.MessageConsumer;
import fr.lespoulpes.messaging.bridge.subscriber.consumer.exceptions.MessageConsumptionException;
import fr.lespoulpes.messaging.bridge.subscriber.consumer.exceptions.MessageConsumptionExceptionHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageSubscriberImpl<K, V, T extends Message<K, V>> implements MessageSubscriber {
    private static final Logger LOG = LogManager.getLogger(MessageSubscriberImpl.class);

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private final MessageSubscriberDependenciesHolder<K, V, T> messageSubscriberDependenciesHolder;

    private boolean doLoop = true;

    public MessageSubscriberImpl(MessageSubscriberDependenciesHolder<K, V, T> messageSubscriberDependenciesHolder) {
        Objects.requireNonNull(messageSubscriberDependenciesHolder, "A MessageSubscriberDependenciesHolder is mandatory");
        this.messageSubscriberDependenciesHolder = messageSubscriberDependenciesHolder;
    }

    @Override
    public void subscribe() {
        this.executor.execute(() -> {
            MessageReader<K, V, T> messageReader = this.messageSubscriberDependenciesHolder.reader();
            MessageConsumptionExceptionHandler<K, V, T> consumptionExceptionHandler = this.messageSubscriberDependenciesHolder.consumptionExceptionHandler();
            MessageReadingExceptionHandler<K, V, T> readingExceptionHandler = this.messageSubscriberDependenciesHolder.readingExceptionHandler();
            MessageConsumer<K, V> consumer = this.messageSubscriberDependenciesHolder.consumer();

            try {
                while (doLoop) {
                    try {
                        List<T> messages = messageReader.read();

                        for (T message : messages) {
                            try {
                                consumer.consume(message);
                            } catch (MessageConsumptionException e) {
                                LOG.error("Processor as raised an exception with behaviour {}", e.getPolicy());
                                consumptionExceptionHandler.handleMessageConsumptionException(e, message);
                            } catch (RuntimeException e) {
                                LOG.error("Unexpected exception while processing a message");
                                consumptionExceptionHandler.handleMessageConsumptionException(e, message);
                            }
                        }
                    } catch (MessageReaderException e) {
                        readingExceptionHandler.handleMessageReadingException(messageReader, e);
                    }
                }
            } finally {
                messageReader.close();
            }
        });
    }

    @Override
    public void shutdown() {
        this.doLoop = false;
        this.messageSubscriberDependenciesHolder.reader().shutdown();
    }
}
