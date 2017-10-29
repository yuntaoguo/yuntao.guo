package net.yc384.service.weixin;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class WeiXinApiImpl implements WeiXinApi {

    private WebClient client;
    private Vertx vertx;
    private static final Logger LOGGER = LoggerFactory.getLogger(WeiXinApiImpl.class);

    public WeiXinApiImpl(Vertx vertx) {
        this.vertx = vertx;
        client = WebClient.create(vertx);
    }

    @Override
    public WeiXinApi getAccessToken(Handler<AsyncResult<String>> resultHandler){

        client.get(443, "api.weixin.qq.com", "/cgi-bin/token?grant_type=client_credential&appid=wxc26a97e5ad3aa6a3&secret=d27a5c9d5806b17f038412096c266eec")
                .ssl(true)
                .send(ar -> {
                    if (ar.succeeded()) {
                        // Obtain response
                        HttpResponse<Buffer> response = ar.result();
                       //response.body();
                        resultHandler.handle(Future.succeededFuture(response.body().toString()));
                    } else {
                        System.out.println("Something went wrong " + ar.cause().getMessage());
                    }
                });

        return  this;
    }
}
