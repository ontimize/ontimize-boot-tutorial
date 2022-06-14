package com.ontimize.hr.model.core.kafka.factory.config.serializer;


import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializer;
import io.confluent.kafka.serializers.protobuf.KafkaProtobufSerializer;
import org.apache.kafka.common.serialization.*;
import org.springframework.kafka.support.serializer.JsonSerializer;

public enum OntimizeKafkaSerializer {
    /** Property: "string-serializer" */
    STRING_SERIALIZER(StringSerializer.class, "string-serializer"),

    /** Property: "double-serializer" */
    DOUBLE_SERIALIZER(DoubleSerializer.class, "double-serializer"),

    /** Property: "float-serializer" */
    FLOAT_SERIALIZER(FloatSerializer.class, "float-serializer"),

    /** Property: "integer-serializer" */
    INTEGER_SERIALIZER(IntegerSerializer.class, "integer-serializer"),

    /** Property: "long-serializer" */
    LONG_SERIALIZER(LongSerializer.class, "long-serializer"),

    /** Property: "short-serializer" */
    SHORT_SERIALIZER(ShortSerializer.class, "short-serializer"),

    /** Property: "json-serializer" */
    JSON_SERIALIZER(JsonSerializer.class, "json-serializer"),

    /** Property: "bytes-serializer" */
    BYTES_SERIALIZER(BytesSerializer.class, "bytes-serializer"),

    /** Property: "byte-array-serializer" */
    BYTE_ARRAY_SERIALIZER(ByteArraySerializer.class, "byte-array-serializer"),

    /** Property: "kafka-avro-serializer" */
    KAFKA_AVRO_SERIALIZER(KafkaAvroSerializer.class, "kafka-avro-serializer"),

    /** Property: "kafka-protobuf-serializer" */
    KAFKA_PROTOBUF_SERIALIZER(KafkaProtobufSerializer.class, "kafka-protobuf-serializer"),

    /** Property: "kafka-json-schema-serializer" */
    KAFKA_JSON_SCHEMA_SERIALIZER(KafkaJsonSchemaSerializer.class, "kafka-json-schema-serializer"),

    /** Property: "uuid-serializer" */
    UUID_SERIALIZER(UUIDSerializer.class, "uuid-serializer");

    private Class value;
    private String name;

    OntimizeKafkaSerializer(final Class value, final String name) {
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
        return new StringBuilder("KafkaSerializer{")
                .append("value=").append(this.getValue()).append("\"")
                .append(", name=").append(this.getName()).append("\"")
                .append("}").toString();
    }
}
