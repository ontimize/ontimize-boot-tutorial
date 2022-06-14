package com.ontimize.hr.model.core.kafka.factory.config.properties;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConditionalOnProperty(prefix = "spring.kafka.factory", name = "enable", havingValue = "true")
@ConfigurationProperties(prefix = "spring.kafka.factory.topics.producers")
public class OntimizeKafkaFactoryProducerProperties {

    /** name */
    private String name;

    /** schema-registry-url */
    private String schemaRegistryUrl;

    /** bootstrap-servers */
    private String bootstrapServers;

    /** key-serializer */
    private String keySerializer;

    /** value-serializer */
    private String valueSerializer;

    /** key-subject-name-strategy */
    private String keySubjectNameStrategy;

    /** value-subject-name-strategy */
    private String valueSubjectNameStrategy;

    /** auto-register-schemas */
    private Boolean autoRegisterSchemas;

    /** topics */
    private List<String> topics;

    public OntimizeKafkaFactoryProducerProperties() {
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
        return new StringBuilder("KafkaProducerProperties{")
                .append("name='").append(this.getName()).append('\'')
                .append(", schemaRegistryUrl='").append(this.getSchemaRegistryUrl()).append('\'')
                .append(", bootstrapServers='").append(this.getBootstrapServers()).append('\'')
                .append(", keySerializer='").append(this.getKeySerializer()).append('\'')
                .append(", valueSerializer='").append(this.getValueSerializer()).append('\'')
                .append(", keySubjectNameStrategy='").append(this.getKeySubjectNameStrategy()).append('\'')
                .append(", valueSubjectNameStrategy='").append(this.getValueSubjectNameStrategy()).append('\'')
                .append(", autoRegisterSchemas='").append(this.getAutoRegisterSchemas()).append('\'')
                .append(", topics='").append(this.getTopics()).append('\'')
                .append('}')
                .toString();
    }
}
