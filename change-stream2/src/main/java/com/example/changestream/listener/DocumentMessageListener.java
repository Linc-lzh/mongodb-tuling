package com.example.changestream.listener;

@Component
public class DocumentMessageListener<S, T> implements MessageListener<S, T> {

    @Override
    public void onMessage(Message<S, T> message) {

        System.out.println(String.format("Received Message in collection %s.\n\trawsource: %s\n\tconverted: %s",
                message.getProperties().getCollectionName(), message.getRaw(), message.getBody()));
    }
}
