package com.ontimize.hr.model.core.kafka.factory.producer;

import java.util.List;
import java.util.Properties;

/**
 * Productor de KafKa que nos permite enviar información unos determinados topics.
 *
 * @param <K> El tipo de la clave de la información
 * @param <V> El tipo de la información
 */
public interface OntimizeKafkaProducer<K, V> {

    /**
     * Obtiene el nombre del productor.
     *
     * @return El nombre del prodcutor
     */
    String getName();

    /**
     * Obtiene las propiedades de configuración del productor.
     *
     * @return Las propiedades de configuración del productor
     */
    Properties getProperties();

    /**
     * Establece las propiedades de configuración del productor.
     *
     * @param properties Las nuevas propiedades de configuración del productor
     */
    void setProperties(final Properties properties);

    /**
     * Obtiene una lista de los topics a los que el productor va a enviar la información si no se especifica otro u otros topics.
     *
     * @return La lista de los topics por defecto que el productor va a enviar la información
     */
    List<String> getDefaultTopics();

    /**
     * Establece una lista de los topics a los que el productor va a enviar la información si no se especifica otro u otros topics.
     *
     * @param defaultTopics La nueva lista de los topics por defecto que el productor va a enviar la información
     */
    void setDefaultTopics(final List<String> defaultTopics);

    /**
     * Añade un nuevo topic a la lista de los topics a los que el productor va a enviar la información si no se especifica otro u otros topics.
     *
     * @param defaultTopic El nuevo topic que se va a añadir a la lista de los topics por defecto que el productor va a enviar la información
     */
    void addDefaultTopic(final String defaultTopic);

    /**
     * Envía información sin clave a los topics que tiene establecidos en su lista de topics por defecto.
     *
     * @param value  La información que se va a enviar.
     * @param action La acción que se va a ejecutar cuando se envíe correctamente la información o cuando haya un error en el envío
     */
    void sendMessage(final V value, final OntimizeKafkaProducerAction<K, V> action);

    /**
     * Envía información con clave a los topics que tiene establecidos en su lista de topics por defecto.
     *
     * @param key    La clave de la información que se va a enviar
     * @param value  La información que se va a enviar
     * @param action La acción que se va a ejecutar cuando se envíe correctamente la información o cuando haya un error en el envío
     */
    void sendMessage(final K key, final V value, final OntimizeKafkaProducerAction<K, V> action);

    /**
     * Envía información con clave a un determinado topic.
     *
     * @param topic  El topic al que se va a enviar la información con clave
     * @param key    La clave de la información que se va a enviar
     * @param value  La información que se va a enviar
     * @param action La acción que se va a ejecutar cuando se envíe correctamente la información o cuando haya un error en el envío
     */
    void sendMessage(final String topic, final K key, final V value, final OntimizeKafkaProducerAction<K, V> action);

    /**
     * Envía información con clave a unos determinados topics.
     *
     * @param topics Los topics a los que se va a enviar la información con clave
     * @param key    La clave de la información que se va a enviar
     * @param value  La información que se va a enviar
     * @param action La acción que se va a ejecutar cuando se envíe correctamente la información o cuando haya un error en el envío
     */
    void sendMessage(final List<String> topics, final K key, final V value, final OntimizeKafkaProducerAction<K, V> action);
}
