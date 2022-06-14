package com.ontimize.hr.model.core.kafka.factory;

import com.ontimize.hr.model.core.kafka.factory.consumer.OntimizeKafkaConsumer;
import com.ontimize.hr.model.core.kafka.factory.producer.OntimizeKafkaProducer;

import java.util.List;
import java.util.Properties;

/**
 * Clase que nos permite crear y obtener productores y consumidores de Kafka de Ontimize.
 */
public interface OntimizeKafkaFactory {

    /**
     * Crea un productor de Kafka de Ontimize.
     *
     * @param name       El nombre del productor
     * @param properties Las propiedades de configuración del productor
     */
    void createProducer(final String name, final Properties properties);

    /**
     * Crea un productor de Kafka de Ontimize estableciendole unos topics por defecto.
     *
     * @param name       El nombre del productor
     * @param topic      Los topics por defecto del productor
     * @param properties Las propiedades de configuración del productor
     */
    void createProducer(final String name, final List<String> topic, final Properties properties);

    /**
     * Crea un consumidor de Kafka de Ontimize.
     *
     * @param name       El nombre del consumidor
     * @param properties Las propiedades de configuración del consumidor
     */
    void createConsumer(final String name, final Properties properties);

    /**
     * Crea un consumidor de Kafka de Ontimize estableciendole unos topics por defecto.
     *
     * @param name       El nombre del consumidor
     * @param topics     Los topics por defecto del consumidor
     * @param properties Las propiedades de configuración del consumidor
     */
    void createConsumer(final String name, final List<String> topics, final Properties properties);

    /**
     * Obtiene un productor de Kafka de Ontimize que se haya creado en la factoría a partir de su nombre.
     *
     * @param name El nombre del productor
     * @return El productor de Kafka de Ontimize
     */
    OntimizeKafkaProducer getProducer(final String name);

    /**
     * Obtiene un consumidor de Kafka de Ontimize que se haya creado en la factoría a partir de su nombre.
     *
     * @param name El nombre del consumidor
     * @return El consumidor de Kafka de Ontimize
     */
    OntimizeKafkaConsumer getConsumer(final String name);
}
