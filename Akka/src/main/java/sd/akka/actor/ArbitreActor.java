package sd.akka.actor;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.actor.ActorRef;
import akka.pattern.Patterns;

import java.time.Duration;
import java.util.concurrent.CompletionStage;

public class ArbitreActor extends AbstractActor {
	private ActorRef joueur1;
	private ActorRef joueur2;
	
	private ActorRef respondTo;

	private ArbitreActor() {
		// Création d'acteurs enfants
		this.joueur1 = getContext().actorOf(PingPongActor.props(), "joueur1");
		this.joueur2 = getContext().actorOf(PingPongActor.props(), "joueur2");
		this.respondTo = null;
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(GetScore.class, message -> getScore())
				.match(StartGame.class, message -> startGame(getSender()))
				.match(EndGame.class, message -> endGame())
				.build();
	}

	public void endGame() {
		/*
		 * Le jeu est terminé, l'arbitre le signale à celui qui a demandé à démarrer le jeu.
		 */
		respondTo.tell("Fini", getSelf());
		respondTo = null;
	}
	
	public void startGame(ActorRef respondTo) {
		/*
		 * Le jeu commence, l'arbitre choisit quel joueur engage.
		 */
		this.respondTo = respondTo;
		if (Math.random() > 0.5) {
			joueur1.forward(new PingPongActor.StartGame(joueur2), getContext());
		} else {
			joueur2.forward(new PingPongActor.StartGame(joueur1), getContext());
		}
	}
	
	public void getScore() {
		/*
		 * L'arbitre récupère le score de chaque joueur.
		 */
		CompletionStage<Object> result1 = Patterns.ask(joueur1, new PingPongActor.GetScore(), Duration.ofSeconds(10));
		CompletionStage<Object> result2 = Patterns.ask(joueur2, new PingPongActor.GetScore(), Duration.ofSeconds(10));
		int scoreJoueur1 = 0;
		int scoreJoueur2 = 0;
		try {
			scoreJoueur1 = (int) result1.toCompletableFuture().get();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			scoreJoueur2 = (int) result2.toCompletableFuture().get();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Joueur 1 : " + scoreJoueur1 + " - Joueur 2 : " + scoreJoueur2);
	}

	public static Props props() {
		return Props.create(ArbitreActor.class);
	}

	// Définition des messages en inner classes
	public interface Message {}

	public static class GetScore implements Message {
		public GetScore() {}
	}
	
	public static class StartGame implements Message {
		public StartGame() {}
	}
	
	public static class EndGame implements Message {
		public EndGame() {}
	}
}
