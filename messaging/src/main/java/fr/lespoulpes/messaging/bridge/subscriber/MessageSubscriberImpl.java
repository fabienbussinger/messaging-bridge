package fr.lespoulpes.messaging.bridge.subscriber;

import fr.lespoulpes.messaging.bridge.Message;
import fr.lespoulpes.messaging.bridge.MessageConsumptionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageSubscriberImpl<K, V, T extends Message<K, V>> implements MessageSubscriber {
    private static final Logger LOG = LogManager.getLogger(MessageSubscriberImpl.class);

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private final MessageSubscriberTemplate<K, V, T> messageSubscriberTemplate;

    public MessageSubscriberImpl(MessageSubscriberTemplate<K, V, T> messageSubscriberTemplate) {
        this.messageSubscriberTemplate = messageSubscriberTemplate;
    }

    @Override
    public void subscribe() {
        this.executor.execute(() -> {
            try {
                while (true) {
                    List<T> messages = this.messageSubscriberTemplate.read();

                    for (T message : messages) {
                        try {
                            this.messageSubscriberTemplate.consume(message);
                        } catch (MessageConsumptionException e) {
                            LOG.error("Processor as raised an exception with behaviour {}", e.getPolicy());
                            this.messageSubscriberTemplate.handleOnMessageProcessing(e, message);
                        } catch (Exception e) {
                            LOG.error("Unexpected exception while processing a message");
                            this.messageSubscriberTemplate.handleOnMessageProcessing(e, message);
                        }
                    }
                }
            } catch (Exception e) {
                this.messageSubscriberTemplate.handleOnMessageReading(e);
            } finally {
                this.messageSubscriberTemplate.finish();
            }
        });
    }
}
