package router;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zgl on 17-10-12.
 */
public class Main {
    public static void main(String[] args) {
        List<String> urls = Arrays.asList("https://www.baidu.com", "https://www.taobao.com",
                "http://www.csdn.com", "http://www.ctrip.com", "https://www.hao123.com");
        ActorSystem system = ActorSystem.create("test");
        ActorRef master = system.actorOf(Props.create(MasterActor.class),"masteractor");
        master.tell(urls,ActorRef.noSender());
        System.out.println("正在计算,请稍后...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("一共"+urls.size()+"网页,共计"+MasterActor.getTotalbytes());


    }
}
