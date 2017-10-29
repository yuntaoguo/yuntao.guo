package net.yc384.service.weixin;


import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

@ProxyGen
public interface WeiXinApi {

    @Fluent
    WeiXinApi getAccessToken(Handler<AsyncResult<String>> resultHandler);

    static WeiXinApi create(Vertx vertx){
        return new WeiXinApiImpl(vertx);
    }

    static WeiXinApi createProxy(Vertx vertx, String address){
        return  new WeiXinApiVertxEBProxy(vertx, address);
    }
}
