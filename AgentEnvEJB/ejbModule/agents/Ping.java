package agents;

import java.util.stream.Collectors;

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
import repository.AgentRepositoryBean;
import repository.AgentRepositoryBeanLocal;
import ws.SessionManager;
import ws.WSMessage;


@Stateful
@LocalBean
public class Ping extends Agent {
	
//	@EJB
	private AgentRepositoryBeanLocal agentRepo;
	
//	@EJB
	private MessageManagerBean messageManager;

//	@EJB
	private SessionManager sessionManager;
	
	public Ping() {}
	
	public Ping(AID aid) throws NamingException {
		super(aid);		
		
		Context ctx = new InitialContext();
		agentRepo = (AgentRepositoryBean) ctx.lookup("java:module/AgentRepositoryBean!repository.AgentRepositoryBean");
		messageManager =  (MessageManagerBean) ctx.lookup("java:module/MessageManagerBean!client2node.MessageManagerBean");
		sessionManager = (SessionManager) ctx.lookup("java:module/SessionManager!ws.SessionManager");
	}
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
		System.out.println("Ping started..");
		ACLMessage aclMessage = new ACLMessage();
		aclMessage.sender = id;
		aclMessage.recievers = agentRepo.getAllRunningAgents()
				.stream()
				.filter(aid -> aid.getAgentType().getName().contains(Pong.class.getSimpleName()))
				.collect(Collectors.toList());
		 aclMessage.performative = Performative.REQUEST;
		 aclMessage.content = "Ping from " + id;
		 messageManager.sendMessage(aclMessage);
	}

	@Override
	public void handleMessage(ACLMessage message) {
		switch(message.performative) {
		case CONFIRM: 
		{
			sessionManager.sendMessage(new WSMessage("NEW_MESSAGE", WSMessage.getNewMessage(message.sender, id, "successful ping!!", message.performative)));
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
