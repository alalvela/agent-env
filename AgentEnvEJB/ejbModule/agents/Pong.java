package agents;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import client2node.MessageManagerBean;
import domain.ACLMessage;
import domain.AID;
import domain.Agent;
import domain.Performative;
import ws.SessionManager;
import ws.WSMessage;


@Stateful
@LocalBean
public class Pong extends Agent {
	
	@EJB
	private MessageManagerBean messageManager;
	
	@EJB
	private SessionManager sessionManager;
	
	public Pong() {}
	
	public Pong(AID aid) throws NamingException {
		super(aid);
		
		Context ctx = new InitialContext();
		messageManager =  (MessageManagerBean) ctx.lookup("java:module/MessageManagerBean!client2node.MessageManagerBean");
		sessionManager = (SessionManager) ctx.lookup("java:module/SessionManager!ws.SessionManager");
	}
	

	@Override
	public void start() {
		System.out.println("Pong " + id + "started..");
		
	}

	@Override
	public void handleMessage(ACLMessage message) {
		switch(message.performative) {
		case REQUEST:
		{
			ACLMessage aclMessage = new ACLMessage();
			aclMessage.sender = id;
			List<AID> recs = new ArrayList<AID>();
			recs.add(message.sender);
			aclMessage.recievers = recs;
			aclMessage.performative = Performative.CONFIRM;
			aclMessage.content = "Pong from " + id;
			messageManager.sendMessage(aclMessage);
		}
		default:
//			sessionManager.sendMessage(new WSMessage("NEW_MESSAGE", WSMessage.getNewMessage(id, null, "Message not understood :(", null)));
		}
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

    

}
