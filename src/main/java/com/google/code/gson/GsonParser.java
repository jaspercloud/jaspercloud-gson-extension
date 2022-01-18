package com.google.code.gson;

import com.google.code.gson.parser.ExpressionParser;
import com.google.code.gson.parser.JsonArrayField;
import com.google.code.gson.parser.JsonField;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class GsonParser {

    private JsonElement element;

    public GsonParser(String json) {
        element = new JsonParser().parse(json);
    }

    public GsonParser(Reader reader) {
        element = new JsonParser().parse(reader);
    }

    public GsonParser(JsonReader jsonReader) {
        element = new JsonParser().parse(jsonReader);
    }

    public GsonParser(JsonElement element) {
        this.element = element;
    }

    public Optional<JsonElement> parse(String keys) {
        ExpressionParser parser = new ExpressionParser(keys);
        List<JsonField> parse = parser.parse();
        Iterator<JsonField> iterator = parse.iterator();
        Optional<JsonElement> result = doParse(element, iterator);
        return result;
    }

    private Optional<JsonElement> doParse(JsonElement element, Iterator<JsonField> iterator) {
        if (!iterator.hasNext()) {
            return Optional.ofNullable(element);
        }
        JsonField jsonField = iterator.next();
        if (jsonField instanceof JsonArrayField) {
            JsonArrayField jsonArrayField = (JsonArrayField) jsonField;
            String name = jsonArrayField.getName();
            JsonArray jsonArray;
            if (null != name) {
                JsonObject jsonObject = element.getAsJsonObject();
                if (!jsonObject.has(name)) {
                    return Optional.empty();
                }
                jsonArray = jsonObject.get(jsonArrayField.getName()).getAsJsonArray();
            } else {
                jsonArray = (JsonArray) element;
            }
            if (jsonArrayField.getIndex() >= jsonArray.size()) {
                return Optional.empty();
            }
            Optional<JsonElement> result = doParse(jsonArray.get(jsonArrayField.getIndex()), iterator);
            return result;
        } else {
            JsonObject jsonObject = element.getAsJsonObject();
            if (!jsonObject.has(jsonField.getName())) {
                return Optional.empty();
            }
            Optional<JsonElement> result = doParse(jsonObject.get(jsonField.getName()), iterator);
            return result;
        }
    }
}