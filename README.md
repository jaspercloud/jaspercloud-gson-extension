# jaspercloud-gson-extension
gson-extension

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
System.out.println();
```