package sd.akka.actor;

import akka.actor.AbstractActor;
import akka.actor.Props;

public class HelloWorldActor extends AbstractActor {

	private HelloWorldActor() {}

	// Méthode servant à déterminer le comportement de l'acteur lorsqu'il reçoit un message
	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(SayHello.class, message -> sayHello(message))
				.match(SayBye.class, message -> sayBye(message))
				.build();
	}

	private void sayHello(final SayHello message) {
		System.out.println("Hello World to " + message.name);
	}

	private void sayBye(final SayBye message) {
		System.out.println("Bye World");
	}

	// Méthode servant à la création d'un acteur
	public static Props props() {
		return Props.create(HelloWorldActor.class);
	}

	// Définition des messages en inner classes
	public interface Message {}
	
	public static class SayBye implements Message {
		public SayBye() {}
	}	
	
	public static class SayHello implements Message {
		public final String name;

		public SayHello(String name) {
			this.name = name;
		}
	}
}
