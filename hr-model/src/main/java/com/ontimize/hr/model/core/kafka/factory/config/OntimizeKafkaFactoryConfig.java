package com.ontimize.hr.model.core.kafka.factory.config;

import com.ontimize.hr.model.core.kafka.factory.OntimizeKafkaFactory;
import com.ontimize.hr.model.core.kafka.factory.OntimizeKafkaFactoryImpl;
import com.ontimize.hr.model.core.kafka.factory.config.properties.OntimizeKafkaFactoryConsumerProperties;
import com.ontimize.hr.model.core.kafka.factory.config.properties.OntimizeKafkaFactoryProducerProperties;
import com.ontimize.hr.model.core.kafka.factory.config.properties.OntimizeKafkaFactoryProperties;
import com.ontimize.hr.model.core.kafka.factory.config.serializer.OntimizeKafkaDeserializer;
import com.ontimize.hr.model.core.kafka.factory.config.serializer.OntimizeKafkaSerializer;
import com.ontimize.hr.model.core.kafka.factory.config.strategy.OntimizeKafkaSubjectNameStrategy;
import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import io.confluent.kafka.serializers.json.KafkaJsonSchemaDeserializerConfig;
import io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializerConfig;
import io.confluent.kafka.serializers.protobuf.KafkaProtobufDeserializerConfig;
import io.confluent.kafka.serializers.protobuf.KafkaProtobufSerializerConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Properties;

@Configuration
@ConditionalOnProperty(prefix = "spring.kafka.factory", name = "enable", havingValue = "true")
public class OntimizeKafkaFactoryConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(OntimizeKafkaFactoryConfig.class);

    @Value(value = "${spring.kafka.bootstrap-servers:#{null}}")
    private String defaultBootstrapServers;

    @Value(value = "${spring.kafka.consumer.group-id:#{null}}")
    private String defaultGroupId;

    @Value(value = "${spring.cloud.schemaRegistryClient.endpoint:#{null}}")
    private String defaultSchemaRegistryUrl;

    private OntimizeKafkaSerializer defaultKeySerializer;
    private OntimizeKafkaSerializer defaultValueSerializer;
    private OntimizeKafkaDeserializer defaultKeyDeserializer;
    private OntimizeKafkaDeserializer defaultValueDeserializer;
    private OntimizeKafkaSubjectNameStrategy defaultKeySubjectNameStrategy;
    private OntimizeKafkaSubjectNameStrategy defaultValueSubjectNameStrategy;
    private Boolean defaultAutoRegisterSchemas;

    @Autowired
    private OntimizeKafkaFactoryProperties ontimizeKafkaFactoryProperties;

    private OntimizeKafkaFactory ontimizeKafkaFactory;

    @Bean
    public OntimizeKafkaFactory factory() {
        this.ontimizeKafkaFactory = new OntimizeKafkaFactoryImpl();
        this.configOntimizeKafkaFactory();
        return this.ontimizeKafkaFactory;
    }

    private void configOntimizeKafkaFactory() throws NoSuchElementException {
        if (this.ontimizeKafkaFactoryProperties != null) {
            //SET DEFAULT VALUES
            LOGGER.info("Configure KafkaFactory from application.yml");
            this.defaultBootstrapServers = this.getBootstrapServersOrDefault(this.ontimizeKafkaFactoryProperties.getBootstrapServers());
            this.defaultGroupId = this.getGroupIdOrDefault(this.ontimizeKafkaFactoryProperties.getGroupId());
            this.defaultKeySerializer = this.getKeySerializerOrDefault(this.ontimizeKafkaFactoryProperties.getKeySerializer());
            this.defaultValueSerializer = this.getValueSerializerOrDefault(this.ontimizeKafkaFactoryProperties.getValueSerializer());
            this.defaultKeyDeserializer = this.getKeyDeserializerOrDefault(this.ontimizeKafkaFactoryProperties.getKeyDeserializer());
            this.defaultValueDeserializer = this.getValueDeserializerOrDefault(this.ontimizeKafkaFactoryProperties.getValueDeserializer());
            this.defaultSchemaRegistryUrl = this.getSchemaRegistryEndpointOrDefault(this.ontimizeKafkaFactoryProperties.getSchemaRegistryUrl());
            this.defaultKeySubjectNameStrategy = this.getKeySubjectNameStrategyOrDefault(this.ontimizeKafkaFactoryProperties.getKeySubjectNameStrategy());
            this.defaultValueSubjectNameStrategy = this.getValueSubjectNameStrategyOrDefault(this.ontimizeKafkaFactoryProperties.getValueSubjectNameStrategy());

            //REGISTER PRODUCERS
            if (this.ontimizeKafkaFactoryProperties.getProducers() != null) {
                LOGGER.info("Configure OntimizeProducers from application.yml");
                this.ontimizeKafkaFactoryProperties.getProducers().stream().forEach(properties -> this.registerProducers(properties));
            }

            //REGISTER CONSUMERS
            if (this.ontimizeKafkaFactoryProperties.getConsumers() != null) {
                LOGGER.info("Configure OntimizeConsumers from application.yml");
                this.ontimizeKafkaFactoryProperties.getConsumers().stream().forEach(properties -> this.registerConsumers(properties));
            }
        }
    }

    private void registerProducers(final OntimizeKafkaFactoryProducerProperties producersProperties) throws NoSuchElementException {
        if (producersProperties == null || (producersProperties != null && StringUtils.isEmpty(producersProperties.getName()))) {
            throw new NoSuchElementException("Producers haven't been configured correctly in the YAML file.");
        }

        //GET VALUES FROM YAML
        final String NAME = producersProperties.getName();
        final String BOOTSTRAP_SERVERS = this.getBootstrapServersOrDefault(producersProperties.getBootstrapServers());
        final OntimizeKafkaSerializer KEY_SERIALIZER = this.getKeySerializerOrDefault(producersProperties.getKeySerializer());
        final OntimizeKafkaSerializer VALUE_SERIALIZER = this.getValueSerializerOrDefault(producersProperties.getValueSerializer());
        final String SCHEMA_REGISTRY_ENDPOINT = this.getSchemaRegistryEndpointOrDefault(producersProperties.getSchemaRegistryUrl());
        final OntimizeKafkaSubjectNameStrategy KEY_SUBJECT_NAME_STRATEGY = this.getKeySubjectNameStrategyOrDefault(producersProperties.getKeySubjectNameStrategy());
        final OntimizeKafkaSubjectNameStrategy VALUE_SUBJECT_NAME_STRATEGY = this.getValueSubjectNameStrategyOrDefault(producersProperties.getValueSubjectNameStrategy());
        final Boolean AUTO_REGISTER_SCHEMAS = this.getAutoRegisterSchemasOrDefault(producersProperties.getAutoRegisterSchemas());

        //COMMON CONFIG
        final Properties PROPERTIES = new Properties();
        this.setPropertyIfValueNotNull(PROPERTIES, ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        this.setSerializers(PROPERTIES, KEY_SERIALIZER, VALUE_SERIALIZER);
        this.setSubjectNameStrategies(PROPERTIES, KEY_SUBJECT_NAME_STRATEGY, VALUE_SUBJECT_NAME_STRATEGY);

        //KAFKA AVRO CONFIG
        if (KEY_SERIALIZER == OntimizeKafkaSerializer.KAFKA_AVRO_SERIALIZER ||
                VALUE_SERIALIZER == OntimizeKafkaSerializer.KAFKA_AVRO_SERIALIZER) {
            this.setPropertyIfValueNotNull(PROPERTIES, KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, SCHEMA_REGISTRY_ENDPOINT);
            this.setPropertyIfValueNotNull(PROPERTIES, KafkaAvroSerializerConfig.AUTO_REGISTER_SCHEMAS, AUTO_REGISTER_SCHEMAS);
        }

        //KAFKA PROTOBUFFER CONFIG
        if (KEY_SERIALIZER == OntimizeKafkaSerializer.KAFKA_PROTOBUF_SERIALIZER ||
                VALUE_SERIALIZER == OntimizeKafkaSerializer.KAFKA_PROTOBUF_SERIALIZER) {
            this.setPropertyIfValueNotNull(PROPERTIES, KafkaProtobufSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, SCHEMA_REGISTRY_ENDPOINT);
            this.setPropertyIfValueNotNull(PROPERTIES, KafkaProtobufSerializerConfig.AUTO_REGISTER_SCHEMAS, AUTO_REGISTER_SCHEMAS);
        }

        //KAFKA JSON CONFIG
        if (KEY_SERIALIZER == OntimizeKafkaSerializer.KAFKA_JSON_SCHEMA_SERIALIZER ||
                VALUE_SERIALIZER == OntimizeKafkaSerializer.KAFKA_JSON_SCHEMA_SERIALIZER) {
            this.setPropertyIfValueNotNull(PROPERTIES, KafkaJsonSchemaSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, SCHEMA_REGISTRY_ENDPOINT);
            this.setPropertyIfValueNotNull(PROPERTIES, KafkaJsonSchemaSerializerConfig.AUTO_REGISTER_SCHEMAS, AUTO_REGISTER_SCHEMAS);
        }

        //CREATE OntimizeKafkaProducer
        this.ontimizeKafkaFactory.createProducer(NAME, producersProperties.getTopics(), PROPERTIES);
    }

    private void registerConsumers(final OntimizeKafkaFactoryConsumerProperties consumersProperties) throws NoSuchElementException {
        if (consumersProperties == null || (consumersProperties != null && StringUtils.isEmpty(consumersProperties.getName()))) {
            throw new NoSuchElementException("Consumers haven't been configured correctly in the YAML file.");
        }

        //GET VALUES FROM YAML
        final String NAME = consumersProperties.getName();
        final String BOOTSTRAP_SERVERS = this.getBootstrapServersOrDefault(consumersProperties.getBootstrapServers());
        final String GROUP_ID = this.getGroupIdOrDefault(consumersProperties.getGroupId());
        final OntimizeKafkaDeserializer KEY_DESERIALIZER = this.getKeyDeserializerOrDefault(consumersProperties.getKeyDeserializer());
        final OntimizeKafkaDeserializer VALUE_DESERIALIZER = this.getValueDeserializerOrDefault(consumersProperties.getValueDeserializer());
        final String SCHEMA_REGISTRY_ENDPOINT = this.getSchemaRegistryEndpointOrDefault(consumersProperties.getSchemaRegistryUrl());
        final String KEY_TYPE = consumersProperties.getKeyType();
        final String VALUE_TYPE = consumersProperties.getValueType();
        final OntimizeKafkaSubjectNameStrategy KEY_SUBJECT_NAME_STRATEGY = this.getKeySubjectNameStrategyOrDefault(consumersProperties.getKeySubjectNameStrategy());
        final OntimizeKafkaSubjectNameStrategy VALUE_SUBJECT_NAME_STRATEGY = this.getValueSubjectNameStrategyOrDefault(consumersProperties.getValueSubjectNameStrategy());
        final Boolean AUTO_REGISTER_SCHEMAS = this.getAutoRegisterSchemasOrDefault(consumersProperties.getAutoRegisterSchemas());

        //COMMON CONFIG
        final Properties PROPERTIES = new Properties();
        this.setPropertyIfValueNotNull(PROPERTIES, ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        this.setPropertyIfValueNotNull(PROPERTIES, ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        this.setDeserializers(PROPERTIES, KEY_DESERIALIZER, VALUE_DESERIALIZER);
        this.setSubjectNameStrategies(PROPERTIES, KEY_SUBJECT_NAME_STRATEGY, VALUE_SUBJECT_NAME_STRATEGY);

        //JSON CONFIG
        if (!PROPERTIES.containsKey(JsonDeserializer.TRUSTED_PACKAGES) &&
                (KEY_DESERIALIZER == OntimizeKafkaDeserializer.JSON_DESERIALIZER ||
                        VALUE_DESERIALIZER == OntimizeKafkaDeserializer.JSON_DESERIALIZER)) {
            PROPERTIES.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        }

        //KAFKA AVRO CONFIG
        if (KEY_DESERIALIZER == OntimizeKafkaDeserializer.KAFKA_AVRO_DESERIALIZER ||
                VALUE_DESERIALIZER == OntimizeKafkaDeserializer.KAFKA_AVRO_DESERIALIZER) {
            PROPERTIES.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
            this.setPropertyIfValueNotNull(PROPERTIES, KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, SCHEMA_REGISTRY_ENDPOINT);
            this.setPropertyIfValueNotNull(PROPERTIES, KafkaAvroDeserializerConfig.AUTO_REGISTER_SCHEMAS, AUTO_REGISTER_SCHEMAS);
        }

        //KAFKA PROTOBUFFER CONFIG
        if (KEY_DESERIALIZER == OntimizeKafkaDeserializer.KAFKA_PROTOBUF_DESERIALIZER ||
                VALUE_DESERIALIZER == OntimizeKafkaDeserializer.KAFKA_PROTOBUF_DESERIALIZER) {
            this.setPropertyIfValueNotNull(PROPERTIES, KafkaProtobufDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, SCHEMA_REGISTRY_ENDPOINT);
            this.setPropertyIfValueNotNull(PROPERTIES, KafkaProtobufDeserializerConfig.AUTO_REGISTER_SCHEMAS, AUTO_REGISTER_SCHEMAS);
            this.setPropertyIfValueNotNull(PROPERTIES, KafkaProtobufDeserializerConfig.SPECIFIC_PROTOBUF_KEY_TYPE, KEY_TYPE);
            this.setPropertyIfValueNotNull(PROPERTIES, KafkaProtobufDeserializerConfig.SPECIFIC_PROTOBUF_VALUE_TYPE, VALUE_TYPE);
        }

        //KAFKA JSON SCHEMA CONFIG
        if (KEY_DESERIALIZER == OntimizeKafkaDeserializer.KAFKA_JSON_SCHEMA_DESERIALIZER ||
                VALUE_DESERIALIZER == OntimizeKafkaDeserializer.KAFKA_JSON_SCHEMA_DESERIALIZER) {
            this.setPropertyIfValueNotNull(PROPERTIES, KafkaJsonSchemaDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, SCHEMA_REGISTRY_ENDPOINT);
            this.setPropertyIfValueNotNull(PROPERTIES, KafkaJsonSchemaDeserializerConfig.AUTO_REGISTER_SCHEMAS, AUTO_REGISTER_SCHEMAS);
            this.setPropertyIfValueNotNull(PROPERTIES, KafkaJsonSchemaDeserializerConfig.JSON_KEY_TYPE, KEY_TYPE);
            this.setPropertyIfValueNotNull(PROPERTIES, KafkaJsonSchemaDeserializerConfig.JSON_VALUE_TYPE, VALUE_TYPE);
        }

        //CREATE OntimizeKafkaConsumer
        this.ontimizeKafkaFactory.createConsumer(NAME, consumersProperties.getTopics(), PROPERTIES);
    }

    private void setPropertyIfValueNotNull(final Properties properties, final String key, final Object value) {
        if (value != null) {
            properties.put(key, value);
        }
    }

    private void setSerializers(
            final Properties properties,
            final OntimizeKafkaSerializer keySerializer,
            final OntimizeKafkaSerializer valueSerializer) {

        if (keySerializer != null) {
            properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer.getValue());
        }

        if (valueSerializer != null) {
            properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer.getValue());
        }
    }

    private void setDeserializers(
            final Properties properties,
            final OntimizeKafkaDeserializer keyDeserializer,
            final OntimizeKafkaDeserializer valueDeserializer) {

        if (keyDeserializer != null) {
            properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
            properties.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, keyDeserializer.getValue());
        }

        if (valueDeserializer != null) {
            properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
            properties.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, valueDeserializer.getValue());
        }
    }

    private void setSubjectNameStrategies(
            final Properties properties,
            final OntimizeKafkaSubjectNameStrategy keySubjectNameStrategy,
            final OntimizeKafkaSubjectNameStrategy valueSubjectNameStrategy) {

        if (keySubjectNameStrategy != null) {
            properties.put(AbstractKafkaSchemaSerDeConfig.KEY_SUBJECT_NAME_STRATEGY, keySubjectNameStrategy.getValue());
        }

        if (valueSubjectNameStrategy != null) {
            properties.put(AbstractKafkaSchemaSerDeConfig.VALUE_SUBJECT_NAME_STRATEGY, valueSubjectNameStrategy.getValue());
        }
    }

    private OntimizeKafkaSerializer getKeySerializerOrDefault(final String keySerializer) {
        return this.getSerializerOrDefault(keySerializer, this.defaultKeySerializer);
    }

    private OntimizeKafkaSerializer getValueSerializerOrDefault(final String valueSerializer) {
        return this.getSerializerOrDefault(valueSerializer, this.defaultValueSerializer);
    }

    private OntimizeKafkaSerializer getSerializerOrDefault(final String serializer, final OntimizeKafkaSerializer defaultSerializer) {
        OntimizeKafkaSerializer result = defaultSerializer;
        if (!StringUtils.isEmpty(serializer)) {
            result = this.mapStringToKafkaSerializer(serializer);
        }
        return result;
    }

    private OntimizeKafkaDeserializer getKeyDeserializerOrDefault(final String keyDeserializer) {
        return this.getDeserializerOrDefault(keyDeserializer, this.defaultKeyDeserializer);
    }

    private OntimizeKafkaDeserializer getValueDeserializerOrDefault(final String valueDeserializer) {
        return this.getDeserializerOrDefault(valueDeserializer, this.defaultValueDeserializer);
    }

    private OntimizeKafkaDeserializer getDeserializerOrDefault(final String deserializer, final OntimizeKafkaDeserializer defaultDeserializer) {
        OntimizeKafkaDeserializer result = defaultDeserializer;
        if (!StringUtils.isEmpty(deserializer)) {
            result = this.mapStringToKafkaDeserializer(deserializer);
        }
        return result;
    }

    private String getBootstrapServersOrDefault(final String bootstrapServers) {
        String result = this.defaultBootstrapServers;

        if (!StringUtils.isEmpty(bootstrapServers)) {
            result = bootstrapServers;
        }

        return result;
    }

    private String getGroupIdOrDefault(final String groupId) {
        String result = this.defaultGroupId;

        if (!StringUtils.isEmpty(groupId)) {
            result = groupId;
        }

        return result;
    }

    private String getSchemaRegistryEndpointOrDefault(final String schemaRegistryEndpoint) {
        String result = this.defaultSchemaRegistryUrl;

        if (!StringUtils.isEmpty(schemaRegistryEndpoint)) {
            result = schemaRegistryEndpoint;
        }

        return result;
    }

    private OntimizeKafkaSubjectNameStrategy getKeySubjectNameStrategyOrDefault(final String keySubjectNameStrategy) {
        return this.getSubjectNameStrategyOrDefault(keySubjectNameStrategy, this.defaultKeySubjectNameStrategy);

    }

    private OntimizeKafkaSubjectNameStrategy getValueSubjectNameStrategyOrDefault(final String valueSubjectNameStrategy) {
        return this.getSubjectNameStrategyOrDefault(valueSubjectNameStrategy, this.defaultValueSubjectNameStrategy);
    }

    private OntimizeKafkaSubjectNameStrategy getSubjectNameStrategyOrDefault(final String subjectNameStrategy, final OntimizeKafkaSubjectNameStrategy defaultSubjectNameStrategy) {
        OntimizeKafkaSubjectNameStrategy result = defaultSubjectNameStrategy;

        if (!StringUtils.isEmpty(subjectNameStrategy)) {
            result = this.mapStringToKafkaSubjectNameStrategy(subjectNameStrategy);
        }

        return result;
    }

    private Boolean getAutoRegisterSchemasOrDefault(final Boolean autoRegisterSchemas) {
        Boolean result = this.defaultAutoRegisterSchemas;

        if (autoRegisterSchemas != null) {
            result = autoRegisterSchemas;
        }

        return result;
    }

    private OntimizeKafkaSerializer mapStringToKafkaSerializer(final String serializerStr) {
        return Arrays.stream(OntimizeKafkaSerializer.values())
                .filter(value -> value.getName().equals(serializerStr))
                .findFirst()
                .orElseThrow();
    }

    private OntimizeKafkaDeserializer mapStringToKafkaDeserializer(final String deserializerStr) {
        return Arrays.stream(OntimizeKafkaDeserializer.values())
                .filter(value -> value.getName().equals(deserializerStr))
                .findFirst()
                .orElseThrow();
    }

    private OntimizeKafkaSubjectNameStrategy mapStringToKafkaSubjectNameStrategy(final String subjectNameStrategy) {
        return Arrays.stream(OntimizeKafkaSubjectNameStrategy.values())
                .filter(value -> value.getName().equals(subjectNameStrategy))
                .findFirst()
                .orElseThrow();
    }
}
