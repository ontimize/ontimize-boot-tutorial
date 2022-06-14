package com.ontimize.hr.model.core.kafka.factory.producer;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class OntimizeSrpingKafkaProducer<K, V> implements OntimizeKafkaProducer<K, V> {
    private static final Logger LOGGER = LoggerFactory.getLogger(OntimizeSrpingKafkaProducer.class);

    private String name;
    private Properties properties;
    private List<String> defaultTopics = new ArrayList<>();
    private KafkaTemplate<K, V> kafkaTemplate;

    public OntimizeSrpingKafkaProducer(final Properties properties) {
        this.setProperties(properties);
    }

    public OntimizeSrpingKafkaProducer(final String name, final Properties properties) {
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
        this.createKafkaTemplate(properties);
    }

    @Override
    public List<String> getDefaultTopics() {
        return this.defaultTopics;
    }

    @Override
    public void setDefaultTopics(final List<String> defaultTopics) {
        this.defaultTopics = defaultTopics;
    }

    @Override
    public void addDefaultTopic(final String defaultTopic) {
        this.defaultTopics.add(defaultTopic);
    }

    @Override
    public void sendMessage(final V value, final OntimizeKafkaProducerAction<K, V> action) {
        this.sendMessage(this.getDefaultTopics(), null, value, action);
    }

    @Override
    public void sendMessage(final K key, final V value, final OntimizeKafkaProducerAction<K, V> action) {
        this.sendMessage(this.getDefaultTopics(), key, value, action);
    }

    @Override
    public void sendMessage(final List<String> topics, final K key, final V value, final OntimizeKafkaProducerAction<K, V> action) {
        topics.stream().forEach(topic -> this.sendMessage(topic, key, value, action));
    }

    @Override
    public void sendMessage(final String topic, final K key, final V value, final OntimizeKafkaProducerAction<K, V> action) {
        ProducerRecord<K, V> record;
        if (key != null) {
            record = new ProducerRecord(topic, key, value);
        } else {
            record = new ProducerRecord(topic, value);
        }

        this.kafkaTemplate.send(record).addCallback(new ListenableFutureCallback<SendResult<K, V>>() {
            @Override
            public void onSuccess(final SendResult<K, V> result) {
                LOGGER.debug("Data \"{}\" have been sent ", result.getProducerRecord());
                action.runOnSuccess(result.getProducerRecord());
            }


            @Override
            public void onFailure(final Throwable throwable) {
                LOGGER.debug("Something went wrong with the data \"{}\" ", value);
                action.runOnFailure(new ProducerRecord<K, V>(topic, key, value), throwable);
            }
        });
    }

    private void createKafkaTemplate(final Properties properties) {
        this.kafkaTemplate = new KafkaTemplate(new DefaultKafkaProducerFactory(properties));
    }
}
