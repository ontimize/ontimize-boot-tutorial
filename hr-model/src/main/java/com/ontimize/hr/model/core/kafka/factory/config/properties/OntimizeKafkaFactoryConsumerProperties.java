package com.ontimize.hr.model.core.kafka.factory.config.properties;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConditionalOnProperty(prefix = "spring.kafka.factory", name = "enable", havingValue = "true")
@ConfigurationProperties(prefix = "spring.kafka.factory.topics.consumers")
public class OntimizeKafkaFactoryConsumerProperties {

    /** name */
    private String name;

    /** schema-registry-url */
    private String schemaRegistryUrl;

    /** bootstrap-servers */
    private String bootstrapServers;

    /** group-id */
    private String groupId;

    /** key-deserializer */
    private String keyDeserializer;

    /** value-deserializer */
    private String valueDeserializer;

    /** key-type */
    private String keyType;

    /** value-type */
    private String valueType;

    /** key-subject-name-strategy */
    private String keySubjectNameStrategy;

    /** value-subject-name-strategy */
    private String valueSubjectNameStrategy;

    /** auto-register-schemas */
    private Boolean autoRegisterSchemas;

    /** topics */
    private List<String> topics;

    public OntimizeKafkaFactoryConsumerProperties() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
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

    public String getKeyType() {
        return this.keyType;
    }

    public void setKeyType(final String keyType) {
        this.keyType = keyType;
    }

    public String getValueType() {
        return this.valueType;
    }

    public void setValueType(final String valueType) {
        this.valueType = valueType;
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

    public List<String> getTopics() {
        return this.topics;
    }

    public void setTopics(final List<String> topics) {
        this.topics = topics;
    }

    @Override
    public String toString() {
        return new StringBuilder("KafkaConsumerProperties{")
                .append("name='").append(this.getName()).append('\'')
                .append(", schemaRegistryUrl='").append(this.getSchemaRegistryUrl()).append('\'')
                .append(", bootstrapServers='").append(this.getBootstrapServers()).append('\'')
                .append(", groupId='").append(this.getGroupId()).append('\'')
                .append(", keyDeserializer='").append(this.getKeyDeserializer()).append('\'')
                .append(", valueDeserializer='").append(this.getValueDeserializer()).append('\'')
                .append(", keyType='").append(this.getKeyType()).append('\'')
                .append(", valueType='").append(this.getValueType()).append('\'')
                .append(", keySubjectNameStrategy='").append(this.getKeySubjectNameStrategy()).append('\'')
                .append(", valueSubjectNameStrategy='").append(this.getValueSubjectNameStrategy()).append('\'')
                .append(", autoRegisterSchemas='").append(this.getAutoRegisterSchemas()).append('\'')
                .append(", topics='").append(this.getTopics()).append('\'')
                .append('}')
                .toString();
    }
}
