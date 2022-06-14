package com.ontimize.hr.model.core.kafka.factory.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;

/**
 * Clase que nos permite establecer una lógica cuando a través de un OntimizeKafkaConsumer se recupere la información de
 * unos determinados topics.
 *
 * @param <K> El tipo de la clave de la información
 * @param <V> El tipo de la información
 */
public interface OntimizeKafkaConsumerAction<K, V> {

    /**
     * Acción que se va a ejecutar cuando se recupere la información que se reciba en los topics a través de un OntmizeKafkaConsumer.
     *
     * @param record La información que se ha recibido de los topics a través de un OntimizeKafkaConsumer
     */
    void run(final ConsumerRecord<K, V> record);
}
