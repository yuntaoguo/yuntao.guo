package net.yc384.web.auth;


import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.auth.User;

public class YcAuthProvider implements AuthProvider {

    private Vertx vertx;

    public YcAuthProvider(Vertx vertx){
        this.vertx=vertx;
    }

    @Override
    public void authenticate(JsonObject authInfo, Handler<AsyncResult<User>> resultHandler) {
       vertx.executeBlocking(fuc->{
           User admin=new AdminUser();
           admin.setAuthProvider(this);
           fuc.complete(admin);
       },resultHandler);
    }
}
