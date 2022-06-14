package com.ontimize.hr.model.core.kafka.factory.config.serializer.custom;

import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;

public class ObjectSerializer implements Serializer<Object> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String topic, Object data) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();

        try {
            ObjectOutputStream o = new ObjectOutputStream(b);
            o.writeObject(data);
            o.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return b.toByteArray();
    }

    @Override
    public byte[] serialize(String topic, Headers headers, Object data) {
        return Serializer.super.serialize(topic, headers, data);
    }

    @Override
    public void close() {
        Serializer.super.close();
    }
}
