package node2agent;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import domain.ACLMessage;
import domain.AID;
import domain.Agent;
import repository.AgentRepositoryBeanLocal;
import ws.SessionManager;
import ws.WSMessage;


@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destination", propertyValue = "queue/TestQueue"), @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Queue")
		})
public class MDBConsumer implements MessageListener {
	
	@EJB
	private AgentRepositoryBeanLocal agentRepo;
	
	@EJB
	private SessionManager sessionManager;

	@Override
	public void onMessage(Message message) {		
		try {
			proccessMessage(message);
		} catch (JMSException e1) {
			e1.printStackTrace();
		}
	}

	private void proccessMessage(Message message) throws JMSException {
		ObjectMessage objMsg = (ObjectMessage) message;
		
		ACLMessage acl = (ACLMessage) objMsg.getObject();
		int ordinal = objMsg.getIntProperty("ordinal");
		
		handleMessage(acl, ordinal);
	}

	private void handleMessage(ACLMessage acl, int ordinal) {
		AID aid = acl.recievers.get(ordinal);
		Agent agent = agentRepo.getAgent(aid);
		if (agent != null) {
			agent.handleMessage(acl);
			sessionManager.sendMessage(new WSMessage("NEW_MESSAGE", WSMessage.getNewMessage(acl.sender, aid, acl.content, acl.performative)));
		} else {
			System.out.println("Agent with aid : " + aid + " does not exist");
		}
	}

    

}
