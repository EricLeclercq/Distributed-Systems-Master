package sd.akka;

import java.time.Duration;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import akka.routing.RoundRobinPool;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import sd.akka.actor.HelloWorldActor;

public class App {
	public static void main(String[] args) {
		ActorSystem actorSystem = ActorSystem.create("myActorSystem");
		ActorRef helloActor = actorSystem.actorOf(HelloWorldActor.props(), "helloActor");		
        // En attente de Ctrl-C        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            actorSystem.terminate();
            System.out.println("System terminated.");
        }));
	}
}
