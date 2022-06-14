package com.ontimize.hr.model.core.kafka.factory.config.properties;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@ConditionalOnProperty(prefix = "spring.kafka.factory", name = "enable", havingValue = "true")
@ConfigurationProperties(prefix = "spring.kafka.factory")
public class OntimizeKafkaFactoryProperties {

    /** spring.kafka.factory.enable */
    private Boolean enable;

    /** spring.kafka.factory.schema-registry-url */
    private String schemaRegistryUrl;

    /** spring.kafka.factory.bootstrap-servers */
    private String bootstrapServers;

    /** spring.kafka.factory.group-id */
    private String groupId;

    /** spring.kafka.factory.key-serializer */
    private String keySerializer;

    /** spring.kafka.factory.value-serializer */
    private String valueSerializer;

    /** spring.kafka.factory.key-deserializer */
    private String keyDeserializer;

    /** spring.kafka.factory.value-deserializer */
    private String valueDeserializer;

    /** spring.kafka.factory.key-subject-name-strategy */
    private String keySubjectNameStrategy;

    /** spring.kafka.factory.value-subject-name-strategy */
    private String valueSubjectNameStrategy;

    /** spring.kafka.factory.auto-register-schemas */
    private Boolean autoRegisterSchemas;

    /** spring.kafka.factory.producers */
    private List<OntimizeKafkaFactoryProducerProperties> producers = Collections.EMPTY_LIST;

    /** spring.kafka.factory.consumers */
    private List<OntimizeKafkaFactoryConsumerProperties> consumers = Collections.EMPTY_LIST;

    public OntimizeKafkaFactoryProperties() {
    }

    public Boolean isEnable() {
        return this.enable;
    }

    public void setEnable(final Boolean enable) {
        this.enable = enable;
    }

    public String getSchemaRegistryUrl() {
        return this.schemaRegistryUrl;
    }

    public void setSchemaRegistryUrl(final String schemaRegistryUrl) {
        this.schemaRegistryUrl = schemaRegistryUrl;
    }

    public String getBootstrapServers() {
        return this.bootstrapServers;
    }

    public void setBootstrapServers(final String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(final String groupId) {
        this.groupId = groupId;
    }

    public String getKeySerializer() {
        return this.keySerializer;
    }

    public void setKeySerializer(final String keySerializer) {
        this.keySerializer = keySerializer;
    }

    public String getValueSerializer() {
        return this.valueSerializer;
    }

    public void setValueSerializer(final String valueSerializer) {
        this.valueSerializer = valueSerializer;
    }

    public String getKeyDeserializer() {
        return this.keyDeserializer;
    }

    public void setKeyDeserializer(final String keyDeserializer) {
        this.keyDeserializer = keyDeserializer;
    }

    public String getValueDeserializer() {
        return this.valueDeserializer;
    }

    public void setValueDeserializer(final String valueDeserializer) {
        this.valueDeserializer = valueDeserializer;
    }

    public String getKeySubjectNameStrategy() {
        return this.keySubjectNameStrategy;
    }

    public void setKeySubjectNameStrategy(final String keySubjectNameStrategy) {
        this.keySubjectNameStrategy = keySubjectNameStrategy;
    }

    public String getValueSubjectNameStrategy() {
        return this.valueSubjectNameStrategy;
    }

    public void setValueSubjectNameStrategy(final String valueSubjectNameStrategy) {
        this.valueSubjectNameStrategy = valueSubjectNameStrategy;
    }

    public Boolean getAutoRegisterSchemas() {
        return this.autoRegisterSchemas;
    }

    public void setAutoRegisterSchemas(final Boolean autoRegisterSchemas) {
        this.autoRegisterSchemas = autoRegisterSchemas;
    }

    public List<OntimizeKafkaFactoryProducerProperties> getProducers() {
        return this.producers;
    }

    public void setProducers(final List<OntimizeKafkaFactoryProducerProperties> producers) {
        this.producers = producers;
    }

    public List<OntimizeKafkaFactoryConsumerProperties> getConsumers() {
        return this.consumers;
    }

    public void setConsumers(final List<OntimizeKafkaFactoryConsumerProperties> consumers) {
        this.consumers = consumers;
    }

    @Override
    public String toString() {
        return new StringBuilder("KafkaFactoryProperties{")
                .append("enable=").append(this.isEnable())
                .append(", schemaRegistryUrl='").append(this.getSchemaRegistryUrl()).append('\'')
                .append(", bootstrapServers='").append(this.getBootstrapServers()).append('\'')
                .append(", groupId='").append(this.getGroupId()).append('\'')
                .append(", keySerializer='").append(this.getKeySerializer()).append('\'')
                .append(", valueSerializer='").append(this.getValueSerializer()).append('\'')
                .append(", keyDeserializer='").append(this.getKeyDeserializer()).append('\'')
                .append(", valueDeserializer='").append(this.getValueSerializer()).append('\'')
                .append(", keySubjectNameStrategy='").append(this.getKeySubjectNameStrategy()).append('\'')
                .append(", valueSubjectNameStrategy='").append(this.getValueSubjectNameStrategy()).append('\'')
                .append(", autoRegisterSchemas='").append(this.getAutoRegisterSchemas()).append('\'')
                .append(", producers=").append(this.getProducers())
                .append(", consumers=").append(this.getConsumers())
                .append('}')
                .toString();
    }
}
