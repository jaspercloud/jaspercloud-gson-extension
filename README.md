# jaspercloud-gson-extension
gson-extension扩展 复杂json的序列化和解析

``` xml
<dependency>
    <groupId>io.github.jaspercloud</groupId>
    <artifactId>gson-extension</artifactId>
    <version>1.2.0</version>
</dependency>
```

``` json
{
    "id":1,
    "data":{
        "uname":"test",
        "upass":"test",
        "ext":{
            "num":1
        },
        "list":[1,2,3,false]
    }
}
```
序列化json
``` java
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
        });
    });
});
```
解析json
``` java
GsonParser jsonParser = new GsonParser(writer.build());
Optional<JsonElement> optional = jsonParser.parse("data.list[4].test[3]");
```
#### 1.2.0版本 支持root为array
``` json
[1, 2, 3, {
	"name": "test",
	"age": "18"
}]
```
``` java
GsonWriter writer = new GsonWriter();
writer.createArray(obj -> {
    obj.value(1).value(2).value(3);
    obj.createObject(e -> {
        e.add("name", "test");
        e.add("age", "18");
    });
});
GsonParser jsonParser = new GsonParser(writer.build());
String name = jsonParser.parse("[3].name").get().getAsString();
```
