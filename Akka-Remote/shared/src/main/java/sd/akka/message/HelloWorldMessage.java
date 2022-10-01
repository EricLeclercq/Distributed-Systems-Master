package sd.akka.message;

import java.io.Serializable;

public class HelloWorldMessage {
	public interface Message extends Serializable {}
	
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
