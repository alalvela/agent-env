package ws;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;


@ServerEndpoint("/wsep")
@Singleton
public class WSEndpoint {
	
	@EJB
	private SessionManager sessionManager;
	
	@OnOpen
	public void open(Session session) {
		System.out.println("OPENED WS SESSION");
		sessionManager.addSession(session);
	}
	
	@OnClose
	public void close(Session session) {
		sessionManager.removeSession(session);
	}
	
	@OnError
	public void onError(Session session, Throwable t) {
		
	}
	
	@OnMessage
	public void handleMessage(Session session, String msg, boolean last) throws IOException {
		WSMessage message = new Gson().fromJson(msg, WSMessage.class);
		System.out.println("recieved ws message: " + message.toJson());
		
		
	}
	
	private void proccessMessage(WSMessage wsMsg) {
		switch(wsMsg.getAction()) {
			case "" : 
		}
	}

}
