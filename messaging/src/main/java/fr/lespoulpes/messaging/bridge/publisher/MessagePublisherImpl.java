package fr.lespoulpes.messaging.bridge.publisher;

import fr.lespoulpes.messaging.bridge.Message;
import fr.lespoulpes.messaging.bridge.publisher.producer.MessageProducer;
import fr.lespoulpes.messaging.bridge.publisher.producer.exceptions.MessageProductionException;
import fr.lespoulpes.messaging.bridge.publisher.producer.exceptions.MessageProductionExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.Objects;

public class MessagePublisherImpl<K, V, T extends Message<K, V>> implements  MessagePublisher<K, V, T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessagePublisherImpl.class);
    private final MessagePublisherBuilder<K, V, T> messagePublisherBuilder;

    public MessagePublisherImpl(MessagePublisherBuilder<K, V, T> messagePublisherBuilder) {
        Objects.requireNonNull(messagePublisherBuilder, "A MessagePublisherBuilder is mandatory");
        this.messagePublisherBuilder = messagePublisherBuilder;
    }

    @Override
    public void publish(MessageProducer<K, V, T> producer) throws MessagePublisherException {
        MessageWriter<K, V, T> writer = this.messagePublisherBuilder.writer();
        MessageProductionExceptionHandler<K, V, T> messageProductionExceptionHandler = this.messagePublisherBuilder.exceptionHandler();

        T message = getMessage(producer, messageProductionExceptionHandler);

        if(message != null) {
            try {
                writer.write(message);
            } catch (MessageWriterException e) {
                LOGGER.error("Writing of the message raised an exception. This writer tells that it's a {} exception. Handler is aware of it", e.getPolicy());
                messageProductionExceptionHandler.handleMessageWritingException(e, message);
            }
        }
    }

    private T getMessage(MessageProducer<K, V, T> producer, MessageProductionExceptionHandler<K, V, T> messageProductionExceptionHandler) {
        try {
            return producer.produce();
        } catch (MessageProductionException e) {
            LOGGER.error("Message production raised an exception. No message will be written");
            messageProductionExceptionHandler.handleMessageProductionException(e);
            return null;
        }
    }

    @Override
    public void close() throws IOException {
        this.messagePublisherBuilder.writer().close();
    }
}
