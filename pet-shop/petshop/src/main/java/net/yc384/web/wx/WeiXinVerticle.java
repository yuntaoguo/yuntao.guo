package net.yc384.web.wx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.serviceproxy.ServiceBinder;
import net.yc384.service.weixin.WeiXinApi;
import net.yc384.web.http.HttpServerVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class WeiXinVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpServerVerticle.class);
    @Override
    public void start(Future<Void> startFuture) throws Exception {

        /*
        WebClient client = WebClient.create(vertx);
        EventBus eb = vertx.eventBus();
        MessageConsumer<String> consumer = eb.consumer("weixin.api");
        MessageConsumer<String> consumer2 = eb.consumer("alipay.api");
        MessageConsumer<String> consumer3 = eb.consumer("weibo.api");
        consumer.handler(message -> {
           String action = message.headers().get("action");

           if(action.equals("getAccessToken")){
               client.get(443, "api.weixin.qq.com", "/cgi-bin/token?grant_type=client_credential&appid=wxc26a97e5ad3aa6a3&secret=d27a5c9d5806b17f038412096c266eec")
                       .ssl(true)
                       .send(ar -> {
                           if (ar.succeeded()) {
                               // Obtain response
                               HttpResponse<Buffer> response = ar.result();
                               message.reply(response.body());

                           } else {
                               System.out.println("Something went wrong " + ar.cause().getMessage());
                           }
                       });
           }
        });
        */
        ServiceBinder binder = new ServiceBinder(vertx);
        binder.setAddress("api.weixin")
                .register(WeiXinApi.class, WeiXinApi.create(vertx));
        super.start(startFuture);
    }


}
