package fr.lespoulpes.messaging.bridge.publisher;

import fr.lespoulpes.messaging.bridge.Message;
import fr.lespoulpes.messaging.bridge.publisher.producer.MessageProducer;
import fr.lespoulpes.messaging.bridge.publisher.producer.exceptions.MessageProductionException;
import fr.lespoulpes.messaging.bridge.publisher.producer.exceptions.MessageProductionExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

public class MessagePublisherImpl<K, V, T extends Message<K, V>> implements  MessagePublisher<K, V> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessagePublisherImpl.class);
    private final MessagePublisherDependenciesHolder<K, V, T> messagePublisherDependenciesHolder;

    public MessagePublisherImpl(MessagePublisherDependenciesHolder<K, V, T> messagePublisherDependenciesHolder) {
        Objects.requireNonNull(messagePublisherDependenciesHolder, "A MessagePublisherDependenciesHolder is mandatory");
        this.messagePublisherDependenciesHolder = messagePublisherDependenciesHolder;
    }

    @Override
    public void publish(MessageProducer<K, V> messageProducer) throws MessagePublisherException {
        MessageWriter<K, V, T> writer = this.messagePublisherDependenciesHolder.writer();
        MessageProductionExceptionHandler messageProductionExceptionHandler = this.messagePublisherDependenciesHolder.productionExceptionHandler();
        MessageWritingExceptionHandler<K, V, T> messageWritingExceptionHandler = this.messagePublisherDependenciesHolder.writingExceptionHandler();
        T message = getMessage(messageProducer, messageProductionExceptionHandler);

        if(message != null) {
            try {
                writer.write(message);
            } catch (MessageWriterException e) {
                LOGGER.error("Writing of the message raised an exception. This writer tells that it's a {} exception. Handler is aware of it", e.getPolicy());
                messageWritingExceptionHandler.handleMessageWritingException(e, message);
            }
        }
    }

    private T getMessage(MessageProducer<K, V> messageProducer, MessageProductionExceptionHandler messageProductionExceptionHandler) {
        try {
            return this.messagePublisherDependenciesHolder.messageFactory().create(messageProducer.produce());
        } catch (MessageProductionException e) {
            LOGGER.error("Message production raised an exception. No message will be written");
            messageProductionExceptionHandler.handleMessageProductionException(e);
            return null;
        }
    }

    @Override
    public void close() throws IOException {
        this.messagePublisherDependenciesHolder.writer().close();
    }
}
