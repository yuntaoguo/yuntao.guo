package net.yc384.web.http;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.User;
import io.vertx.ext.auth.jdbc.JDBCAuth;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.web.handler.StaticHandler;
import net.yc384.service.weixin.WeiXinApi;
import net.yc384.web.database.JDBCCONF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.auth.AuthProvider;

/**
 * @author Daivd Chin
 */
public class HttpServerVerticle extends AbstractVerticle {
    public static final String CONFIG_HTTP_SERVER_PORT = "http.server.port";

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpServerVerticle.class);



    @Override
    public void start(Future<Void> startFuture) throws Exception {
        HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx);
        router.get("/api/").handler(this::indexHandler);
        router.get("/api/login/").handler(this::loginHandler);
        router.get("/api/wx/").handler(this::wxHandler);

        JDBCClient client = JDBCClient.createShared(vertx, new JsonObject()
                .put("url","jdbc:mysql://localhost:3306/yc384_web")
                .put("driver_class","com.mysql.cj.jdbc.Driver")
                .put("user","root")
                .put("password","123456")
                .put("max_pool_size",30)
        );

        JDBCAuth authProvider = JDBCAuth.create(vertx, client);



        int portNumber = config().getInteger(CONFIG_HTTP_SERVER_PORT, 8080);
        server.requestHandler(router::accept)
                .listen(portNumber, ar -> {
                    if (ar.succeeded()) {
                        LOGGER.info("HTTP server running on port " + portNumber);
                        startFuture.complete();
                    } else {
                        LOGGER.error("Could not start a HTTP server", ar.cause());
                        startFuture.fail(ar.cause());
                    }
                });
    }

    private void wxHandler(RoutingContext context) {
        /*
        EventBus eventBus = vertx.eventBus();
        DeliveryOptions options = new DeliveryOptions();
        options.addHeader("action", "getAccessToken");
        eventBus.send("weixin.api", "Yay! Someone kicked a ball across a patch of grass",options, ar -> {
            if (ar.succeeded()) {
                context.response().setStatusCode(200);
                context.response().putHeader("Content-type","text/json");

                context.response().end(ar.result().body().toString());
            }
        });
*/
        WeiXinApi weiXinApi = WeiXinApi.createProxy(vertx,"api.weixin");
        weiXinApi.getAccessToken(ar -> {
            if (ar.succeeded()) {
                context.response().setStatusCode(200);
                context.response().putHeader("Content-type","text/json");

                context.response().end(ar.result().toString());
            }
        });
    }

    private void loginHandler(RoutingContext context) {
        context.response().setStatusCode(200);
        context.response().putHeader("Content-type","text/json");

        context.response().end(new JsonObject().put("res","ok!").encode());
    }


    private void indexHandler(RoutingContext context){

        context.response().putHeader("Content-type","text/html;charset=UTF-8");
        context.response().end("你好~!");
    }


}