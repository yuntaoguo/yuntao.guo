package net.yc384.web.database;

import io.vertx.core.json.JsonObject;

public class JDBCCONF {
    public static io.vertx.core.json.JsonObject  confg(){
        return new JsonObject() .put("url", "jdbc:mysql://localhost:3306/yc384_web")
                .put("driver_class", "com.mysql.cj.jdbc.Driver")
                .put("max_pool_size",  30);
    }
}
