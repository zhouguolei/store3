package router;

import akka.actor.UntypedActor;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
/**
 * Created by zgl on 17-10-11.
 */
public class WorkerActor extends UntypedActor {

    @Override
    public void onReceive(Object message) throws Throwable {
        Integer pagelength = 0;
        HttpGet httpGet;
        CloseableHttpResponse response = null;
        CloseableHttpClient client = HttpClients.createDefault();
        String url = message.toString();
            httpGet = new HttpGet(url);
            String body = "";
            try {
                response = client.execute(httpGet);
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    body = EntityUtils.toString(entity, "utf-8");
                }
                EntityUtils.consume(entity);
                response.close();
                pagelength = body.getBytes().length;
            } catch (Exception e) {
                e.printStackTrace();
            }

        context().actorSelection("akka://test/user/masteractor").tell(pagelength,getSelf());
    }
}
