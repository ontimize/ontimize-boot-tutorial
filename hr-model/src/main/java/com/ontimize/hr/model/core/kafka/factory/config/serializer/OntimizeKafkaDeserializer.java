package com.ontimize.hr.model.core.kafka.factory.config.serializer;

import com.ontimize.hr.model.core.kafka.factory.config.serializer.custom.ObjectDeserializer;
import com.ontimize.hr.model.core.kafka.factory.config.serializer.custom.ObjectSerializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.json.KafkaJsonSchemaDeserializer;
import io.confluent.kafka.serializers.protobuf.KafkaProtobufDeserializer;
import org.apache.kafka.common.serialization.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;

public enum OntimizeKafkaDeserializer {
    /** Property: "object-deserializer" */
    OBJECT_DESERIALIZER(ObjectDeserializer.class, "object-deserializer"),

    /** Property: "string-deserializer" */
    STRING_DESERIALIZER(StringDeserializer.class, "string-deserializer"),

    /** Property: "double-deserializer" */
    DOUBLE_DESERIALIZER(DoubleDeserializer.class, "double-deserializer"),

    /** Property: "float-deserializer" */
    FLOAT_DESERIALIZER(FloatDeserializer.class, "float-deserializer"),

    /** Property: "integer-deserializer" */
    INTEGER_DESERIALIZER(IntegerDeserializer.class, "integer-deserializer"),

    /** Property: "long-deserializer" */
    LONG_DESERIALIZER(LongDeserializer.class, "long-deserializer"),

    /** Property: "short-deserializer" */
    SHORT_DESERIALIZER(ShortDeserializer.class, "short-deserializer"),

    /** Property: "json-deserializer" */
    JSON_DESERIALIZER(JsonDeserializer.class, "json-deserializer"),

    /** Property: "bytes-deserializer" */
    BYTES_DESERIALIZER(BytesDeserializer.class, "bytes-deserializer"),

    /** Property: "byte-array-deserializer" */
    BYTE_ARRAY_DESERIALIZER(ByteArrayDeserializer.class, "byte-array-deserializer"),

    /** Property: "kafka-avro-deserializer" */
    KAFKA_AVRO_DESERIALIZER(KafkaAvroDeserializer.class, "kafka-avro-deserializer"),

    /** Property: "kafka-protobuf-deserializer" */
    KAFKA_PROTOBUF_DESERIALIZER(KafkaProtobufDeserializer.class, "kafka-protobuf-deserializer"),

    /** Property: "kafka-json-schema-deserializer" */
    KAFKA_JSON_SCHEMA_DESERIALIZER(KafkaJsonSchemaDeserializer.class, "kafka-json-schema-deserializer"),

    /** Property: "uuid-deserializer" */
    UUID_DESERIALIZER(UUIDDeserializer.class, "uuid-deserializer");

    private Class value;
    private String name;

    OntimizeKafkaDeserializer(final Class value, final String name) {
        this.value = value;
        this.name = name;
    }

    public Class getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return new StringBuilder("KafkaDeserializer{")
                .append("value=").append(this.getValue()).append("\"")
                .append(", name=").append(this.getName()).append("\"")
                .append("}").toString();
    }
}
