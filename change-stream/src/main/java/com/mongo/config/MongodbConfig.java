package com.mongo.config;

import com.mongo.listener.DocumentMessageListener;
import com.mongodb.client.model.changestream.FullDocument;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.messaging.ChangeStreamRequest;
import org.springframework.data.mongodb.core.messaging.DefaultMessageListenerContainer;
import org.springframework.data.mongodb.core.messaging.MessageListenerContainer;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class MongodbConfig {
    @Bean
    MessageListenerContainer messageListenerContainer(MongoTemplate template, DocumentMessageListener documentMessageListener) {

        Executor executor = Executors.newFixedThreadPool(5);

        MessageListenerContainer messageListenerContainer = new DefaultMessageListenerContainer(template, executor) {
            @Override
            public boolean isAutoStartup() {
                return true;
            }
        };

        ChangeStreamRequest<Document> request = ChangeStreamRequest.builder(documentMessageListener)
                .collection("user")  //需要监听的集合名
                //过滤需要监听的操作类型，可以根据需求指定过滤条件
                .filter(Aggregation.newAggregation(Aggregation.match(
                        Criteria.where("operationType").in("insert", "update", "delete"))))
                //不设置时，文档更新时，只会发送变更字段的信息，设置UPDATE_LOOKUP会返回文档的全部信息
                .fullDocumentLookup(FullDocument.UPDATE_LOOKUP)
                .build();
        messageListenerContainer.register(request, Document.class);

        return messageListenerContainer;
    }
}
