package net.yc384.web;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLConnection;
import net.yc384.web.wx.WeiXinVerticle;


public class MainVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        Future<String> wxVerticleDeployment = Future.future();
        vertx.deployVerticle(new WeiXinVerticle(),res->{
            wxVerticleDeployment.complete();
        });
        wxVerticleDeployment.compose(id->{
            Future<String> httpVerticleDeployment = Future.future();
            vertx.deployVerticle(
                    "net.yc384.web.http.HttpServerVerticle",
                    new DeploymentOptions().setInstances(2),
                    httpVerticleDeployment.completer());
           return httpVerticleDeployment;

        }).setHandler(ar->{
            if (ar.succeeded()) {
                startFuture.complete();
            } else {
                startFuture.fail(ar.cause());
            }
        });






    }
}