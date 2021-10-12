package com.google.code.gson;

import com.google.code.gson.parser.ExpressionParser;
import com.google.gson.JsonElement;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

public class GsonTest {

    @Test
    public void parse() {
        ExpressionParser parser = new ExpressionParser("data.list[4].test.items[1].a");
        parser.parse();
    }

    @Test
    public void test() throws Exception {
        GsonWriter writer = new GsonWriter();
        writer.createObject(obj -> {
            obj.add("id", 1);
            obj.addObject("data", data -> {
                data.add("uname", "test");
                data.add("upass", "test");
                data.addObject("ext", ext -> {
                    ext.add("num", 1);
                });
                data.addArray("list", list -> {
                    list.value(1).value("2").value(3).value(false);
                    list.createObject(o -> {
                        o.addArray("test", test -> {
                            test.value(1).value(2).value("end");
                        });
                    });
                });
            });
        });

        GsonParser jsonParser = new GsonParser(writer.build());
        Optional<JsonElement> optional = jsonParser.parse("data.list[4].test[3]");
        System.out.println();
    }
}
