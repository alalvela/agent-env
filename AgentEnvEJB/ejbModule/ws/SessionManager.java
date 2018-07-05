package ws;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.websocket.Session;


@Singleton
@LocalBean
public class SessionManager {

	private List<Session> sessions;
	
	
	@PostConstruct 
	private void init() {
		sessions = new ArrayList<>();
	}
	
	@Lock(LockType.WRITE)
	public void addSession(Session session) {
		sessions.add(session);
	}

	@Lock(LockType.WRITE)
	public void removeSession(Session session) {
		sessions.remove(session);
	}
	
	public void sendMessage(WSMessage message) {
		sessions.forEach(s -> {
			try {
				s.getBasicRemote().sendText(message.toJson());
				System.out.println("sent message : " + message.toJson() + "to " + s.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
	

}
