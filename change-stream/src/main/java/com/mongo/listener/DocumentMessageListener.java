package com.mongo.listener;

import org.springframework.data.mongodb.core.messaging.Message;
import org.springframework.data.mongodb.core.messaging.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class DocumentMessageListener<S, T> implements MessageListener<S, T> {

    @Override
    public void onMessage(Message<S, T> message) {
        // TODO
        System.out.println(String.format("Received Message in collection %s.\n\trawsource: %s\n\tconverted: %s",
                message.getProperties().getCollectionName(), message.getRaw(), message.getBody()));
    }
}
