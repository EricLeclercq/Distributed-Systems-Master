package sd.akka.actor;

import akka.actor.AbstractActor;
import akka.actor.Props;

public class JourNuitActor extends AbstractActor {
	private int heure;
	
	private JourNuitActor() {
		this.heure = 12;
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(Increment.class, message -> incrementeJour())
				.build();
	}

	/*
	 * Méthode alternative qui peut être utilisée lors de la réception d'un message.
	 */
	public Receive createReceiveNuit() {
		return receiveBuilder()
				.match(Increment.class, message -> incrementeNuit())
				.build();
	}
	
	private void incrementeJour() {
		heure++;
		System.out.println("Il est " + heure + "h, c'est la journée.");
		if (heure == 20) {
			// Changement du comportement de l'acteur
			getContext().become(createReceiveNuit());
		}
	}
	
	private void incrementeNuit() {
		heure = (heure + 1) % 24;
		System.out.println("Il est " + heure + "h, c'est la nuit.");
		if (heure == 7) {
			// Changement du comportement de l'acteur
			getContext().become(createReceive());
		}
	}

	public static Props props() {
		return Props.create(JourNuitActor.class);
	}

	// Définition des messages en inner classes
	public interface Message {}
	
	public static class Increment implements Message {
		public Increment() {}
	}	
}
