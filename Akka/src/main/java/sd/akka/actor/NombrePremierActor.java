package sd.akka.actor;

import akka.actor.AbstractActor;
import akka.actor.Props;

public class NombrePremierActor extends AbstractActor {

	private NombrePremierActor() {}

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(Nombre.class, message -> verifieNombrePremier(message.nombre))
				.build();
	}

	private void verifieNombrePremier(final int nombre) {
		for (int i = 2; i <= Math.sqrt(nombre) + 1; i++) {
			if (nombre % i == 0) {
				System.out.println(this.getSelf().path().name() + " : " + nombre + " n'est pas premier.");
				return;
			}
		}
		System.out.println(this.getSelf().path().name() + " : " + nombre + " est premier.");
	}

	public static Props props() {
		return Props.create(NombrePremierActor.class);
	}

	// Définition des messages en inner classes
	public interface Message {}	
	
	public static class Nombre implements Message {
		public final int nombre;

		public Nombre(int nombre) {
			this.nombre = nombre;
		}
	}
}
