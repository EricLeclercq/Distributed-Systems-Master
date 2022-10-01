package sd.akka.actor;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.actor.ActorRef;

public class PingPongActor extends AbstractActor {
	private int score;
	
	private PingPongActor() {
		this.score = 0;
	}
	
	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(GetScore.class, message -> sendScore(getSender()))
				.match(StartGame.class, message -> startGame(message.adversaire))
				.match(Ping.class, message -> continueGame(getSender(), new Pong()))
				.match(Pong.class, message -> continueGame(getSender(), new Ping()))
				.match(Miss.class, message -> endGame())
				.build();
	}
	
	public void sendScore(ActorRef actor) {
		actor.tell(this.score, this.getSelf());
	}
	
	public void continueGame(ActorRef adversaire, Message message) {
		/*
		 * A la réception d'une balle, le joueur peut la rater ou réussir à la renvoyer.
		 * S'il rate il prévient l'adversaire avec un message Miss, s'il réussit il renvoie la balle.
		 */
		if (Math.random() > 0.7) {
			System.out.println(this.getSelf().path().name() + " rate la balle !");
			adversaire.tell(new Miss(), this.getSelf());
		} else {
			System.out.println(this.getSelf().path().name() + " " + message.getClass().getSimpleName());
			adversaire.tell(message, this.getSelf());
		}
	}
	
	public void startGame(ActorRef adversaire) {
		/*
		 * Le joueur démarre le jeu et envoie la balle à son adversaire désisgné.
		 */
		System.out.println(this.getSelf().path().name() + " sert.");
		adversaire.tell(new Ping(), this.getSelf());
	}
	
	public void endGame() {
		/*
		 * Le jeu est terminé, le joueur gagne un point et prévient l'arbitre de la fin du jeu.
		 */
		score++;
		this.getContext().parent().tell(new ArbitreActor.EndGame(), getSelf());
	}
	
	public static Props props() {
		return Props.create(PingPongActor.class);
	}
	
	// Définition des messages en inner classes
	public interface Message {}
	
	public static class StartGame implements Message {
		public ActorRef adversaire;
		
		public StartGame(ActorRef adversaire) {
			this.adversaire = adversaire;
		}
	}
	
	public static class Ping implements Message {
		public Ping() {}
	}	
	
	public static class Pong implements Message {
		public Pong() {}
	}
	
	public static class Miss implements Message {
		public Miss() {}
	}
	
	public static class GetScore implements Message {
		public GetScore() {}
	}
}
