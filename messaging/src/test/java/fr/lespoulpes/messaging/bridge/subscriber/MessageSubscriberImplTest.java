package fr.lespoulpes.messaging.bridge.subscriber;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MessageSubscriberImplTest {
    @Test
    public void cannot_instantiate_with_null() {
        Assertions.assertThrows(NullPointerException.class, () -> new MessageSubscriberImpl<>(null));
    }
}
