package com.ontimize.hr.model.core.kafka.factory.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;

public class OntimizeSpringKafkaConsumer<K, V> implements OntimizeKafkaConsumer<K, V> {
    private static final Logger LOGGER = LoggerFactory.getLogger(OntimizeSpringKafkaConsumer.class);

    private String name;
    private Properties properties;
    private AbstractMessageListenerContainer<K, V> messageListenerContainer;
    private ConcurrentKafkaListenerContainerFactory<K, V> concurrentKafkaListenerContainerFactory;
    private List<String> topics = new ArrayList<>();
    private LinkedBlockingQueue<ConsumerRecord<K, V>> records = new LinkedBlockingQueue<>();

    public OntimizeSpringKafkaConsumer(final Properties properties) {
        this.setProperties(properties);
    }

    public OntimizeSpringKafkaConsumer(final String name, final Properties properties) {
        this.name = name;
        this.setProperties(properties);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Properties getProperties() {
        return new Properties(this.properties);
    }

    @Override
    public void setProperties(final Properties properties) {
        this.properties = properties;
    }

    @Override
    public LinkedBlockingQueue<ConsumerRecord<K, V>> getRecords() {
        return this.records;
    }

    @Override
    public void subscribe(final String topic) {
        this.subscribe(Arrays.asList(topic));
    }

    @Override
    public void subscribe(final List<String> topics) {
        this.topics = topics;
    }

    @Override
    public void start(final OntimizeKafkaConsumerAction<K, V> action) {
        this.start(this.topics, action);
    }

    @Override
    public void start(final String topic, final OntimizeKafkaConsumerAction<K, V> action) {
        this.start(Arrays.asList(topic), action);
    }

    @Override
    public void start(final List<String> topics, final OntimizeKafkaConsumerAction<K, V> action) {
        if (this.messageListenerContainer == null) {
            LOGGER.info("{}: start", this.getName());
            this.createContainer(topics);

            this.messageListenerContainer.setupMessageListener(new MessageListener<K, V>() {
                @Override
                public void onMessage(final ConsumerRecord<K, V> data) {
                    LOGGER.debug("Listener \"{}\" received message: \"{}\"", OntimizeSpringKafkaConsumer.this.name, data);
                    OntimizeSpringKafkaConsumer.this.records.add(data);
                    action.run(data);
                }
            });

            this.messageListenerContainer.start();
        }
    }

    @Override
    public void stop() {
        if (this.messageListenerContainer != null) {
            LOGGER.info("{}: stop", this.getName());
            this.messageListenerContainer.stop();
            this.messageListenerContainer = null;
        }
    }

    private void createConcurrentKafkaListenerContainerFactory(final Properties properties) {
        ConsumerFactory<K, V> consumerFactory = new DefaultKafkaConsumerFactory(properties);
        this.concurrentKafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory();
        this.concurrentKafkaListenerContainerFactory.setConsumerFactory(consumerFactory);
    }

    private void createContainer(final List<String> topics) {
        this.createConcurrentKafkaListenerContainerFactory(this.properties);
        final String[] PARAM_TOPICS = topics.toArray(new String[topics.size()]);
        this.messageListenerContainer = this.concurrentKafkaListenerContainerFactory.createContainer(PARAM_TOPICS);
    }
}
