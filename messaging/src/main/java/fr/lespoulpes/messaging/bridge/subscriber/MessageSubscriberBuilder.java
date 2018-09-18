package fr.lespoulpes.messaging.bridge.subscriber;

import fr.lespoulpes.messaging.bridge.Message;
import fr.lespoulpes.messaging.bridge.subscriber.consumer.MessageConsumer;
import fr.lespoulpes.messaging.bridge.subscriber.consumer.exceptions.MessageConsumptionExceptionHandler;

public interface MessageSubscriberBuilder<K, V, T extends Message<K, V>> {
    MessageReader<K, V, T> reader();
    MessageConsumptionExceptionHandler<K, V, T> exceptionHandler();

    MessageConsumer<K,V,T> consumer();
}
