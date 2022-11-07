package sd.akka;

import java.time.Duration;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import akka.routing.RoundRobinPool;
import com.typesafe.config.ConfigFactory;

import sd.akka.message.HelloWorldMessage;

public class App {
	public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("Client", ConfigFactory.load("client.conf"));
        ActorSelection selection =
  actorSystem.actorSelection("akka://myActorSystem@127.0.0.1:8000/user/helloActor");
        selection.tell(new HelloWorldMessage.SayHello("Akka Remote"), ActorRef.noSender());
        // En attente de Ctrl-C        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            actorSystem.terminate();
            System.out.println("System terminated.");
        }));
	}
}
