package com.ontimize.hr.model.core.kafka.factory.config.strategy;

import io.confluent.kafka.serializers.subject.RecordNameStrategy;
import io.confluent.kafka.serializers.subject.TopicNameStrategy;
import io.confluent.kafka.serializers.subject.TopicRecordNameStrategy;

public enum OntimizeKafkaSubjectNameStrategy {
    /** Property: "topic-name-strategy" */
    TOPIC_NAME_STRATEGY(TopicNameStrategy.class.getName(), "topic-name-strategy"),

    /** Property: "record-name-strategy" */
    RECORD_NAME_STRATEGY(RecordNameStrategy.class.getName(), "record-name-strategy"),

    /** Property: "topic-record-name-strategy" */
    TOPIC_RECORD_NAME_STRATEGY(TopicRecordNameStrategy.class.getName(), "topic-record-name-strategy");

    private String value;
    private String name;

    OntimizeKafkaSubjectNameStrategy(final String value, final String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return new StringBuilder("KafkaSubjectNameStrategy{")
                .append("value=").append(this.getValue()).append("\"")
                .append(", name=").append(this.getName()).append("\"")
                .append("}").toString();
    }
}
