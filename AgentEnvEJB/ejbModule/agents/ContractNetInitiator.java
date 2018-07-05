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


@Stateful
@LocalBean
public class ContractNetInitiator extends Agent {
	
	@EJB
	private MessageManagerBean messageManager;
	
	@EJB
	private AgentRepositoryBeanLocal agentRepo;
	
	public ContractNetInitiator() {
		
	}
	
	public ContractNetInitiator(AID aid) throws NamingException {
		super(aid);
	
		Context ctx = new InitialContext();
		messageManager =  (MessageManagerBean) ctx.lookup("java:module/MessageManagerBean!client2node.MessageManagerBean");
		agentRepo = (AgentRepositoryBean) ctx.lookup("java:module/AgentRepositoryBean!repository.AgentRepositoryBean");
	}

	@Override
	public void start() {
		ACLMessage aclMessage = new ACLMessage();
		aclMessage.sender = id;
		agentRepo.getAllRunningAgents()
			.stream()
			.filter(aid -> aid.getAgentType().getName().contains(ContractNetParticipant.class.getSimpleName()))
			.collect(Collectors.toList());
		aclMessage.performative = Performative.CALL_FOR_PROPOSAL;
		aclMessage.content = "Agent " + id + " called for proposal";
		messageManager.sendMessage(aclMessage);
	}

	@Override
	public void handleMessage(ACLMessage message) {
		switch(message.performative) {
		case PROPOSE: {
			
		}
		}
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

    

}
