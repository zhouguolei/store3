package router;

import akka.actor.*;
import akka.routing.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zgl on 17-10-12.
 */
public class MasterActor extends UntypedActor {

    public Router router;
    private static int totalbytes;

    public Router getRouter() {
        return router;
    }

    public void setRouter(Router router) {
        this.router = router;
    }

    public static int getTotalbytes() {
        return totalbytes;
    }

    public static void setTotalbytes(int totalbytes) {
        MasterActor.totalbytes = totalbytes;
    }

    {
        ArrayList<Routee> routees = new ArrayList<Routee>();
        for(int i=0;i<5;i++){
            ActorRef worker = getContext().actorOf(Props.create(WorkerActor.class),"workerActor_"+i);
            getContext().watch(worker);
            routees.add(new ActorRefRoutee(worker));
        }
        router = new Router(new RandomRoutingLogic(),routees);
    }
    @Override
    public void onReceive(Object message) throws Throwable {
        if(message instanceof Integer) {
            totalbytes += (Integer)message;
        }else if (message instanceof List){
            List<String> list =(List)message;
            for(int i=0;i<list.size();i++) {
                router.route(list.get(i), getSender());
            }
        }else {
            unhandled(message);
        }

    }
}
