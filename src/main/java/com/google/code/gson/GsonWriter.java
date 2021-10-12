package com.google.code.gson;


import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.StringWriter;

public class GsonWriter extends JsonWriter {

    private StringWriter writer;

    public GsonWriter() {
        this(new StringWriter());
    }

    private GsonWriter(StringWriter out) {
        super(out);
        this.writer = out;
    }

    public String build() {
        return writer.toString();
    }

    public void add(String key, String val) throws IOException {
        name(key).value(val);
    }

    public void add(String key, Boolean val) throws IOException {
        name(key).value(val);
    }

    public void add(String key, Number val) throws IOException {
        name(key).value(val);
    }

    public void createObject(JsonConsumer<GsonWriter> consumer) throws IOException {
        beginObject();
        consumer.accept(this);
        endObject();
    }

    public void addObject(String key, JsonConsumer<GsonWriter> consumer) throws IOException {
        name(key);
        beginObject();
        consumer.accept(this);
        endObject();
    }

    public void addArray(String key, JsonConsumer<GsonWriter> consumer) throws IOException {
        name(key);
        beginArray();
        consumer.accept(this);
        endArray();
    }
}