package com.google.code.gson;

import java.io.IOException;

@FunctionalInterface
public interface JsonConsumer<T> {

    void accept(T t) throws IOException;
}