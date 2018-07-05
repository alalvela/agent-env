package agents;

import java.util.ArrayList;
import java.util.List;

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


@Stateful
@LocalBean
public class ContractNetParticipant extends Agent {

	private MessageManagerBean messageManager;
	
	public ContractNetParticipant() {
	
	}

	public ContractNetParticipant(AID aid) throws NamingException {
		super(aid);
		
		Context ctx = new InitialContext();
		messageManager =  (MessageManagerBean) ctx.lookup("java:module/MessageManagerBean!client2node.MessageManagerBean");
	}
	
	@Override
	public void start() {
		
		
	}

	@Override
	public void handleMessage(ACLMessage message) {
		switch(message.performative) {
		case CALL_FOR_PROPOSAL:
		{
			ACLMessage aclMessage = new ACLMessage();
			aclMessage.sender = id;
			List<AID> recs = new ArrayList<AID>();
			recs.add(message.sender);
			aclMessage.performative = Performative.PROPOSE;
			if(Math.random() < 0.5) {
				aclMessage.content = "Agent " + id + " is bidding";
			} else {
				aclMessage.content = "Agent " + id + " is not bidding";			}
		}
		default : System.out.println("Unknown performative for " + id);
		}
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
