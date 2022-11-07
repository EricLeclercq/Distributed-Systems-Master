package sd.akka.actor;

import akka.actor.AbstractActor;
import akka.actor.Props;

import sd.akka.message.HelloWorldMessage;

public class HelloWorldActor extends AbstractActor {

	private HelloWorldActor() {}

	// Méthode servant à déterminer le comportement de l'acteur lorsqu'il reçoit un message
	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(HelloWorldMessage.SayHello.class, message -> sayHello(message))
				.match(HelloWorldMessage.SayBye.class, message -> sayBye(message))
                .matchAny(message -> System.out.println(message))
				.build();
	}

	private void sayHello(final HelloWorldMessage.SayHello message) {
		System.out.println("Hello World to " + message.name);
	}

	private void sayBye(final HelloWorldMessage.SayBye message) {
		System.out.println("Bye World");
	}

	// Méthode servant à la création d'un acteur
	public static Props props() {
		return Props.create(HelloWorldActor.class);
	}
}
