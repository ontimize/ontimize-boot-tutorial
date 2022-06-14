package com.ontimize.hr.model.core.kafka.factory;

import com.ontimize.hr.model.core.kafka.factory.consumer.OntimizeKafkaConsumer;
import com.ontimize.hr.model.core.kafka.factory.consumer.OntimizeSpringKafkaConsumer;
import com.ontimize.hr.model.core.kafka.factory.producer.OntimizeKafkaProducer;
import com.ontimize.hr.model.core.kafka.factory.producer.OntimizeSrpingKafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class OntimizeKafkaFactoryImpl implements OntimizeKafkaFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(OntimizeKafkaFactoryImpl.class);

    private final Map<String, OntimizeKafkaProducer> producers = new HashMap<>();
    private final Map<String, OntimizeKafkaConsumer> consumers = new HashMap<>();

    @Override
    public void createProducer(final String name, final Properties properties) {
        LOGGER.info("Register OntimizeProducer: name={}, properties={}", name, properties);
        this.producers.put(name, new OntimizeSrpingKafkaProducer(name, properties));
    }

    @Override
    public void createProducer(final String name, final List<String> topics, final Properties properties) {
        LOGGER.info("Register OntimizeProducer: name={}, topics={}, properties={}", name, topics, properties);
        OntimizeKafkaProducer producer = new OntimizeSrpingKafkaProducer(name, properties);
        if (topics != null && !topics.isEmpty()) {
            producer.setDefaultTopics(topics);
        }
        this.producers.put(name, producer);
    }

    @Override
    public void createConsumer(final String name, final Properties properties) {
        LOGGER.info("Register OntimizeConsumer: name={}, properties={}", name, properties);
        this.consumers.put(name, new OntimizeSpringKafkaConsumer(name, properties));
    }

    @Override
    public void createConsumer(final String name, final List<String> topics, final Properties properties) {
        LOGGER.info("Register OntimizeConsumer: name={}, topics={}, properties={}", name, topics, properties);
        OntimizeKafkaConsumer consumer = new OntimizeSpringKafkaConsumer(name, properties);
        if (topics != null && !topics.isEmpty()) {
            consumer.subscribe(topics);
        }
        this.consumers.put(name, consumer);
    }

    @Override
    public OntimizeKafkaConsumer getConsumer(final String name) {
        return this.consumers.get(name);
    }

    @Override
    public OntimizeKafkaProducer getProducer(final String name) {
        return this.producers.get(name);
    }
}
