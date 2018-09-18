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

    private final MessageSubscriberBuilder<K, V, T> messageSubscriberBuilder;

    private boolean doLoop = true;

    public MessageSubscriberImpl(MessageSubscriberBuilder<K, V, T> messageSubscriberBuilder) {
        Objects.requireNonNull(messageSubscriberBuilder, "A MessageSubscriberBuilder is mandatory");
        this.messageSubscriberBuilder = messageSubscriberBuilder;
    }

    @Override
    public void subscribe() {
        this.executor.execute(() -> {
            MessageReader<K, V, T> messageReader = this.messageSubscriberBuilder.reader();
            MessageConsumptionExceptionHandler<K, V, T> exceptionHandler = this.messageSubscriberBuilder.exceptionHandler();
            MessageConsumer<K, V, T> consumer = this.messageSubscriberBuilder.consumer();

            try {
                while (doLoop) {
                    try {
                        List<T> messages = messageReader.read();

                        for (T message : messages) {
                            try {
                                consumer.consume(message);
                            } catch (MessageConsumptionException e) {
                                LOG.error("Processor as raised an exception with behaviour {}", e.getPolicy());
                                exceptionHandler.handleMessageConsumptionException(e, message);
                            } catch (RuntimeException e) {
                                LOG.error("Unexpected exception while processing a message");
                                exceptionHandler.handleMessageConsumptionException(e, message);
                            }
                        }
                    } catch (MessageReaderException e) {
                        doLoop = false;
                        exceptionHandler.handleMessageReadingException(e);
                    }
                }
            } finally {
                consumer.close();
                messageReader.close();
            }
        });
    }
}
