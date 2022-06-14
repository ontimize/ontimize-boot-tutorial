package com.ontimize.hr.model.core.kafka.factory.producer;

import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * Clase que nos permite establecer una lógica cuando a través de un OntimizeKafkaProducer se envíe correctamente
 * información u ocurra algún error el proceso.
 *
 * @param <K> El tipo de la clave de la información
 * @param <V> El tipo de la información
 */
public interface OntimizeKafkaProducerAction<K, V> {

    /**
     * Acción que se va a ejecutar cuando se envíe correctamente la información a través de un OntmizeKafkaProducer.
     *
     * @param data La información que se ha enviado a través de un OntimizeKafkaProducer
     */
    void runOnSuccess(final ProducerRecord<K, V> data);

    /**
     * Acción que se va a ejecutar cuando se produzca un error al enviar la información a través de un OntmizeKafkaProducer.
     *
     * @param data      La información que se pretendía enviar a través de un OntimizeKafkaProducer
     * @param throwable El error que se ha producido al enviar información a través de un OntimizeKafkaProducer
     */
    void runOnFailure(final ProducerRecord<K, V> data, final Throwable throwable);
}
