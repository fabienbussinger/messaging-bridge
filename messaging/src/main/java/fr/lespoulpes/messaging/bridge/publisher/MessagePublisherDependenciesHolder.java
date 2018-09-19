package fr.lespoulpes.messaging.bridge.publisher;

import fr.lespoulpes.messaging.bridge.Message;
import fr.lespoulpes.messaging.bridge.publisher.producer.exceptions.MessageProductionExceptionHandler;

public interface MessagePublisherDependenciesHolder<K, V, T extends Message<K, V>> {
    MessageWriter<K, V, T> writer();

    MessageProductionExceptionHandler productionExceptionHandler();

    MessageFactory<K, V, T> messageFactory();

    MessageWritingExceptionHandler<K,V,T> writingExceptionHandler();
}
