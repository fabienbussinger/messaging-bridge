package fr.lespoulpes.messaging.bridge.publisher;

import fr.lespoulpes.messaging.bridge.Message;
import fr.lespoulpes.messaging.bridge.publisher.producer.exceptions.MessageProductionExceptionHandler;

public interface MessagePublisherBuilder<K, V, T extends Message<K, V>> {
    MessageWriter<K, V, T> writer();

    MessageProductionExceptionHandler<K,V,T> exceptionHandler();
}
