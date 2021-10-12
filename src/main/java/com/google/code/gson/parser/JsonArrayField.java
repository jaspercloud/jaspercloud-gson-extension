package com.google.code.gson.parser;

public class JsonArrayField extends JsonField {

    private int index;

    public int getIndex() {
        return index;
    }

    public JsonArrayField(String name, int index) {
        super(name);
        this.index = index;
    }
}
