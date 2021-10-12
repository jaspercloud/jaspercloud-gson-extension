package com.google.code.gson.parser;

import java.util.ArrayList;
import java.util.List;

public class ExpressionParser {

    private String expression;

    public ExpressionParser(String expression) {
        this.expression = expression;
    }

    public List<JsonField> parse() {
        int i = 0;
        List<JsonField> jsonFieldList = new ArrayList<>();
        List<String> list = parseToken();
        while (i < list.size()) {
            String field = list.get(i);
            if (i + 3 < list.size()) {
                String l = list.get(i + 1);
                String indexStr = list.get(i + 2);
                String r = list.get(i + 3);
                if ("[".equals(l) && "]".equals(r)) {
                    int index = Integer.parseInt(indexStr);
                    jsonFieldList.add(new JsonArrayField(field, index));
                    i += 3;
                } else {
                    jsonFieldList.add(new JsonField(field));
                }
            } else {
                jsonFieldList.add(new JsonField(field));
            }
            i++;
        }
        return jsonFieldList;
    }

    private List<String> parseToken() {
        List<String> tokenList = new ArrayList<>();
        int i = 0;
        char[] chars = expression.toCharArray();
        StringBuilder builder = new StringBuilder();
        while (i < chars.length) {
            char c = chars[i];
            switch (c) {
                case '.':
                    if (builder.length() > 0) {
                        tokenList.add(builder.toString());
                        builder = new StringBuilder();
                    }
                    break;
                case '[':
                    if (builder.length() > 0) {
                        tokenList.add(builder.toString());
                        builder = new StringBuilder();
                    }
                    tokenList.add(String.valueOf(c));
                    break;
                case ']':
                    if (builder.length() > 0) {
                        tokenList.add(builder.toString());
                        builder = new StringBuilder();
                    }
                    tokenList.add(String.valueOf(c));
                    break;
                default:
                    builder.append(c);
                    break;
            }
            i++;
        }
        if (builder.length() > 0) {
            tokenList.add(builder.toString());
        }
        return tokenList;
    }

}
