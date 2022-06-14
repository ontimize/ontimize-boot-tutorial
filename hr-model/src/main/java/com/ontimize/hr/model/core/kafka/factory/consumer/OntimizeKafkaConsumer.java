package com.ontimize.hr.model.core.kafka.factory.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Consumidor de Kafka que nos permite recuperar la información de unos determinados topics
 *
 * @param <K> El tipo de la clave de la información que se va a recuperar
 * @param <V> El tipo de la información que se va a recuperar
 */
public interface OntimizeKafkaConsumer<K, V> {

    /**
     * Obtiene el nombre del consumidor.
     *
     * @return El nombre del consumidor
     */
    String getName();

    /**
     * Obtiene las propiedades de configuración del consumidor.
     *
     * @return Las propiedades de configuración del consumidor
     */
    Properties getProperties();

    /**
     * Establece las propiedades de configuración del consumidor.
     *
     * @param properties Las nuevas propiedades de configuración del consumidor
     */
    void setProperties(final Properties properties);

    /**
     * Obtiene una lista de la información obtenida en el consumidor.
     *
     * @return La lista de la información obtenida en el consumidor
     */
    LinkedBlockingQueue<ConsumerRecord<K, V>> getRecords();

    /**
     * Añade un topic del que se va a obtener la información que se envíe a este.
     *
     * @param topic El topic del que se va a obtener la información
     */
    void subscribe(final String topic);

    /**
     * Añade varios topics de los que se va a obtener la información que se envíe a estos.
     *
     * @param topics La lista de los topics de los que se va a obtener la información
     */
    void subscribe(final List<String> topics);

    /**
     * Inicia en segundo plano el consumidor y procesa la información que se recibe en los topics que se han establecido
     * en el consumidor. Esta información es procesada por un OntimizeConsumerAction.
     *
     * @param action La acción que se va a tomar al recibir información de los topics
     */
    void start(final OntimizeKafkaConsumerAction<K, V> action);

    /**
     * Inicia en segundo plano el consumidor y procesa la información que se recibe en el topic que se ha indicado.
     * Esta información es procesada por un OntimizeConsumerAction.
     *
     * @param topic  El topic del que se va a recuperar la información
     * @param action La acción que se va a tomar al recibir información de los topics
     */
    void start(final String topic, final OntimizeKafkaConsumerAction<K, V> action);

    /**
     * Inicia en segundo plano el consumidor y procesa la información que se recibe en los topics que se han indicado.
     * Esta información es procesada por un OntimizeConsumerAction.
     *
     * @param topics Los topics de los que se va a recuperar la información
     * @param action La acción que se va a tomar al recibir información de los topics
     */
    void start(final List<String> topics, final OntimizeKafkaConsumerAction<K, V> action);

    /**
     * Para la ejecución en segundo plano de la lectura de información de los topics que esta realizando el consumidor.
     */
    void stop();
}
